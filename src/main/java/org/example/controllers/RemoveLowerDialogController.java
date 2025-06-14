package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.model.User;

import java.util.Optional;
import java.util.ResourceBundle;

public class RemoveLowerDialogController {
    @FXML private Label labelThreshold;
    @FXML private Spinner<Long> thresholdSpinner;
    @FXML private Button okButton;
    @FXML private Button cancelButton;

    private Long threshold;
    private ResourceBundle bundle;
    private User currentUser;
    private Stage dialogStage;

    public static Optional<Long> showDialog(User user, ResourceBundle bundle) {
        try {
            var loader = new javafx.fxml.FXMLLoader(
                    RemoveLowerDialogController.class.getResource("/fxml/RemoveLowerDialog.fxml"),
                    bundle
            );
            Parent pane = loader.load();
            RemoveLowerDialogController ctrl = loader.getController();
            ctrl.bundle = bundle;
            ctrl.currentUser = user;
            ctrl.dialogStage = new Stage();
            ctrl.dialogStage.setScene(new Scene(pane));
            ctrl.dialogStage.setTitle(bundle.getString("dialog.remove_lower.title"));
            ctrl.initFields();
            ctrl.dialogStage.showAndWait();
            return ctrl.threshold != null ? Optional.of(ctrl.threshold) : Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @FXML
    private void initialize() {
        thresholdSpinner.setValueFactory(
                new MusicBandDialogController.LongSpinnerValueFactory(1, Long.MAX_VALUE, 1, 1, "participants")
        );

        okButton.setOnAction(e -> handleOk());
        cancelButton.setOnAction(e -> dialogStage.close());
    }

    private void initFields() {
        labelThreshold.setText(bundle.getString("dialog.field.threshold"));
        okButton.setText(bundle.getString("dialog.button.ok"));
        cancelButton.setText(bundle.getString("dialog.button.cancel"));
    }

    private void handleOk() {
        boolean isValid = validateInput();

        if (isValid) {
            threshold = thresholdSpinner.getValue();
            dialogStage.close();
        }
    }

    private boolean validateInput() {
        String errorMessage = "";

        try {
            String thresholdText = thresholdSpinner.getEditor().getText();
            try {
                long value = Long.parseLong(thresholdText);
                if (value <= 0) {
                    errorMessage += "Threshold must be greater than 0!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Threshold must be a valid positive integer!\n";
            }
        } catch (Exception e) {
            errorMessage += "Threshold must be a valid positive integer!\n";
        }

        if (!errorMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("dialog.error.title"));
            alert.setHeaderText(bundle.getString("dialog.error.invalid_input.header"));
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }

        return true;
    }
}