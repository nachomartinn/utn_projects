package models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_ROL")
    private Integer id;
    @Setter
    @Getter
    @Column(name = "NOMBRE", nullable = false, unique = true)
    private String nombre;
    @Getter
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean sosAnalistaSismos(){
        return this.getNombre().equals("AnalistaSismos");
    }

    @Override
    public String toString() {
        return "Rol{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
