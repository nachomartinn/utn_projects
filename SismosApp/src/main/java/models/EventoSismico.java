package models;

import dto.DatosSismicosDTO;
import dto.EstacionSismologicaDTO;
import dto.EventoSismicoDTO;
import jakarta.persistence.*;
import lombok.*;
import models.estados.EstadoEventoSismico;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "EVENTOS_SISMICOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoSismico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENTO_SISMICO")
    private Integer id;

    @Column(name = "FECHA_HORA_OCURRENCIA", nullable = false)
    private String fechaHora;

    @Column(name = "LATITUD_EPICENTRO", nullable = false)
    private double latitudEpicentro;

    @Column(name = "LATITUD_HIPOCENTRO", nullable = false)
    private double latitudHipocentro;

    @Column(name = "LONGITUD_EPICENTRO", nullable = false)
    private double longitudEpicentro;

    @Column(name = "LONGITUD_HIPOCENTRO", nullable = false)
    private double longitudHipocentro;

    @Column(name = "VALOR_MAGNITUD", nullable = false)
    private double valorMagnitud;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ALCANCE_ID", referencedColumnName = "ID_ALCANCE")
    private AlcanceSismo alcanceSismo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CLASIFICACION_ID", referencedColumnName = "ID_CLASIFICACION")
    private ClasificacionSismo clasificacionSismo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORIGEN_ID", referencedColumnName = "ID_ORIGEN")
    private OrigenDeGeneracion origenDeGeneracion;

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CambioEstado> historialCambios = new ArrayList<>();

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SerieTemporal> seriesTemporales = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private EstadoEventoSismico estado;



    public EventoSismico(String fechaHora, double latEpi, double latHipo,
                         double lonEpi, double lonHipo, double magnitud,
                         EstadoEventoSismico estadoInicial,
                         AlcanceSismo alcance, ClasificacionSismo clasificacion,
                         OrigenDeGeneracion origen) {

        this.fechaHora = fechaHora;
        this.latitudEpicentro = latEpi;
        this.latitudHipocentro = latHipo;
        this.longitudEpicentro = lonEpi;
        this.longitudHipocentro = lonHipo;
        this.valorMagnitud = magnitud;
        // Asegurar que siempre tengamos un estado lógico no nulo antes de usarlo
        EstadoEventoSismico estadoAUsar = (estadoInicial != null) ? estadoInicial : new models.estados.EstadoAutodetectado();
        this.estado = estadoAUsar;
        this.alcanceSismo = alcance;
        this.clasificacionSismo = clasificacion;
        this.origenDeGeneracion = origen;

        // Usamos la variable local para evitar NullPointerException
        this.setCambioEstadoActual(new CambioEstado(estadoAUsar.getNombreEstado(), LocalDateTime.now(), null));
    }

    public String getFechaHoraOcurrencia() { return fechaHora; }


    // ---------------- PATRÓN STATE (DELEGACIÓN) ----------------
    public void confirmar(LocalDateTime fechaHoraActual, Empleado analista) {
        if (estado == null) throw new IllegalStateException("Estado de evento no inicializado");
        estado.confirmar(this, fechaHoraActual, analista);
    }

    public void rechazarEvento(LocalDateTime fechaHoraActual, Empleado analista) {
        if (estado == null) throw new IllegalStateException("Estado de evento no inicializado");
        estado.rechazar(this, fechaHoraActual, analista);
    }

    public void solicitarRevisionExperto(LocalDateTime fechaHoraActual, Empleado analista) {
        if (estado == null) throw new IllegalStateException("Estado de evento no inicializado");
        estado.solicitarRevisionExperto(this, fechaHoraActual, analista);
    }

    public void bloquear(LocalDateTime fechaHoraActual) {
        if (estado == null) throw new IllegalStateException("Estado de evento no inicializado");
        estado.bloquear(this, fechaHoraActual);
    }

    public CambioEstado getCambioEstadoActual() {
        return historialCambios.getLast();
    }

    public void setCambioEstadoActual(CambioEstado nuevoCambio) {
        historialCambios.add(nuevoCambio);
        nuevoCambio.setEventoSismico(this);
    }

    public String getEstadoInterno() {
        return estado != null ? estado.getNombreEstado() : "Sin estado";
    }


    public boolean validarExistenciaDeDatosEvento() {
        return valorMagnitud > 0 && latitudEpicentro != 0 && longitudEpicentro != 0;
    }

    // ---------------- SERIES Y MUESTRAS (LÓGICA DE COLECCIÓN) ----------------

    public Map<EstacionSismologicaDTO, List<String>> obtenerMuestras() {
        Map<EstacionSismologicaDTO, List<String>> datosPorEstacion = new HashMap<>();

        for (SerieTemporal serie : this.seriesTemporales) {
            EstacionSismologicaDTO estacion = serie.getEstacion().toDto();
            if (estacion == null) {
                System.out.println("ADVERTENCIA: Serie temporal sin estación.");
                continue;
            }

            List<String> muestrasSerie = serie.obtenerMuestras();
            datosPorEstacion
                    .computeIfAbsent(estacion, k -> new ArrayList<>())
                    .addAll(muestrasSerie);
        }

        return datosPorEstacion;
    }


    // ---------------- DTO ----------------
    public DatosSismicosDTO buscarDatosSismicos() {
        return new DatosSismicosDTO(
                alcanceSismo.getNombre(),
                alcanceSismo.getDescripcion(),
                clasificacionSismo.getNombre(),
                String.valueOf(clasificacionSismo.getKmProfundidadDesde()),
                String.valueOf(clasificacionSismo.getKmProfundidadHasta()),
                origenDeGeneracion.getNombre(),
                origenDeGeneracion.getDescripcion()
        );
    }

    public EventoSismicoDTO toDTO() {
        return new EventoSismicoDTO(
                this.id,fechaHora, latitudEpicentro, longitudEpicentro,
                latitudHipocentro, longitudHipocentro, valorMagnitud,
                getEstadoInterno(),
                alcanceSismo != null ? alcanceSismo.getNombre() : "Desconocido",
                clasificacionSismo != null ? clasificacionSismo.getNombre() : "Desconocida",
                origenDeGeneracion != null ? origenDeGeneracion.getNombre() : "Desconocido"
        );
    }



    // ================== toString ==================
    @Override
    public String toString() {
        return """
                Evento Sismico
                - Fecha y hora: %s
                - Magnitud: %.1f
                - Epicentro: (%.2f, %.2f)
                - Hipocentro: (%.2f, %.2f)
                - Estado actual: %s
                - Alcance: %s
                - Clasificacion: %s
                - Origen: %s
                """.formatted(
                fechaHora, valorMagnitud,
                latitudEpicentro, longitudEpicentro,
                latitudHipocentro, longitudHipocentro,
                getEstadoInterno(),
                alcanceSismo != null ? alcanceSismo.getNombre() : "Desconocido",
                clasificacionSismo != null ? clasificacionSismo.getNombre() : "Desconocida",
                origenDeGeneracion != null ? origenDeGeneracion.getNombre() : "Desconocido"
        );
    }
}