package models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CLASIFICACION_SISMO")
@Data
@NoArgsConstructor
public class ClasificacionSismo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_CLASIFICACION")
    private Integer id;
    @Getter
    @Column(name = "KM_PROFUNDIDAD_DESDE", nullable = false)
    private int kmProfundidadDesde;
    @Getter
    @Column(name = "KM_PROFUNDIDAD_HASTA", nullable = false)
    private int kmProfundidadHasta;
    @Setter
    @Getter
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    //Constructor

    public ClasificacionSismo(int kmProfundidadDesde, int kmProfundidadHasta, String nombre) {
        this.kmProfundidadDesde = kmProfundidadDesde;
        this.kmProfundidadHasta = kmProfundidadHasta;
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "kmProfundidadDesde=" + kmProfundidadDesde +
                ", kmProfundidadHasta=" + kmProfundidadHasta +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
