package small_management_program.view.stages.administrator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.queries.administrator.AdministratorAddNew;
import small_management_program.model.DatabaseException;
import small_management_program.view.graphicutilities.GraphicUtilities;

public class StageAddAdministrator extends Stage {

    private GridPane buildLayout(){
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(3, 3, new int[]{16, 42, 42}, new int[]{});
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(new Label("Acronimo"), 0, 0);
        gridPane.add(new Label("Nome"), 1, 0);
        gridPane.add(new Label("Cognome"), 2, 0);

        Button buttonAddAdministrator = new Button("Aggiungi");
        buttonAddAdministrator.setGraphic(new ImageView(this.getClass().getResource("/images/icons/account-plus.png").toString()));

        Button buttonExit = new Button("Esci");
        buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));

        TextField textFieldAcronym = new TextField();
        TextField textFieldName = new TextField();
        TextField textFieldSurname = new TextField();


        gridPane.add(textFieldAcronym, 0, 1);
        textFieldAcronym.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textFieldAcronym.getText().length() == 0 || textFieldName.getText().length() == 0 || textFieldSurname.getText().length() == 0){
                buttonAddAdministrator.setDisable(true);
            }
            else{
                buttonAddAdministrator.setDisable(false);
            }
            if(newValue.length() > 3){
                textFieldAcronym.setText(oldValue);
            }
        });

        gridPane.add(textFieldName, 1, 1);
        textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textFieldAcronym.getText().length() == 0 || textFieldName.getText().length() == 0 || textFieldSurname.getText().length() == 0){
                buttonAddAdministrator.setDisable(true);
            }
            else{
                buttonAddAdministrator.setDisable(false);
            }
        });

        gridPane.add(textFieldSurname, 2, 1);
        textFieldSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textFieldAcronym.getText().length() == 0 || textFieldName.getText().length() == 0 || textFieldSurname.getText().length() == 0){
                buttonAddAdministrator.setDisable(true);
            }
            else{
                buttonAddAdministrator.setDisable(false);
            }
        });

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(10);
        gridPane.add(hBoxButtons, 2, 2);

        buttonExit.setPrefWidth(1000);
        hBoxButtons.getChildren().add(buttonExit);
        buttonExit.setOnAction(event -> {
            event.consume();
            ControllerFacade.getInstance().updateAll();
            this.close();
        });

        buttonAddAdministrator.setPrefWidth(1000);
        buttonAddAdministrator.setDisable(true);
        hBoxButtons.getChildren().add(buttonAddAdministrator);
        buttonAddAdministrator.setOnAction(event -> {
            event.consume();
            try{
                ControllerFacade.getInstance().executeQuery(new AdministratorAddNew(textFieldName.getText(), textFieldSurname.getText(),
                        textFieldAcronym.getText()));
                textFieldAcronym.setText("");
                textFieldName.setText("");
                textFieldSurname.setText("");
                buttonAddAdministrator.setDisable(true);
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        return gridPane;
    }

    public StageAddAdministrator(){
        setTitle("Aggiungi amministratore");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 3);
        Scene scene = new Scene(buildLayout());
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
        this.setOnCloseRequest(event -> ControllerFacade.getInstance().updateAll());
    }
}
