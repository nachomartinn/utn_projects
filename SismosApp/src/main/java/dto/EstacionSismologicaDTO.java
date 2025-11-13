package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacionSismologicaDTO {
    private String nombre;
    private int codigoEstacion;

    public EstacionSismologicaDTO(String nombre, int codigoEstacion) {
        this.nombre = nombre;
        this.codigoEstacion = codigoEstacion;
    }

}
