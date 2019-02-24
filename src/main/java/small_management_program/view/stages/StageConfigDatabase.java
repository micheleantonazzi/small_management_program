package small_management_program.view.stages;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import small_management_program.controller.ControllerFacade;
import small_management_program.view.graphicutilities.GraphicUtilities;

public class StageConfigDatabase extends Stage {
    private EventHandlerSupplier eventHandlerSupplier;
    private ControllerFacade controller;

    private TextField textFieldDatabaseAddress;
    private TextField textFieldDatabaseName;
    private TextField textFieldDatabasePort;
    private TextField textFieldUser;
    private PasswordField textFieldPassword;
    private Button buttonTestConnection;
    private Button buttonSaveSettings;
    private Button buttonConnect;
    private CheckBox checkBoxSsl;

    private GridPane buildLayout(){
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(2, 7, new int[]{30});
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        this.buttonTestConnection = new Button("Test connessione");
        this.buttonTestConnection.setGraphic(new ImageView(this.getClass().getResource("/images/icons/database-search.png").toString()));

        this.buttonSaveSettings = new Button("Salva impostazioni");
        this.buttonSaveSettings.setDisable(true);
        this.buttonSaveSettings.setGraphic(new ImageView(this.getClass().getResource("/images/icons/content-save-outline.png").toString()));

        this.buttonConnect = new Button("Connetti");
        this.buttonConnect.setGraphic(new ImageView(this.getClass().getResource("/images/icons/database-export.png").toString()));

        Label address = new Label("Indirizzo:");
        gridPane.add(address, 0, 0);
        this.textFieldDatabaseAddress = new TextField();
        this.textFieldDatabaseAddress.textProperty().addListener(this.eventHandlerSupplier.getListenerEnableButtonSave());
        gridPane.add(this.textFieldDatabaseAddress, 1, 0);

        Label name = new Label("Nome:");
        gridPane.add(name, 0, 1);
        this.textFieldDatabaseName = new TextField();
        this.textFieldDatabaseName.textProperty().addListener(this.eventHandlerSupplier.getListenerEnableButtonSave());
        gridPane.add(this.textFieldDatabaseName, 1, 1);

        Label port = new Label("Porta:");
        gridPane.add(port, 0, 2);
        this.textFieldDatabasePort = new TextField();
        this.textFieldDatabasePort.textProperty().addListener(this.eventHandlerSupplier.getListenerEnableButtonSave());
        gridPane.add(this.textFieldDatabasePort, 1, 2);

        Label user = new Label("Utente:");
        gridPane.add(user, 0, 3);
        this.textFieldUser = new TextField();
        this.textFieldUser.textProperty().addListener(this.eventHandlerSupplier.getListenerEnableButtonSave());
        gridPane.add(this.textFieldUser, 1, 3);

        Label password = new Label("Password:");
        gridPane.add(password, 0, 4);
        this.textFieldPassword = new PasswordField();
        this.textFieldPassword.textProperty().addListener(this.eventHandlerSupplier.getListenerEnableButtonSave());
        gridPane.add(this.textFieldPassword, 1, 4);

        Label ssl = new Label("Connessione criptata");
        gridPane.add(ssl,0, 5);
        this.checkBoxSsl = new CheckBox();
        gridPane.add(this.checkBoxSsl, 1, 5);
        checkBoxSsl.selectedProperty().addListener((observable, oldValue, newValue) -> this.controller.setDatabaseSsl(String.valueOf(newValue)));

        getParameters();

        HBox HBoxButtons = new HBox();
        HBoxButtons.setSpacing(10);
        HBoxButtons.setAlignment(Pos.BOTTOM_RIGHT);
        HBoxButtons.setPadding(new Insets(5, 0, 0, 0));
        gridPane.add(HBoxButtons, 0, 6, 2, 1);
        HBoxButtons.getChildren().addAll(this.buttonTestConnection, this.buttonSaveSettings, this.buttonConnect);
        setButtonsActions();
        return gridPane;
    }

    private void getParameters(){
        this.textFieldDatabaseAddress.setText(controller.getDatabaseAddress());
        this.textFieldDatabaseName.setText(controller.getDatabaseName());
        this.textFieldDatabasePort.setText(controller.getDatabasePort());
        this.textFieldUser.setText(controller.getDatabaseUser());
        this.textFieldPassword.setText(controller.getDatabasePassword());
        this.checkBoxSsl.setSelected(Boolean.valueOf(this.controller.getDatabaseSsl()));

    }

