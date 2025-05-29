package models;

public class Estado {
    private String nombreEstado;
    private String descripcion;
    private String ambito;

    // constructor
    public Estado(String nombreEstado, String descripcion, String ambito) {
        this.nombreEstado = nombreEstado;
        this.descripcion = descripcion;
        this.ambito = ambito;
    }

    @Override
    public String toString() {
        return nombreEstado + " (" + descripcion + ")" + ambito;
    }


    public  boolean sosAmbitoEventoSismico() {
        return this.getAmbito().equals("Evento Sismico");
    }

    public boolean sosAutoDetectado() {
        return this.getNombreEstado().equals("Autodetectado");
    }
    public String getNombreEstado() {
        return nombreEstado;
    }

    public boolean sosBloqueadoEnRevision() {
        return this.getNombreEstado().equals("Bloqueado En Revision");
    }

    public boolean sosRechazado() {
        return this.getNombreEstado().equals("Rechazado");
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }


    public boolean sosConfirmado() {
        return this.getNombreEstado().equals("Confirmado");
    }

    public boolean sosSolicitarRevisionExperto() {
        return this.getNombreEstado().equals("Solicitar Revision Experto");
    }
}
