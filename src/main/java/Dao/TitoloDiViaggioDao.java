package Dao;
import Entities.TitoloDiViaggio;
import Entities.Biglietto;
import Entities.Abbonamento;
import Entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
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

//    //Cerca abbonamento attivo per utente
//
//    public Abbonamento findAbbonamentoAttivoByUtente(Utente utente) {
//        try {
//            TypedQuery<Abbonamento> query = em.createQuery(
//                    "SELECT a FROM Abbonamento a WHERE a.tessera.utente = :utente",
//                    Abbonamento.class
//            );
//            query.setParameter("utente", utente);
//            return query.getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }


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
    public List<Object[]> countTitoliPerTipoRivenditore(LocalDate data) {
        return em.createQuery(
                        "SELECT TYPE(t.rivenditore), COUNT(t) " +
                                "FROM TitoloDiViaggio t " +
                                "WHERE t.dataEmissione = :data " +
                                "GROUP BY TYPE(t.rivenditore)",
                        Object[].class
                )
                .setParameter("data", data)
                .getResultList();
    }


}

