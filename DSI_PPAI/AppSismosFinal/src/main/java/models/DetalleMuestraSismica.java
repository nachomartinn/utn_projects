package models;
import models.TipoDato;


public class DetalleMuestraSismica {
    private int valor;
    private TipoDato tipoDato;


    public DetalleMuestraSismica(int valor, TipoDato tipoDato) {
        this.valor = valor;
        this.tipoDato = tipoDato;
    }

    public String getDatos() {
        return "Valor: " + valor + ", Denominación: " + tipoDato.getDenominacion();
    }

}
