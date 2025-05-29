package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CambioEstado {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Estado estado;
    private Empleado empleado;

    public CambioEstado(String fechaHoraInicio, Estado estado, Empleado empleado) {
        this.fechaHoraInicio = parsearFechaHora(fechaHoraInicio);
        this.estado = estado;
        this.empleado = empleado;
    }

    public LocalDateTime parsearFechaHora(String fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(fechaHora, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha y hora no válida: " + fechaHora);
        }
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "Desde: " + fechaHoraInicio.format(formatter) +
                " | Hasta: " + (fechaHoraFin != null ? fechaHoraFin.format(formatter) : "null") +
                " | Estado: " + estado.getNombreEstado() +
                " | Empleado: " + empleado;
    }


    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }


    public boolean sosActual() {
        return this.fechaHoraFin == null;
    }
}
