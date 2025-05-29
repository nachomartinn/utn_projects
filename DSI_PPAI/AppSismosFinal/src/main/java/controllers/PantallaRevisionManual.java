package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import models.EventoSismico;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ArrayList;

public class PantallaRevisionManual {

    @FXML private AnchorPane contenedorRevisionCompleto;
    @FXML private Button btnRegistrarRevision;
    @FXML private Button btnVisualizarMapa;
    @FXML private TableView<EventoSismico> tablaEventos;
    @FXML private TableColumn<EventoSismico, String> columnaFecha;
    @FXML private TableColumn<EventoSismico, Double> columnaLatEpicentro;
    @FXML private TableColumn<EventoSismico, Double> columnaLongEpicentro;
    @FXML private TableColumn<EventoSismico, Double> columnaLatHipocentro;
    @FXML private TableColumn<EventoSismico, Double> columnaLongHipocentro;
    @FXML private TableColumn<EventoSismico, Double> columnaMagnitud;
    @FXML private TableView<Map.Entry<String, Map.Entry<String, String>>> tablaDetalles;
    @FXML private TableColumn<Map.Entry<String, Map.Entry<String, String>>, String> columnaPropiedad;
    @FXML private TableColumn<Map.Entry<String, Map.Entry<String, String>>, String> columnaNombre;
    @FXML private TableColumn<Map.Entry<String, Map.Entry<String, String>>, String> columnaDescripcion;

    @FXML private Button btnModificar;
    @FXML private Button btnRechazar;
    @FXML private Button btnConfirmar;
    @FXML private Button btnSolicitarRevision;

    private GestorRevisionManual gestor;

    @FXML
    public void opcionRegistrarRevisionManual() {
        contenedorRevisionCompleto.setVisible(true); // Cambiado
        btnRegistrarRevision.setDisable(true);
    }


    @FXML
    public void initialize() {
        inicializarPantalla();
    }

    @FXML
    private void inicializarPantalla() {
        contenedorRevisionCompleto.setVisible(false);
        gestor = new GestorRevisionManual();
        gestor.setPantalla(this);
        gestor.opcionRegistrarRevisionManual();

        columnaFecha.setCellValueFactory(cellData -> {
            LocalDateTime fechaHora = cellData.getValue().getFechaHoraOcurrencia();
            String fechaFormateada = fechaHora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return new javafx.beans.property.SimpleStringProperty(fechaFormateada);
        });
        columnaLatEpicentro.setCellValueFactory(new PropertyValueFactory<>("latitudEpicentro"));
        columnaLongEpicentro.setCellValueFactory(new PropertyValueFactory<>("longitudEpicentro"));
        columnaLatHipocentro.setCellValueFactory(new PropertyValueFactory<>("latitudHipocentro"));
        columnaLongHipocentro.setCellValueFactory(new PropertyValueFactory<>("longitudHipocentro"));
        columnaMagnitud.setCellValueFactory(new PropertyValueFactory<>("valorMagnitud"));

        columnaPropiedad.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));
        columnaNombre.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getKey()));
        columnaDescripcion.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getValue()));
    }


    @FXML
    public void mostrarDatosEventosSismicos(ArrayList<EventoSismico> eventosSismicos) {
        ObservableList<EventoSismico> eventos = FXCollections.observableArrayList(eventosSismicos);
        tablaEventos.setItems(eventos);
    }

    @FXML
    public void tomarSeleccionDeEvento() {
        EventoSismico eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (eventoSeleccionado != null) {
            gestor.tomarSeleccionDeEvento(eventoSeleccionado);
            mostrarDatosSismicos();
            habilitarOpcionVisualizarMapa();
            habilitarOpcionModificarDatosEvento();
            solicitarSeleccionDeAccionEvento();

        } else {
            mostrarDatosSismicos(); // limpia si no hay selección
        }
    }

    @FXML
    private void mostrarDatosSismicos() {
        ObservableList<Map.Entry<String, Map.Entry<String, String>>> detalles =
                FXCollections.observableArrayList(gestor.buscarDatosSismicos());

        tablaDetalles.setItems(detalles);

    }

    @FXML
    public void habilitarOpcionVisualizarMapa() {
        btnVisualizarMapa.setDisable(false);
    }

    @FXML
    public void habilitarOpcionModificarDatosEvento(){
        btnModificar.setDisable(false);
    }

    @FXML
    public void solicitarSeleccionDeAccionEvento(){
        btnRechazar.setDisable(false);
        btnConfirmar.setDisable(false);
        btnSolicitarRevision.setDisable(false);
    }

    @FXML
    public void tomarOpcionRechazarEvento() {
        gestor.tomarOpcionRechazarEvento();
    }

    @FXML
    public void tomarOpcionConfirmarEvento(){
        gestor.tomarOpcionConfirmarEvento();
    }

    @FXML
    public void tomarOpcionSolicitarRevisionExperto() {
        gestor.tomarOpcionSolicitarRevisionExperto();
    }
}