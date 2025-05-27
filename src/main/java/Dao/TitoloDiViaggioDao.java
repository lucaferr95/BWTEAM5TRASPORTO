package Dao;
import Entities.TitoloDiViaggio;
import Entities.Biglietto;
import Entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
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
}

