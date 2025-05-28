package Dao;

import Entities.Mezzi;
import Entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

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

}
