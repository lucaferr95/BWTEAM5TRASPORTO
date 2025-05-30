package Dao;

import Entities.Mezzi;
import Entities.Tratta;
import Enumeration.TipoPeriodicoManutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

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
    public void aggiornaStatoMezzo(Long mezzoId, TipoPeriodicoManutenzione nuovoStato) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Mezzi mezzo = em.find(Mezzi.class, mezzoId);
            if (mezzo != null) {
                mezzo.setStatoAttuale(nuovoStato);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    public List<Mezzi> listaMezzi(){
        TypedQuery<Mezzi> query = em.createQuery("SELECT m FROM Mezzi m", Mezzi.class);
        return query.getResultList();
    }
}
