package models;

public class TipoDato {
    private String nombreUnidadMedida;
    private int valorUmbral;
    private String denominacion;

    public TipoDato(String nombreUnidadMedida, int valorUmbral, String denominacion) {
        this.nombreUnidadMedida = nombreUnidadMedida;
        this.valorUmbral = valorUmbral;
        this.denominacion = denominacion;
    }

    public String getDenominacion() {
        return denominacion;
    }

    @Override
    public String toString() {
        return "TipoDato{" +
                "nombreUnidadMedida='" + nombreUnidadMedida + '\'' +
                ", valorUmbral=" + valorUmbral +
                ", denominacion='" + denominacion + '\'' +
                '}';
    }
}
