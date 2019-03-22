/*
 * Michele Antonazzi
 */

package small_management_program.view.right.billing;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.model.Months;
import small_management_program.model.database.Database;
import small_management_program.model.databaseclasses.BillingRepresentation;
import small_management_program.view.algorithms.AlgorithmsBills;
import small_management_program.view.graphicutilities.GraphicUtilities;

public class TableViewBilling extends TableView<BillingRepresentation> implements Observer {

    private static TableViewBilling instance;

    private HBoxAction hBoxActionInstance;

    private TableViewBilling(){
        this.setColumns();
    }

    private void setColumns(){
        this.getColumns().clear();
        TableColumn tableColumn1 = new TableColumn<>("Codice");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("id"));
        tableColumn1.prefWidthProperty().bind(this.widthProperty().multiply(0.05));

        TableColumn tableColumn2 = new TableColumn<>("Nome");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("name"));
        tableColumn2.prefWidthProperty().bind(this.widthProperty().multiply(0.177));

        TableColumn tableColumn3 = new TableColumn<>("Totale");
        tableColumn3.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("total"));
        tableColumn3.prefWidthProperty().bind(this.widthProperty().multiply(0.10));

        TableColumn tableColumn4 = new TableColumn<>("Gennaio");
        tableColumn4.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("january"));
        tableColumn4.prefWidthProperty().bind(this.widthProperty().multiply(0.055));
        tableColumn4.setCellFactory(new CellFactory());

        TableColumn tableColumn5 = new TableColumn<>("Febbraio");
        tableColumn5.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("february"));
        tableColumn5.prefWidthProperty().bind(this.widthProperty().multiply(0.055));
        tableColumn5.setCellFactory(new CellFactory());

        TableColumn tableColumn6 = new TableColumn<>("Marzo");
        tableColumn6.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("march"));
        tableColumn6.prefWidthProperty().bind(this.widthProperty().multiply(0.055));
        tableColumn6.setCellFactory(new CellFactory());

        TableColumn tableColumn7 = new TableColumn<>("Aprile");
        tableColumn7.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("april"));
        tableColumn7.prefWidthProperty().bind(this.widthProperty().multiply(0.055));
        tableColumn7.setCellFactory(new CellFactory());

        TableColumn tableColumn8 = new TableColumn<>("Maggio");
        tableColumn8.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("may"));
        tableColumn8.prefWidthProperty().bind(this.widthProperty().multiply(0.055));
        tableColumn8.setCellFactory(new CellFactory());

        TableColumn tableColumn9 = new TableColumn<>("Giugno");
        tableColumn9.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("june"));
        tableColumn9.prefWidthProperty().bind(this.widthProperty().multiply(0.055));
        tableColumn9.setCellFactory(new CellFactory());

        TableColumn tableColumn10 = new TableColumn<>("Luglio");
        tableColumn10.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("july"));
        tableColumn10.prefWidthProperty().bind(this.widthProperty().multiply(0.054));
        tableColumn10.setCellFactory(new CellFactory());

        TableColumn tableColumn11 = new TableColumn<>("Agosto");
        tableColumn11.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("august"));
        tableColumn11.prefWidthProperty().bind(this.widthProperty().multiply(0.054));
        tableColumn11.setCellFactory(new CellFactory());

        TableColumn tableColumn12 = new TableColumn<>("Settembre");
        tableColumn12.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("september"));
        tableColumn12.prefWidthProperty().bind(this.widthProperty().multiply(0.054));
        tableColumn12.setCellFactory(new CellFactory());

        TableColumn tableColumn13 = new TableColumn<>("Ottobre");
        tableColumn13.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("october"));
        tableColumn13.prefWidthProperty().bind(this.widthProperty().multiply(0.054));
        tableColumn13.setCellFactory(new CellFactory());

        TableColumn tableColumn14 = new TableColumn<>("Novembre");
        tableColumn14.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("november"));
        tableColumn14.prefWidthProperty().bind(this.widthProperty().multiply(0.054));
        tableColumn14.setCellFactory(new CellFactory());

        TableColumn tableColumn15 = new TableColumn<>("Dicembre");
        tableColumn15.setCellValueFactory(new PropertyValueFactory<BillingRepresentation, String>("december"));
        tableColumn15.prefWidthProperty().bind(this.widthProperty().multiply(0.054));
        tableColumn15.setCellFactory(new CellFactory());

        this.getColumns().addAll(tableColumn1, tableColumn2, tableColumn3, tableColumn4, tableColumn5, tableColumn6, tableColumn7,
                tableColumn8, tableColumn9, tableColumn10, tableColumn11, tableColumn12, tableColumn13, tableColumn14, tableColumn15);
    }

    public static TableViewBilling getInstance(){
        if (instance == null)
            instance = new TableViewBilling();
        return instance;
    }

    public HBox getActions(){
        if(this.hBoxActionInstance == null)
            this.hBoxActionInstance = new HBoxAction();
        return this.hBoxActionInstance;
    }

    @Override
    public void update(Subject subject){
        try{
            this.getItems().clear();
            this.setItems(subject.getTableViewData());
            this.setRowFactory(new ShowRowMenu());
        }
        catch (UpdateException exception){}
    }

    private class CellFactory implements Callback<TableColumn<BillingRepresentation, String>, TableCell<BillingRepresentation, String>> {

        @Override
        public TableCell<BillingRepresentation, String> call(TableColumn<BillingRepresentation, String> param) {
            return new TableCell<>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setStyle("");
                    this.setText(null);
                    if(!empty && item != null){
                        int rowIndex = getIndex();
                        String index = getTableView().getItems().get(rowIndex).getMonth();
                        int month = Months.getInstance().getMonthNumber(index);
                        if (month == getTableView().getColumns().indexOf(getTableColumn()) - 3) {
                            this.setStyle(this.getStyle() + "-fx-background-color: #FF8080;");
                        }
                        if(getTableView().getItems().get(rowIndex).isMonthBilled(getTableView().getColumns().indexOf(getTableColumn()) - 3)){
                            this.setStyle(this.getStyle() + "-fx-background-color: #FFFACD; -fx-text-fill: black;");
                        }
                    }
                    if(item != null){
                        this.setText(item);
                    }
                }
            };
        }
    }

    private class ShowRowMenu implements Callback<TableView<BillingRepresentation>, TableRow<BillingRepresentation>>{

        @Override
        public TableRow<BillingRepresentation> call(TableView<BillingRepresentation> tableView) {
            final TableRow<BillingRepresentation> row = new TableRow<>();
            final ContextMenuTableViewBilling rowMenu = new ContextMenuTableViewBilling(TableViewBilling.this, row);
            //Mostra il menu solo se l'item non è nullo
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenuTableViewBilling) null));

            return row;
        }
    }

    public void disableButtons(boolean disable){
        this.hBoxActionInstance.buttonSave.setDisable(disable);
        this.hBoxActionInstance.buttonDiscardLast.setDisable(disable);
        this.hBoxActionInstance.buttonDiscard.setDisable(disable);
    }


    private class HBoxAction extends HBox{

        TableViewBilling table;
        private Button buttonSave;
        private Button buttonDiscard;
        private Button buttonDiscardLast;

        public HBoxAction(){
            this.table = TableViewBilling.this;
            this.setSpacing(10);
            this.addButtonBillAll();
            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            this.getChildren().add(region);
            this.addButtonSaveDiscardChanges();
            this.setPadding(new Insets(0, 0, 0, 10));
        }

        private void addButtonBillAll(){
            Button buttonBillAll = new Button("Fattura tutti");
            buttonBillAll.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card-multiple.png").toString()));
            this.getChildren().add(buttonBillAll);
            buttonBillAll.setOnAction(event -> {
                event.consume();
                ObservableList<BillingRepresentation> items = table.getItems();

                for(BillingRepresentation billingRepresentation : items){
                    AlgorithmsBills.getInstance().createBill(billingRepresentation, true);
                }
                TreeViewSubject.getInstance().updateAll();
            });
        }

        private void addButtonSaveDiscardChanges(){

            this.buttonSave = new Button("Salva modifiche");
            this.buttonSave.setGraphic(new ImageView(this.getClass().getResource("/images/icons/content-save-outline.png").toString()));
            this.buttonSave.setDisable(true);
            this.getChildren().add(this.buttonSave);
            this.buttonSave.setOnAction(event -> {
                event.consume();
                if(GraphicUtilities.getInstance().showAlertConfirmationSave("Salva modifiche", "Vuoi salvare tutte le modifiche " +
                        "che hai effettuato in questa sessione di lavoro? ")){
                    Database.getInstance().saveChanges();
                    TreeViewSubject.getInstance().updateAll();
                    this.disableButtons(true);
                }
            });

            this.buttonDiscardLast = new Button("Elimina ultima modifica");
            this.buttonDiscardLast.setGraphic(new ImageView(this.getClass().getResource("/images/icons/backup-restore.png").toString()));
            this.buttonDiscardLast.setDisable(true);
            this.getChildren().add(this.buttonDiscardLast);
            this.buttonDiscardLast.setOnAction(event -> {
                event.consume();
                boolean empty = Database.getInstance().discardLastChange();
                TreeViewSubject.getInstance().updateAll();
                if(empty)
                    this.disableButtons(true);
            });

            this.buttonDiscard = new Button("Elimina modifiche");
            this.buttonDiscard.setGraphic(new ImageView(this.getClass().getResource("/images/icons/delete.png").toString()));
            this.buttonDiscard.setDisable(true);
            this.getChildren().add(this.buttonDiscard);
            this.buttonDiscard.setOnAction(event -> {
                event.consume();
                if(GraphicUtilities.getInstance().showAlertConfirmationDelete("Elimina modifiche", "Vuoi rimuovere tutte le modifice " +
                        "che hai effettuato in questa sessione di lavoro?")){
                    Database.getInstance().discardChanges();
                    TreeViewSubject.getInstance().updateAll();
                    this.disableButtons(true);
                }

            });
        }

        public void disableButtons(boolean disable){
            this.buttonSave.setDisable(disable);
            this.buttonDiscardLast.setDisable(disable);
            this.buttonDiscard.setDisable(disable);
        }

    }
}
