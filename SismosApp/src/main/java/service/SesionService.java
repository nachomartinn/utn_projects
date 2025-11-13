package service;

import models.Empleado;
import models.Sesion;
import repository.SesionRepository;

public class SesionService {

    private final SesionRepository sesionRepository;

    public SesionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    public Empleado obtenerASLogueado() {
        // La lógica de acceso a datos está aquí
        Sesion sesion = sesionRepository.buscarSesionActiva();

        if (sesion == null) {
            // Aquí se aplica una regla de negocio de Sesión
            throw new IllegalStateException("El sistema no tiene una sesión activa registrada.");
        }

        return sesion.obtenerASLogueado();
    }
}