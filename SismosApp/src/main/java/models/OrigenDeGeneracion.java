package models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ORIGEN_DE_GENERACION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrigenDeGeneracion {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_ORIGEN")
    private Integer id;
    @Getter
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Setter
    @Getter
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    //Constructor

    public OrigenDeGeneracion(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "OrigenDeGeneracion{" +
                "descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public boolean existeOrigen() {
        return !this.descripcion.isEmpty() && !this.nombre.isEmpty();
    }
}
