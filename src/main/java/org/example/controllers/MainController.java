package org.example.controllers;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.*;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Label;
import org.example.MainApp;
import org.example.controllers.MusicBandDialogController;
import org.example.controllers.RemoveLowerDialogController;
import org.example.managers.DBManager;
import org.example.model.MusicBand;
import org.example.model.MusicGenre;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;


public class MainController {
    @FXML private Label currentUserLabel;
    @FXML private ComboBox<String> localeComboBox;
    @FXML private Button logoutButton;
    @FXML private Label filterNameLabel;
    @FXML private TextField filterField;
    @FXML private Label filterStudioLabel;
    @FXML private TextField filterStudioField;
    @FXML private TableView<MusicBand> tableView;
    @FXML private TableColumn<MusicBand,Integer>     colId;
    @FXML private TableColumn<MusicBand,String>      colName;
    @FXML private TableColumn<MusicBand,Integer>     colCoordX;
    @FXML private TableColumn<MusicBand,Long>        colCoordY;
    @FXML private TableColumn<MusicBand,LocalDateTime> colCreationDate;
    @FXML private TableColumn<MusicBand,Long>        colParticipants;
    @FXML private TableColumn<MusicBand,String>      colGenre;
    @FXML private TableColumn<MusicBand,String>      colStudio;
    @FXML private TableColumn<MusicBand,String>      colCreatedBy;
    @FXML private Canvas canvas;
    @FXML private Button reloadButton;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button removeButton;
    @FXML private Button removeLowerButton;
    @FXML private Button addIfMinButton;
    @FXML private Button countByStudioButton;
    @FXML private Button countGreaterThanGenreButton;
    @FXML private Button zoomInButton;
    @FXML private Button zoomOutButton;

    private final ObservableList<MusicBand> masterData = FXCollections.observableArrayList();

