package models;
import dto.EstacionSismologicaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Entity
@Table(name = "ESTACION_SISMOLOGICA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionSismologica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTACION_SISMOLOGICA")
    private Integer id;
    @Getter
    @Column(name = "CODIGO_ESTACION", nullable = false, unique = true)
    private int codigoEstacion;
    @Getter
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "LATITUD", nullable = false)
    private float latitud;
    @Column(name = "LONGITUD", nullable = false)
    private float longitud;

    public EstacionSismologica(int codigoEstacion, String nombre, float latitud, float longitud) {
        this.codigoEstacion = codigoEstacion;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstacionSismologica that = (EstacionSismologica) o;
        return codigoEstacion == that.codigoEstacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoEstacion);
    }

    public EstacionSismologicaDTO toDto() {
        return new EstacionSismologicaDTO(this.nombre, this.codigoEstacion);
    }
}
