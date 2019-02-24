package small_management_program.view.stages.billingperiod;

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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.billingperiod.BillingPeriodGetFreeMonths;
import small_management_program.controller.queries.billingperiod.BillingPeriodInsertNew;
import small_management_program.model.DatabaseException;
import small_management_program.view.View;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.util.*;

public class StageAddBillingPeriod extends Stage {

    private GraphicUtilities graphicUtilities;
    private View view;
    private VBox vBoxRight;

    private ArrayList<ChoiceBox> choiceBoxes = new ArrayList<>();
    private TextField textFieldName;
    private ObservableList<ChoiceBoxItemId> items = FXCollections.observableArrayList();
    private Button buttonSave;
    private Button buttonRemoveMonth;
    private Button buttonAddMonth;


    private GridPane buildLayout() throws DatabaseException{
        GridPane gridPane = GraphicUtilities.getInstance().getEquivalentGridPane(2, 1, new int[]{70, 30}, new int[]{});
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        this.graphicUtilities = GraphicUtilities.getInstance();
        gridPane.add(getLeft(), 0, 0);
        gridPane.add(getRight(), 1, 0);
        return gridPane;
    }

    private VBox getLeft(){
        VBox vBoxLeft = new VBox();
        vBoxLeft.setSpacing(10);

        Label labelName = new Label("Nome periodo");

        vBoxLeft.getChildren().add(labelName);

        this.textFieldName = new TextField();
        vBoxLeft.getChildren().add(this.textFieldName);
        this.textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
                if(this.textFieldName.getText().length() > 0 && items.size() > 0)
                    this.buttonSave.setDisable(false);
                else
                    this.buttonSave.setDisable(true);
        });

        HBox buttonsBox = new HBox();
        vBoxLeft.getChildren().add(buttonsBox);
        buttonsBox.setSpacing(10);

        this.buttonRemoveMonth = new Button("Rimuovi mese");
        this.buttonRemoveMonth.setGraphic(new ImageView(this.getClass().getResource("/images/icons/minus.png").toString()));
        this.buttonRemoveMonth.setDisable(true);

        buttonsBox.getChildren().add(buttonRemoveMonth);
        this.buttonRemoveMonth.setPrefWidth(10000);
        this.buttonRemoveMonth.setOnAction(event -> {
            this.choiceBoxes.remove(choiceBoxes.size() - 1);
            this.vBoxRight.getChildren().remove(this.vBoxRight.getChildren().size() - 1);
            if(this.choiceBoxes.size() < 3){
                this.buttonAddMonth.setDisable(false);
            }
            if(this.choiceBoxes.size() < 2){
                this.buttonRemoveMonth.setDisable(true);
            }
            event.consume();
        });

        this.buttonAddMonth = new Button("Aggiungi mese");
        this.buttonAddMonth.setGraphic(new ImageView(this.getClass().getResource("/images/icons/plus.png").toString()));
        buttonsBox.getChildren().add(buttonAddMonth);
        this.buttonAddMonth.setPrefWidth(10000);
        this.buttonAddMonth.setOnAction(event -> {
            ChoiceBox choiceBox = new ChoiceBox(this.items);
            choiceBox.getSelectionModel().selectFirst();
            this.choiceBoxes.add(choiceBox);
            this.vBoxRight.getChildren().add(choiceBox);
            choiceBox.setPrefWidth(1000);
            if(this.choiceBoxes.size() > 1){
                this.buttonRemoveMonth.setDisable(false);
            }
            if(this.choiceBoxes.size() > 2){
                this.buttonAddMonth.setDisable(true);
            }
            event.consume();
        });

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(10);
        vBoxLeft.getChildren().add(hBoxButtons);

        Button buttonExit = new Button("Esci");
        buttonExit.setGraphic(new ImageView(this.getClass().getResource("/images/icons/exit-to-app.png").toString()));
        buttonExit.setPrefWidth(1000);
        hBoxButtons.getChildren().add(buttonExit);
        buttonExit.setOnAction(event -> {
            event.consume();
            this.close();
        });

        this.buttonSave = new Button("Salva");
        this.buttonSave.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar-plus.png").toString()));
        hBoxButtons.getChildren().add(this.buttonSave);
        this.buttonSave.setPrefWidth(1000);
        this.buttonSave.setDisable(true);
        this.buttonSave.setOnAction(event -> {
            int[] months = new int[this.choiceBoxes.size()];
            for(int i = 0; i < this.choiceBoxes.size(); ++i){
                months[i] = this.choiceBoxes.get(i).getSelectionModel().getSelectedItem().hashCode();
            }
            try {
                ControllerFacade.getInstance().executeQuery(new BillingPeriodInsertNew(this.textFieldName.getText(), months));
                textFieldName.setText("");
                for(int i = 1; i < choiceBoxes.size();){
                    vBoxRight.getChildren().remove(choiceBoxes.get(i));
                    choiceBoxes.remove(i);
                }
                items.clear();
                BillingPeriodGetFreeMonths query = new BillingPeriodGetFreeMonths();
                ControllerFacade.getInstance().executeQuery(query);
                DuplicateMap<Integer, String> freeMonths = query.getResults();
                Set<Integer> monthsList = freeMonths.keySet();
                for (Iterator<Integer> it = monthsList.iterator(); it.hasNext();) {
                    int month = it.next();
                    items.add(new ChoiceBoxItemId(month, freeMonths.get(month, 0)));
                }
                choiceBoxes.get(0).getSelectionModel().select(0);
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
            event.consume();
        });


        return vBoxLeft;
    }

    private VBox getRight() throws DatabaseException {
        this.vBoxRight = new VBox();
        this.vBoxRight.setSpacing(10);

        Label labelRight = new Label("Mesi");
        this.vBoxRight.getChildren().add(labelRight);

        ChoiceBox choiceBox = new ChoiceBox(items);
        choiceBox.getSelectionModel().selectFirst();
        this.choiceBoxes.add(choiceBox);
        vBoxRight.getChildren().add(this.choiceBoxes.get(0));

        BillingPeriodGetFreeMonths query = new BillingPeriodGetFreeMonths();
        ControllerFacade.getInstance().executeQuery(query);
        DuplicateMap<Integer, String> months = query.getResults();
        Set<Integer> monthsList = months.keySet();
        for (Iterator<Integer> it = monthsList.iterator(); it.hasNext();) {
            int month = it.next();
            this.items.add(new ChoiceBoxItemId(month, months.get(month, 0)));
        }

        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setPrefWidth(1000);

        return this.vBoxRight;
    }

    public StageAddBillingPeriod(View view) throws DatabaseException{
        this.view = view;
        setTitle("Aggiungi periodo di fatturazione");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(GraphicUtilities.getInstance().getScreenWidth() / 3);
        Scene scene = new Scene(buildLayout());
        scene.getStylesheets().add(this.getClass().getResource("/style/style.css").toString());
        setScene(scene);
        setHeight(GraphicUtilities.getInstance().getScreenHeight() / 5);
    }
}
