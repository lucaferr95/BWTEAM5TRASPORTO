package Dao;

import Entities.Mezzi;
import jakarta.persistence.EntityManager;

import java.util.List;

public class MezziDao {
    private EntityManager em;

    public MezziDao(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzi m){
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore nel salvataggio: " + e.getMessage());
        }

    }
    public List<Mezzi> getMezziByTrattaId(Long trattaId) {
        return em.createQuery(
                "SELECT m FROM Mezzi m WHERE m.tratta.id = :trattaId",
                Mezzi.class
        ).setParameter("trattaId", trattaId).getResultList();
    }


    public Mezzi getById(Long id){
        return em.find(Mezzi.class, id);
    }

    public void remove(Long id){
        Mezzi m = getById(id);

        if (m != null) {
            try {
                em.getTransaction().begin();
                em.remove(m);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.out.println("Errore nella rimozione: " + e.getMessage());
            }
        } else {
            System.out.println("Il mezzo con id " + id + " non esiste");
        }

    }
}
