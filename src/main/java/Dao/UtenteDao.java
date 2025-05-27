package Dao;

import Entities.Utente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UtenteDao {

    private EntityManager em;

    public UtenteDao(EntityManager em) {
        this.em = em;
    }

    public void salvaUtente(Utente u) {
        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public  Utente cercaUtentePerId(int id) {
        return em.find(Utente.class, id);
    }

    public Utente cercaUtentePerTessera(int codiceIdentificativo) {
        return em.find(Utente.class, codiceIdentificativo);
    }
    public List<Utente> trovaUtenti() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }

    public void eliminaUtente(int id) {
        Utente u = cercaUtentePerId(id);
        if (u != null) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        }
    }
}
