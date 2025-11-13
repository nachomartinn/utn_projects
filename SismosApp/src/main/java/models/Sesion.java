package models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sesion {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_SESION")
    private Integer id;
    @Column(name = "FECHA_HORA_DESDE", nullable = false)
    private LocalDateTime fechaHoraDesde;
    @Column(name = "FECHA_HORA_HASTA")
    private LocalDateTime fechaHoraHasta;
    @ManyToOne(fetch = FetchType.LAZY) // 1. Tipo de relación
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    public Sesion(LocalDateTime fechaHoraDesde, Usuario usuario) {
        this.fechaHoraDesde = fechaHoraDesde;
        this.usuario = usuario;
    }

    public Empleado obtenerASLogueado(){
        return this.usuario.obtenerASLogueado();
    }

}
