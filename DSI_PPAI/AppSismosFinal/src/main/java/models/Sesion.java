package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sesion {
    private LocalDateTime fechaHoraDesde;
    private LocalDateTime fechaHoraHasta;
    private Usuario usuario;

    public Sesion(LocalDateTime fechaHoraDesde, Usuario usuario) {
        this.fechaHoraDesde = fechaHoraDesde;
        this.usuario = usuario;
    }

    public Empleado obtenerASLogueado(){
        return this.usuario.obtenerASLogueado();
    }

}
