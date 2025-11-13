package models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SISMOGRAFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sismografo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_SISMOGRAFO")
    private Integer id;
    @Column(name = "FECHA_ADQUICICION")
    private String fechaAdquicicion;
    @Column(name = "NRO_SERIE")
    private int nroSerie;
    @OneToMany(mappedBy = "sismografo", cascade = CascadeType.ALL)
    private List<SerieTemporal> serieTemporales;
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ESTACION_SISMOLOGICA_ID", referencedColumnName = "ID_ESTACION_SISMOLOGICA", nullable = false)
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

