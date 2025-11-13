package models;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
@Entity
@Table(name = "TIPO_DATO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDato {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_DATO")
    private Integer id;
    // --- NUEVO GETTER ---
    @Getter
    @Column(name = "NOMBRE_UNIDAD_MEDIDA", nullable = false)
    private String nombreUnidadMedida;
    @Column(name = "VALOR_UMBRAL", nullable = false)
    private int valorUmbral;
    @Getter
    @Column(name = "DENOMINACION", nullable = false)
    private String denominacion;

    public TipoDato(String nombreUnidadMedida, int valorUmbral, String denominacion) {
        this.nombreUnidadMedida = nombreUnidadMedida;
        this.valorUmbral = valorUmbral;
        this.denominacion = denominacion;
    }

    @Override
    public String toString() {
        return "TipoDato{" +
                "nombreUnidadMedida='" + nombreUnidadMedida + '\'' +
                ", valorUmbral=" + valorUmbral +
                ", denominacion='" + denominacion + '\'' +
                '}';
    }
}
