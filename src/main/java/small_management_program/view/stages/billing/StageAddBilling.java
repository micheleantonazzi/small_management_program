package small_management_program.view.stages.billing;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.billing.*;
import small_management_program.controller.queries.condo.CondoSelectAll;
import small_management_program.model.DatabaseException;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.util.ArrayList;
import java.util.Calendar;

public class StageAddBilling extends Stage {

    public static final boolean MODIFY = false;

    public static final boolean ADD = true;

    private boolean type;

    public StageAddBilling(boolean type) throws DatabaseException{
        this.type = type;
        setTitle("Aggiungi fatturazione");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 4);
        Scene scene = new Scene(buildLayout());
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
        this.setOnCloseRequest(event -> ControllerFacade.getInstance().updateAll());
    }

    private GridPane buildLayout() throws DatabaseException{
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(4, 1, new int[]{30, 10, 30, 30});
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        ChoiceBox choiceBoxCondos = new ChoiceBox();
        ChoiceBox choiceBoxYear = new ChoiceBox();
        TextField textFieldTotal = new TextField();
        Button buttonAdd = new Button();
        if(this.type == StageAddBilling.MODIFY){
            buttonAdd.setText("Modifica");
            buttonAdd.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card-settings.png").toString()));
        }
        else if(this.type == StageAddBilling.ADD){
            buttonAdd.setText("Aggiungi");
            buttonAdd.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card-plus.png").toString()));
        }

        Button buttonExit = new Button("Esci");
        ObservableList<Integer> years = FXCollections.observableArrayList();

        QueryWithResults queryCondos = new CondoSelectAll();

        if(this.type == StageAddBilling.MODIFY){
            queryCondos = new BillingSelectCondos();
        }

        ControllerFacade.getInstance().executeQuery(queryCondos);
        choiceBoxCondos.setItems(GraphicUtilities.getInstance().getCondosObservableList(queryCondos.getResults()));

        choiceBoxCondos.setPrefWidth(1000);
        gridPane.add(new Label("Condominio"), 0, 0);
        gridPane.add(choiceBoxCondos, 1, 0, 3, 1);
        choiceBoxCondos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            //Se lo stage è aperto in modifica, alla scelta di un condominio con fatture popolo la choicheBox con gli anni
            // delle fatture che sono inserite in database, coì da scegliere quella da modificare

            if(this.type == StageAddBilling.MODIFY){
                try {
                    if(newValue != null){
                        QueryWithResults querySelectYears = new BillingSelectYears(newValue.hashCode());
                        ControllerFacade.getInstance().executeQuery(querySelectYears);
                        ArrayList<String> yearsResults = querySelectYears.getResults().get(0);
                        ObservableList yearsList = FXCollections.observableArrayList();
                        for (String s : yearsResults){
                            yearsList.add(s.substring(0, s.indexOf('-')));
                        }
                        choiceBoxYear.setItems(yearsList);
                        choiceBoxYear.setDisable(false);
                    }
                } catch (DatabaseException exception) {}
            }
            else {
                choiceBoxYear.setDisable(false);
                textFieldTotal.setDisable(false);
            }
        });

        textFieldTotal.setDisable(true);
        gridPane.add(textFieldTotal, 0, 1, 3, 1);
        textFieldTotal.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    textFieldTotal.setText(oldValue);
            }
            buttonAdd.setDisable(checkInput(textFieldTotal, choiceBoxYear));
            });

        int year = Calendar.getInstance().get(Calendar.YEAR);
        years.addAll(year - 2, year - 1, year);
        choiceBoxYear.setItems(years);
        choiceBoxYear.setDisable(true);
        choiceBoxYear.setPrefWidth(1000);
        gridPane.add(choiceBoxYear, 3, 1);
        choiceBoxYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    //Se lo stage è aperto in modifica, alla scelta di un anno di fattura carico l'importo corretto da modificare

                    if(this.type == StageAddBilling.MODIFY && newValue != null){
                        try{
                            WhereParameters whereParameters = new WhereParameters("id_condo = " + choiceBoxCondos.getSelectionModel().getSelectedItem().hashCode());
                            String selectedYear =  newValue.toString();
                            whereParameters.add("year = " + selectedYear);
                            QueryWithResults queryWithResults = new BillingSelectWithParameter(whereParameters);
                            ControllerFacade.getInstance().executeQuery(queryWithResults);
                            DuplicateMap<Integer, String> results = queryWithResults.getResults();
                            textFieldTotal.setText(results.get(choiceBoxCondos.getSelectionModel().getSelectedItem().hashCode(), 1));
                            textFieldTotal.setDisable(false);
                            choiceBoxYear.setDisable(true);
                            buttonExit.setDisable(false);
                        }catch (DatabaseException exception){
                            GraphicUtilities.getInstance().showAlertError(exception);
                        }

                    }
                    buttonAdd.setDisable(checkInput(textFieldTotal, choiceBoxYear));

                });


        buttonAdd.setPrefWidth(1000);
        buttonAdd.setDisable(true);

        gridPane.add(buttonAdd, 3, 2);
        buttonAdd.setOnAction(event -> {
            event.consume();
            try {
                Query query;
                if(this.type == StageAddBilling.ADD){
                    query = new BillingAddNew(choiceBoxCondos.getSelectionModel().getSelectedItem().hashCode(),
                            Integer.valueOf(choiceBoxYear.getSelectionModel().getSelectedItem().toString()),
                            Double.valueOf(textFieldTotal.getText()));
                }
                else{
                    query = new BillingUpdate(choiceBoxCondos.getSelectionModel().getSelectedItem().hashCode(),
                            Integer.valueOf(choiceBoxYear.getSelectionModel().getSelectedItem().toString()),
                            Double.valueOf(textFieldTotal.getText()));
                    buttonExit.setDisable(true);
                }

                ControllerFacade.getInstance().executeQuery(query);
                choiceBoxCondos.getSelectionModel().select(-1);
                choiceBoxYear.getSelectionModel().select(-1);
                choiceBoxYear.setDisable(true);
                textFieldTotal.setText("");
                textFieldTotal.setDisable(true);
                buttonAdd.setDisable(true);
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        });

        buttonExit.setPrefWidth(1000);
        if(this.type == StageAddBilling.ADD){
            buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        }
        else{
            buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/delete.png").toString()));
            buttonExit.setText("Elimina");
            buttonExit.setDisable(true);
        }
        gridPane.add(buttonExit, 2, 2);
        buttonExit.setOnAction(event -> {
            event.consume();
            if(this.type == StageAddBilling.MODIFY){
                try{
                    Query query = new BillingDelete(choiceBoxCondos.getSelectionModel().getSelectedItem().hashCode(),
                            Integer.valueOf(choiceBoxYear.getSelectionModel().getSelectedItem().toString()));
                    ControllerFacade.getInstance().executeQuery(query);
                    choiceBoxCondos.getSelectionModel().select(-1);
                    choiceBoxYear.getSelectionModel().select(-1);
                    choiceBoxYear.setDisable(true);
                    textFieldTotal.setText("");
                    textFieldTotal.setDisable(true);
                    buttonAdd.setDisable(true);
                    buttonExit.setDisable(true);
                    QueryWithResults querySelectCondos = new BillingSelectCondos();
                    ControllerFacade.getInstance().executeQuery(querySelectCondos);
                    choiceBoxCondos.setItems(GraphicUtilities.getInstance().getCondosObservableList(querySelectCondos.getResults()));
                }catch (DatabaseException exception){
                    GraphicUtilities.getInstance().showAlertError(exception);
                }
            }
            else{
                ControllerFacade.getInstance().updateAll();
                this.close();
            }

        });

        return gridPane;
    }

    private boolean checkInput(TextField textField, ChoiceBox choiceBox){
        if(textField.getText().equals("") || choiceBox.getSelectionModel().getSelectedIndex() == -1)
            return true;
        else
            return false;
    }

}
