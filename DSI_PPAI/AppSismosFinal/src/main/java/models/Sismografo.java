package models;

import java.util.ArrayList;

public class Sismografo {
    private String fechaAdquicicion;
    private int nroSerie;
    private ArrayList<SerieTemporal> serieTemporales;
    private EstacionSismologica estacionSismologica;

    public Sismografo(String fechaAdquicicion, int nroSerie) {
        this.fechaAdquicicion = fechaAdquicicion;
        this.nroSerie = nroSerie;
    }
    public int sosDeSerieTemporal(SerieTemporal unaSerieTemporal) {
        for (SerieTemporal serieTemporal : serieTemporales) {
            if (serieTemporal.equals(unaSerieTemporal)) {
                return estacionSismologica.getCodigoEstacion(); // Retorna código si encuentra
            }
        }
        return -1; // Si no se encuentra la serie temporal, se retorna -1
    }



    @Override
    public String toString() {
        return "Sismografo{" +
                "fechaAdquicicion='" + fechaAdquicicion + '\'' +
                ", nroSerie=" + nroSerie +
                ", serieTemporals=" + serieTemporales +
                ", estacionSismologica=" + estacionSismologica +
                '}';
    }

}

