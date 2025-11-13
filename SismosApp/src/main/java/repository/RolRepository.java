package repository;

import models.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import repository.db.DbContext;

public class RolRepository extends Repository<Rol, Integer> {

    @Override
    public Rol findById(Integer id) {
        return null;
    }

    @Override
    public List<Rol> getAll() {
        return List.of();
    }

    public Optional<Rol> findByName(String nombre) {
        EntityManager em = DbContext.getInstance().getEntityManager();
        try {
            Rol rol = em.createQuery("SELECT r FROM Rol r WHERE r.nombre = :nombreRol", Rol.class)
                    .setParameter("nombreRol", nombre)
                    .getSingleResult();
            return Optional.of(rol);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}