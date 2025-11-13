package models.estados;

import jakarta.persistence.Entity;
import models.EventoSismico;
import models.Empleado;
import models.CambioEstado;
import java.time.LocalDateTime;
@Entity
public class EstadoEnRevisionExperto extends EstadoEventoSismico {
    public EstadoEnRevisionExperto() {
        super("En Revisión Experto");
    }
    @Override
    public String getNombreEstado() {
        return "En Revisión de Experto";
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
        System.out.println("⚠️ El evento ya está en revisión de experto. No se puede volver a solicitar.");
    }

    @Override
    public void bloquear(EventoSismico evento, LocalDateTime fechaHoraActual) {
        throw new IllegalStateException("No se puede bloquear un evento que ya está en revisión de experto.");
    }

}
