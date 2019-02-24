package small_management_program.view.stages.billingperiod;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.billingperiod.*;
import small_management_program.model.DatabaseException;
import small_management_program.model.Months;
import small_management_program.view.View;
import small_management_program.view.graphicutilities.*;

import javafx.event.ActionEvent;

import java.util.*;

public class StageModifyBillingPeriod extends Stage {

    private View view;

    private GridPane buildLayout(DuplicateMap<Integer, String> results){
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(1, 1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        Set<Integer> keys = results.keySet();
        int row = 0;
        for(Iterator<Integer> it = keys.iterator(); it.hasNext();){
            int key = it.next();
            GridPane gridPaneBillingPeriod = createSingleGridPane(key, results.get(key));

            ButtonId buttonRemoveBillingPeriod = new ButtonId("Elimina", key);
            buttonRemoveBillingPeriod.setGraphic(new ImageView(this.getClass().getResource("/images/icons/delete.png").toString()));
            buttonRemoveBillingPeriod.setMaxWidth(1000);
            gridPaneBillingPeriod.add(buttonRemoveBillingPeriod, 1, 1);
            gridPaneBillingPeriod.setFillWidth(buttonRemoveBillingPeriod, true);
            buttonRemoveBillingPeriod.setOnAction(event -> {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma");
                alert.setHeaderText("Vuoi davvero eliminare questo periodo di fatturazione?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    try{
                        ControllerFacade.getInstance().executeQuery(new BillingPeriodDelete(buttonRemoveBillingPeriod.retId()));
                        this.setHeight(this.getHeight() - gridPaneBillingPeriod.getHeight());
                        gridPane.getChildren().remove(gridPaneBillingPeriod);
                    }
                    catch (DatabaseException exception){
                        GraphicUtilities.getInstance().showAlertError(exception);
                    }
                }
            });

            gridPane.add(gridPaneBillingPeriod, 0, row++);

        }

        Button buttonExit = new Button("Esci");
        buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        buttonExit.setPrefWidth(1000);
        buttonExit.setOnAction(event -> {
            event.consume();
            this.close();
        });

        GridPane gridPaneLast = GraphicUtilities.getInstance().getEquivalentGridPane(2, 1, new int[]{67}, new int []{});
        gridPaneLast.setPadding(new Insets(10, 0, 0, 0));
        gridPaneLast.setHgap(10);
        gridPaneLast.add(buttonExit, 1, 0);
        gridPane.add(gridPaneLast, 0, row);

        return gridPane;
    }

    private GridPane createSingleGridPane(int id, ArrayList<String> strings){
        GridPane gridPaneBillingPeriod = GraphicUtilities.getInstance().getEquivalentGridPane(2, 2, new int[]{67, 33}, new int[]{});
        gridPaneBillingPeriod.setHgap(10);
        gridPaneBillingPeriod.setVgap(10);
        gridPaneBillingPeriod.setPadding(new Insets(20, 0, 0, 0));

        TextFieldId textFieldId = new TextFieldId(strings.get(0), id);
        gridPaneBillingPeriod.add(textFieldId, 0, 0);

        Button buttonChangeName = new Button("Cambia nome");
        buttonChangeName.setGraphic(new ImageView(this.getClass().getResource("/images/icons/pencil.png").toString()));
        buttonChangeName.setDisable(true);
        buttonChangeName.setPrefWidth(1000);
        buttonChangeName.addEventHandler(ActionEvent.ANY, event -> {
            event.consume();
            try {
                ControllerFacade.getInstance().executeQuery(new BillingPeriodChangeName(textFieldId.retId(), textFieldId.getText()));
                buttonChangeName.setDisable(true);
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }

        });
        textFieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textFieldId.getText().length() > 0){
                buttonChangeName.setDisable(false);
            }
            else{
                buttonChangeName.setDisable(true);
            }
        });

        gridPaneBillingPeriod.add(buttonChangeName, 1, 0);
        gridPaneBillingPeriod.add(createHBoxesMonths(id, strings.subList(1, strings.size())), 0,1);

        return gridPaneBillingPeriod;
    }

    private HBox createHBoxesMonths(int id, List<String> months){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        ObservableList<ChoiceBoxItemId> items = Months.getInstance().getListAllMonths();
        items.add(0, new ChoiceBoxItemId(-1, "Nessuno"));
        for(int i = 0; i < 3; ++i){
            ChoiceBoxId choiceBoxId = new ChoiceBoxId(items, id);
            choiceBoxId.setPrefWidth(1000);
            if(i < months.size()){
                choiceBoxId.getSelectionModel().select(Integer.valueOf(months.get(i)) + 1);
            }
            else{
                choiceBoxId.getSelectionModel().select(0);
            }
            choiceBoxId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ChoiceBoxItemId>() {
                @Override
                public void changed(ObservableValue<? extends ChoiceBoxItemId> observable, ChoiceBoxItemId oldValue, ChoiceBoxItemId newValue) {
                    try{
                        if(newValue.hashCode() == -1){
                            BillingPeriodCheckMonthsNumber query = new BillingPeriodCheckMonthsNumber(choiceBoxId.retId());
                            ControllerFacade.getInstance().executeQuery(query);
                            DuplicateMap<Integer, String> monthsNumber = query.getResults();
                            Set<Integer> periods = monthsNumber.keySet();
                            int key = periods.iterator().next();
                            if(monthsNumber.get(key).size() > 1){
                                ControllerFacade.getInstance().executeQuery(new BillingPeriodRemoveMonth(oldValue.hashCode()));
                            }
                            else{
                                throw new DatabaseException("Errore database", "Attenzione, non è possibile rimuovere l'unico mese del periodo di fatturazione.");
                            }
                        }
                        else if(oldValue.hashCode() == -1){
                            ControllerFacade.getInstance().executeQuery(new BillingPeriodAddMonth(choiceBoxId.retId(), newValue.hashCode()));
                        }
                        else{
                            ControllerFacade.getInstance().executeQuery(new BillingPeriodChangeMonth(oldValue.hashCode(), newValue.hashCode()));
                        }
                    }
                    catch (DatabaseException exception){
                        choiceBoxId.getSelectionModel().selectedItemProperty().removeListener(this);
                        choiceBoxId.getSelectionModel().select(oldValue);
                        choiceBoxId.getSelectionModel().selectedItemProperty().addListener(this);
                        GraphicUtilities.getInstance().showAlertError(exception);
                    }
                }
            });
            hBox.getChildren().add(choiceBoxId);
        }
        return hBox;
    }

    public StageModifyBillingPeriod(View view) throws DatabaseException{
        this.view = view;
        setTitle("Modifica perdiodo di fatturazione");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 7 * 2);
        QueryWithResults query = new BillingPeriodSelectAll();
        ControllerFacade.getInstance().executeQuery(query);
        DuplicateMap<Integer, String> results = query.getResults();
        Scene scene = new Scene(buildLayout(results));
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
    }
}