    private MainApp mainApp;
    private User currentUser;
    private ResourceBundle bundle;
    private Locale locale;
    private FilteredList<MusicBand> filtered;
    private GraphicsContext gc;
    private AnimationTimer animationTimer;
    private double animationPhase = 0;
    private Random random = new Random();
    private double scaleFactor = 1.0; // Scale factor for zoom

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle("bundles.messages", locale);
        currentUserLabel.setText(bundle.getString("main.label.user")+" "+currentUser.getLogin());
        logoutButton.setText(bundle.getString("main.button.logout"));
        filterNameLabel.setText(bundle.getString("main.filter.name"));
        filterStudioLabel.setText(bundle.getString("main.filter.studio"));
        reloadButton.setText(bundle.getString("button.clear"));
        addButton.setText(bundle.getString("button.add"));
        editButton.setText(bundle.getString("button.edit"));
        removeButton.setText(bundle.getString("button.remove"));
        removeLowerButton.setText(bundle.getString("button.remove_lower"));
        addIfMinButton.setText(bundle.getString("button.add_if_min"));
        countByStudioButton.setText(bundle.getString("button.count_by_studio"));
        countGreaterThanGenreButton.setText(bundle.getString("button.count_greater_than_genre"));
        colId.setText(bundle.getString("table.column.id"));
        colName.setText(bundle.getString("table.column.name"));
        colCreationDate.setText(bundle.getString("table.column.creation"));
        colParticipants.setText(bundle.getString("table.column.value"));
        colGenre.setText(bundle.getString("table.column.genre"));
        colStudio.setText(bundle.getString("table.column.studio"));

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
        colId.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getId()).asObject()
        );
        colName.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getName())
        );
        colCoordX.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getCoordinates().getX()).asObject()
        );
        colCoordY.setCellValueFactory(c ->
                new SimpleLongProperty(c.getValue().getCoordinates().getY()).asObject()
        );
        colCreationDate.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getCreationDate())
        );
        colParticipants.setCellValueFactory(c ->
                new SimpleLongProperty(c.getValue().getNumberOfParticipants()).asObject()
        );
        colGenre.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getGenre() == null ? "" : c.getValue().getGenre().name()
                )
        );
        colStudio.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getStudio() == null ? "" : c.getValue().getStudio().getName()
                )
        );
        colCreatedBy.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCreatedBy())
        );


        filtered = new FilteredList<>(masterData);
        SortedList<MusicBand> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sorted);

        filterField.textProperty().addListener((obs, oldV, newV) -> updateFilter());
        filterStudioField.textProperty().addListener((obs, oldV, newV) -> updateFilter());

        updateFilter();

        localeComboBox.getItems().addAll("Русский", "Português", "Latviešu", "Español (MX)");
        localeComboBox.setOnAction(e -> {
            switch (localeComboBox.getValue()) {
                case "Русский":   setLocale(new Locale("ru"));    break;
                case "Português": setLocale(new Locale("pt"));    break;
                case "Latviešu":  setLocale(new Locale("lv"));    break;
                default:          setLocale(new Locale("es","MX")); break;
            }
            try {
                mainApp.changeLocale(locale);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gc = canvas.getGraphicsContext2D();

        canvas.widthProperty().bind(((AnchorPane)canvas.getParent()).widthProperty());
        canvas.heightProperty().bind(((AnchorPane)canvas.getParent()).heightProperty());

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> drawCanvas());
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> drawCanvas());

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                animationPhase += 0.05;
                if (animationPhase > Math.PI * 2) {
                    animationPhase = 0;
                }
                drawCanvas();
            }
        };
        animationTimer.start();

        canvas.setOnMouseClicked(this::onCanvasClick);

        zoomInButton.setOnAction(e -> {
            scaleFactor *= 1.2;
            drawCanvas();
        });

        zoomOutButton.setOnAction(e -> {
            scaleFactor /= 1.2;
            drawCanvas();
        });

        reloadButton.setOnAction(e -> loadData());
        removeButton.setOnAction(e -> {
            MusicBand sel = tableView.getSelectionModel().getSelectedItem();
            if (sel != null
                    && DBManager.getInstance().removeByID(currentUser.getLogin(), sel.getId())) {
                filtered.getSource().remove(sel);
                drawCanvas();
            }
        });
        addButton.setOnAction(e -> {
            Optional<MusicBand> result = MusicBandDialogController.showDialog(true, currentUser, bundle);
            if (result.isPresent()) {
                loadData();
            }
        });
        editButton.setOnAction(e -> {
            MusicBand selectedBand = tableView.getSelectionModel().getSelectedItem();
            if (selectedBand != null) {
                if (selectedBand.getCreatedBy().equals(currentUser.getLogin())) {
                    Optional<MusicBand> result = MusicBandDialogController.showDialog(false, currentUser, bundle, selectedBand);
                    if (result.isPresent()) {
                        loadData();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("dialog.error.title"));
                    alert.setHeaderText(bundle.getString("dialog.error.edit.header"));
                    alert.setContentText(bundle.getString("dialog.error.edit.content"));
                    alert.showAndWait();
                }
            }
        });
        removeButton.setOnAction(e -> {
            MusicBand selectedBand = tableView.getSelectionModel().getSelectedItem();
            if (selectedBand != null) {
                if (DBManager.getInstance().removeByID(currentUser.getLogin(), selectedBand.getId())) {
                    masterData.remove(selectedBand);
                    drawCanvas();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("dialog.error.title"));
                    alert.setHeaderText(bundle.getString("dialog.error.delete.header"));
                    alert.setContentText(bundle.getString("dialog.error.delete.content"));
                    alert.showAndWait();
                }
            }
        });
        reloadButton.setOnAction(e -> {
            int deletedCount = DBManager.getInstance().clear(currentUser.getLogin());
            loadData();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("dialog.clear.title"));
            alert.setHeaderText(bundle.getString("dialog.clear.header"));
            alert.setContentText(java.text.MessageFormat.format(
                bundle.getString("dialog.clear.content"), deletedCount));
            alert.showAndWait();
        });
        removeLowerButton.setOnAction(e -> {
            Optional<Long> result = RemoveLowerDialogController.showDialog(currentUser, bundle);
            if (result.isPresent()) {
                long threshold = result.get();
                int deletedCount = DBManager.getInstance().removeLower(currentUser.getLogin(), threshold);
                loadData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("dialog.remove_lower.title"));
                alert.setHeaderText(bundle.getString("dialog.remove_lower.header"));
                alert.setContentText(java.text.MessageFormat.format(
                    bundle.getString("dialog.remove_lower.content"), deletedCount));
                alert.showAndWait();
            }
        });

        addIfMinButton.setOnAction(e -> {
            Optional<MusicBand> result = MusicBandDialogController.showDialog(true, true, currentUser, bundle);
            if (result.isPresent()) {
                MusicBand band = result.get();
                if (DBManager.getInstance().isMinParticipants(band.getNumberOfParticipants())) {

                    DBManager.getInstance().addMusicBand(band, currentUser.getLogin());
                    loadData();
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("dialog.error.title"));
                    alert.setHeaderText(bundle.getString("dialog.error.add_if_min.header"));
                    alert.setContentText(bundle.getString("dialog.error.add_if_min.content"));
                    alert.showAndWait();
                }
            }
        });

        countByStudioButton.setOnAction(e -> {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(bundle.getString("dialog.count_by_studio.title"));
            dialog.setHeaderText(bundle.getString("dialog.count_by_studio.header"));
            dialog.setContentText(bundle.getString("dialog.field.studio"));


            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String studioName = result.get().trim();
                if (studioName.isEmpty()) {
                    studioName = null;
                }

                int count = DBManager.getInstance().countByStudio(studioName);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("dialog.count_by_studio.title"));
                alert.setHeaderText(bundle.getString("dialog.count_by_studio.header"));

                String studioDisplayName = (studioName == null) ? 
                        bundle.getString("dialog.count_by_studio.null_studio") : 
                        "\"" + studioName + "\"";

                alert.setContentText(java.text.MessageFormat.format(
                    bundle.getString("dialog.count_by_studio.content"), 
                    studioDisplayName, count));

                alert.showAndWait();
            }
        });

        countGreaterThanGenreButton.setOnAction(e -> {

            ChoiceDialog<MusicGenre> dialog = new ChoiceDialog<>(null, MusicGenre.values());
            dialog.setTitle(bundle.getString("dialog.count_greater_than_genre.title"));
            dialog.setHeaderText(bundle.getString("dialog.count_greater_than_genre.header"));
            dialog.setContentText(bundle.getString("dialog.field.genre"));


            Optional<MusicGenre> result = dialog.showAndWait();

            if (result.isPresent()) {
                MusicGenre genre = result.get();
                int count = DBManager.getInstance().countGreaterThanGenre(genre);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("dialog.count_greater_than_genre.title"));
                alert.setHeaderText(bundle.getString("dialog.count_greater_than_genre.header"));

                String genreDisplayName = (genre == null) ? 
                        "null" : 
                        genre.name();

                alert.setContentText(java.text.MessageFormat.format(
                    bundle.getString("dialog.count_greater_than_genre.content"), 
                    genreDisplayName, count));

                alert.showAndWait();
            }
        });

        countGreaterThanGenreButton.setOnAction(e -> {

            ChoiceDialog<MusicGenre> dialog = new ChoiceDialog<>(null, MusicGenre.values());
            dialog.setTitle(bundle.getString("dialog.count_greater_than_genre.title"));
            dialog.setHeaderText(bundle.getString("dialog.count_greater_than_genre.header"));
            dialog.setContentText(bundle.getString("dialog.field.genre"));

            Optional<MusicGenre> result = dialog.showAndWait();

            if (result.isPresent()) {
                MusicGenre genre = result.get();
                int count = DBManager.getInstance().countGreaterThanGenre(genre);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("dialog.count_greater_than_genre.title"));
                alert.setHeaderText(bundle.getString("dialog.count_greater_than_genre.header"));

                String genreDisplayName = (genre == null) ? 
                        "null" : 
                        genre.name();

                alert.setContentText(java.text.MessageFormat.format(
                    bundle.getString("dialog.count_greater_than_genre.content"), 
                    genreDisplayName, count));

                alert.showAndWait();
            }
        });

        logoutButton.setOnAction(e -> {
            stopAnimation();
            System.exit(0);
        });

        loadData();
    }


    @FXML
    public void loadData() {
        masterData.setAll(DBManager.getInstance().getMusicBands());
        drawCanvas();
    }

    private void updateFilter() {
        String nameFilter = filterField.getText().toLowerCase();
        String studioFilter = filterStudioField.getText();
        int studioFilterLength = studioFilter.length();

        filtered.setPredicate(band -> {
            return java.util.stream.Stream.of(
                // Name filter
                band.getName().toLowerCase().contains(nameFilter),

                studioFilter.isEmpty() || 
                    ((band.getStudio() == null || band.getStudio().getName() == null) 
                        ? 0 < studioFilterLength 
                        : band.getStudio().getName().length() < studioFilterLength)
            ).allMatch(condition -> condition);
        });
    }

    private void drawCanvas() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        gc.clearRect(0, 0, width, height);

        drawCoordinateSystem(width, height);

        String currentUserLogin = DBManager.getInstance().getCurrentUser().getLogin();

        filtered.getSource().forEach(band -> {
            int id = band.getId();
            String createdBy = band.getCreatedBy();
            double x = band.getCoordinates().getX();
            double y = band.getCoordinates().getY();
            long participants = band.getNumberOfParticipants();

            double baseSize = Math.min(Math.max(participants / 10.0, 10), 50);

            double animatedSize = baseSize * (1 + 0.4 * Math.sin(animationPhase));

            boolean isCurrentUserBand = createdBy.equals(currentUserLogin);

            Color userColor = getUserColor(band.getCreatedBy(), isCurrentUserBand);

            drawMusicBand(band, x, y, animatedSize, userColor);
        });
    }

    private void drawCoordinateSystem(double width, double height) {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);

        double baseGridSpacing = 50.0;

        double gridSpacing = baseGridSpacing * scaleFactor;

        gc.strokeLine(0, height/2, width, height/2);

        gc.strokeLine(width/2, 0, width/2, height);

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineDashes(2, 4);

        double centerY = height / 2;
        double centerX = width / 2;

        double valueIncrement = baseGridSpacing / scaleFactor;

        int gridCount = 0;
        for (double i = centerY; i >= 0; i -= gridSpacing) {
            gc.strokeLine(0, i, width, i);
            if (gridCount > 0) {
                double yValue = gridCount * valueIncrement;
                gc.setFill(Color.BLACK);
                gc.fillText(String.format("%.1f", yValue), width/2 + 5, i - 5);
            }
            gridCount++;
        }

        gridCount = 0;
        for (double i = centerY; i <= height; i += gridSpacing) {
            gc.strokeLine(0, i, width, i);
            if (gridCount > 0) {
                double yValue = -gridCount * valueIncrement;
                gc.setFill(Color.BLACK);
                gc.fillText(String.format("%.1f", yValue), width/2 + 5, i - 5);
            }
            gridCount++;
        }

        gridCount = 0;
        for (double i = centerX; i >= 0; i -= gridSpacing) {
            gc.strokeLine(i, 0, i, height);
            if (gridCount > 0) {
                double xValue = -gridCount * valueIncrement;
                gc.setFill(Color.BLACK);
                gc.fillText(String.format("%.1f", xValue), i + 5, centerY + 15);
            }
            gridCount++;
        }

        gridCount = 0;
        for (double i = centerX; i <= width; i += gridSpacing) {
            gc.strokeLine(i, 0, i, height);
            if (gridCount > 0) {
                double xValue = gridCount * valueIncrement;
                gc.setFill(Color.BLACK);
                gc.fillText(String.format("%.1f", xValue), i + 5, centerY + 15);
            }
            gridCount++;
        }

        gc.setFill(Color.BLACK);
        gc.fillText("0", centerX + 5, centerY + 15);

        gc.setLineDashes(null);
    }

    /**
     * Get color based on username
     * @param username The username
     * @param isCurrentUser Whether the band belongs to the current user
     * @return Color for the user
     */
    private Color getUserColor(String username, boolean isCurrentUser) {
        int userHash = username.hashCode();

        if (isCurrentUser) {
            return Color.BLUE;
        }

        double hue = (Math.abs(userHash) % 300) / 360.0;
        double saturation = 0.7 + (Math.abs(userHash % 30) / 100.0);
        double brightness = 0.7 + (Math.abs(userHash % 30) / 100.0);

        return Color.hsb(hue * 360, saturation, brightness);
    }

    private void drawMusicBand(MusicBand band, double x, double y, double size, Color color) {
        double centerX = canvas.getWidth() / 2 + (x * scaleFactor);
        double centerY = canvas.getHeight() / 2 - (y * scaleFactor);

        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        if (band.getGenre() == null) {
            gc.fillOval(centerX - size/2, centerY - size/2, size, size);
            gc.strokeOval(centerX - size/2, centerY - size/2, size, size);
        } else {
            switch (band.getGenre()) {
                case JAZZ:
                    double[] xPoints = {
                        centerX, 
                        centerX - size/2, 
                        centerX + size/2
                    };
                    double[] yPoints = {
                        centerY - size/2, 
                        centerY + size/2, 
                        centerY + size/2
                    };
                    gc.fillPolygon(xPoints, yPoints, 3);
                    gc.strokePolygon(xPoints, yPoints, 3);
                    break;

                case PUNK_ROCK:
                    drawStar(centerX, centerY, size/2, size/4, 5, color);
                    break;

                case ROCK:
                    gc.fillRect(centerX - size/2, centerY - size/2, size, size);
                    gc.strokeRect(centerX - size/2, centerY - size/2, size, size);
                    break;

                default:
                    gc.fillOval(centerX - size/2, centerY - size/2, size, size);
                    gc.strokeOval(centerX - size/2, centerY - size/2, size, size);
            }
        }

        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(String.valueOf(band.getId()), centerX, centerY);
    }

    private void drawStar(double centerX, double centerY, double outerRadius, double innerRadius, int numPoints, Color color) {
        gc.setFill(color);
        gc.setStroke(Color.BLACK);

        double[] xPoints = new double[numPoints * 2];
        double[] yPoints = new double[numPoints * 2];

        for (int i = 0; i < numPoints * 2; i++) {
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;
            double angle = Math.PI / numPoints * i;

            xPoints[i] = centerX + radius * Math.sin(angle);
            yPoints[i] = centerY - radius * Math.cos(angle);
        }

        gc.fillPolygon(xPoints, yPoints, numPoints * 2);
        gc.strokePolygon(xPoints, yPoints, numPoints * 2);
    }

    /**
     * Stops the animation timer to prevent memory leaks
     */
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    private void onCanvasClick(MouseEvent e) {
        double clickX = (e.getX() - canvas.getWidth() / 2) / scaleFactor;
        double clickY = (canvas.getHeight() / 2 - e.getY()) / scaleFactor; // Invert Y axis

        String currentUserLogin = DBManager.getInstance().getCurrentUser().getLogin();

        filtered.getSource().stream()
            .filter(band -> {
                double bandX = band.getCoordinates().getX();
                double bandY = band.getCoordinates().getY();

                double dx = clickX - bandX;
                double dy = clickY - bandY;
                double distance = Math.hypot(dx, dy);

                long participants = band.getNumberOfParticipants();
                double baseSize = Math.min(Math.max(participants / 10.0, 10), 50);

                return distance <= baseSize / 2;
            })
            .findFirst()
            .ifPresent(band -> {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle(bundle.getString("dialog.band_info.title"));

                boolean isUserBand = band.getCreatedBy().equals(currentUserLogin);
                if (isUserBand) {
                    dialog.setHeaderText(bundle.getString("dialog.band_info.header.own") + " " + band.getName());
                } else {
                    dialog.setHeaderText(bundle.getString("dialog.band_info.header.other") + " " + band.getName() + 
                                        " (" + bundle.getString("dialog.band_info.owner") + ": " + band.getCreatedBy() + ")");
                }

                VBox content = new VBox(10);
                content.getChildren().addAll(
                    new Label(bundle.getString("table.column.id") + ": " + band.getId()),
                    new Label(bundle.getString("table.column.name") + ": " + band.getName()),
                    new Label(bundle.getString("dialog.band_info.coordinates") + ": (" + band.getCoordinates().getX() + ", " + band.getCoordinates().getY() + ")"),
                    new Label(bundle.getString("table.column.creation") + ": " + band.getCreationDate()),
                    new Label(bundle.getString("table.column.value") + ": " + band.getNumberOfParticipants()),
                    new Label(bundle.getString("table.column.genre") + ": " + (band.getGenre() == null ? bundle.getString("dialog.band_info.none") : band.getGenre().name())),
                    new Label(bundle.getString("table.column.studio") + ": " + (band.getStudio() == null ? bundle.getString("dialog.band_info.none") : band.getStudio().getName())),
                    new Label(bundle.getString("table.column.createdBy") + ": " + band.getCreatedBy())
                );

                dialog.getDialogPane().setContent(content);

                if (isUserBand) {
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.APPLY);
                } else {
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                }

                Optional<ButtonType> result = dialog.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.APPLY && isUserBand) {
                    Optional<MusicBand> editResult = MusicBandDialogController.showDialog(false, currentUser, bundle, band);
                    if (editResult.isPresent()) {
                        loadData();
                    }
                }
            });
    }
}
