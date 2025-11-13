package models.estados;

import jakarta.persistence.Entity;
import models.EventoSismico;
import models.Empleado;
import java.time.LocalDateTime;
@Entity
public class EstadoRechazado extends EstadoEventoSismico {
    public EstadoRechazado() {
        super("Rechazado");
    }
    @Override
    public String getNombreEstado() {
        return "Rechazado";
    }

    @Override
    public void confirmar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("No se puede confirmar un evento rechazado.");
    }

    @Override
    public void rechazar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("El evento ya está rechazado.");
    }

    @Override
    public void solicitarRevisionExperto(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("No se puede solicitar revisión para un evento rechazado.");
    }

    @Override
    public void bloquear(EventoSismico evento, LocalDateTime fechaHoraActual) {
        throw new IllegalStateException("No se puede bloquear un evento rechazado.");
    }
}
