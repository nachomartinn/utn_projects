package models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "CAMBIO_ESTADO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CambioEstado {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_CAMBIO_ESTADO")
    private Integer id;
    @Column(name = "NOMBRE_ESTADO", nullable = false)
    private String nombreEstado;
    @Column(name = "FECHA_HORA_INICIO", nullable = false)
    private LocalDateTime fechaHoraInicio;
    @Setter
    @Column(name = "FECHA_HORA_FIN")
    private LocalDateTime fechaHoraFin;
    @ManyToOne(fetch = FetchType.LAZY, optional = true,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_ANALISTA", referencedColumnName = "ID_EMPLEADO", nullable = true)
    private Empleado analista;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_EVENTO_SISMICO", nullable = false)
    private EventoSismico eventoSismico;

    public CambioEstado(String nombreEstado, LocalDateTime fechaHoraInicio, Empleado analista) {
        this.nombreEstado = nombreEstado;
        this.fechaHoraInicio = fechaHoraInicio;
        this.analista = analista;
    }

    @Override
    public String toString() {
        return "CambioEstado{" +
                "estado='" + nombreEstado + '\'' +
                ", inicio=" + fechaHoraInicio +
                ", fin=" + fechaHoraFin +
                '}';
    }
}
