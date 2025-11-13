package repository;

import jakarta.persistence.EntityManager;
import models.EventoSismico;
import models.estados.EstadoAutodetectado;
import models.estados.EstadoConfirmado;
import models.estados.EstadoEventoSismico;
import models.estados.EstadoRechazado;
import repository.db.DbContext;

import java.util.List;

public class EventoSismicoRepository extends Repository<EventoSismico, Integer> {

    @Override
    public EventoSismico findById(Integer id) {
        return em.find(EventoSismico.class, id);
    }

    @Override
    public List<EventoSismico> getAll() {
        return em.createQuery("SELECT e FROM EventoSismico e", EventoSismico.class)
                .getResultList();
    }

    public List<EventoSismico> findByEstadoAutodetectado() {
        return em.createQuery("SELECT e FROM EventoSismico e WHERE TYPE(e.estado) = EstadoAutodetectado", EventoSismico.class)
                .getResultList();
    }


    public EstadoEventoSismico findOrCreateEstadoReference(String nombreEstado) {
        EstadoEventoSismico estado = em.find(EstadoEventoSismico.class, nombreEstado);
        if (estado == null) {
            switch (nombreEstado) {
                case "Autodetectado":
                    estado = new EstadoAutodetectado();
                    break;
                case "Confirmado":
                    estado = new EstadoConfirmado();
                    break;
                case "Rechazado":
                    estado = new EstadoRechazado();
                    break;
                case "En Revisión de Experto":
                    estado = new models.estados.EstadoEnRevisionExperto();
                    break;
                default:
                    throw new IllegalArgumentException("Estado desconocido: " + nombreEstado);
            }
            em.persist(estado);
        } else {
            estado = em.getReference(EstadoEventoSismico.class, nombreEstado);
        }
        return estado;
    }
    public void update(EventoSismico evento) {
        try {
            em.getTransaction().begin();
            em.merge(evento);
            em.getTransaction().commit();
            System.out.println("[OK] Evento " + evento.getId() + " actualizado con estado: " + evento.getEstado().getNombreEstado());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            System.err.println("[ERROR] No se pudo actualizar el evento: " + e.getMessage());
        }
        System.out.println("[DEBUG] Estado actual en BD: " +
                em.createQuery("SELECT e.estado.nombreEstado FROM EventoSismico e WHERE e.id = :id", String.class)
                        .setParameter("id", evento.getId())
                        .getSingleResult());

    }

}
