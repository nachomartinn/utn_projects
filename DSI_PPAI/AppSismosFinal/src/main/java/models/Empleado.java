package models;

public class Empleado {
    private String nombre;
    private String apellido;
    private int telefono;
    private String mail;
    private Rol rol;

    public Empleado(String nombre, String apellido, int telefono, String mail, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.mail = mail;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Empleado obtenerASLogeado(){
        if (this.rol.sosAnalistaSismos()){
            return this;
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " | " + mail + " | Tel: " + telefono + " | Rol: " + rol;
    }

}