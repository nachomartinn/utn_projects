package models;

public class OrigenDeGeneracion {
    private String descripcion;
    private String nombre;

    //Constructor

    public OrigenDeGeneracion(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    //Getters and Setters

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
