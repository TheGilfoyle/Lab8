package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.managers.DBManager;
import org.example.model.Coordinates;
import org.example.model.MyObject;
import org.example.model.User;

import java.util.Optional;
import java.util.ResourceBundle;

public class ObjectDialogController {
    @FXML private Label labelName;
    @FXML private TextField nameField;
    @FXML private Label labelValue;
    @FXML private Spinner<Integer> valueSpinner;
    @FXML private Label labelX;
    @FXML private Spinner<Double> xSpinner;
    @FXML private Label labelY;
    @FXML private Spinner<Double> ySpinner;
    @FXML private Label labelSize;
    @FXML private Spinner<Double> sizeSpinner;
    @FXML private Button okButton;
    @FXML private Button cancelButton;

    private MyObject object;
    private boolean isAdd;
    private ResourceBundle bundle;
    private User currentUser;
    private Stage dialogStage;

    public static Optional<MyObject> showDialog(boolean addMode, User user, ResourceBundle bundle) {
        try {
            var loader = new javafx.fxml.FXMLLoader(
                    ObjectDialogController.class.getResource("/fxml/ObjectDialog.fxml"),
                    bundle
            );
            Parent pane = loader.load();
            ObjectDialogController ctrl = loader.getController();
            ctrl.bundle = bundle;
            ctrl.currentUser = user;
            ctrl.isAdd = addMode;
            ctrl.dialogStage = new Stage();
            ctrl.dialogStage.setScene(new Scene(pane));
            ctrl.dialogStage.setTitle(addMode
                    ? bundle.getString("dialog.add.title")
                    : bundle.getString("dialog.edit.title"));
            ctrl.initFields();
            ctrl.dialogStage.initOwner(null);
            ctrl.dialogStage.showAndWait();
            return ctrl.object!=null ? Optional.of(ctrl.object) : Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @FXML
    private void initialize() {
        valueSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1_000_000, 0)
        );
        xSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2000, 100, 1)
        );
        ySpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2000, 100, 1)
        );
        sizeSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 500, 50, 1)
        );
        okButton.setOnAction(e -> handleOk());
        cancelButton.setOnAction(e -> dialogStage.close());
    }

    private void initFields() {
        labelName.setText(bundle.getString("dialog.field.name"));
        labelValue.setText(bundle.getString("dialog.field.value"));
        labelX.setText(bundle.getString("dialog.field.x"));
        labelY.setText(bundle.getString("dialog.field.y"));
        labelSize.setText(bundle.getString("dialog.field.size"));
        okButton.setText(bundle.getString("dialog.button.ok"));
        cancelButton.setText(bundle.getString("dialog.button.cancel"));

        if (!isAdd) {
            if (object != null) {
                object = DBManager.getInstance().getObjectById(object.getId());
            } else {
                object = new MyObject();
                object.setOwner(currentUser.getLogin());
            }
        } else {
            object = new MyObject();
            object.setOwner(currentUser.getLogin());
        }

        nameField   .setText(object.getName());
        valueSpinner.getValueFactory().setValue(object.getValue());
        xSpinner.getValueFactory().setValue(object.getX());
        ySpinner.getValueFactory().setValue(object.getY());
        sizeSpinner.getValueFactory().setValue(object.getSize());
    }

    private void handleOk() {
        object.setName(nameField.getText());
        object.setValue(valueSpinner.getValue());
        object.setX(xSpinner.getValue());
        object.setY(ySpinner.getValue());
        object.setSize(sizeSpinner.getValue());
        if (isAdd) {
            DBManager.getInstance().addObject(object, currentUser.getLogin());
        } else {
            DBManager.getInstance().updateObject(object.getId(), object, currentUser.getLogin());
        }
        dialogStage.close();
    }
}