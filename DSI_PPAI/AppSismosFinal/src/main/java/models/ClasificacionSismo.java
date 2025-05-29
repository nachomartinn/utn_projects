package models;

public class ClasificacionSismo {
    private int kmProfundidadDesde;
    private int kmProfundidadHasta;
    private String nombre;

    //Constructor

    public ClasificacionSismo(int kmProfundidadDesde, int kmProfundidadHasta, String nombre) {
        this.kmProfundidadDesde = kmProfundidadDesde;
        this.kmProfundidadHasta = kmProfundidadHasta;
        this.nombre = nombre;
    }

    //Getters and Setters

    public int getKmProfundidadDesde() {
        return kmProfundidadDesde;
    }

    public void setKmProfundidadDesde(int kmProfundidadDesde) {
        this.kmProfundidadDesde = kmProfundidadDesde;
    }

    public int getKmProfundidadHasta() {
        return kmProfundidadHasta;
    }

    public void setKmProfundidadHasta(int kmProfundidadHasta) {
        this.kmProfundidadHasta = kmProfundidadHasta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "kmProfundidadDesde=" + kmProfundidadDesde +
                ", kmProfundidadHasta=" + kmProfundidadHasta +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
