package Dao;
import Entities.*;
import Enumeration.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TitoloDiViaggioDao {

    private EntityManager em;

    public TitoloDiViaggioDao(EntityManager em) {
        this.em = em;
    }

    // Salva abbonamento o biglietto
    public void save(TitoloDiViaggio titolo) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(titolo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    public long countTitoliEmessiInData(LocalDate data) {
        return em.createQuery(
                "SELECT COUNT(t) FROM TitoloDiViaggio t WHERE t.dataEmissione = :data",
                Long.class
        ).setParameter("data", data).getSingleResult();
    }

    // Trova per ID un abbonamento o un biglietto
    public TitoloDiViaggio findById(Long id) {
        return em.find(TitoloDiViaggio.class, id);
    }

    // Rimuove un titolo di viaggio
    public void delete(Long id) {
        TitoloDiViaggio titolo = findById(id);
        if (titolo != null) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.remove(titolo);
                tx.commit();
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                e.printStackTrace();
            }
        }
    }

    public Abbonamento findAbbonamentoAttivoByTipo(Utente utente, TipoAbbonamento tipo) {
        List<Abbonamento> risultati = em.createQuery(
                        "SELECT a FROM Abbonamento a WHERE a.tessera.utente = :utente AND a.tipoAbbonamento = :tipo AND a.dataScadenza >= :oggi",
                        Abbonamento.class
                ).setParameter("utente", utente)
                .setParameter("tipo", tipo)
                .setParameter("oggi", LocalDate.now())
                .getResultList();

        return risultati.isEmpty() ? null : risultati.get(0);
    }


    public Abbonamento findAbbonamentoAttivoByUtente(Utente utente) {
        List<Abbonamento> risultati = em.createQuery(
                        "SELECT a FROM Abbonamento a WHERE a.tessera.utente = :utente AND a.dataScadenza >= :oggi",
                        Abbonamento.class
                ).setParameter("utente", utente)
                .setParameter("oggi", LocalDate.now())
                .getResultList();

        return risultati.isEmpty() ? null : risultati.get(0); // Prendi il più recente
    }



    // Recupera tutti i biglietti
    public List<Biglietto> findAllBiglietti() {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
        return query.getResultList();
    }

    // Recupera tutti gli abbonamenti
    public List<Abbonamento> findAllAbbonamenti() {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a", Abbonamento.class);
        return query.getResultList();
    }
    public List<String> riepilogoTitoliPerRivenditoreETipo(LocalDate data) {
        String jpql = """
        SELECT t.rivenditore, TYPE(t), COUNT(t)
        FROM TitoloDiViaggio t
        WHERE DATE(t.dataEmissione) = :data
        GROUP BY t.rivenditore, TYPE(t)
        """;

        List<Object[]> risultati = em.createQuery(jpql, Object[].class)
                .setParameter("data", data)
                .getResultList();

        List<String> riepilogo = new ArrayList<>();

        for (Object[] riga : risultati) {
            Rivenditore rivenditore = (Rivenditore) riga[0];
            Class<?> tipoTitolo = (Class<?>) riga[1];
            long count = (long) riga[2];

            String tipoTitoloStr = tipoTitolo.getSimpleName(); // Biglietto o Abbonamento
            String descrizione;

            if (rivenditore instanceof PuntoVendita pv) {
                descrizione = "Punto Vendita - " + pv.getNome() + ": " + count + " " + tipoTitoloStr;
            } else if (rivenditore instanceof Macchinetta) {
                descrizione = "Macchinetta: " + count + " " + tipoTitoloStr;
            } else {
                descrizione = "Altro Rivenditore: " + count + " " + tipoTitoloStr;
            }

            riepilogo.add(descrizione);
        }

        return riepilogo;
    }



    //------Metodo per aggiornare un oggetto gia esistente

    public void update(TitoloDiViaggio titolo) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(titolo); // aggiorna o "attacca" l'entità
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }


}

