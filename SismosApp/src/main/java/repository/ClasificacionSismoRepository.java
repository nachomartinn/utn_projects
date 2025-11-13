package repository;

import jakarta.persistence.NoResultException;
import models.ClasificacionSismo;
import java.util.List;

public class ClasificacionSismoRepository extends Repository<ClasificacionSismo, Integer> {
    @Override
    public ClasificacionSismo findById(Integer id) {
        return em.find(ClasificacionSismo.class, id);
    }

    @Override
    public List<ClasificacionSismo> getAll() {
        return em.createQuery("SELECT c FROM ClasificacionSismo c", ClasificacionSismo.class).getResultList();
    }
    public ClasificacionSismo getByName(String name) {
        try {
            return em.createQuery("SELECT a FROM ClasificacionSismo a WHERE a.nombre = :name", ClasificacionSismo.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}