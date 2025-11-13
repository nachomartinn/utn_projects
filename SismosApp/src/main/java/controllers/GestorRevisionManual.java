package controllers;

import dto.DatosSismicosDTO;
import dto.EstacionSismologicaDTO;
import lombok.Setter;
import models.*;
import dto.EventoSismicoDTO;
import service.EventoService;
import service.SesionService;

import java.time.format.DateTimeFormatter;


import java.time.LocalDateTime;
import java.util.*;

public class GestorRevisionManual {

    // --- ESTADO DEL CONTROLADOR (FLUIDO DEL CASO DE USO) ---
    private EventoSismico eventoSeleccionado; // Entidad usada para el flujo activo
    private List<EventoSismico> eventosAutodetectados = new ArrayList<>();
    private List<EventoSismico> eventosOrdenados;

    // --- DEPENDENCIAS Y AUXILIARES ---
    @Setter
    private PantallaRevisionManual pantalla;
    private Empleado analista;
    private final GenerarSismograma sismograma = new GenerarSismograma(); // CU externo
    private SesionService sesionService;
    private final EventoService eventoService;


    // Mapa para rastrear el evento seleccionado entre DTO (Vista) y Entidad (Modelo)
    private final Map<EventoSismicoDTO, EventoSismico> mapaDtoAEvento = new HashMap<>();


    // ===================== CONSTRUCTOR Y SETUP =====================

    public GestorRevisionManual(EventoService eventoService, SesionService sesionService) {
        this.sesionService = sesionService;
        this.eventoService = eventoService;
    }


    // ===================== MÉTODOS DE CASO DE USO =====================

    public void opcionRegistrarRevisionManual() {
        buscarEventosSismicosAutodetectados();
        ordenarEventosSismicos();
        List<EventoSismicoDTO> eventosDTO = convertirADTO(eventosOrdenados);
        if (pantalla != null) {
            pantalla.mostrarDatosEventosSismicos(eventosDTO);
        }

        // --- Presentación limpia en la terminal ---
        System.out.println("EVENTOS SISMICOS AUTODETECTADOS DISPONIBLES");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        int i = 1;

        for (EventoSismico e : eventosOrdenados) {
            System.out.printf(
                    "%2d) Fecha/Hora: %s | Magnitud: %.1f%n    Epicentro (lat, lon): (%.2f, %.2f)%n    Hipocentro (lat, lon): (%.2f, %.2f)%n%n",
                    i++,
                    e.getFechaHoraOcurrencia(),
                    e.getValorMagnitud(),
                    e.getLatitudEpicentro(), e.getLongitudEpicentro(),
                    e.getLatitudHipocentro(), e.getLongitudHipocentro()
            );
        }
        System.out.println("Seleccione un evento para continuar...");
    }


    private void buscarEventosSismicosAutodetectados() {
        // Buscar los eventos autodetectados desde el servicio
        eventosAutodetectados = eventoService.findEventosAutodetectados();
    }

    private void ordenarEventosSismicos() {
        eventosOrdenados = new ArrayList<>(eventosAutodetectados);
        eventosOrdenados.sort(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia));
    }



    public void tomarSeleccionDeEvento(EventoSismicoDTO eventoDTO) {
        if (eventoDTO == null) {
            System.out.println("No se selecciono ningun evento.");
            return;
        }

        EventoSismico encontrado = mapaDtoAEvento.get(eventoDTO);
        if (encontrado == null) {
            System.out.println("No se encontro el evento seleccionado. Refresque la lista.");
            return;
        }
        eventoSeleccionado = encontrado;
        // 1. BLOQUEAR: Llama al Service para ejecutar la lógica de negocio y persistir el cambio
        eventoService.bloquearEventoSismico(eventoSeleccionado.getId(), getFechaHoraActual());

        // 2. Continuar con el flujo de presentación de datos
        buscarDatosSismicos(eventoSeleccionado);
        llamarCUGenerarSismograma();

        // 3. Mostrar detalle de muestras por estación
        Map<EstacionSismologicaDTO, List<String>> muestras = obtenerMuestras();
        pantalla.mostrarDetalleMuestrasPorEstacion(muestras);


    }


    // ===================== LÓGICA DE DETALLE Y MUESTRAS =====================

    private void buscarDatosSismicos(EventoSismico eventoSeleccionado) {
        DatosSismicosDTO datos = eventoSeleccionado.buscarDatosSismicos();
        pantalla.mostrarDatosSismicos(datos);
    }


    private Map<EstacionSismologicaDTO, List<String>> obtenerMuestras() {
        return eventoSeleccionado.obtenerMuestras();
    }

    // ===================== ACCIONES DE CAMBIO DE ESTADO =====================

    public void tomarOpcionRechazarEvento() {
        if (eventoSeleccionado == null) return;
        buscarASLogueado();
        rechazarEventoSismico();
    }

    public void rechazarEventoSismico() {
        LocalDateTime fechaHora = getFechaHoraActual();
        eventoService.rechazarEvento(eventoSeleccionado.getId(), fechaHora, analista);
        System.out.println("Evento rechazado correctamente: " + eventoSeleccionado.getId());
        System.out.println(eventoSeleccionado);
    }

    public void tomarOpcionConfirmarEvento() {
        if (eventoSeleccionado == null) return;
        buscarASLogueado();
        eventoService.confirmarEvento(eventoSeleccionado.getId(), getFechaHoraActual(), analista);

        System.out.println("Evento confirmado correctamente: " + eventoSeleccionado.getId());
        System.out.println(eventoSeleccionado);
    }

    public void tomarOpcionSolicitarRevisionExperto() {
        if (eventoSeleccionado == null) return;
        buscarASLogueado();
        eventoService.solicitarRevisionExperto(eventoSeleccionado.getId(), getFechaHoraActual(), analista);

        System.out.println("Revision a experto solicitada: ");
        System.out.println(eventoSeleccionado);
    }

    // ===================== MÉTODOS AUXILIARES =====================

    public List<EventoSismicoDTO> convertirADTO(List<EventoSismico> eventos) {
        mapaDtoAEvento.clear();
        List<EventoSismicoDTO> lista = new ArrayList<>();
        if (eventos == null) return lista;

        for (EventoSismico e : eventos) {
            EventoSismicoDTO dto = e.toDTO();
            lista.add(dto);
            mapaDtoAEvento.put(dto, e);
        }

        return lista;
    }


    public void llamarCUGenerarSismograma() {
        sismograma.include();
    }


    public void buscarASLogueado() {
        this.analista = sesionService.obtenerASLogueado();
        if (analista == null) {
            throw new IllegalStateException("La sesión activa no pertenece a un Analista de Sismos.");
        }
    }

    public LocalDateTime getFechaHoraActual() {
        return LocalDateTime.now();
    }

    public List<EventoSismicoDTO> refrescarEventos() {
        return convertirADTO(eventosOrdenados);
    }
}