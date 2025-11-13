package models.estados;

import jakarta.persistence.*;
import models.EventoSismico;
import models.Empleado;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EstadoEventoSismico {
    @Id
    @Column(name = "NOMBRE_ESTADO")
    protected String nombreEstado;

    public EstadoEventoSismico() {}

    public EstadoEventoSismico(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public abstract String getNombreEstado();

    // Métodos que los estados concretos pueden redefinir
    public void confirmar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("Transición no válida: No se puede confirmar desde el estado: " + getNombreEstado());
    }

    public void rechazar(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("Transición no válida: No se puede rechazar desde el estado: " + getNombreEstado());
    }

    public void solicitarRevisionExperto(EventoSismico evento, LocalDateTime fechaHoraActual, Empleado analista) {
        throw new IllegalStateException("Transición no válida: No se puede solicitar revisión de experto desde el estado: " + getNombreEstado());
    }

    public void bloquear(EventoSismico evento, LocalDateTime fechaHoraActual) {
        throw new IllegalStateException("Transición no válida: No se puede bloquear desde el estado: " + getNombreEstado());
    }
}
