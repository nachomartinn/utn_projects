package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SerieTemporal {

    private String condicionAlarma;
    private Date fechaHoraRegistro;
    private Date fechaHoraInicioRegistroMuestra;
    private float frecuenciaMuestreo;
    private List<MuestraSismica> muestraSismica;
    private Sismografo sismografo;

    //Constructor
    public SerieTemporal(String condicionAlarma, Date fechaHoraRegistro, Date fechaHoraInicioRegistroMuestra,
                          float frecuenciaMuestreo, Sismografo sismografo, List<MuestraSismica> muestraSismica) {
        this.condicionAlarma = condicionAlarma;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.fechaHoraInicioRegistroMuestra = fechaHoraInicioRegistroMuestra;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.muestraSismica = muestraSismica;
        this.sismografo = sismografo; // Asignar correctamente
    }

    public void obtenerMuestras() {
        for (MuestraSismica muestra : muestraSismica) {
            muestra.getDatos();
            buscarEstacion(this.sismografo);
        }
    }

    public void buscarEstacion(Sismografo sismografo) {
        sismografo.sosDeSerieTemporal(this);
        // FALTA CORREGIR
    }
    public void getSismografo() {
        System.out.println("Sismógrafo: " + sismografo);
    }
}

