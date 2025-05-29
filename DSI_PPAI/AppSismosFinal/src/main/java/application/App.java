package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utilities.Paths;
import javafx.scene.Parent;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.PANTALLA_REVISION_MANUAL));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Revisión Manual de Eventos Sísmicos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}