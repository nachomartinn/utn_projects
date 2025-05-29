package models;

public class EstacionSismologica {

    private int codigoEstacion;
    private String nombre;
    private float latitud;
    private float longitud;

    public EstacionSismologica(int codigoEstacion, String nombre, float latitud, float longitud) {
        this.codigoEstacion = codigoEstacion;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getCodigoEstacion() {
        return codigoEstacion;
    }
}
