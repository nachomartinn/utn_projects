package repository;

import jakarta.persistence.NoResultException;
import models.AlcanceSismo;
import java.util.List;

public class AlcanceSismoRepository extends Repository<AlcanceSismo, Integer> {
    @Override
    public AlcanceSismo findById(Integer id) {
        return em.find(AlcanceSismo.class, id);
    }

    @Override
    public List<AlcanceSismo> getAll() {
        return em.createQuery("SELECT a FROM AlcanceSismo a", AlcanceSismo.class).getResultList();
    }
    public AlcanceSismo getByName(String name) {
        try {
            return em.createQuery("SELECT a FROM AlcanceSismo a WHERE a.nombre = :name", AlcanceSismo.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}