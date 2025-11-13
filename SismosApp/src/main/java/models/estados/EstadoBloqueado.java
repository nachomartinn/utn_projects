package models.estados;

import jakarta.persistence.Entity;
import models.EventoSismico;
import models.Empleado;
import models.CambioEstado;
import java.time.LocalDateTime;
@Entity
public class EstadoBloqueado extends EstadoEventoSismico {
    public EstadoBloqueado() {
        super("Bloqueado en Revisión");
    }
    @Override
    public String getNombreEstado() {
        return "Bloqueado en Revisión";
    }

    @Override
    public void confirmar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        CambioEstado ce = evento.getCambioEstadoActual();
        ce.setFechaHoraFin(fechaHoraActual);
        evento.setEstado(new EstadoConfirmado());
        CambioEstado cambioNuevo = new CambioEstado("Confirmado", fechaHoraActual, analista);
        evento.setCambioEstadoActual(cambioNuevo);

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
    public void solicitarRevisionExperto(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        CambioEstado ce = evento.getCambioEstadoActual();
        ce.setFechaHoraFin(fechaHoraActual);
        evento.setEstado(new EstadoEnRevisionExperto());
        CambioEstado cambioNuevo = new CambioEstado("En Revisión por Experto", fechaHoraActual, analista);
        evento.setCambioEstadoActual(cambioNuevo);
    }

    @Override
    public void bloquear(EventoSismico evento, LocalDateTime fechaHoraActual) {
        throw new IllegalStateException("El evento ya está bloqueado.");
    }


}