    private void setButtonsActions(){
        this.buttonTestConnection.addEventFilter(ActionEvent.ANY, eventHandlerSupplier.getEventTestConnection());
        this.buttonSaveSettings.addEventFilter(ActionEvent.ANY, eventHandlerSupplier.getEventSaveSettings());
        this.buttonConnect.addEventFilter(ActionEvent.ANY, eventHandlerSupplier.getEventSetConnection());
    }

    public StageConfigDatabase() {
        this.controller = ControllerFacade.getInstance();
        this.eventHandlerSupplier = new EventHandlerSupplier();
        setTitle("Impostazioni database");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 3);
        Scene scene = new Scene(buildLayout());
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
    }


    //---------------- PRIVATE CLASS --------------//


    private class EventHandlerSupplier{

        public EventHandler<ActionEvent> getEventTestConnection(){
            EventHandler<ActionEvent> eventTestConnection = (event) -> {
                boolean connectionSuccess = StageConfigDatabase.this.controller.testConnection(textFieldDatabaseAddress.getText(),
                        StageConfigDatabase.this.textFieldDatabasePort.getText(),
                        StageConfigDatabase.this.textFieldDatabaseName.getText(),
                        StageConfigDatabase.this.textFieldUser.getText(),
                        StageConfigDatabase.this.textFieldPassword.getText());
                if(connectionSuccess == false){
                    GraphicUtilities.getInstance().showAlertError("Errore di connessione",
                            "Attenzione, impossibile stabilire una connessione con il database.");
                }
                else{
                    GraphicUtilities.getInstance().showAlertSuccess("Connessione riuscita",
                            "La connessione al database è avvenuta con successo.");
                }
                event.consume();
            };
            return eventTestConnection;
        }

        public EventHandler<ActionEvent> getEventSaveSettings(){
            EventHandler<ActionEvent> eventSaveSettings = (event) -> {
                StageConfigDatabase.this.controller.setDatabaseAddress(StageConfigDatabase.this.textFieldDatabaseAddress.getText());
                StageConfigDatabase.this.controller.setDatabaseName(StageConfigDatabase.this.textFieldDatabaseName.getText());
                StageConfigDatabase.this.controller.setDatabasePort(StageConfigDatabase.this.textFieldDatabasePort.getText());
                StageConfigDatabase.this.controller.setDatabaseUser(StageConfigDatabase.this.textFieldUser.getText());
                StageConfigDatabase.this.controller.setDatabasePassword(StageConfigDatabase.this.textFieldPassword.getText());
                StageConfigDatabase.this.controller.setDatabaseSsl(String.valueOf(StageConfigDatabase.this.checkBoxSsl.isSelected()));
                StageConfigDatabase.this.buttonSaveSettings.setDisable(true);
                event.consume();
            };
            return eventSaveSettings;
        }

        public EventHandler<ActionEvent> getEventSetConnection(){
            EventHandler<ActionEvent> eventSetConnection = (event) ->{
                boolean connection = StageConfigDatabase.this.controller.setConnection(textFieldDatabaseAddress.getText(), textFieldDatabasePort.getText(),
                        textFieldDatabaseName.getText(), textFieldUser.getText(), textFieldPassword.getText());
                if (connection){
                    StageConfigDatabase.this.controller.setDatabaseAddress(StageConfigDatabase.this.textFieldDatabaseAddress.getText());
                    StageConfigDatabase.this.controller.setDatabaseName(StageConfigDatabase.this.textFieldDatabaseName.getText());
                    StageConfigDatabase.this.controller.setDatabasePort(StageConfigDatabase.this.textFieldDatabasePort.getText());
                    StageConfigDatabase.this.controller.setDatabaseUser(StageConfigDatabase.this.textFieldUser.getText());
                    StageConfigDatabase.this.controller.setDatabasePassword(StageConfigDatabase.this.textFieldPassword.getText());
                    StageConfigDatabase.this.controller.setDatabaseSsl(String.valueOf(StageConfigDatabase.this.checkBoxSsl.isSelected()));
                    GraphicUtilities.getInstance().showAlertSuccess("Connessione riuscita",
                            "La connessione con il database è avvenuta con successo");
                    StageConfigDatabase.this.close();
                }
                else{
                    GraphicUtilities.getInstance().showAlertError("Errore connnessione",
                            "La connessione con il database non è riuscita, " +
                                    "controlla le credenziali o assicurati di essere connesso a internet");
                }
                event.consume();
            };
            return eventSetConnection;
        }

        public ChangeListener<String> getListenerEnableButtonSave(){
            return (observable, oldText, newText) -> StageConfigDatabase.this.buttonSaveSettings.setDisable(false);
        }
    }

}
