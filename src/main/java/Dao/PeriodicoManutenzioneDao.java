package Dao;

import Entities.Mezzi;
import Entities.PeriodicoManutenzione;
import jakarta.persistence.EntityManager;

public class PeriodicoManutenzioneDao {
    private EntityManager em;

    public PeriodicoManutenzioneDao(EntityManager em) {
        this.em = em;
    }

    public void save(PeriodicoManutenzione p){
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore nel salvataggio: " + e.getMessage());
        }

    }

    public PeriodicoManutenzione getById(Long id){
        return em.find(PeriodicoManutenzione.class, id);
    }

    public void remove(Long id){
        PeriodicoManutenzione p = getById(id);

        if (p != null) {
            try {
                em.getTransaction().begin();
                em.remove(p);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.out.println("Errore nella rimozione: " + e.getMessage());
            }
        } else {
            System.out.println("Lo stato in servizio o in movimento " + id + " non esiste");
        }

    }
}
