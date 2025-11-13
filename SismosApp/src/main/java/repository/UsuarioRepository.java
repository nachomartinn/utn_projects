package repository;

import models.Usuario;
import models.Empleado; // Importa Empleado si lo necesitas para métodos específicos
import java.util.List;
import jakarta.persistence.NoResultException;

// Usuario usa Integer como clave primaria (K)
public class UsuarioRepository extends Repository<Usuario, Integer> {

    private Class<Usuario> getEntityClass() {
        return Usuario.class;
    }

    @Override
    public Usuario findById(Integer id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    public List<Usuario> getAll() {
        return em.createQuery("SELECT u FROM Usuario u", getEntityClass()).getResultList();
    }

    /**
     * Busca un Usuario por su nombre de usuario.
     */
    public Usuario findByNombreUsuario(String nombreUsuario) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombre", getEntityClass())
                    .setParameter("nombre", nombreUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null si no lo encuentra
        }
    }

    // Si quisieras obtener un Usuario basado en su Empleado (útil para el seeding)
    public Usuario findByEmpleado(Empleado empleado) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.empleado = :empleado", getEntityClass())
                    .setParameter("empleado", empleado)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}