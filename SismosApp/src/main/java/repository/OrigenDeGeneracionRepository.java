package repository;

import jakarta.persistence.NoResultException;
import models.OrigenDeGeneracion;
import java.util.List;

public class OrigenDeGeneracionRepository extends Repository<OrigenDeGeneracion, Integer> {
    @Override
    public OrigenDeGeneracion findById(Integer id) {
        return em.find(OrigenDeGeneracion.class, id);
    }

    @Override
    public List<OrigenDeGeneracion> getAll() {
        return em.createQuery("SELECT o FROM OrigenDeGeneracion o", OrigenDeGeneracion.class).getResultList();
    }
    public OrigenDeGeneracion getByName(String name) {
        try {
            return em.createQuery("SELECT a FROM OrigenDeGeneracion a WHERE a.nombre = :name", OrigenDeGeneracion.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}