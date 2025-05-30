package Dao;

import Entities.Biglietto;
import Entities.PuntoVendita;
import Entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

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

    /* ------------------- metodi ------------------- */

    // lista di rivenditori
    public List<Rivenditore> getAll() {
        TypedQuery<Rivenditore> query = em.createNamedQuery("Rivenditore.findAll", Rivenditore.class);
        return query.getResultList();
    }

    // quanti biglietti per periodo
    public long countBigliettiEmessiPerPeriodo(int rivenditoreId, LocalDateTime start, LocalDateTime end) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(b) FROM Biglietto b WHERE b.rivenditore.id = :id AND b.dataEmissione BETWEEN :start AND :end",
                Long.class
        );
        query.setParameter("id", rivenditoreId);
        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getSingleResult();
    }

    // totale biglietti emessi

    public List<Biglietto> getBigliettiByRivenditore(int rivenditoreId) {
        TypedQuery<Biglietto> query = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.rivenditore.id = :id", Biglietto.class
        );
        query.setParameter("id", rivenditoreId);
        return query.getResultList();
    }
    public Rivenditore getRivenditoreByTipo(Class<? extends Rivenditore> tipo) {
        TypedQuery<Rivenditore> query = em.createQuery(
                "SELECT r FROM Rivenditore r WHERE TYPE(r) = :tipo", Rivenditore.class
        );
        query.setParameter("tipo", tipo);

        List<Rivenditore> result = query.getResultList();
        if (!result.isEmpty()) {
            return result.get(0); // restituisce il primo trovato
        } else {
            throw new RuntimeException("Nessun rivenditore trovato per tipo: " + tipo.getSimpleName());
        }
    }
    public List<PuntoVendita> getTuttiIPuntiVendita() {
        return em.createQuery("SELECT p FROM PuntoVendita p", PuntoVendita.class).getResultList();
    }

}

