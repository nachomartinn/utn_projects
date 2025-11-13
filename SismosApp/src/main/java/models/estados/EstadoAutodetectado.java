package models.estados;

import jakarta.persistence.Entity;
import models.EventoSismico;
import models.Empleado;
import models.CambioEstado;
import java.time.LocalDateTime;
@Entity
public class EstadoAutodetectado extends EstadoEventoSismico {
    public EstadoAutodetectado() {
        super("Autodetectado");
    }

    @Override
    public String getNombreEstado() {
        return "Autodetectado";
    }

    @Override
    public void rechazar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        CambioEstado ce = evento.getCambioEstadoActual();
        ce.setFechaHoraFin(fechaHoraActual);
        evento.setEstado(new EstadoRechazado());
        CambioEstado cambioNuevo = new CambioEstado("Rechazado", fechaHoraActual, analista);
        evento.setCambioEstadoActual(cambioNuevo);
    }

    @Override
    public void bloquear(EventoSismico evento, LocalDateTime fechaHoraActual) {
        // Cierra el cambio de estado actual
        CambioEstado ce = evento.getCambioEstadoActual();
        ce.setFechaHoraFin(fechaHoraActual);
        // Transición a "Bloqueado"
        evento.setEstado(new EstadoBloqueado());
        // Registrar el cambio
        CambioEstado cambioNuevo = new CambioEstado("Bloqueado en Revisión", fechaHoraActual, null);
        evento.setCambioEstadoActual(cambioNuevo);
    }
}