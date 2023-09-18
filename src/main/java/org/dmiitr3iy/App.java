package org.dmiitr3iy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.slf4j.*;

import org.dmiitr3iy.controller.MainController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(App.class);

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main" + ".fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 640, 390);
        stage.setScene(scene);
        stage.show();
        MainController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        logger.debug("Запуск приложения");
        launch();
        logger.debug("Выключение приложения");
    }

    /**
     * Метод для отображения окна уведомлений
     * @param title
     * @param message
     * @param alertType
     */
    public static void showMessage(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}