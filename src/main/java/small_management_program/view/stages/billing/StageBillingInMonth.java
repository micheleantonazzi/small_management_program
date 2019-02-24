package small_management_program.view.stages.billing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import small_management_program.controller.Observer;
import small_management_program.model.Months;
import small_management_program.model.databaseclasses.BillingRepresentation;
import small_management_program.view.algorithms.AlgorithmsBills;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;
import small_management_program.view.graphicutilities.GraphicUtilities;

public class StageBillingInMonth extends Stage {

    private GridPane buildLayout(BillingRepresentation billingRepresentation){
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(2, 3, new int[]{30});
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(25, 25, 5, 25));

        Label title;
        ChoiceBox choiceBoxMonths;
        HBox hBox;
        Button buttonCreateBill = new Button("Crea fattura");
        Button buttonExit;

        title = new Label("Crea fattura in ");
        gridPane.add(title, 0,0);

        ObservableList<ChoiceBoxItemId> months = FXCollections.observableArrayList();
        choiceBoxMonths = new ChoiceBox(months);

        ObservableList<ChoiceBoxItemId> listBefore;
        ObservableList<ChoiceBoxItemId> listAfter;

        //Aggiungo i mesi possibili per inserire una fattura
        if(billingRepresentation.monthLastBill() <= -1) {
            listBefore = Months.getInstance().getListMonths(0, billingRepresentation.getRealMonth() - 1);
            listAfter = Months.getInstance().getListMonths(billingRepresentation.getRealMonth() + 1, 11);
        }
        else if(billingRepresentation.monthLastBill() <= billingRepresentation.getRealMonth()){
            listBefore = Months.getInstance().getListMonths(billingRepresentation.monthLastBill() + 1, billingRepresentation.getRealMonth());
            listAfter = Months.getInstance().getListMonths(billingRepresentation.getRealMonth() + 1, 11);
        }
        else {
            listBefore = Months.getInstance().getListMonths(0, billingRepresentation.getRealMonth() - 1);
            listAfter = Months.getInstance().getListMonths(billingRepresentation.monthLastBill() + 1, 11);
        }

        //Aggiungo gli item alla lista
        for(ChoiceBoxItemId month : listBefore){
            month.setMessage(Integer.valueOf(billingRepresentation.getYear()) + 1 + " - " + month.toString());
            months.add(month);
        }
        for(ChoiceBoxItemId month : listAfter){
            month.setMessage(Integer.valueOf(billingRepresentation.getYear()) + " - " + month.toString() );
            months.add(month);
        }

        choiceBoxMonths.setPrefWidth(1000);
        gridPane.add(choiceBoxMonths, 1,0);
        choiceBoxMonths.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> buttonCreateBill.setDisable(false));

        hBox = new HBox();
        hBox.setSpacing(10);

        buttonExit = new Button("Esci");
        buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        buttonExit.setPrefWidth(950);
        hBox.getChildren().add(buttonExit);
        buttonExit.setOnAction(event -> this.close());

        buttonCreateBill.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card-plus.png").toString()));
        buttonCreateBill.setDisable(true);
        buttonCreateBill.setPrefWidth(1000);
        hBox.getChildren().add(buttonCreateBill);
        buttonCreateBill.setOnAction(event -> {
            event.consume();

            int year = Integer.valueOf(billingRepresentation.getYear());
            int month = choiceBoxMonths.getSelectionModel().getSelectedItem().hashCode();

            //Se il mese di fatturazione è antecendete al mese di chiusura dell'esercizio allora l'anno in cui effettuare la fattura è il successivo
            if(month <= billingRepresentation.getRealMonth())
                year++;

            AlgorithmsBills.getInstance().createBill(billingRepresentation, year, month, true);
            this.close();
        });

        gridPane.add(hBox, 1,1);
        return gridPane;
    }

    public StageBillingInMonth(BillingRepresentation billingRepresentation){

        setTitle("Crea fattura in un mese specifico");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 5);
        Scene scene = new Scene(buildLayout(billingRepresentation));
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
    }


}
