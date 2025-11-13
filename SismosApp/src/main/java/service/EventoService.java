package service;

import dto.EventoSismicoDTO;
import models.*;
import models.estados.EstadoEventoSismico;
import repository.EventoSismicoRepository;
import repository.AlcanceSismoRepository;
import repository.ClasificacionSismoRepository;
import repository.OrigenDeGeneracionRepository;
import java.time.LocalDateTime;
import java.util.*;

public class EventoService {

    private final EventoSismicoRepository eventoRepository;
    private final AlcanceSismoRepository alcanceSismoRepository;
    private final ClasificacionSismoRepository clasificacionSismoRepository;
    private final OrigenDeGeneracionRepository origenDeGeneracionRepository;


    public EventoService(EventoSismicoRepository eventoRepository, AlcanceSismoRepository alcanceSismoRepository, ClasificacionSismoRepository clasificacionSismoRepository, OrigenDeGeneracionRepository origenDeGeneracionRepository) {
        this.eventoRepository = eventoRepository;
        this.alcanceSismoRepository = alcanceSismoRepository;
        this.clasificacionSismoRepository = clasificacionSismoRepository;
        this.origenDeGeneracionRepository = origenDeGeneracionRepository;
    }


    public EventoSismico crearEventoSismico(
            String fechaHora, double latEpi, double latHipo, double lonEpi, double lonHipo, double magnitud,
            Integer alcanceId, Integer clasificacionId, Integer origenId, String nombreEstadoInicial) { // 👈 NUEVO PARÁMETRO

        AlcanceSismo alcance = alcanceSismoRepository.findById(alcanceId);
        ClasificacionSismo clasificacion = clasificacionSismoRepository.findById(clasificacionId);
        OrigenDeGeneracion origen = origenDeGeneracionRepository.findById(origenId);


        EstadoEventoSismico estadoInicial = eventoRepository.findOrCreateEstadoReference(nombreEstadoInicial);

        EventoSismico nuevoEvento = new EventoSismico(
                fechaHora, latEpi, latHipo, lonEpi, lonHipo, magnitud,
                estadoInicial, alcance, clasificacion, origen
        );

        if (!nuevoEvento.validarExistenciaDeDatosEvento()) {
            throw new IllegalArgumentException("Los datos sísmicos son incompletos para el registro.");
        }

        return eventoRepository.save(nuevoEvento);
    }


    public EventoSismico crearEventoSismico(
            String fechaHora, double latEpi, double latHipo, double lonEpi, double lonHipo, double magnitud,
            Integer alcanceId, Integer clasificacionId, Integer origenId) {

        AlcanceSismo alcance = alcanceSismoRepository.findById(alcanceId);
        ClasificacionSismo clasificacion = clasificacionSismoRepository.findById(clasificacionId);
        OrigenDeGeneracion origen = origenDeGeneracionRepository.findById(origenId);
        EstadoEventoSismico estadoInicial = eventoRepository.findOrCreateEstadoReference("Autodetectado");

        if (estadoInicial == null) {
            throw new IllegalStateException("Error de inicialización: El estado lógico '" + estadoInicial + "' no pudo ser creado. Verifique EstadoFactory y EventoRepository.");
        }

        EventoSismico nuevoEvento = new EventoSismico(
                fechaHora, latEpi, latHipo, lonEpi, lonHipo, magnitud,
                estadoInicial, alcance, clasificacion, origen
        );

        if (!nuevoEvento.validarExistenciaDeDatosEvento()) {
            throw new IllegalArgumentException("Los datos sísmicos son incompletos para el registro.");
        }
        return eventoRepository.save(nuevoEvento);
    }


    // CAMBIOS DE ESTADO (PATRÓN STATE)

    public void confirmarEvento(Integer id, LocalDateTime fechaHora, Empleado analista) {
        EventoSismico evento = eventoRepository.findById(id);
        if (evento == null) throw new NoSuchElementException("No se encontró el evento con id: " + id);
        evento.confirmar(fechaHora, analista);
        eventoRepository.update(evento);
    }

    public void rechazarEvento(Integer id, LocalDateTime fechaHora, Empleado analista) {
        EventoSismico evento = eventoRepository.findById(id);
        if (evento == null) throw new NoSuchElementException("No se encontró el evento con id: " + id);
        evento.rechazarEvento(fechaHora, analista);
        eventoRepository.update(evento);
    }

    public void solicitarRevisionExperto(Integer id, LocalDateTime fechaHora, Empleado analista) {
        EventoSismico evento = eventoRepository.findById(id);
        if (evento == null) throw new NoSuchElementException("No se encontró el evento con id: " + id);
        evento.solicitarRevisionExperto(fechaHora, analista);
        eventoRepository.update(evento);
    }

    public void bloquearEventoSismico(Integer id, LocalDateTime fechaHora) {
        EventoSismico evento = eventoRepository.findById(id);
        if (evento == null) throw new NoSuchElementException("No se encontró el evento con id: " + id);
        evento.bloquear(fechaHora);
        eventoRepository.update(evento);
    }

    public List<EventoSismico> findEventosAutodetectados() {
        return eventoRepository.findByEstadoAutodetectado();
    }

}