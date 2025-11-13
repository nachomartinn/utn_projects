package dto;

public class DatosSismicosDTO {
    private String nombreAlcance;
    private String descripcionAlcance;
    private String clasificacion;
    private String profundidadDesde;
    private String profundidadHasta;
    private String nombreOrigen;
    private String descripcionOrigen;

    public DatosSismicosDTO(String nombreAlcance, String descripcionAlcance,
                            String clasificacion, String profundidadDesde, String profundidadHasta,
                            String nombreOrigen, String descripcionOrigen) {
        this.nombreAlcance = nombreAlcance;
        this.descripcionAlcance = descripcionAlcance;
        this.clasificacion = clasificacion;
        this.profundidadDesde = profundidadDesde;
        this.profundidadHasta = profundidadHasta;
        this.nombreOrigen = nombreOrigen;
        this.descripcionOrigen = descripcionOrigen;
    }

    // Getters
    public String getNombreAlcance() { return nombreAlcance; }
    public String getDescripcionAlcance() { return descripcionAlcance; }
    public String getClasificacion() { return clasificacion; }
    public String getProfundidadDesde() { return profundidadDesde; }
    public String getProfundidadHasta() { return profundidadHasta; }
    public String getNombreOrigen() { return nombreOrigen; }
    public String getDescripcionOrigen() { return descripcionOrigen; }
}