package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Main;
import org.example.managers.DBManager;
import org.example.model.Coordinates;
import org.example.model.MusicBand;
import org.example.model.MusicGenre;
import org.example.model.Studio;
import org.example.model.User;

import java.util.Optional;
import java.util.ResourceBundle;

public class MusicBandDialogController {
    @FXML private Label labelName;
    @FXML private TextField nameField;
    @FXML private Label labelCoordinateX;
    @FXML private Spinner<Integer> xSpinner;
    @FXML private Label labelCoordinateY;
    @FXML private Spinner<Long> ySpinner;
    @FXML private Label labelParticipants;
    @FXML private Spinner<Long> participantsSpinner;
    @FXML private Label labelGenre;
    @FXML private ComboBox<MusicGenre> genreComboBox;
    @FXML private Label labelStudio;
    @FXML private TextField studioField;
    @FXML private Button okButton;
    @FXML private Button cancelButton;

    private MusicBand musicBand;
    private boolean isAdd;
    private boolean isAddIfMin;
    private ResourceBundle bundle;
    private User currentUser;
    private Stage dialogStage;

    public static Optional<MusicBand> showDialog(boolean addMode, User user, ResourceBundle bundle) {
        return showDialog(addMode, false, user, bundle, null);
    }

    public static Optional<MusicBand> showDialog(boolean addMode, boolean isAddIfMin, User user, ResourceBundle bundle) {
        return showDialog(addMode, isAddIfMin, user, bundle, null);
    }

    public static Optional<MusicBand> showDialog(boolean addMode, User user, ResourceBundle bundle, MusicBand existingBand) {
        return showDialog(addMode, false, user, bundle, existingBand);
    }

