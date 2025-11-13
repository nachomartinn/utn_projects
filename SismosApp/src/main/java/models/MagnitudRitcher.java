package models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MAGNITUD_RICHTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MagnitudRitcher {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_MAGNITUD_RICHTER")
    private Integer id;
    @Column(name = "DESCRIPCION_MAGNITUD", nullable = false)
    private String descripcionMagnitud;
    @Column(name = "NUMERO", nullable = false)
    private int numero;

    public MagnitudRitcher(String descripcionMagnitud, int numero) {
        this.descripcionMagnitud = descripcionMagnitud;
        this.numero = numero;
    }


    public String getDescripcionMagnitud() {
        return descripcionMagnitud;
    }

    public void setDescripcionMagnitud(String descripcionMagnitud) {
        this.descripcionMagnitud = descripcionMagnitud;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
