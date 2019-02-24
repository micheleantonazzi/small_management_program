package small_management_program.view.stages.condo;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import small_management_program.controller.queries.billing.BillingSelectWithParameter;
import small_management_program.controller.queries.condo.CondoDelete;
import small_management_program.controller.queries.condo.CondoModify;
import small_management_program.controller.queries.condo.CondoSelectAll;
import small_management_program.controller.queries.condo.CondoSelectWithParameters;
import small_management_program.model.DatabaseException;
import small_management_program.model.Months;
import small_management_program.view.graphicutilities.ButtonId;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;
import small_management_program.view.graphicutilities.GraphicUtilities;
import java.util.Iterator;
import java.util.Set;

public class StageModifyCondo extends Stage {

    ChoiceBox choiceBoxCondos = new ChoiceBox();

    TextField textFieldId = new TextField();
    Label labelId = new Label("Codice");

    TextField textFieldCode = new TextField();
    Label labelCode = new Label("Codice fiscale");

    ChoiceBox choiceBoxAdministrators = new ChoiceBox();
    Label labelAdministrator = new Label("Amministratore");

    ChoiceBox choiceBoxMonths = new ChoiceBox();
    Label labelMonths = new Label("Mese di fatturazione");

    TextField textFieldName = new TextField();
    Label labelName = new Label("Nome");

    TextField textFieldProvince = new TextField();
    Label labelProvince = new Label("Provincia");

    TextField textFieldCity = new TextField();
    Label labelCity = new Label("Città");

    TextField textFieldAddress = new TextField();
    Label labelAddress = new Label("Indirizzo");

    TextField textFieldCap = new TextField();
    Label labelCap = new Label("CAP");

    TextField textFieldFlats = new TextField();
    Label labelFlats = new Label("Appartamenti");

    ButtonId buttonModify = new ButtonId("Modifica");
    ButtonId buttonDelete = new ButtonId("Elimina");
    Button buttonExit = new Button("Esci");

    ChangeListener<ChoiceBoxItemId> listenerCondos;

