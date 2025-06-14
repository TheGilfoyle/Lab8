
package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.controllers.LoginController;
import org.example.controllers.MainController;
import org.example.functional.JSCh;
import org.example.managers.DBManager;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {
    private Stage primaryStage;
    private Locale currentLocale;
    private ResourceBundle bundle;
    private org.example.model.User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            JSCh jsch = new JSCh();
            jsch.connectSSH();
        } catch (com.jcraft.jsch.JSchException ex) {
            System.err.println("Не удалось установить SSH-туннель: " + ex.getMessage());
            new Alert(Alert.AlertType.WARNING,
                    "Не удалось установить SSH-туннель, попытка прямого подключения к БД")
                    .showAndWait();
        }

        DBManager.getInstance().connect();

        this.primaryStage = stage;
        this.currentLocale = Locale.getDefault();
        this.bundle = ResourceBundle.getBundle("bundles.messages", currentLocale);
        showLoginWindow();
        primaryStage.show();
    }

    public void showLoginWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/Login.fxml"),
                bundle
        );
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setMainApp(this);
        ctrl.setLocale(currentLocale);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(bundle.getString("login.title"));
    }

    public void showMainWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/Main.fxml"),
                bundle
        );
        Parent root = loader.load();
        MainController ctrl = loader.getController();
        ctrl.setMainApp(this);
        ctrl.setCurrentUser(currentUser);
        ctrl.setLocale(currentLocale);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(bundle.getString("main.title"));
    }

    public void onLoginSuccess(org.example.model.User user) throws Exception {
        this.currentUser = user;
        showMainWindow();
    }

    public void changeLocale(Locale newLocale) throws Exception {
        this.currentLocale = newLocale;
        this.bundle = ResourceBundle.getBundle("bundles.messages", currentLocale);
        if (currentUser == null) {
            showLoginWindow();
        } else {
            showMainWindow();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
