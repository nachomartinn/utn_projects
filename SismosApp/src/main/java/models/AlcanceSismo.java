package models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ALCANCE_SISMO")
@Data
@NoArgsConstructor
public class AlcanceSismo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_ALCANCE")
    private Integer id;
    @Setter
    @Getter
    @Column(name = "NOMBRE_ALCANCE", nullable = false)
    String nombre;
    @Setter
    @Getter
    @Column(name = "DESCRIPCION_ALCANCE", nullable = false)
    String descripcion;

    public AlcanceSismo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "AlcanceSismo{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public boolean existeAlcance() {
        return !this.nombre.isEmpty() && !this.descripcion.isEmpty();
    }
}
