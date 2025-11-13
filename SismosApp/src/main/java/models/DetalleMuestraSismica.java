package models;
import jakarta.persistence.*;
import lombok.*;
import models.TipoDato;

@Entity
@Table(name = "DETALLE_MUESTRA_SISMICA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMuestraSismica {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_MUESTRA_SISMICA")
    private Integer id;
    // --- NUEVOS GETTERS ---
    @Getter
    @Column(name = "VALOR")
    private double valor;
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TIPO_DATO_ID", referencedColumnName = "ID_TIPO_DATO")
    private TipoDato tipoDato;
    @ManyToOne
    @JoinColumn(name = "ID_MUESTRA_SISMICA")
    private MuestraSismica muestraSismica;

    public DetalleMuestraSismica(double valor, TipoDato tipoDato) {
        this.valor = valor;
        this.tipoDato = tipoDato;
    }


    public String getDatos() {
        String valorFormateado = (valor == (long) valor) ?
                String.format("%d", (long)valor) :
                String.format("%.2f", valor);

        return "Valor: " + valorFormateado + ", Denominación: " + tipoDato.getDenominacion();
    }
}