    private GridPane buildLayout() throws DatabaseException{
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(2, 1, new int[]{45});
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        QueryWithResults queryCondos = new CondoSelectAll();
        ControllerFacade.getInstance().executeQuery(queryCondos);
        this.choiceBoxCondos.setItems(GraphicUtilities.getInstance().getCondosObservableList(queryCondos.getResults()));
        this.choiceBoxCondos.setPrefWidth(1000);
        gridPane.add(new Label("Condominio da modficare"), 0, 0);
        gridPane.add(this.choiceBoxCondos, 1, 0);

        this.listenerCondos = (observableValue, oldValue, newValue) -> {
            try{
                QueryWithResults querySelectCondo = new CondoSelectWithParameters(new WhereParameters("id_condo = " + newValue.hashCode()));
                ControllerFacade.getInstance().executeQuery(querySelectCondo);
                DuplicateMap<Integer, String> result = querySelectCondo.getResults();
                int idCondo = result.keySet().iterator().next();
                this.buttonModify.setId(idCondo);
                this.buttonDelete.setId(idCondo);

                this.textFieldId.setText(String.valueOf(idCondo));
                this.textFieldCode.setText(result.get(idCondo, 0));

                int idAdministrator = Integer.valueOf(result.get(idCondo, 1));
                Iterator<ChoiceBoxItemId> itAdministrator = this.choiceBoxAdministrators.getItems().iterator();
                int countAdministrator = 0;
                for(; idAdministrator != itAdministrator.next().hashCode(); ++countAdministrator){}
                this.choiceBoxAdministrators.getSelectionModel().select(countAdministrator);

                int idMonth = Integer.valueOf(result.get(idCondo, 2));
                Iterator<ChoiceBoxItemId> itMonths = this.choiceBoxMonths.getItems().iterator();
                int countMonth = 0;
                for(; idMonth != itMonths.next().hashCode(); ++countMonth){}
                this.choiceBoxMonths.getSelectionModel().select(countMonth);

                this.textFieldName.setText(result.get(idCondo, 3));
                this.textFieldProvince.setText(result.get(idCondo, 4));
                this.textFieldCity.setText(result.get(idCondo, 5));
                this.textFieldAddress.setText(result.get(idCondo, 6));
                this.textFieldCap.setText(result.get(idCondo, 7));
                this.textFieldFlats.setText(result.get(idCondo, 8));

                this.setDisabled(false);

                //Se ci sono fatture diabilito la possibilità di cambiare mese di chiusura dell'esercizio,
                //altrimenti verranno introdotti errori

                QueryWithResults billingSelect = new BillingSelectWithParameter("id_condo=" + idCondo);
                ControllerFacade.getInstance().executeQuery(billingSelect);
                if(billingSelect.getResults().keySet().size() > 0)
                    this.choiceBoxMonths.setDisable(true);

            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        };
        this.choiceBoxCondos.getSelectionModel().selectedItemProperty().addListener(listenerCondos);

        setDisabled(true);

        GridPane gridPaneCondo = GraphicUtilities.getInstance().getEquivalentGridPane(2, 1, new int[]{45});
        gridPaneCondo.setAlignment(Pos.CENTER);
        gridPaneCondo.setHgap(10);
        gridPaneCondo.setVgap(10);
        gridPaneCondo.setPadding(new Insets(25, 0, 0, 0));
        gridPane.add(gridPaneCondo, 0, 1, 2, 1);

        gridPaneCondo.add(labelId,0,0);
        gridPaneCondo.add(this.textFieldId, 1, 0);
        this.textFieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 3)
                this.textFieldId.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldId.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.buttonModify.setDisable(!checkInput());
        });

        gridPaneCondo.add(this.labelCode, 0, 1);
        gridPaneCondo.add(this.textFieldCode, 1, 1);
        this.textFieldCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 11)
                this.textFieldCode.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldCode.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.buttonModify.setDisable(!checkInput());
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
        gridPaneCondo.add(this.labelAdministrator,0, 2);
        gridPaneCondo.add(choiceBoxAdministrators, 1, 2);
        this.choiceBoxAdministrators.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> this.buttonModify.setDisable(!checkInput()));

        ObservableList<ChoiceBoxItemId> itemsMonths = FXCollections.observableArrayList();
        for(int i = 0; i < 12; ++i){
            itemsMonths.add(new ChoiceBoxItemId(i, Months.getInstance().getMonthName(i)));
        }
        this.choiceBoxMonths.setItems(itemsMonths);
        this.choiceBoxMonths.setPrefWidth(1000);
        gridPaneCondo.add(this.labelMonths, 0, 3);
        gridPaneCondo.add(this.choiceBoxMonths, 1, 3);
        this.choiceBoxMonths.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> this.buttonModify.setDisable(!checkInput()));

        gridPaneCondo.add(this.labelName, 0, 4);
        gridPaneCondo.add(this.textFieldName, 1, 4);
        this.textFieldName.textProperty().addListener((observable, oldValue, newValue) -> this.buttonModify.setDisable(!checkInput()));

        gridPaneCondo.add(this.labelProvince, 0, 5);
        gridPaneCondo.add(this.textFieldProvince, 1, 5);
        this.textFieldProvince.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 2)
                this.textFieldProvince.setText(oldValue);
            this.buttonModify.setDisable(!checkInput());
        });


        gridPaneCondo.add(this.labelCity, 0, 6);
        gridPaneCondo.add(this.textFieldCity, 1, 6);
        this.textFieldCity.textProperty().addListener((observable, oldValue, newValue) -> buttonModify.setDisable(!checkInput()));

        gridPaneCondo.add(this.labelAddress, 0, 7);
        gridPaneCondo.add(this.textFieldAddress, 1, 7);
        this.textFieldAddress.textProperty().addListener((observable, oldValue, newValue) -> buttonModify.setDisable(!checkInput()));

        gridPaneCondo.add(this.labelCap, 0, 8);
        gridPaneCondo.add(this.textFieldCap, 1, 8);
        this.textFieldCap.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 5)
                this.textFieldCap.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldCap.setText(newValue.replaceAll("[^\\d]", ""));
            }
            buttonModify.setDisable(!checkInput());
        });

        gridPaneCondo.add(this.labelFlats, 0, 9);
        gridPaneCondo.add(this.textFieldFlats, 1, 9);
        this.textFieldFlats.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.textFieldFlats.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.buttonModify.setDisable(!checkInput());
        });

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(10);
        gridPaneCondo.add(hBoxButtons, 0, 10, 2, 1);
        hBoxButtons.setAlignment(Pos.BOTTOM_RIGHT);

        this.buttonExit.setPrefWidth(1000);
        this.buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        hBoxButtons.getChildren().add(this.buttonExit);
        this.buttonExit.setOnAction(event -> {
            event.consume();
            ControllerFacade.getInstance().updateAll();
            this.close();
        });

        this.buttonDelete.setPrefWidth(1000);
        this.buttonDelete.setGraphic(new ImageView(this.getClass().getResource("/images/icons/delete.png").toString()));
        hBoxButtons.getChildren().add(this.buttonDelete);
        this.buttonDelete.setDisable(true);
        this.buttonDelete.setOnAction(event -> {
            event.consume();
            try {
                if(GraphicUtilities.getInstance().showAlertConfirmationDelete("Conferma eliminazione", "Vuoi davvero eliminare il condiminio?")){
                    Query condoDelete = new CondoDelete(this.buttonDelete.retId());
                    ControllerFacade.getInstance().executeQuery(condoDelete);
                    this.setDisabled(true);
                    this.choiceBoxCondos.getSelectionModel().selectedItemProperty().removeListener(this.listenerCondos);
                    QueryWithResults query = new CondoSelectAll();
                    ControllerFacade.getInstance().executeQuery(query);
                    this.choiceBoxCondos.setItems(GraphicUtilities.getInstance().getCondosObservableList(query.getResults()));
                    this.choiceBoxCondos.getSelectionModel().selectedItemProperty().addListener(this.listenerCondos);
                }
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }

        });

        this.buttonModify.setPrefWidth(1000);
        this.buttonModify.setGraphic(new ImageView(this.getClass().getResource("/images/icons/pencil.png").toString()));
        hBoxButtons.getChildren().add(this.buttonModify);
        this.buttonModify.setDisable(true);
        this.buttonModify.setOnAction(event -> {
            event.consume();
            try{
                Query queryModifyCondo = new CondoModify(buttonModify.retId(), Integer.valueOf(this.textFieldId.getText()), this.textFieldCode.getText(),
                        Integer.valueOf(this.choiceBoxAdministrators.getSelectionModel().getSelectedItem().hashCode()), Integer.valueOf(this.choiceBoxMonths.getSelectionModel().getSelectedItem().hashCode()),
                        this.textFieldName.getText(), this.textFieldProvince.getText(), this.textFieldCity.getText(), this.textFieldAddress.getText(),
                        this.textFieldCap.getText(), Integer.valueOf(this.textFieldFlats.getText()));
                ControllerFacade.getInstance().executeQuery(queryModifyCondo);

                this.setDisabled(true);
                this.choiceBoxCondos.getSelectionModel().selectedItemProperty().removeListener(this.listenerCondos);
                QueryWithResults query = new CondoSelectAll();
                ControllerFacade.getInstance().executeQuery(query);
                this.choiceBoxCondos.setItems(GraphicUtilities.getInstance().getCondosObservableList(query.getResults()));
                this.choiceBoxCondos.getSelectionModel().selectedItemProperty().addListener(this.listenerCondos);
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        return gridPane;
    }

    private boolean checkInput(){
        if(textFieldId.getText().length() > 0 && textFieldCode.getText().length() == 11 && choiceBoxAdministrators.getSelectionModel().getSelectedIndex() > -1 &&
                choiceBoxMonths.getSelectionModel().getSelectedIndex() > -1 && textFieldName.getText().length() > 0 && textFieldProvince.getText().length() == 2 &&
                textFieldCity.getText().length() > 0 && textFieldAddress.getText().length() > 0 && textFieldCap.getText().length() == 5 && textFieldFlats.getText().length() > 0)
            return true;
        return false;
    }

    private void setDisabled(boolean value){
        this.textFieldId.setDisable(value);
        this.labelId.setDisable(value);

        this.textFieldCode.setDisable(value);
        this.labelCode.setDisable(value);

        this.choiceBoxAdministrators.setDisable(value);
        this.labelAdministrator.setDisable(value);

        this.choiceBoxMonths.setDisable(value);
        this.labelMonths.setDisable(value);

        this.textFieldName.setDisable(value);
        this.labelName.setDisable(value);

        this.textFieldProvince.setDisable(value);
        this.labelProvince.setDisable(value);

        this.textFieldCity.setDisable(value);
        this.labelCity.setDisable(value);

        this.textFieldAddress.setDisable(value);
        this.labelAddress.setDisable(value);

        this.textFieldCap.setDisable(value);
        this.labelCap.setDisable(value);

        this.textFieldFlats.setDisable(value);
        this.labelFlats.setDisable(value);

        this.buttonDelete.setDisable(value);

        if(value){
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
    }

    public StageModifyCondo() throws DatabaseException {
        setTitle("Modifica condomini");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 4);
        Scene scene = new Scene(buildLayout());
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
        this.setOnCloseRequest(event -> ControllerFacade.getInstance().updateAll());
    }

    public StageModifyCondo(int idCondo) throws DatabaseException{

        this();

        //Seleziono il condominio corrispondente al parametro in ingresso
        ObservableList<ChoiceBoxItemId> condos = this.choiceBoxCondos.getItems();
        boolean found = false;
        for(Iterator<ChoiceBoxItemId> it = condos.iterator(); it.hasNext() && !found;){
            ChoiceBoxItemId item = it.next();
            if(idCondo == item.hashCode()){
                found = true;
                this.choiceBoxCondos.getSelectionModel().select(item);
            }
        }
    }
}
