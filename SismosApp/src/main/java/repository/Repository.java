package repository;

import jakarta.persistence.EntityManager;
import models.EventoSismico;
import repository.db.DbContext;

import java.util.List;

public abstract class Repository<T, K> {

    protected EntityManager em;

    public Repository() {
        em = DbContext.getInstance().getManager();
    }

    public T save(T entity) {
        var transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            return entity; // Retorna la entidad (que ahora tiene el ID)
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            // Manejar o relanzar la excepción
            throw new RuntimeException("Error al guardar la entidad: " + e.getMessage(), e);
        }
    }

    public void update(T entity) {
        var transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        transaction.commit();
    }

    public T delete(K id) {
        var transaction = em.getTransaction();
        try {
            transaction.begin();
            // Necesitas encontrar la entidad para poder removerla
            var entity = this.findById(id);
            if (entity != null) {
                // Si la entidad no está managed (cargada por findById), debes hacer merge antes
                T managedEntity = em.contains(entity) ? entity : em.merge(entity);
                em.remove(managedEntity);
            }
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al eliminar la entidad con ID: " + id, e);
        }
    }

    public void closeManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public abstract T findById(K id);

    public abstract List<T> getAll();

    // public abstract Stream<T> getAllStream();



}