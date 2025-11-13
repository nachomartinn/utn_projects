package dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventoSismicoDTO {
    private Integer id;
    private LocalDateTime fechaHoraOcurrencia;
    private double latitudEpicentro;
    private double longitudEpicentro;
    private double latitudHipocentro;
    private double longitudHipocentro;
    private double valorMagnitud;
    private String estado;
    private String alcance;
    private String clasificacion;
    private String origen;

    public EventoSismicoDTO(Integer id,String fechaHoraOcurrencia,
                            double latitudEpicentro, double longitudEpicentro,
                            double latitudHipocentro, double longitudHipocentro,
                            double valorMagnitud,
                            String estado, String alcance, String clasificacion, String origen) {
        try {
            this.fechaHoraOcurrencia = LocalDateTime.parse(fechaHoraOcurrencia, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException ex) {
            // Compatibilidad con formato antiguo "yyyy-MM-dd HH:mm"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.fechaHoraOcurrencia = LocalDateTime.parse(fechaHoraOcurrencia, formatter);
        }

        this.id = id;
        this.latitudEpicentro = latitudEpicentro;
        this.longitudEpicentro = longitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudHipocentro = longitudHipocentro;
        this.valorMagnitud = valorMagnitud;
        this.estado = estado;
        this.alcance = alcance;
        this.clasificacion = clasificacion;
        this.origen = origen;
    }

    @Override
    public String toString() {
        return "EventoSismicoDTO{" +
                "fechaHoraOcurrencia=" + fechaHoraOcurrencia +
                ", latEpi=" + latitudEpicentro +
                ", lonEpi=" + longitudEpicentro +
                ", latHip=" + latitudHipocentro +
                ", lonHip=" + longitudHipocentro +
                ", magnitud=" + valorMagnitud +
                ", estado='" + estado + '\'' +
                ", alcance='" + alcance + '\'' +
                ", clasificacion='" + clasificacion + '\'' +
                ", origen='" + origen + '\'' +
                '}';
    }

    // Getters
    public LocalDateTime getFechaHoraOcurrencia() { return fechaHoraOcurrencia; }
    public double getLatitudEpicentro() { return latitudEpicentro; }
    public double getLongitudEpicentro() { return longitudEpicentro; }
    public double getLatitudHipocentro() { return latitudHipocentro; }
    public double getLongitudHipocentro() { return longitudHipocentro; }
    public double getValorMagnitud() { return valorMagnitud; }
    public String getEstado() { return estado; }
    public String getAlcance() { return alcance; }
    public String getClasificacion() { return clasificacion; }
    public String getOrigen() { return origen; }

    public Integer getId() {
        return id;
    }
}
