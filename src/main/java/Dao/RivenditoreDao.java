package Dao;

import Entities.Rivenditore;
import jakarta.persistence.EntityManager;

public class RivenditoreDao {
    private EntityManager em;
    public RivenditoreDao(EntityManager em) {
        this.em = em;
    }

    public void save(Rivenditore p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public Rivenditore getById(int id){
        return em.find(Rivenditore.class, id);
    }

    public void remove(int id) {
        Rivenditore r = getById(id);

        if(r!=null){
            em.getTransaction().begin();
            em.remove(r);
            em.getTransaction().commit();
        } else {
            System.out.println("Il rivenditore" + r + " non è presente nel database");
        }
    }

}
