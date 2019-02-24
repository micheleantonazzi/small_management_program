package small_management_program.view.stages.condo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import small_management_program.controller.queries.condo.CondoAddNew;
import small_management_program.model.DatabaseException;
import small_management_program.model.Months;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.util.Iterator;
import java.util.Set;

public class StageAddCondo extends Stage {

    TextField textFieldId = new TextField();
    TextField textFieldCode = new TextField();
    ChoiceBox choiceBoxAdministrators = new ChoiceBox();
    ChoiceBox choiceBoxMonths = new ChoiceBox();
    TextField textFieldName = new TextField();
    TextField textFieldProvince = new TextField();
    TextField textFieldCity = new TextField();
    TextField textFieldAddress = new TextField();
    TextField textFieldCap = new TextField();
    TextField textFieldFlats = new TextField();
    Button buttonAdd = new Button("Aggiungi");
    Button buttonExit = new Button("Esci");

    private GridPane buildLayout() throws DatabaseException {
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(2, 11, new int[]{45});
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(new Label("Codice"),0,0);
        gridPane.add(this.textFieldId, 1, 0);
        this.textFieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 3)
                this.textFieldId.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldId.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.buttonAdd.setDisable(!checkInput());
        });

        gridPane.add(new Label("Codice fiscale"), 0, 1);
        gridPane.add(this.textFieldCode, 1, 1);
        this.textFieldCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 11)
                this.textFieldCode.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldCode.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.buttonAdd.setDisable(!checkInput());
        });

        QueryWithResults getAdministrator = new AdministratorSelectAll();
        ControllerFacade.getInstance().executeQuery(getAdministrator);
        DuplicateMap<Integer, String> results = getAdministrator.getResults();
        ObservableList<ChoiceBoxItemId> itemsAdministrators = FXCollections.observableArrayList();
        Set<Integer> administratorsIds = results.keySet();
        for(Iterator<Integer> it = administratorsIds.iterator(); it.hasNext();){
            int id = it.next();
            itemsAdministrators.add(new ChoiceBoxItemId(id, results.get(id, 1) + " " + results.get(id, 2)));
        }
        this.choiceBoxAdministrators.setItems(itemsAdministrators);
        this.choiceBoxAdministrators.setPrefWidth(1000);
        gridPane.add(new Label("Amministratore"),0, 2);
        gridPane.add(choiceBoxAdministrators, 1, 2);
        this.choiceBoxAdministrators.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> this.buttonAdd.setDisable(!checkInput()));

        ObservableList<ChoiceBoxItemId> itemsMonths = FXCollections.observableArrayList();
        for(int i = 0; i < 12; ++i){
            itemsMonths.add(new ChoiceBoxItemId(i, Months.getInstance().getMonthName(i)));
        }
        this.choiceBoxMonths.setItems(itemsMonths);
        this.choiceBoxMonths.setPrefWidth(1000);
        gridPane.add(new Label("Mese di fatturazione"), 0, 3);
        gridPane.add(this.choiceBoxMonths, 1, 3);
        this.choiceBoxMonths.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> this.buttonAdd.setDisable(!checkInput()));

        gridPane.add(new Label("Nome"), 0, 4);
        gridPane.add(this.textFieldName, 1, 4);
        this.textFieldName.textProperty().addListener((observable, oldValue, newValue) -> this.buttonAdd.setDisable(!checkInput()));

        gridPane.add(new Label("Provincia"), 0, 5);
        gridPane.add(this.textFieldProvince, 1, 5);
        this.textFieldProvince.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 2)
                this.textFieldProvince.setText(oldValue);
            this.buttonAdd.setDisable(!checkInput());
        });


        gridPane.add(new Label("Città"), 0, 6);
        gridPane.add(this.textFieldCity, 1, 6);
        this.textFieldCity.textProperty().addListener((observable, oldValue, newValue) -> this.buttonAdd.setDisable(!checkInput()));

        gridPane.add(new Label("Indirizzo"), 0, 7);
        gridPane.add(this.textFieldAddress, 1, 7);
        this.textFieldAddress.textProperty().addListener((observable, oldValue, newValue) -> this.buttonAdd.setDisable(!checkInput()));

        gridPane.add(new Label("CAP"), 0, 8);
        gridPane.add(this.textFieldCap, 1, 8);
        this.textFieldCap.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 5)
                this.textFieldCap.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldCap.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.buttonAdd.setDisable(!checkInput());
        });

        gridPane.add(new Label("Appartamenti"), 0, 9);
        gridPane.add(this.textFieldFlats, 1, 9);
        this.textFieldFlats.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.textFieldFlats.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.buttonAdd.setDisable(!checkInput());
        });

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(10);

        this.buttonExit.setPrefWidth(1000);
        this.buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        hBoxButtons.getChildren().add(this.buttonExit);
        this.buttonExit.setOnAction(event -> {
            event.consume();
            ControllerFacade.getInstance().updateAll();
            this.close();
        });

        this.buttonAdd.setDisable(true);
        this.buttonAdd.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home-plus.png").toString()));
        this.buttonAdd.setPrefWidth(1000);
        hBoxButtons.getChildren().add(this.buttonAdd);
        this.buttonAdd.setOnAction(event -> {
            event.consume();
            try{
                ControllerFacade.getInstance().executeQuery(new CondoAddNew(Integer.valueOf(this.textFieldId.getText()), this.textFieldCode.getText(),
                        this.choiceBoxAdministrators.getSelectionModel().getSelectedItem().hashCode(), this.choiceBoxMonths.getSelectionModel().getSelectedItem().hashCode(),
                        this.textFieldName.getText(), this.textFieldProvince.getText(), this.textFieldCity.getText(), this.textFieldAddress.getText(), this.textFieldCap.getText(),
                        Integer.valueOf(this.textFieldFlats.getText())));
                this.textFieldId.setText("");
                this.textFieldCode.setText("");
                this.choiceBoxAdministrators.getSelectionModel().select(-1);
                this.choiceBoxMonths.getSelectionModel().select(-1);
                this.textFieldName.setText("");
                this.textFieldProvince.setText("");
                this.textFieldCity.setText("");
                this.textFieldAddress.setText("");
                this.textFieldCap.setText("");
                this.textFieldFlats.setText("");
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        gridPane.add(hBoxButtons, 1, 10);

        return gridPane;
    }

    private boolean checkInput(){
        if(textFieldId.getText().length() > 0 && textFieldCode.getText().length() == 11 && choiceBoxAdministrators.getSelectionModel().getSelectedIndex() > -1 &&
                choiceBoxMonths.getSelectionModel().getSelectedIndex() > -1 && textFieldName.getText().length() > 0 && textFieldProvince.getText().length() == 2 &&
                textFieldCity.getText().length() > 0 && textFieldAddress.getText().length() > 0 && textFieldCap.getText().length() == 5 && textFieldFlats.getText().length() > 0)
            return true;
        return false;
    }

    public StageAddCondo() throws DatabaseException{
        setTitle("Aggiungi condominio");
        initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(buildLayout());
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 4);
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
        this.setOnCloseRequest(event -> ControllerFacade.getInstance().updateAll());
    }

}
