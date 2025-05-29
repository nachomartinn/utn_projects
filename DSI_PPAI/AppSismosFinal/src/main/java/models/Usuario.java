package models;

public class Usuario {
    private String nombreUsuario;
    private String contrasenia;
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
