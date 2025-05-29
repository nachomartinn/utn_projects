package models;

public class MagnitudRitcher {
    private String descripcionMagnitud;
    private int numero;

    //Constructor

    public MagnitudRitcher(String descripcionMagnitud, int numero) {
        this.descripcionMagnitud = descripcionMagnitud;
        this.numero = numero;
    }

    //Getters and Setters

    public String getDescripcionMagnitud() {
        return descripcionMagnitud;
    }

    public void setDescripcionMagnitud(String descripcionMagnitud) {
        this.descripcionMagnitud = descripcionMagnitud;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