    public static Optional<MusicBand> showDialog(boolean addMode, boolean isAddIfMin, User user, ResourceBundle bundle, MusicBand existingBand) {
        try {
            var loader = new javafx.fxml.FXMLLoader(
                    MusicBandDialogController.class.getResource("/fxml/MusicBandDialog.fxml"),
                    bundle
            );
            Parent pane = loader.load();
            MusicBandDialogController ctrl = loader.getController();
            ctrl.bundle = bundle;
            ctrl.currentUser = user;
            ctrl.isAdd = addMode;
            ctrl.isAddIfMin = isAddIfMin;
            ctrl.musicBand = existingBand;
            ctrl.dialogStage = new Stage();
            ctrl.dialogStage.setScene(new Scene(pane));
            ctrl.dialogStage.setTitle(addMode
                    ? bundle.getString("dialog.add.title")
                    : bundle.getString("dialog.edit.title"));
            ctrl.initFields();
            ctrl.dialogStage.showAndWait();
            return ctrl.musicBand != null ? Optional.of(ctrl.musicBand) : Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @FXML
    private void initialize() {
        xSpinner.setValueFactory(
                new IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1, "X")
        );
        ySpinner.setValueFactory(
                new LongSpinnerValueFactory(Long.MIN_VALUE, Long.MAX_VALUE, 0, 1, "Y")
        );
        participantsSpinner.setValueFactory(
                new LongSpinnerValueFactory(1, Long.MAX_VALUE, 1, 1, "participants")
        );

        genreComboBox.getItems().addAll(MusicGenre.values());
        genreComboBox.getItems().add(null);

        okButton.setOnAction(e -> handleOk());
        cancelButton.setOnAction(e -> dialogStage.close());
    }

    private void initFields() {
        labelName.setText(bundle.getString("dialog.field.name"));
        labelCoordinateX.setText(bundle.getString("dialog.field.x"));
        labelCoordinateY.setText(bundle.getString("dialog.field.y"));
        labelParticipants.setText(bundle.getString("dialog.field.participants"));
        labelGenre.setText(bundle.getString("dialog.field.genre"));
        labelStudio.setText(bundle.getString("dialog.field.studio"));
        okButton.setText(bundle.getString("dialog.button.ok"));
        cancelButton.setText(bundle.getString("dialog.button.cancel"));

        if (!isAdd) {
            if (musicBand != null) {
                nameField.setText(musicBand.getName());
                xSpinner.getValueFactory().setValue(musicBand.getCoordinates().getX());
                ySpinner.getValueFactory().setValue(musicBand.getCoordinates().getY());
                participantsSpinner.getValueFactory().setValue(musicBand.getNumberOfParticipants());
                genreComboBox.setValue(musicBand.getGenre());
                if (musicBand.getStudio() != null) {
                    studioField.setText(musicBand.getStudio().getName());
                }
            }
        } else {
            musicBand = new MusicBand();
            musicBand.setCreatedBy(currentUser.getLogin());
        }
    }

    private void handleOk() {
        boolean isValid = validateInput();

        if (isValid) {
            musicBand.setName(nameField.getText().trim());

            Coordinates coordinates = new Coordinates();
            coordinates.setX(xSpinner.getValue());
            coordinates.setY(ySpinner.getValue());
            musicBand.setCoordinates(coordinates);

            musicBand.setNumberOfParticipants(participantsSpinner.getValue());
            musicBand.setGenre(genreComboBox.getValue());

            String studioName = studioField.getText().trim();
            if (!studioName.isEmpty()) {
                Studio studio = new Studio();
                studio.setName(studioName);
                musicBand.setStudio(studio);
            } else {
                musicBand.setStudio(null);
            }

            if (isAdd) {
                if (!isAddIfMin) {
                    Main.db.addMusicBand(musicBand, currentUser.getLogin());
                }
            } else {
                Main.db.updateMusicBand(musicBand.getId(), musicBand, currentUser.getLogin());
            }

            dialogStage.close();
        }
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage += "Name cannot be empty!\n";
        }

        try {
            String xText = xSpinner.getEditor().getText();
            try {
                int x = Integer.parseInt(xText);
                if (x < Integer.MIN_VALUE) {
                    errorMessage += "X coordinate is too small!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "X coordinate must be a valid integer!\n";
            }
        } catch (Exception e) {
            errorMessage += "X coordinate must be a valid integer!\n";
        }

        try {
            String yText = ySpinner.getEditor().getText();
            try {
                long y = Long.parseLong(yText);
                if (y < Long.MIN_VALUE) {
                    errorMessage += "Y coordinate is too small!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Y coordinate must be a valid long value!\n";
            }
        } catch (Exception e) {
            errorMessage += "Y coordinate must be a valid long value!\n";
        }

        try {
            String participantsText = participantsSpinner.getEditor().getText();
            try {
                long participants = Long.parseLong(participantsText);
                if (participants <= 0) {
                    errorMessage += "Number of participants must be greater than 0!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Number of participants must be a valid positive integer!\n";
            }
        } catch (Exception e) {
            errorMessage += "Number of participants must be a valid positive integer!\n";
        }

        if (!errorMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }

        return true;
    }

    public static class LongSpinnerValueFactory extends SpinnerValueFactory<Long> {
        private final long min;
        private final long max;
        private final long step;
        private final String fieldType; // "X", "Y", or "participants"

        public LongSpinnerValueFactory(long min, long max, long initialValue) {
            this(min, max, initialValue, 1, "participants");
        }

        public LongSpinnerValueFactory(long min, long max, long initialValue, long step, String fieldType) {
            this.min = min;
            this.max = max;
            this.step = step;
            this.fieldType = fieldType;
            setValue(initialValue);

            setConverter(new javafx.util.StringConverter<Long>() {
                @Override
                public String toString(Long value) {
                    return value == null ? "" : value.toString();
                }

                @Override
                public Long fromString(String string) {
                    if (string == null || string.isEmpty()) {
                        return 0L;
                    }
                    try {
                        long value = Long.parseLong(string);
                        if ((value <= 0 && min > 0) || (value < min && min > Long.MIN_VALUE)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Input");

                            if (fieldType.equals("participants")) {
                                alert.setHeaderText("Invalid Number of Participants");
                                alert.setContentText("Number of participants must be a positive integer.");
                            } else if (fieldType.equals("Y")) {
                                alert.setHeaderText("Invalid Y Coordinate");
                                alert.setContentText("Y coordinate must be at least " + min + ".");
                            }

                            alert.showAndWait();
                            return getValue();
                        }
                        return value;
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");

                        if (fieldType.equals("participants")) {
                            alert.setHeaderText("Invalid Number Format");
                            alert.setContentText("Please enter a valid integer value for participants.");
                        } else if (fieldType.equals("Y")) {
                            alert.setHeaderText("Invalid Y Coordinate Format");
                            alert.setContentText("Y coordinate must be a valid long value. Please enter a valid long number.");
                        }

                        alert.showAndWait();
                        return getValue();
                    }
                }
            });
        }

        @Override
        public void decrement(int steps) {
            long newValue = getValue() - (step * steps);
            setValue(Math.max(min, newValue));
        }

        @Override
        public void increment(int steps) {
            long newValue = getValue() + (step * steps);
            setValue(Math.min(max, newValue));
        }
    }

    public static class IntegerSpinnerValueFactory extends SpinnerValueFactory<Integer> {
        private final int min;
        private final int max;
        private final int step;
        private final String fieldType; // "X" or other

        public IntegerSpinnerValueFactory(int min, int max, int initialValue, int step, String fieldType) {
            this.min = min;
            this.max = max;
            this.step = step;
            this.fieldType = fieldType;
            setValue(initialValue);

            setConverter(new javafx.util.StringConverter<Integer>() {
                @Override
                public String toString(Integer value) {
                    return value == null ? "" : value.toString();
                }

                @Override
                public Integer fromString(String string) {
                    if (string == null || string.isEmpty()) {
                        return 0;
                    }
                    try {
                        int value = Integer.parseInt(string);
                        if (value < min && min > Integer.MIN_VALUE) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Input");

                            if (fieldType.equals("X")) {
                                alert.setHeaderText("Invalid X Coordinate Value");
                                alert.setContentText("X coordinate must be at least " + min + ".");
                            }

                            alert.showAndWait();
                            return getValue();
                        }
                        return value;
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");

                        if (fieldType.equals("X")) {
                            alert.setHeaderText("Invalid X Coordinate Format");
                            alert.setContentText("X coordinate must be a valid integer value. Please enter a valid integer number.");
                        }

                        alert.showAndWait();
                        return getValue();
                    }
                }
            });
        }

        @Override
        public void decrement(int steps) {
            int newValue = getValue() - (step * steps);
            setValue(Math.max(min, newValue));
        }

        @Override
        public void increment(int steps) {
            int newValue = getValue() + (step * steps);
            setValue(Math.min(max, newValue));
        }
    }
}
