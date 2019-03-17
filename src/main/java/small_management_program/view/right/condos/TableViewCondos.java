package small_management_program.view.right.condos;

import javafx.beans.binding.Bindings;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.model.Months;
import small_management_program.model.databaseclasses.CondoRepresentation;


public class TableViewCondos extends TableView implements Observer {

    private static TableViewCondos instance;

    public static TableViewCondos getInstance(){
        if (instance == null)
            instance = new TableViewCondos();
        return instance;
    }

    private TableViewCondos(){

        this.setMaxWidth(Double.POSITIVE_INFINITY);

        TableColumn tableColumn1 = new TableColumn<>("Codice");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("id"));
        tableColumn1.prefWidthProperty().bind(this.widthProperty().multiply(0.05));

        TableColumn tableColumn2 = new TableColumn<>("Codice fiscale");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("code"));
        tableColumn2.prefWidthProperty().bind(this.widthProperty().multiply(0.09));

        TableColumn tableColumn3 = new TableColumn<>("Amministratore");
        tableColumn3.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("administrator"));
        tableColumn3.prefWidthProperty().bind(this.widthProperty().multiply(0.12));

        TableColumn tableColumn4 = new TableColumn<>("Mese");
        tableColumn4.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("idMonth"));
        tableColumn4.setComparator((month1, month2) -> Months.getInstance().compareMonths(month1.toString(), month2.toString()));
        tableColumn4.prefWidthProperty().bind(this.widthProperty().multiply(0.07));

        TableColumn tableColumn5 = new TableColumn<>("Nome");
        tableColumn5.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("name"));
        tableColumn5.prefWidthProperty().bind(this.widthProperty().multiply(0.19));

        TableColumn tableColumn6 = new TableColumn<>("Provincia");
        tableColumn6.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("province"));
        tableColumn6.prefWidthProperty().bind(this.widthProperty().multiply(0.05));

        TableColumn tableColumn7 = new TableColumn<>("Citt\u00E0");
        tableColumn7.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("city"));
        tableColumn7.prefWidthProperty().bind(this.widthProperty().multiply(0.12));

        TableColumn tableColumn8 = new TableColumn<>("Indirizzo");
        tableColumn8.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("address"));
        tableColumn8.prefWidthProperty().bind(this.widthProperty().multiply(0.17));

        TableColumn tableColumn9 = new TableColumn<>("CAP");
        tableColumn9.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("cap"));
        tableColumn9.prefWidthProperty().bind(this.widthProperty().multiply(0.08));

        TableColumn tableColumn10 = new TableColumn<>("Appartamenti");
        tableColumn10.setCellValueFactory(new PropertyValueFactory<CondoRepresentation, String>("flats"));
        tableColumn10.prefWidthProperty().bind(this.widthProperty().multiply(0.055));

        this.getColumns().addAll(tableColumn1, tableColumn2, tableColumn3, tableColumn4, tableColumn5, tableColumn6, tableColumn7,
                tableColumn8, tableColumn9, tableColumn10);
    }

    @Override
    public void update(Subject subject){
        try{
            this.setItems(subject.getTableViewData());
            this.setRowFactory(new ShowRowMenu());
        }
        catch (UpdateException exception){}
    }

    private class ShowRowMenu implements Callback<TableView<CondoRepresentation>, TableRow<CondoRepresentation>> {

        @Override
        public TableRow<CondoRepresentation> call(TableView<CondoRepresentation> tableView) {
            final TableRow<CondoRepresentation> row = new TableRow<>();
            final ContextMenuTableViewCondos rowMenu = new ContextMenuTableViewCondos(row);
            //Mostra il menu solo se l'item non è nullo
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenuTableViewCondos) null));
            return row;
        }
    }
}
