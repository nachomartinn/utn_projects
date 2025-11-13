package seeder;

import models.*;

import models.estados.*;
import repository.AlcanceSismoRepository;
import repository.ClasificacionSismoRepository;
import repository.OrigenDeGeneracionRepository;
import repository.RolRepository;
import repository.db.DbContext;
import service.EventoService;
import repository.UsuarioRepository;
import repository.SesionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataSeeder {

    private final AlcanceSismoRepository alcanceRepo;
    private final ClasificacionSismoRepository clasificacionRepo;
    private final OrigenDeGeneracionRepository origenRepo;
    private final EventoService eventoService;
    private final RolRepository rolRepo;
    private final UsuarioRepository usuarioRepo;
    private final SesionRepository sesionRepo;

    public DataSeeder(AlcanceSismoRepository alcanceRepo,
                      ClasificacionSismoRepository clasificacionRepo, OrigenDeGeneracionRepository origenRepo,
                      EventoService eventoService, RolRepository rolRepo,UsuarioRepository usuarioRepo,SesionRepository sesionRepo) {
        this.alcanceRepo = alcanceRepo;
        this.clasificacionRepo = clasificacionRepo;
        this.origenRepo = origenRepo;
        this.eventoService = eventoService;
        this.rolRepo = rolRepo;
        this.usuarioRepo = usuarioRepo;
        this.sesionRepo = sesionRepo;
    }

    public void seedAll() {
        try {
            seedClasificaciones();
            seedAlcances();
            seedOrigenes();
            seedEventosDeEjemplo();
            seedAnalistaYSession();
        } catch (Exception ex) {
            System.err.println("Warning: falla al intentar poblar la BD con datos de ejemplo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void seedAlcances() {
        alcanceRepo.save(new AlcanceSismo("Local", "Hasta 100 km"));
        alcanceRepo.save(new AlcanceSismo("Regional", "Hasta 1000 km"));
    }

    private void seedClasificaciones() {
        clasificacionRepo.save(new ClasificacionSismo(0, 70, "Superficial"));
        clasificacionRepo.save(new ClasificacionSismo(71, 300, "Profundo"));
    }

    private void seedOrigenes() {
        origenRepo.save(new OrigenDeGeneracion("Origen natural", "Natural"));
        origenRepo.save(new OrigenDeGeneracion("Origen artificial", "Artificial"));
    }

    private void seedEventosDeEjemplo() throws Exception {
        AlcanceSismo alcanceLocal = alcanceRepo.getByName("Local");
        ClasificacionSismo clasificacionSuperficial = clasificacionRepo.getByName("Superficial");
        OrigenDeGeneracion origenNatural = origenRepo.getByName("Natural");

        Integer alcanceId = alcanceLocal.getId();
        Integer clasificacionId = clasificacionSuperficial.getId();
        Integer origenId = origenNatural.getId();


        EntityManager em = DbContext.getInstance().getEntityManager();


        //  CREAR EVENTOS AUTO-DETECTADOS

        eventoService.crearEventoSismico("2019-06-12 03:45", -34.6, -34.7, -58.4, -58.5, 4.2,
                alcanceId, clasificacionId, origenId);
        eventoService.crearEventoSismico("2021-01-25 14:22", -35.0, -35.1, -59.0, -59.1, 5.1,
                alcanceId, clasificacionId, origenId);
        eventoService.crearEventoSismico("2023-03-03 09:10", -36.0, -36.1, -62.0, -62.1, 7.0,
                alcanceId, clasificacionId, origenId);
        eventoService.crearEventoSismico("2022-07-18 19:47", -37.2, -37.3, -63.0, -63.2, 3.5,
                alcanceId, clasificacionId, origenId);
        eventoService.crearEventoSismico("2020-12-30 06:15", -38.1, -38.2, -64.5, -64.6, 4.8,
                alcanceId, clasificacionId, origenId);
        eventoService.crearEventoSismico("2024-08-09 11:59", -29.0, -31.3, -56.0, -57.4, 1.6,alcanceId, clasificacionId, origenId, "Rechazado");
        eventoService.crearEventoSismico("2023-05-22 21:05", -33.3, -35.1, -60.8, -61.5, 3.0, alcanceId, clasificacionId, origenId);
        System.out.println("Seed: eventos creados.");


        // === CREAR EVENTOS DE OTROS ESTADOS

        eventoService.crearEventoSismico("2023-08-09 11:59", -29.0, -31.3, -56.0, -57.4, 1.6,alcanceId, clasificacionId, origenId, "Rechazado");
        eventoService.crearEventoSismico("2024-04-02 15:59", -33.0, -33.1, -65.0, -65.1, 3.8,
                alcanceId, clasificacionId, origenId, "Confirmado");
        eventoService.crearEventoSismico("2022-02-20 10:31", -31.0, -31.1, -67.0, -67.1, 2.1,
                alcanceId, clasificacionId, origenId, "Rechazado");
        eventoService.crearEventoSismico("2025-05-05 09:01", -30.0, -30.1, -69.0, -69.1, 6.0,
                alcanceId, clasificacionId, origenId, "En Revisión de Experto");


        System.out.println("Seed: Eventos confirmados, rechazados y en revisión creados.");

        // ============================================================
        // === ASOCIAR SERIES Y MUESTRAS A CADA AUTO-DETECTADO ========
        // ============================================================

        List<EventoSismico> eventosAutodetectados = eventoService.findEventosAutodetectados();

        if (eventosAutodetectados != null && !eventosAutodetectados.isEmpty()) {
            for (EventoSismico evento : eventosAutodetectados) {
                EntityTransaction tx = em.getTransaction();
                try {
                    tx.begin();
                    EventoSismico eventoManejado = em.find(EventoSismico.class, evento.getId());
                    if (eventoManejado != null) {
                        seedMuestrasYSeries(em, eventoManejado);
                        System.out.println("Seed: series y muestras asociadas al Evento " + eventoManejado.getId());
                    } else {
                        System.err.println("No se encontró el evento con ID " + evento.getId() + " en el contexto.");
                    }
                    tx.commit();
                } catch (Exception ex) {
                    if (tx.isActive()) tx.rollback();
                    throw ex;
                }
            }
        } else {
            System.err.println("No se encontraron eventos autodetectados para asociar muestras.");
        }
    }



    private void seedMuestrasYSeries(EntityManager em, EventoSismico eventoCreado) {
        // 1) Tipos de dato (solo si no existen, para evitar duplicados)
        TipoDato tipoVelocidad = new TipoDato("m/s", 0, "Velocidad de onda");
        TipoDato tipoFrecuencia = new TipoDato("Hz", 0, "Frecuencia de onda");
        TipoDato tipoLongitud = new TipoDato("nm", 0, "Longitud");
        em.persist(tipoVelocidad);
        em.persist(tipoFrecuencia);
        em.persist(tipoLongitud);

        // 2) Estaciones únicas por evento
        int codigoEst1 = 1000 + eventoCreado.getId() * 2;
        int codigoEst2 = 1001 + eventoCreado.getId() * 2;

        EstacionSismologica est1 = new EstacionSismologica(
                codigoEst1,
                "Estación Norte " + eventoCreado.getId(),
                -34.60f + eventoCreado.getId() * 0.01f,
                -58.40f - eventoCreado.getId() * 0.01f
        );

        EstacionSismologica est2 = new EstacionSismologica(
                codigoEst2,
                "Estación Sur " + eventoCreado.getId(),
                -34.70f + eventoCreado.getId() * 0.01f,
                -58.50f - eventoCreado.getId() * 0.01f
        );

        em.persist(est1);
        em.persist(est2);

        // 3) Sismógrafos asociados a las estaciones
        Sismografo s1 = new Sismografo("07/09/2022 17:35:41", 1100 + eventoCreado.getId());
        s1.setEstacionSismologica(est1);
        em.persist(s1);

        Sismografo s2 = new Sismografo("07/09/2022 18:00:41", 1200 + eventoCreado.getId());
        s2.setEstacionSismologica(est2);
        em.persist(s2);

        // 4) Muestras para la estación 1
        List<DetalleMuestraSismica> detalles1 = new ArrayList<>();
        MuestraSismica m1 = new MuestraSismica("07/09/2022 18:15:41", detalles1);

        DetalleMuestraSismica d1 = new DetalleMuestraSismica(123.0, tipoVelocidad);
        d1.setMuestraSismica(m1);
        em.persist(d1);
        detalles1.add(d1);

        DetalleMuestraSismica d2 = new DetalleMuestraSismica(4.5, tipoFrecuencia);
        d2.setMuestraSismica(m1);
        em.persist(d2);
        detalles1.add(d2);

        DetalleMuestraSismica d3 = new DetalleMuestraSismica(0.85, tipoLongitud);
        d3.setMuestraSismica(m1);
        em.persist(d3);
        detalles1.add(d3);

        // 5) Muestras para la estación 2
        List<DetalleMuestraSismica> detalles2 = new ArrayList<>();
        MuestraSismica m2 = new MuestraSismica("07/09/2023 18:10:41", detalles2);

        DetalleMuestraSismica d4 = new DetalleMuestraSismica(200.0, tipoVelocidad);
        d4.setMuestraSismica(m2);
        em.persist(d4);
        detalles2.add(d4);

        DetalleMuestraSismica d5 = new DetalleMuestraSismica(6.7, tipoFrecuencia);
        d5.setMuestraSismica(m2);
        em.persist(d5);
        detalles2.add(d5);

        DetalleMuestraSismica d6 = new DetalleMuestraSismica(1.23, tipoLongitud);
        d6.setMuestraSismica(m2);
        em.persist(d6);
        detalles2.add(d6);

        // 6) Series temporales (una por estación)
        List<MuestraSismica> muestrasSerie1 = new ArrayList<>();
        muestrasSerie1.add(m1);
        SerieTemporal serie1 = new SerieTemporal("False", new Date(), new Date(), 100.0f, s1, muestrasSerie1);
        serie1.setEventoSismico(eventoCreado);
        m1.setSerieTemporal(serie1);
        em.persist(serie1);

        List<MuestraSismica> muestrasSerie2 = new ArrayList<>();
        muestrasSerie2.add(m2);
        SerieTemporal serie2 = new SerieTemporal("False", new Date(), new Date(), 50.0f, s2, muestrasSerie2);
        serie2.setEventoSismico(eventoCreado);
        m2.setSerieTemporal(serie2);
        em.persist(serie2);
    }
    // Dentro de DataSeeder.java

    private void seedAnalistaYSession() throws Exception {
        // 1. Crear el Rol (Si no existe)
        Rol rolAnalista = rolRepo.findByName("AnalistaSismos") // Asegúrate de usar el nombre correcto del rol
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol("AnalistaSismos", "Analista en Sismos");
                    rolRepo.save(nuevoRol);
                    return nuevoRol;
                });

        // 2. Crear el Empleado
        Empleado analistaDummy = new Empleado(
                "Felipe", "Loyola", 351223443, "felipeloyola@dsi.com", rolAnalista
        );

        // El Empleado debe ser persistido antes de crear el Usuario
        EntityManager em = DbContext.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(analistaDummy);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new Exception("Error al persistir el Empleado Dummy.", ex);
        }

        // 3. Crear el Usuario para ese Empleado
        Usuario usuarioAnalista = new Usuario("AnalistaSismos", "RojoSosMiVida7L", analistaDummy);
        usuarioRepo.save(usuarioAnalista);

        // 4. Crear la Sesión ACTIVA
        Sesion sesionActiva = new Sesion(LocalDateTime.now(), usuarioAnalista);
        sesionRepo.save(sesionActiva);

        System.out.println("Seed: Sesión activa para AnalistaSismos creada exitosamente.");
    }

}