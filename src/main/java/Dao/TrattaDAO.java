package Dao;

import Entities.Mezzi;
import Entities.Tratta;
import Entities.Utente;
import jakarta.persistence.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class TrattaDAO {

    private EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta tratta) {
        em.getTransaction().begin();
        em.persist(tratta);
        em.getTransaction().commit();
    }
    public Tratta cercaTrattaById(int id) {
        return em.find(Tratta.class, id);
    }

    public void deleteTrattaById(int id){
        Tratta tratta = em.find(Tratta.class,id);


        if (tratta!=null){
            em.getTransaction().begin();
            em.remove(tratta);
            em.getTransaction().commit();
            System.out.println("La tratta selezionata n°: "+ id +  "è stata eliminata");
        }else {
            System.out.println("La tratta selezionata n°: "+ id + "non è stata trovata");
        }
    }

    public List<Mezzi> cercaPerTratta(String tratta){
        TypedQuery<Mezzi> query = em.createQuery(
                "SELECT l FROM Mezzi l WHERE l.tratta = :tratta", Mezzi.class);
        query.setParameter("tratta", tratta);
        return query.getResultList();
    }
    public Double calcolaTempoMedioEffettivo(Long mezzoId, Long trattaId) {
        return em.createQuery(
                        "SELECT AVG(p.durataEffettiva) FROM Percorrenza p WHERE p.mezzo.id = :mezzoId AND p.tratta.id = :trattaId",
                        Double.class
                )
                .setParameter("mezzoId", mezzoId)
                .setParameter("trattaId", trattaId)
                .getSingleResult();
    }

    public double calcolaTempoMedioTratta(String nomeTratta) {
        TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM tratta t WHERE t.tratta = :tratta", Tratta.class);
        query.setParameter("tratta", nomeTratta);

        List<Tratta> tratte = query.getResultList();

        if (tratte.isEmpty()) {
            System.out.println("Nessuna tratta trovata con nome: " + nomeTratta);
            return 0;
        }

        double media = tratte.stream()
                .filter(t -> t.getTempoEffettivo() != null && t.getTempoPrevisto() != null)
                .mapToLong(t -> ChronoUnit.HOURS.between(t.getTempoPrevisto(), t.getTempoEffettivo()))
                .average()
                .orElse(0);
        return media;
    }
    public List<Tratta> listaTratte(){
        TypedQuery<Tratta> query = em.createQuery("SELECT t FROM Tratta t", Tratta.class);
        return query.getResultList();
    }
    public void aggiornaMezzoTratta(int trattaId, Mezzi mezzo) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tratta tratta = em.find(Tratta.class, trattaId);
            if (tratta != null) {
                tratta.setMezzo(mezzo);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Mezzi> getMezziByTrattaId(Long trattaId) {
        return em.createQuery(
                "SELECT m FROM Mezzi m WHERE m.tratta.id = :trattaId",
                Mezzi.class
        ).setParameter("trattaId", trattaId).getResultList();
    }


}
