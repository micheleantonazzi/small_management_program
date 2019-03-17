/*
 * Michele Antonazzi
 */

package small_management_program.view.right.bill;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.image.ImageView;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.bill.BillRemoveLast;
import small_management_program.controller.queries.billing.BillingDelete;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.databaseclasses.BillingRepresentation;
import small_management_program.view.algorithms.AlgorithmsBills;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.sql.SQLException;

public class ContextMenuTableViewBill extends ContextMenu {

    private TableViewBilling table;
    private TableRow<BillingRepresentation> row;

    private MenuItem menuItemMadeBill;
    private MenuItem menuItemMadeBillInMonth;
    private MenuItem menuItemRemoveLastBill;
    private MenuItem menuItemRemoveBill;

    public ContextMenuTableViewBill(TableViewBilling table, TableRow<BillingRepresentation> row) {

        this.table = table;
        this.row = row;

        this.menuItemMadeBill = this.getItemMadeBill();
        this.menuItemMadeBillInMonth = this.getItemMadeBillInMonth();
        this.menuItemRemoveLastBill = this.getItemRemoveLastBill();
        this.menuItemRemoveBill = this.getItemRemoveBill();

        this.getItems().addAll(this.menuItemMadeBill, this.menuItemMadeBillInMonth, this.menuItemRemoveLastBill, this.menuItemRemoveBill);
    }

    private MenuItem getItemMadeBill(){
        MenuItem itemMadeBill = new MenuItem("Crea fattura");
        itemMadeBill.setGraphic(new ImageView(this.getClass().getResource("/images/icons/credit-card-plus.png").toString()));
        itemMadeBill.setOnAction(event-> {
            event.consume();
            AlgorithmsBills.getInstance().createBill(this.row.getItem(), true);
            TreeViewSubject.getInstance().updateAll();
            this.table.disableButtons(false);
        });
        return itemMadeBill;
    }

    private MenuItem getItemMadeBillInMonth(){
        MenuItem itemMadeBillInMonth = new MenuItem("Crea fattura in un mese");
        itemMadeBillInMonth.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar-plus.png").toString()));
        itemMadeBillInMonth.setOnAction(event-> {
            event.consume();
            /*new StageBillingInMonth(this.row.getItem()).showAndWait();
            ControllerFacade.getInstance().updateAll();
            this.table.disableButtons(false);
            */
        });

        return itemMadeBillInMonth;
    }

    private MenuItem getItemRemoveLastBill(){
        MenuItem itemRemoveLastBill = new MenuItem("Rimuovi ultima fattura");
        itemRemoveLastBill.setGraphic(new ImageView(this.getClass().getResource("/images/icons/backup-restore.png").toString()));
        itemRemoveLastBill.setOnAction(event -> {
            event.consume();
            BillingRepresentation item = this.row.getItem();
            if(GraphicUtilities.getInstance().showAlertConfirmationDelete("Elimina ultima fattura", "Vuoi davvero elimare " +
                    "l'ultima fattura del condominio " + item.getName() + "?")){
                try{
                    Query billRemoveLast = new BillRemoveLast(item.getId(), Integer.valueOf(item.getYear()), item.monthLastBill());
                    Database.getInstance().executeQuery(billRemoveLast);
                    TreeViewSubject.getInstance().updateAll();
                }
                catch (DatabaseException exception){
                    GraphicUtilities.getInstance().showAlertError(exception);
                }
                catch (SQLException exception){
                    GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", exception.getMessage());
                }
            }
        });
        return  itemRemoveLastBill;
    }

    private MenuItem getItemRemoveBill(){
        MenuItem itemRemoveBill = new MenuItem("Rimuovi fatturato");
        itemRemoveBill.setGraphic(new ImageView(this.getClass().getResource("/images/icons/delete.png").toString()));
        itemRemoveBill.setOnAction(event -> {
            event.consume();
            try {
                if(GraphicUtilities.getInstance().showAlertConfirmationDelete("Conferma eliminazione", "Vuoi davvero eliminare il fatturato " +
                        "per l'anno " + this.row.getItem().getYear() + "?")){
                    Query billingDelete = new BillingDelete(this.row.getItem().getId(), Integer.valueOf(this.row.getItem().getYear()));
                    Database.getInstance().executeQuery(billingDelete);
                    TreeViewSubject.getInstance().updateAll();
                }
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
            catch (SQLException exception){
                GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", exception.getMessage());
            }

        });
        return itemRemoveBill;
    }

    @Override
    public void show(Node anchor, double screenX, double screenY){
        super.show(anchor, screenX, screenY);

        //Ridefinisco questo metodo perché quando il menù compare (quindi viene chiamato show(), disabilito le azioni che non possono
        //essere eseguite sull'item specifico contenuto dalla riga

        BillingRepresentation item = this.row.getItem();

        //Disabilito l'item che permette di creare la fattura
        if(item.getTotal().equals("") || !AlgorithmsBills.getInstance().isPossible(item))
            this.menuItemMadeBill.setDisable(true);
        else
            this.menuItemMadeBill.setDisable(false);

        //Disabilito l'item che permette di creare una fattura in un mese specifico
        if(item.getTotal().equals(""))
            this.menuItemMadeBillInMonth.setDisable(true);
        else
            this.menuItemMadeBillInMonth.setDisable(false);

        //Disabilito l'item che permette di creare l'ultima fattura
        if(item.billsNumber() <= 0)
            this.menuItemRemoveLastBill.setDisable(true);
        else
            this.menuItemRemoveLastBill.setDisable(false);

        if (item.getYear().equals(""))
            this.menuItemRemoveBill.setDisable(true);
        else
            this.menuItemRemoveBill.setDisable(false);
    }
}
