package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import org.example.MainApp;
import org.example.managers.DBManager;
import org.example.model.User;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    @FXML private Label labelUsername;
    @FXML private TextField usernameField;
    @FXML private Label labelPassword;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private ComboBox<String> localeComboBox;

    private MainApp mainApp;
    private ResourceBundle bundle;
    private Locale locale;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle("bundles.messages", locale);
        labelUsername.setText(bundle.getString("login.username"));
        usernameField.setPromptText(bundle.getString("login.username"));
        labelPassword.setText(bundle.getString("login.password"));
        passwordField.setPromptText(bundle.getString("login.password"));
        loginButton.setText(bundle.getString("login.button.ok"));
        registerButton.setText(bundle.getString("login.button.register"));

        if (localeComboBox != null) {
            if (locale.getLanguage().equals("ru")) {
                localeComboBox.setValue("Русский");
            } else if (locale.getLanguage().equals("pt")) {
                localeComboBox.setValue("Português");
            } else if (locale.getLanguage().equals("lv")) {
                localeComboBox.setValue("Latviešu");
            } else if (locale.getLanguage().equals("es")) {
                localeComboBox.setValue("Español (MX)");
            }
        }
    }

    @FXML
    public void initialize() {
        localeComboBox.getItems().addAll("Русский", "Português", "Latviešu", "Español (MX)");
        localeComboBox.setOnAction(e -> {
            switch (localeComboBox.getValue()) {
                case "Русский":      locale = new Locale("ru");    break;
                case "Português":    locale = new Locale("pt");    break;
                case "Latviešu":     locale = new Locale("lv");    break;
                default:             locale = new Locale("es","MX"); break;
            }
            setLocale(locale);
            try {
                mainApp.changeLocale(locale);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    public void onLogin(ActionEvent e) {
        String u = usernameField.getText().trim();
        String p = passwordField.getText().trim();
        if (u.isEmpty() || p.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, bundle.getString("login.alert.empty")).showAndWait();
            return;
        }
        if (DBManager.getInstance().login(u,p)) {
            User user = DBManager.getInstance().getCurrentUser();
            try {
                mainApp.onLoginSuccess(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, bundle.getString("login.alert.invalid")).showAndWait();
        }
    }

    @FXML
    public void onRegister(ActionEvent e) {
        DBManager.getInstance().registerUser(
                usernameField.getText().trim(),
                passwordField.getText().trim()
        );
        new Alert(Alert.AlertType.INFORMATION, bundle.getString("login.alert.registered"))
                .showAndWait();
    }
}
