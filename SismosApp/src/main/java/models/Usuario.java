package models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer id;
    @Column(name = "NOMBRE_USUARIO", nullable = false, unique = true)
    private String nombreUsuario;
    @Column(name = "CONTRASENIA", nullable = false)
    private String contrasenia;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPLEADO", nullable = false)
    private Empleado empleado;

    public Usuario(String nombreUsuario, String contrasenia, Empleado empleado) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.empleado = empleado;
    }

    public Empleado obtenerASLogueado(){
        return this.empleado.obtenerASLogeado();
    }
}
