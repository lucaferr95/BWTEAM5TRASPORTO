package Dao;

import Entities.Utente;
import Exceptions.UsernameEsistenteException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UtenteDao {

    private EntityManager em;

    public UtenteDao(EntityManager em) {
        this.em = em;
    }

    public void salvaUtente(Utente u) {
        try {
            // Controllo username duplicato prima di iniziare la transazione
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(u) FROM Utente u WHERE u.username = :username", Long.class);
            query.setParameter("username", u.getUsername());
            Long count = query.getSingleResult();

            if (count != null && count > 0) {
                throw new UsernameEsistenteException("Username '" + u.getUsername() + "' già registrato.");
            }

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

    public Utente trovaUtenteConUsername(String username) {
        try {
            TypedQuery<Utente> query = em.createQuery(
                    "SELECT u FROM Utente u WHERE u.username = :username", Utente.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {

            return null;
        }
    }
}
