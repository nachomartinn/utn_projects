package models;

import java.util.ArrayList;

public class MuestraSismica {
    private String fechaHoraMuestra;
    private ArrayList<DetalleMuestraSismica> detalleMuestraSismica;


    public MuestraSismica(String fechaHoraMuestra, ArrayList<DetalleMuestraSismica> detalleMuestraSismica) {
        this.fechaHoraMuestra = fechaHoraMuestra;
        this.detalleMuestraSismica = detalleMuestraSismica;
    }

    public String getDatos() {
        return "Fecha muestra: " + fechaHoraMuestra + this.obtenerDetalleMuestra();
    }

    public String obtenerDetalleMuestra() {
        StringBuilder datos = new StringBuilder();
        for (DetalleMuestraSismica detalle : detalleMuestraSismica) {
            datos.append("- ").append(detalle.getDatos()).append("\n");
        }
        return datos.toString();
    }
}
