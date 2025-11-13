package controllers;

import dto.DatosSismicosDTO;
import dto.EstacionSismologicaDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import dto.EventoSismicoDTO;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.EstacionSismologica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PantallaRevisionManual {

    // --- Componentes FXML de la Interfaz ---
    @FXML private VBox contenedorRevisionCompleto;
    @FXML private Button btnRegistrarRevision;
    @FXML private Button btnCerrar;
    @FXML private Button btnVisualizarMapa;
    @FXML private TableView<EventoSismicoDTO> tablaEventos;
    @FXML private TableColumn<EventoSismicoDTO, String> columnaFecha;
    @FXML private TableColumn<EventoSismicoDTO, Double> columnaLatEpicentro;
    @FXML private TableColumn<EventoSismicoDTO, Double> columnaLongEpicentro;
    @FXML private TableColumn<EventoSismicoDTO, Double> columnaLatHipocentro;
    @FXML private TableColumn<EventoSismicoDTO, Double> columnaLongHipocentro;
    @FXML private TableColumn<EventoSismicoDTO, Double> columnaMagnitud;

    @FXML private TableView<Map.Entry<String, Map.Entry<String, String>>> tablaDetalles;
    @FXML private TableColumn<Map.Entry<String, Map.Entry<String, String>>, String> columnaPropiedad;
    @FXML private TableColumn<Map.Entry<String, Map.Entry<String, String>>, String> columnaNombre;
    @FXML private TableColumn<Map.Entry<String, Map.Entry<String, String>>, String> columnaDescripcion;

    @FXML
    private TableView<Map<String, String>> tablaSeries;
    @FXML
    private TableColumn<Map<String, String>, String> columnaEstacion;
    @FXML
    private TableColumn<Map<String, String>, String> columnaFechaHora;
    @FXML
    private TableColumn<Map<String, String>, String> columnaVelocidad;
    @FXML
    private TableColumn<Map<String, String>, String> columnaFrecuencia;
    @FXML
    private TableColumn<Map<String, String>, String> columnaLongitud;

    @FXML private Button btnModificar;
    @FXML private Button btnRechazar;
    @FXML private Button btnConfirmar;
    @FXML private Button btnSolicitarRevision;

    // --- Dependencia del Gestor (Será inyectada) ---
    private GestorRevisionManual gestor;

    // ===================== MÉTODOS DE INICIALIZACIÓN FXML =====================

    @FXML
    public void initialize() {
        inicializarPantalla();
        if (gestor != null) {
            gestor.opcionRegistrarRevisionManual();
        }
    }

    private void inicializarPantalla() {
        contenedorRevisionCompleto.setVisible(false);
        btnVisualizarMapa.setDisable(true);
        btnModificar.setDisable(true);
        btnRechazar.setDisable(true);
        btnConfirmar.setDisable(true);
        btnSolicitarRevision.setDisable(true);

        // Configurar columnas de la tabla de eventos
        columnaFecha.setCellValueFactory(cellData -> {
            // Se asume que getFechaHoraOcurrencia() devuelve LocalDateTime
            LocalDateTime fechaHora = cellData.getValue().getFechaHoraOcurrencia();
            String fechaFormateada = fechaHora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return new javafx.beans.property.SimpleStringProperty(fechaFormateada);
        });

        columnaLatEpicentro.setCellValueFactory(new PropertyValueFactory<>("latitudEpicentro"));
        columnaLongEpicentro.setCellValueFactory(new PropertyValueFactory<>("longitudEpicentro"));
        columnaLatHipocentro.setCellValueFactory(new PropertyValueFactory<>("latitudHipocentro"));
        columnaLongHipocentro.setCellValueFactory(new PropertyValueFactory<>("longitudHipocentro"));
        columnaMagnitud.setCellValueFactory(new PropertyValueFactory<>("valorMagnitud"));

        // Configurar columnas de la tabla de detalles
        columnaPropiedad.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));

        columnaNombre.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getKey()));

        columnaDescripcion.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getValue()));

        columnaEstacion.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOrDefault("estacion", "")));
        columnaFechaHora.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOrDefault("fechaHora", "")));
        columnaVelocidad.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOrDefault("velocidad", "")));
        columnaFrecuencia.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOrDefault("frecuencia", "")));
        columnaLongitud.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOrDefault("longitud", "")));
    }


    public void setGestor(GestorRevisionManual gestor) {
        this.gestor = gestor;
        this.gestor.setPantalla(this);
    }

    // ===================== FLUJO DEL CASO DE USO =====================

    // Al hacer clic en el botón "Registrar Revisión Manual"
    @FXML
    public void opcionRegistrarRevisionManual() {
        contenedorRevisionCompleto.setVisible(true);
        btnRegistrarRevision.setDisable(true);

        // Llama al gestor para refrescar los datos iniciales
        gestor.opcionRegistrarRevisionManual();
    }

    public void mostrarDatosEventosSismicos(List<EventoSismicoDTO> eventosSismicos) {
        ObservableList<EventoSismicoDTO> eventos = FXCollections.observableArrayList(eventosSismicos);
        tablaEventos.setItems(eventos);
        tablaEventos.refresh(); // Asegura que se actualice la vista
    }

    @FXML
    public void tomarSeleccionDeEvento() {
        EventoSismicoDTO eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (eventoSeleccionado != null) {

            // Lógica existente: Bloquear el evento seleccionado en el gestor
            try {
                gestor.tomarSeleccionDeEvento(eventoSeleccionado);

            // 🔄 Refrescar la lista (para ver el estado 'Bloqueado')
            List<EventoSismicoDTO> eventosActualizados = gestor.refrescarEventos();
            tablaEventos.setItems(FXCollections.observableArrayList(eventosActualizados));


            // Buscar el evento nuevamente (ahora bloqueado) en la lista fresca (por si se necesita el DTO actualizado)
            EventoSismicoDTO eventoBloqueado = eventosActualizados.stream()
                    .filter(e -> e.getId().equals(eventoSeleccionado.getId())) // Asumiendo que el DTO tiene ID
                    .findFirst()
                    .orElse(null);


            // Mostrar un mensaje visual confirmando el bloqueo
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Evento bloqueado");
            alerta.setHeaderText(null);
            alerta.setContentText("El evento fue bloqueado correctamente para revisión manual.");
            alerta.showAndWait();

            Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
            alerta1.setTitle("Generar Sismograma");
            alerta1.setHeaderText(null);
            alerta1.setContentText("Sismograma generado con éxito para el evento seleccionado.");
            alerta1.showAndWait();

            // Habilitar botones de acción
            habilitarOpcionVisualizarMapa();
            habilitarOpcionModificarDatosEvento();
            solicitarSeleccionDeAccionEvento();

            } catch (IllegalStateException e) {
                mostrarAlerta("Operación no válida", e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            tablaDetalles.getItems().clear();
        }
    }

    @FXML
    public void mostrarDatosSismicos(DatosSismicosDTO datos) {
        List<Map.Entry<String, Map.Entry<String, String>>> detalles = new ArrayList<>();
        detalles.add(new AbstractMap.SimpleEntry<>("Alcance", new AbstractMap.SimpleEntry<>("Nombre", datos.getNombreAlcance())));
        detalles.add(new AbstractMap.SimpleEntry<>("Alcance", new AbstractMap.SimpleEntry<>("Descripción", datos.getDescripcionAlcance())));
        detalles.add(new AbstractMap.SimpleEntry<>("Clasificación", new AbstractMap.SimpleEntry<>("Clasificación", datos.getClasificacion())));
        detalles.add(new AbstractMap.SimpleEntry<>("Profundidad", new AbstractMap.SimpleEntry<>("Desde (km)", datos.getProfundidadDesde())));
        detalles.add(new AbstractMap.SimpleEntry<>("Profundidad", new AbstractMap.SimpleEntry<>("Hasta (km)", datos.getProfundidadHasta())));
        detalles.add(new AbstractMap.SimpleEntry<>("Origen", new AbstractMap.SimpleEntry<>("Nombre", datos.getNombreOrigen())));
        detalles.add(new AbstractMap.SimpleEntry<>("Origen", new AbstractMap.SimpleEntry<>("Descripción", datos.getDescripcionOrigen())));
        ObservableList<Map.Entry<String, Map.Entry<String, String>>> obs = FXCollections.observableArrayList(detalles);
        tablaDetalles.setItems(obs);
    }

    // ===================== HABILITACIÓN DE BOTONES =====================

    @FXML
    public void habilitarOpcionVisualizarMapa() {
        btnVisualizarMapa.setDisable(false);
    }

    @FXML
    public void habilitarOpcionModificarDatosEvento() {
        btnModificar.setDisable(false);
    }

    @FXML
    public void solicitarSeleccionDeAccionEvento() {
        btnRechazar.setDisable(false);
        btnConfirmar.setDisable(false);
        btnSolicitarRevision.setDisable(false);
    }

    // ===================== ACCIONES FINALES =====================

    @FXML
    public void tomarOpcionRechazarEvento() {
        try {
            gestor.tomarOpcionRechazarEvento();

            mostrarAlerta("Evento Rechazado", "El evento ha sido rechazado correctamente.", Alert.AlertType.INFORMATION);
            // 🔄 Opcional: Refrescar la tabla para ver el nuevo estado
            tablaEventos.setItems(FXCollections.observableArrayList(gestor.refrescarEventos()));
        } catch (IllegalStateException e) {
            mostrarAlerta("Error al Rechazar", "Operación no válida", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void tomarOpcionConfirmarEvento() {
        try {
            gestor.tomarOpcionConfirmarEvento();
            mostrarAlerta("Evento Confirmado", "El evento ha sido confirmado exitosamente.", Alert.AlertType.INFORMATION);
            // 🔄 Opcional: Refrescar la tabla para ver el nuevo estado
            tablaEventos.setItems(FXCollections.observableArrayList(gestor.refrescarEventos()));
        } catch (IllegalStateException e) {
            mostrarAlerta("Error al Confirmar", "Operación no válida", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void tomarOpcionSolicitarRevisionExperto() {
        try {
            gestor.tomarOpcionSolicitarRevisionExperto();
            mostrarAlerta("Solicitud enviada", "Se solicitó revisión a un experto correctamente.", Alert.AlertType.INFORMATION);
            // 🔄 Opcional: Refrescar la tabla para ver el nuevo estado
            tablaEventos.setItems(FXCollections.observableArrayList(gestor.refrescarEventos()));
        } catch (IllegalStateException e) {
            mostrarAlerta("Error al Solicitar Revisión", "Operación no válida", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void cerrarPantalla() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar cierre");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Seguro que deseas cerrar la pantalla?");

        ButtonType botonSi = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(botonSi, botonNo);

        alerta.showAndWait().ifPresent(respuesta -> {
            if (respuesta == botonSi) {
                Stage stage = (Stage) btnCerrar.getScene().getWindow();
                stage.close();
            }
        });
    }

    // --- Métodos Auxiliares ---

    private void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private void mostrarAlerta(String titulo, String encabezado, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.showAndWait();
    }

    public void mostrarDetalleMuestrasPorEstacion(Map<EstacionSismologicaDTO, List<String>> muestras) {
        ObservableList<Map<String, String>> filas = FXCollections.observableArrayList();

        // Recorremos cada estación y sus muestras
        for (Map.Entry<EstacionSismologicaDTO, List<String>> entry : muestras.entrySet()) {
            EstacionSismologicaDTO estacion = entry.getKey();
            List<String> listaMuestras = entry.getValue();

            for (String linea : listaMuestras) {
                // Ejemplo de línea: "12/11/2025 14:00:00 | Velocidad: 20 m/s | Frecuencia: 5 Hz | Longitud: 2.5 m"
                String[] partes = linea.split("\\|");

                if (partes.length < 4) continue; // Evitamos líneas incompletas

                String fechaHora = partes[0].trim();
                String velocidad = partes[1].replace("Velocidad:", "").trim();
                String frecuencia = partes[2].replace("Frecuencia:", "").trim();
                String longitud = partes[3].replace("Longitud:", "").trim();

                Map<String, String> fila = new HashMap<>();
                fila.put("estacion", estacion.getNombre()+ " (Código: " + estacion.getCodigoEstacion() + ")");
                fila.put("fechaHora", fechaHora);
                fila.put("velocidad", velocidad);
                fila.put("frecuencia", frecuencia);
                fila.put("longitud", longitud);

                filas.add(fila);
            }
        }
        // Cargamos los datos en la tabla
        tablaSeries.setItems(filas);
    }

    private String extraerDato(String texto, String clave) {
        int i = texto.indexOf(clave);
        if (i == -1) return "";
        String sub = texto.substring(i + clave.length()).trim();
        int salto = sub.indexOf("\n");
        if (salto > 0) sub = sub.substring(0, salto).trim();
        return sub;
    }


}