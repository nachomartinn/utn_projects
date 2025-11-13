package models;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "MUESTRA_SISMICA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MuestraSismica {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_MUESTRA_SISMICA")
    private Integer id;
    @Getter
    @Column(name = "FECHA_HORA_MUESTRA", nullable = false)
    private String fechaHoraMuestra;
    @OneToMany(mappedBy = "muestraSismica", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleMuestraSismica> detalleMuestraSismica;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIE_TEMPORAL_ID",referencedColumnName = "ID_SERIE_TEMPORAL", nullable = false)
    private SerieTemporal serieTemporal;

    public MuestraSismica(String fechaHoraMuestra, List<DetalleMuestraSismica> detalleMuestraSismica) {
        this.fechaHoraMuestra = fechaHoraMuestra;
        this.detalleMuestraSismica = (detalleMuestraSismica != null) ? detalleMuestraSismica : new ArrayList<>();
    }

    private String getDetalleFormateado(String denominacion) {
        for (DetalleMuestraSismica detalle : detalleMuestraSismica) {
            if (detalle.getTipoDato().getDenominacion().equalsIgnoreCase(denominacion)) {
                double valor = detalle.getValor();
                String unidad = detalle.getTipoDato().getNombreUnidadMedida();

                // Formatear valor para que no siempre muestre .0
                String valorFormateado = (valor == (long) valor) ?
                        String.format("%d", (long)valor) :
                        String.format("%.2f", valor); // Ajusta los decimales si es necesario

                return String.format("%s %s", valorFormateado, unidad);
            }
        }
        return "N/A"; // Valor no encontrado
    }

    public String getDatosFormateados() {
        String velocidad = getDetalleFormateado("Velocidad de onda");
        String frecuencia = getDetalleFormateado("Frecuencia de onda");
        String longitud = getDetalleFormateado("Longitud");

        return String.format("Velocidad: %s | Frecuencia: %s | Longitud: %s",
                velocidad, frecuencia, longitud);
    }


}