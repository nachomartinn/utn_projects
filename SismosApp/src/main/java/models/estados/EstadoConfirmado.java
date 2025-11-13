package models.estados;

import jakarta.persistence.Entity;
import models.EventoSismico;
import models.Empleado;
import java.time.LocalDateTime;
@Entity
public class EstadoConfirmado extends EstadoEventoSismico {

    public EstadoConfirmado() {
        super("Confirmado");
    }

    @Override
    public String getNombreEstado() {
        return "Confirmado";
    }

    @Override
    public void confirmar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("El evento ya fue confirmado.");
    }

    @Override
    public void rechazar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("No se puede rechazar un evento ya confirmado.");
    }

    @Override
    public void solicitarRevisionExperto(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("El evento confirmado no puede pasar a revisión de experto.");
    }

    @Override
    public void bloquear(EventoSismico evento, LocalDateTime fechaHoraActual) {
        throw new IllegalStateException("No se puede bloquear un evento confirmado.");
    }
}
