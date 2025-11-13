package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import controllers.GestorRevisionManual;
import controllers.PantallaRevisionManual;
import repository.*;
import repository.db.DbContext;
import service.EventoService;
import seeder.DataSeeder;
import java.net.URL;
import service.SesionService;

public class App extends Application {

    private static final String FXML_PANTALLA_REVISION_MANUAL = "/PantallaRevisionManual.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. INICIALIZAR JPA/HIBERNATE (Base de Datos)
        DbContext.getInstance();

        // 2. CREAR LOS REPOSITORIES (Acceso a Datos)
        EventoSismicoRepository eventoRepo = new EventoSismicoRepository();
        AlcanceSismoRepository alcanceRepo = new AlcanceSismoRepository();
        ClasificacionSismoRepository clasificacionRepo = new ClasificacionSismoRepository();
        OrigenDeGeneracionRepository origenRepo = new OrigenDeGeneracionRepository();
        RolRepository rolRepo = new RolRepository();
        SesionRepository sesionRepo = new SesionRepository();
        UsuarioRepository usuarioRepo = new UsuarioRepository();

        // 3. CREAR EL SERVICE (Lógica de Negocio)
        EventoService eventoService = new EventoService(eventoRepo, alcanceRepo, clasificacionRepo, origenRepo);
        SesionService sesionService = new SesionService(new SesionRepository());
        // --- SEED: insertar datos mínimos si la BD está vacía ---
        try {
            if (eventoRepo.getAll() == null || eventoRepo.getAll().isEmpty()) {
                DataSeeder seeder = new DataSeeder(alcanceRepo, clasificacionRepo, origenRepo, eventoService,rolRepo,usuarioRepo,sesionRepo);
                seeder.seedAll();
            }
        } catch (Exception ex) {
            System.err.println("Warning: falla al intentar poblar la BD con datos de ejemplo: " + ex.getMessage());
            ex.printStackTrace();
        }

        // 4. CREAR EL GESTOR (Controlador de Flujo)
        GestorRevisionManual gestor = new GestorRevisionManual(eventoService,sesionService);

        // 5. CARGAR LA VISTA (FXML)
        URL fxmlUrl = getClass().getResource(FXML_PANTALLA_REVISION_MANUAL);

        if (fxmlUrl == null) {
            System.err.println("Error FATAL: No se pudo encontrar el archivo FXML en la ruta: " + FXML_PANTALLA_REVISION_MANUAL);
            throw new IllegalStateException("Ruta FXML no encontrada.");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        // 6. INYECTAR EL GESTOR EN EL CONTROLADOR DE LA PANTALLA
        PantallaRevisionManual pantallaController = loader.getController();
        pantallaController.setGestor(gestor);

        // 7. MOSTRAR LA APLICACIÓN
        Scene scene = new Scene(root);
        primaryStage.setTitle("Revisión Manual de Eventos Sísmicos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}