package repository;

import models.Sesion;
import java.util.List;
import jakarta.persistence.NoResultException;

// La Sesion usa Integer como clave primaria (K)
public class SesionRepository extends Repository<Sesion, Integer> {

    // Método necesario para obtener la clase de la entidad para consultas
    private Class<Sesion> getEntityClass() {
        return Sesion.class;
    }

    @Override
    public Sesion findById(Integer id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    public List<Sesion> getAll() {
        return em.createQuery("SELECT s FROM Sesion s", getEntityClass()).getResultList();
    }

    public Sesion buscarSesionActiva() {
        try {
            // JPQL: Busca la última sesión que no tiene fechaHoraHasta y ordénala
            // Limitamos a 1 (setMaxResults(1)) para obtener solo la superior
            return em.createQuery(
                            "SELECT s FROM Sesion s WHERE s.fechaHoraHasta IS NULL ORDER BY s.fechaHoraDesde DESC",
                            getEntityClass())
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Si no hay ninguna sesión activa
            return null;
        }
    }
}