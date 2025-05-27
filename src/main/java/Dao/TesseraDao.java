package Dao;

import Entities.Tessera;
import Entities.Utente;
import jakarta.persistence.EntityManager;

import java.util.List;


    public class TesseraDao {

        private EntityManager em;

        public TesseraDao(EntityManager em) {
            this.em = em;
        }

        public void salvaTessera(Tessera t) {
            try {
                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw e;
            }
        }

        public  Tessera cercaTessera (int codiceIdentificativo) {
            return em.find(Tessera.class, codiceIdentificativo);
        }

        public Tessera cercaTesseraPerUtente(int idUtente) {
            return em.createQuery(
                            "SELECT t FROM Tessera t WHERE t.utente.id = :idUtente", Tessera.class)
                    .setParameter("idUtente", idUtente)
                    .getSingleResult();
        }

        public List<Tessera> trovaTessere() {
            return em.createQuery("SELECT t FROM Tessera t", Tessera.class).getResultList();
        }

        public void eliminaTessera(int id) {
            Tessera t = cercaTessera(id);
            if (t != null) {
                em.getTransaction().begin();
                em.remove(t);
                em.getTransaction().commit();
            }
        }
    }


