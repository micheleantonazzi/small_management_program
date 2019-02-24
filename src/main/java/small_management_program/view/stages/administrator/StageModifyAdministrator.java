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
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorDelete;
import small_management_program.controller.queries.administrator.AdministratorModify;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import small_management_program.model.DatabaseException;
import small_management_program.view.graphicutilities.ButtonId;
import small_management_program.view.graphicutilities.GraphicUtilities;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StageModifyAdministrator extends Stage {

    private GridPane buildLayout(DuplicateMap<Integer, String> administrators){
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(1, administrators.keySet().size());
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        int row = 0;
        Set<Integer> ids = administrators.keySet();
        for(Iterator<Integer> it = ids.iterator(); it.hasNext();){
            int id = it.next();
            gridPane.add(buildGridPaneAdministrator(id, administrators.get(id), gridPane), 0, row++);
        }

        GridPane gridPaneLast = GraphicUtilities.getInstance().getEquivalentGridPane(2, 1, new int[]{80}, new int []{});
        gridPaneLast.setHgap(20);
        gridPaneLast.setPadding(new Insets(5, 0, 0, 0));
        gridPane.add(gridPaneLast, 0, row);

        Button buttonExit = new Button("Esci");
        buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        buttonExit.setPrefWidth(1000);
        gridPaneLast.add(buttonExit, 1, 0);
        buttonExit.setOnAction(event -> {
            event.consume();
            ControllerFacade.getInstance().updateAll();
            this.close();
        });

        return gridPane;
    }

    private GridPane buildGridPaneAdministrator(int id, List<String> fields, GridPane parentPane){
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(3, 3 , new int[]{16, 42, 42}, new int[]{});
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(15, 0, 0, 0));

        gridPane.add(new Label("Acronimo"), 0, 0);
        gridPane.add(new Label("Nome"), 1, 0);
        gridPane.add(new Label("Cognome"), 2, 0);

        ButtonId buttonModifyAdministrator = new ButtonId("Modifica", id);
        buttonModifyAdministrator.setGraphic(new ImageView(this.getClass().getResource("/images/icons/account-edit.png").toString()));

        ButtonId buttonRemoveAdministrator = new ButtonId("Elimina", id);
        buttonRemoveAdministrator.setGraphic(new ImageView(this.getClass().getResource("/images/icons/delete.png").toString()));

        TextField textFieldAcronym = new TextField(fields.get(0));
        TextField textFieldName = new TextField(fields.get(1));
        TextField textFieldSurname = new TextField(fields.get(2));


        gridPane.add(textFieldAcronym, 0, 1);
        textFieldAcronym.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textFieldAcronym.getText().length() == 0 || textFieldName.getText().length() == 0 || textFieldSurname.getText().length() == 0){
                buttonModifyAdministrator.setDisable(true);
            }
            else{
                buttonModifyAdministrator.setDisable(false);
            }
            if(newValue.length() > 3){
                textFieldAcronym.setText(oldValue);
            }
        });

        gridPane.add(textFieldName, 1, 1);
        textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textFieldAcronym.getText().length() == 0 || textFieldName.getText().length() == 0 || textFieldSurname.getText().length() == 0){
                buttonModifyAdministrator.setDisable(true);
            }
            else{
                buttonModifyAdministrator.setDisable(false);
            }
        });

        gridPane.add(textFieldSurname, 2, 1);
        textFieldSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textFieldAcronym.getText().length() == 0 || textFieldName.getText().length() == 0 || textFieldSurname.getText().length() == 0){
                buttonModifyAdministrator.setDisable(true);
            }
            else{
                buttonModifyAdministrator.setDisable(false);
            }
        });

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(10);
        gridPane.add(hBoxButtons, 2, 2);

        buttonRemoveAdministrator.setPrefWidth(1000);
        hBoxButtons.getChildren().add(buttonRemoveAdministrator);
        buttonRemoveAdministrator.setOnAction(event -> {
            event.consume();
            try{
                ControllerFacade.getInstance().executeQuery(new AdministratorDelete(buttonRemoveAdministrator.retId()));
                parentPane.getChildren().remove(gridPane);
                this.setHeight(this.getHeight() - gridPane.getHeight());
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        buttonModifyAdministrator.setPrefWidth(1000);
        buttonModifyAdministrator.setDisable(true);
        hBoxButtons.getChildren().add(buttonModifyAdministrator);
        buttonModifyAdministrator.setOnAction(event -> {
            event.consume();
            try{
                ControllerFacade.getInstance().executeQuery(new AdministratorModify(buttonModifyAdministrator.retId(), textFieldAcronym.getText(), textFieldName.getText(),
                        textFieldSurname.getText()));
                buttonModifyAdministrator.setDisable(true);
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        return gridPane;
    }

    public StageModifyAdministrator() throws DatabaseException {
        setTitle("Modifica amministratori");
        QueryWithResults query = new AdministratorSelectAll();
        ControllerFacade.getInstance().executeQuery(query);
        DuplicateMap<Integer, String> administrators = query.getResults();
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 3);
        Scene scene = new Scene(buildLayout(administrators));
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
        this.setOnCloseRequest(event -> ControllerFacade.getInstance().updateAll());
    }
}
