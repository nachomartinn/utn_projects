package models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat; // Importar
import java.util.Map;

@Entity
@Table(name = "SERIE_TEMPORAL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SerieTemporal {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_SERIE_TEMPORAL")
    private Integer id;
    @Column(name = "CONDICION_ALARMA", nullable = false)
    private String condicionAlarma;
    @Column(name = "FECHA_HORA_REGISTRO", nullable = false)
    private Date fechaHoraRegistro;
    @Column(name = "FECHA_HORA_INICIO_REGISTRO_MUESTRA", nullable = false)
    private Date fechaHoraInicioRegistroMuestra;
    @Column(name = "FRECUENCIA_MUESTREO", nullable = false)
    private float frecuenciaMuestreo;
    @OneToMany(mappedBy = "serieTemporal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MuestraSismica> muestraSismica;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SISMOGRAFO_ID", referencedColumnName = "ID_SISMOGRAFO", nullable = false)
    private Sismografo sismografo;
    @ManyToOne
    @JoinColumn(name = "ID_EVENTO_SISMICO") // Usa el nombre real de tu columna FK en la base de datos
    private EventoSismico eventoSismico;


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

    public List<String> obtenerMuestras() {
        List<String> muestrasFormateadas = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (MuestraSismica muestra : muestraSismica) {
            String linea = String.format("%s | %s",
                    muestra.getFechaHoraMuestra(),
                    muestra.getDatosFormateados()
            );
            muestrasFormateadas.add(linea);
        }

        return muestrasFormateadas;
    }

    public EstacionSismologica getEstacion() {
        if (this.sismografo != null) {
            return this.sismografo.getEstacionSismologica();
        }
        return null;
    }

    public Sismografo getSismografo() {
        return sismografo;
    }

    public List<MuestraSismica> getMuestras() { // <-- MÉTODO CLAVE AÑADIDO
        return muestraSismica;
    }

    public Date getFechaHoraRegistro() { // <-- MÉTODO CLAVE AÑADIDO
        return fechaHoraRegistro;
    }

    public Date getFechaHoraInicioMuestreo() { // <-- MÉTODO CLAVE AÑADIDO (Usa el campo fechaHoraInicioRegistroMuestra)
        return fechaHoraInicioRegistroMuestra;
    }

    public void setEventoSismico(EventoSismico eventoSismico) {
        // Implementación corregida: asigna la referencia en el lado propietario (ManyToOne)
        this.eventoSismico = eventoSismico;
        // Mantener sincronizada la colección del Evento (lado inverso)
        if (eventoSismico != null) {
            if (eventoSismico.getSeriesTemporales() == null) {
                // Asegurar que la lista exista
                eventoSismico.setSeriesTemporales(new java.util.ArrayList<>());
            }
            if (!eventoSismico.getSeriesTemporales().contains(this)) {
                eventoSismico.getSeriesTemporales().add(this);
            }
        }
    }

}
