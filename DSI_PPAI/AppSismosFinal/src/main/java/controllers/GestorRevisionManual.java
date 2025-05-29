package controllers;

import models.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class GestorRevisionManual {

    private final ArrayList<EventoSismico> eventos = new ArrayList<>();
    private final ArrayList<Estado> estados = new ArrayList<>();
    private final ArrayList<AlcanceSismo> alcances = new ArrayList<>();
    private final ArrayList<ClasificacionSismo> clasificaciones = new ArrayList<>();
    private final ArrayList<OrigenDeGeneracion> origenes = new ArrayList<>();
    private ArrayList<EventoSismico> eventosAutodetectados;
    private ArrayList<EventoSismico> eventosOrdenados;
    private EventoSismico eventoSeleccionado;
    private Estado estadoBloqueadoEnRevision;
    private PantallaRevisionManual pantalla;
    private Estado estadoRechazado;
    private Sesion sesion;
    private Empleado analista;
    private Estado estadoConfirmado;
    private Estado estadoSolicitarRevisionExperto;
    private GenerarSismograma sismograma = new GenerarSismograma();

    public GestorRevisionManual() {
        inicializarEstados();
        inicializarAlcance();
        inicializarClasificacion();
        inicializarOrigen();
        inicializarEventos();
        inicializarSesion();
    }


    private void inicializarEstados() {
        estados.add(new Estado("Autodetectado", "Es estado autodetectado", "Evento Sismico"));
        estados.add(new Estado("Rechazado", "Es estado rechazado", "Evento Sismico"));
        estados.add(new Estado("Bloqueado En Revision", "Es estado bloqueado en revisión", "Evento Sismico"));
        estados.add(new Estado("Confirmado", "Es estado confirmado", "Evento Sismico"));
        estados.add(new Estado("Solicitar Revision Experto", "Es estado solicitar revision experto", "Evento Sismico"));
    }

    private void inicializarAlcance() {
        alcances.add(new AlcanceSismo("Local", "Hasta 100 km"));
        alcances.add(new AlcanceSismo("Regional", "Hasta 1000 km"));
        alcances.add(new AlcanceSismo("Telesismo", "Más de 1000 km"));
    }

    private void inicializarClasificacion() {
        clasificaciones.add(new ClasificacionSismo(0, 60, "Superficial"));
        clasificaciones.add(new ClasificacionSismo(61, 300, "Intermedio"));
        clasificaciones.add(new ClasificacionSismo(301, 650, "Profundo"));
    }

    private void inicializarOrigen() {
        origenes.add(new OrigenDeGeneracion("Sismo generado por interacción de placas tectónicas.", "Interplaca"));
        origenes.add(new OrigenDeGeneracion("Sismo causado por actividad volcánica.", "Volcánico"));
        origenes.add(new OrigenDeGeneracion("Sismo causado por explosiones en minas subterráneas.", "Explosión de mina"));
    }

    private void inicializarEventos() {
        eventos.add(new EventoSismico("2019-06-12 03:45", -33.5, -35.2, -60.3, -61.0, 3.3,
                estados.get(0), alcances.get(0), clasificaciones.get(0), origenes.get(0), null));

        eventos.add(new EventoSismico("2021-01-25 14:22", -32.8, -34.0, -59.5, -60.1, 1.1,
                estados.get(0), alcances.get(1), clasificaciones.get(1), origenes.get(1), null));

        eventos.add(new EventoSismico("2023-03-03 09:10", -31.2, -33.9, -58.9, -59.3, 3.7,
                estados.get(2), alcances.get(0), clasificaciones.get(0), origenes.get(2), null));

        eventos.add(new EventoSismico("2022-07-18 19:47", -34.1, -36.0, -62.4, -63.2, 2.9,
                estados.get(0), alcances.get(1), clasificaciones.get(1), origenes.get(0), null));

        eventos.add(new EventoSismico("2020-12-30 06:15", -30.5, -32.0, -57.7, -58.6, 3.8,
                estados.get(1), alcances.get(0), clasificaciones.get(0), origenes.get(1), null));

        eventos.add(new EventoSismico("2024-08-09 11:59", -29.0, -31.3, -56.0, -57.4, 1.6,
                estados.get(0), alcances.get(1), clasificaciones.get(1), origenes.get(2), null));

        eventos.add(new EventoSismico("2023-05-22 21:05", -33.3, -35.1, -60.8, -61.5, 3.0,
                estados.get(0), alcances.get(2), clasificaciones.get(2), origenes.get(0), null));

        eventos.add(new EventoSismico("2025-02-14 02:30", -32.0, -33.5, -59.1, -59.9, 2.5,
                estados.get(0), alcances.get(0), clasificaciones.get(0), origenes.get(2), null));

        eventos.add(new EventoSismico("2021-09-07 17:18", -31.7, -33.0, -58.3, -59.0, 3.3,
                estados.get(0), alcances.get(1), clasificaciones.get(1), origenes.get(1), null));

        eventos.add(new EventoSismico("2022-11-28 10:40", -34.4, -36.2, -62.9, -63.8, 3.6,
                estados.get(1), alcances.get(2), clasificaciones.get(2), origenes.get(0), null));
    }

    private void inicializarSesion() {
        this.sesion = new Sesion(getFechaHoraActual(),
                new Usuario("AnalistaSismos", "RojoSosMiVida7L",
                        new Empleado("Felipe", "Loyola", 351223443, "felipeloyola@21gmail.com",
                                new Rol("AnalistaSismos", "Analista en Sismos"))));
    }


    public void setPantalla(PantallaRevisionManual pantalla) {
        this.pantalla = pantalla;
    }


    public void opcionRegistrarRevisionManual() {
        buscarEventosSismicosAutodetectados();
        ordenarEventosSismicos();
        pantalla.mostrarDatosEventosSismicos(eventosOrdenados);

    }

    public void buscarEventosSismicosAutodetectados() {
        eventosAutodetectados = new ArrayList<>();
        for (EventoSismico evento : eventos) {
            if (evento.estaAutodetectado()) {
                eventosAutodetectados.add(evento);
            }
        }
    }

    private void ordenarEventosSismicos() {
        eventosOrdenados = new ArrayList<>(eventosAutodetectados);
        eventosOrdenados.sort((e1, e2) -> e1.getFechaHoraOcurrencia().compareTo(e2.getFechaHoraOcurrencia()));
    }

    public void tomarSeleccionDeEvento(EventoSismico evento) {
        this.eventoSeleccionado = evento;
        buscarEstadoBloqueadoEnRevision();

        if (estadoBloqueadoEnRevision != null) {
            LocalDateTime fechaHoraActual = getFechaHoraActual();
            eventoSeleccionado.bloquearEventoSismico(fechaHoraActual, estadoBloqueadoEnRevision);
        } else {
            System.out.println("No se encontró el estado 'Bloqueado En Revisión'");
        }
        llamarCUGenerarSismograma();
        //obtenerMuestras();
        //ACA QUEDAMOS CON LA LINEA DE EJECUCION
        // HAY QUE VER COMO ORDENAMOS LAS SERIES TEMPORALES POR ESTACION SISMOLOGICA
    }

    public void obtenerMuestras() {
        eventoSeleccionado.obtenerMuestras();
    }

    public void ordenarPorEstacionSismologica() {

    }

    public void buscarEstadoBloqueadoEnRevision() {
        for (Estado estado : estados) {
            if (estado.sosAmbitoEventoSismico() && estado.sosBloqueadoEnRevision()) {
                estadoBloqueadoEnRevision = estado;
                break;
            }
        }
    }

    public ArrayList<Map.Entry<String, Map.Entry<String, String>>> buscarDatosSismicos() {
        if (eventoSeleccionado != null) {
            return eventoSeleccionado.buscarDatosSismicos();
        } else {
            return new ArrayList<>();
        }
    }

    public LocalDateTime getFechaHoraActual() {
        return LocalDateTime.now();
    }

    public void tomarOpcionRechazarEvento() {
        boolean existenDatos = validarExistenciaDeDatosEvento();
        if (existenDatos) {
            buscarEstadoRechazado();
            buscarASLogueado();
            rechazarEventoSismico();
            System.out.println(eventoSeleccionado);
        }
    }

    public boolean validarExistenciaDeDatosEvento() {
        return eventoSeleccionado.validarExistenciaDeDatosEvento();
    }

    public void buscarEstadoRechazado() {
        for (Estado estado : estados) {
            if (estado.sosAmbitoEventoSismico() && estado.sosRechazado()) {
                estadoRechazado = estado;
                break;
            }
        }

    }

    public void buscarASLogueado() {
        analista = this.sesion.obtenerASLogueado();
    }

    public void rechazarEventoSismico() {
        eventoSeleccionado.rechazarEventoSismico(getFechaHoraActual(), estadoRechazado, this.analista);

    }

    // Alternativa para confirmar evento sismico
    public void tomarOpcionConfirmarEvento() {
        boolean existenDatos = validarExistenciaDeDatosEvento();
        if (existenDatos) {
            buscarEstadoConfirmado();
            buscarASLogueado();
            confirmarEventoSismico();
            System.out.println(eventoSeleccionado);
        }
    }

    private void buscarEstadoConfirmado() {
        for (Estado estado : estados) {
            if (estado.sosAmbitoEventoSismico() && estado.sosConfirmado()) {
                estadoConfirmado = estado;
                break;
            }
        }
    }

    private void confirmarEventoSismico() {
        eventoSeleccionado.confirmarEventoSismico(getFechaHoraActual(), estadoConfirmado, this.analista);
    }

    // Alternativa para solicitar revisión por experto

    public void tomarOpcionSolicitarRevisionExperto() {
        boolean existenDatos = validarExistenciaDeDatosEvento();
        if (existenDatos) {
            buscarEstadoSolicitarRevisionExperto();
            buscarASLogueado();
            solicitarRevisionExperto();
            System.out.println(eventoSeleccionado);
        }
    }

    public void buscarEstadoSolicitarRevisionExperto() {
        for (Estado estado : estados) {
            if (estado.sosAmbitoEventoSismico() && estado.sosSolicitarRevisionExperto()) {
                estadoSolicitarRevisionExperto = estado;
                break;
            }
        }
    }

    private void solicitarRevisionExperto() {
        eventoSeleccionado.solicitarRevisionExperto(getFechaHoraActual(), estadoSolicitarRevisionExperto, this.analista);
    }


    public void llamarCUGenerarSismograma() {
        sismograma.include();
    }

}