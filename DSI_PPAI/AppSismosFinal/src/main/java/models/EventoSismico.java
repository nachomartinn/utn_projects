package models;

import controllers.GestorRevisionManual;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class EventoSismico {
    private LocalDateTime fechaHoraOcurrencia;
    private LocalDateTime fechaHoraFin;
    private double latitudEpicentro;
    private double latitudHipocentro;
    private double longitudEpicentro;
    private double longitudHipocentro;
    private double valorMagnitud;
    private Estado estado;
    private List<CambioEstado> cambioEstado = new ArrayList<>();
    private AlcanceSismo alcanceSismo;
    private OrigenDeGeneracion origenDeGeneracion;
    private ClasificacionSismo clasificacionSismo;
    private List<SerieTemporal> serieTemporal = new ArrayList<>();


    // Constructor
    public EventoSismico(String fechaHoraOcurrencia, double latitudEpicentro, double latitudHipocentro, double longitudEpicentro, double longitudHipocentro, double valorMagnitud, Estado estado,AlcanceSismo alcanceSismo, ClasificacionSismo clasificacionSismo, OrigenDeGeneracion origenDeGeneracion, List<SerieTemporal> serieTemporal) {

        this.fechaHoraOcurrencia = parsearFechaHora(fechaHoraOcurrencia);
        this.latitudEpicentro = latitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudEpicentro = longitudEpicentro;
        this.longitudHipocentro = longitudHipocentro;
        this.valorMagnitud = valorMagnitud;
        this.estado = estado;
        this.cambioEstado.add(new CambioEstado(fechaHoraOcurrencia, estado, null));
        this.alcanceSismo = alcanceSismo;
        this.origenDeGeneracion = origenDeGeneracion;
        this.clasificacionSismo = clasificacionSismo;
        this.serieTemporal = serieTemporal;
    }

    public boolean estaAutodetectado(){
        return this.estado.sosAutoDetectado();
    }

    public LocalDateTime parsearFechaHora(String fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(fechaHora, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha y hora no válida: " + fechaHora);
        }
    }

    public String obtenerDatos() {
        return "EventoSismico{" +
                "fechaHoraOcurrencia=" + fechaHoraOcurrencia.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                ", latitudEpicentro=" + latitudEpicentro +
                ", latitudHipocentro=" + latitudHipocentro +
                ", longitudEpicentro=" + longitudEpicentro +
                ", longitudHipocentro=" + longitudHipocentro +
                ", valorMagnitud=" + valorMagnitud + '}';
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "\n📍 Evento Sísmico" +
                "\n ├── Fecha y Hora de Ocurrencia: " + fechaHoraOcurrencia.format(formatter) +
                "\n ├── Latitud Epicentro: " + latitudEpicentro +
                "\n ├── Longitud Epicentro: " + longitudEpicentro +
                "\n ├── Latitud Hipocentro: " + latitudHipocentro +
                "\n ├── Longitud Hipocentro: " + longitudHipocentro +
                "\n ├── Magnitud: " + valorMagnitud +
                "\n ├── Estado Actual: " + estado +
                "\n ├── Alcance: " + alcanceSismo +
                "\n ├── Clasificación: " + clasificacionSismo +
                "\n ├── Origen: " + origenDeGeneracion +
                "\n ├── Cambios de Estado:\n" + cambioEstado.stream()
                .map(c -> "     • " + c)
                .collect(Collectors.joining("\n"));
    }

    public LocalDateTime getFechaHoraOcurrencia() {
        return fechaHoraOcurrencia;
    }


    public void setEstado(Estado estado) {
        this.estado = estado;
    }


    public void bloquearEventoSismico(LocalDateTime fechaHoraActual, Estado estadoBloqueadoEnRevision) {
        CambioEstado cambioEstadoActual = this.buscarCambioEstadoActual();
        EventoSismico.this.setEstado(estadoBloqueadoEnRevision);
        if (cambioEstadoActual != null) {
            cambioEstadoActual.setFechaHoraFin(fechaHoraActual); // Primero cerrar el estado actual
        }
        this.setEstado(estadoBloqueadoEnRevision); // Ahora sí cambiar el estado del evento
        this.crearCE(estadoBloqueadoEnRevision, fechaHoraActual, null);


    }

    public CambioEstado buscarCambioEstadoActual() {
        for (CambioEstado cambio : EventoSismico.this.cambioEstado) {
            if (cambio.sosActual()) {
                return cambio;
            }
        }
        return null;
    }

    public void crearCE(Estado estado, LocalDateTime fechaHoraActual, Empleado analista) {
        CambioEstado nuevoCambio = new CambioEstado(fechaHoraActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), estado, analista);
        cambioEstado.add(nuevoCambio);
    }

    public ArrayList<Map.Entry<String, Map.Entry<String, String>>> buscarDatosSismicos() {
        ArrayList<Map.Entry<String, Map.Entry<String, String>>> detalles = new ArrayList<>();

        detalles.add(new AbstractMap.SimpleEntry<>("Alcance",
                new AbstractMap.SimpleEntry<>(alcanceSismo.getNombre(),
                        alcanceSismo.getDescripcion())));

        detalles.add(new AbstractMap.SimpleEntry<>("Clasificación",
                new AbstractMap.SimpleEntry<>(clasificacionSismo.getNombre(),
                        "De " + clasificacionSismo.getKmProfundidadDesde() + " a " +
                                clasificacionSismo.getKmProfundidadHasta() + " km")));

        detalles.add(new AbstractMap.SimpleEntry<>("Origen",
                new AbstractMap.SimpleEntry<>(origenDeGeneracion.getNombre(),
                        origenDeGeneracion.getDescripcion())));

        return detalles;
    }

    public void obtenerMuestras() {
        for (SerieTemporal serie : serieTemporal) {
            serie.obtenerMuestras();
        }
    }

    public double getLatitudEpicentro() {
        return latitudEpicentro;
    }

    public double getLatitudHipocentro() {
        return latitudHipocentro;
    }

    public double getLongitudEpicentro() {
        return longitudEpicentro;
    }

    public double getLongitudHipocentro() {
        return longitudHipocentro;
    }

    public double getValorMagnitud() {
        return valorMagnitud;
    }
    public double getSerieTemporal() {
        return valorMagnitud;
    }

    public boolean existeMagnitud() {
        return this.valorMagnitud > 0;
    }

    public boolean validarExistenciaDeDatosEvento() {
        return existeMagnitud() && alcanceSismo.existeAlcance() && origenDeGeneracion.existeOrigen();
    }

    public void rechazarEventoSismico(LocalDateTime fechaHoraActual, Estado estadoRechazado, Empleado analista) {

        if (this.estado.equals(estadoRechazado)) {
            return;
        }

        // Cerrar el cambio de estado actual si existe
        CambioEstado cambioEstadoActual = this.buscarCambioEstadoActual();
        if (cambioEstadoActual != null) {
            cambioEstadoActual.setFechaHoraFin(fechaHoraActual);
        }

        // Cambiar estado y registrar nuevo cambio
        this.setEstado(estadoRechazado);
        this.crearCE(estadoRechazado, fechaHoraActual, analista);
    }

    //Alternativa que el analista confirma el evento sismico

    public void confirmarEventoSismico(LocalDateTime fechaHoraActual, Estado estadoConfirmado, Empleado analista) {

        if (this.estado.equals(estadoConfirmado)) {
            return;
        }
        // Cerrar el cambio de estado actual si existe
        CambioEstado cambioEstadoActual = this.buscarCambioEstadoActual();
        if (cambioEstadoActual != null) {
            cambioEstadoActual.setFechaHoraFin(fechaHoraActual);
        }
        // Cambiar estado y registrar nuevo cambio
        this.setEstado(estadoConfirmado);
        this.crearCE(estadoConfirmado, fechaHoraActual, analista);

    }

    // Alternativa que el analista solicita revision de experto
    public void solicitarRevisionExperto(LocalDateTime fechaHoraActual, Estado estadoSolicitarRevisionExperto, Empleado analista){
        if (this.estado.equals(estadoSolicitarRevisionExperto)) {
            return;
        }
        // Cerrar el cambio de estado actual si existe
        CambioEstado cambioEstadoActual = this.buscarCambioEstadoActual();
        if (cambioEstadoActual != null) {
            cambioEstadoActual.setFechaHoraFin(fechaHoraActual);
        }
        // Cambiar estado y registrar nuevo cambio
        this.setEstado(estadoSolicitarRevisionExperto);
        this.crearCE(estadoSolicitarRevisionExperto, fechaHoraActual, analista);
    }

}
