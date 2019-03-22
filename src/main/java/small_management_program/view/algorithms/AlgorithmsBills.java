/*
 * Michele Antonazzi
 */

package small_management_program.view.algorithms;

import javafx.scene.control.Alert;
import small_management_program.controller.queries.MultipleQueriesRevert;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.bill.BillAddNew;
import small_management_program.controller.queries.billing.BillingSetPaidTrue;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.Months;
import small_management_program.model.databaseclasses.BillingRepresentation;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class AlgorithmsBills {

    private static AlgorithmsBills instance;

    public static AlgorithmsBills getInstance(){
        if (instance == null){
            instance = new AlgorithmsBills();
        }
        return instance;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private AlgorithmsBills(){}

    public boolean isPossible(BillingRepresentation billingRepresentation) {
        return this.isPossible(billingRepresentation, Calendar.getInstance().get(Calendar.MONTH));
    }

    public boolean isPossible(BillingRepresentation billingRepresentation, int month){
        //Se l'anno è vuoto allora la fatturazione non si può fare
        if(billingRepresentation.getYear() == null || billingRepresentation.getYear().equals(""))
            return false;

        //se la fatturazione si riferisce a due anni fa posso fatturare in qualsiasi mese io voglia

        if(Integer.valueOf(billingRepresentation.getYear()) < Calendar.getInstance().get(Calendar.YEAR) - 1)
            return true;

        Calendar condoCalendar = Calendar.getInstance();
        condoCalendar.set(Integer.valueOf(billingRepresentation.getYear()),
                Months.getInstance().getMonthNumber(billingRepresentation.getMonth()), 1);
        condoCalendar.getTime();

        Calendar billingCalendar = Calendar.getInstance();
        billingCalendar.set(Calendar.getInstance().get(Calendar.YEAR), month, 2);
        billingCalendar.getTime();
        return Calendar.getInstance().get(Calendar.YEAR) > Integer.valueOf(billingRepresentation.getYear()) ?
                condoCalendar.before(billingCalendar) : !billingRepresentation.isMonthBilled(month);
    }

    //Questo metodo viene chiamato quando si effettua una fattura alla data corrente, infatti passa al metodo sottostante i valori di anno e mese correnti
    public void createBill(BillingRepresentation billingRepresentation, boolean showAlert){

        int month = Calendar.getInstance().get(Calendar.MONTH);

        if(!this.isPossible(billingRepresentation, month))
            return;
        //Se sto fatturando in automatico un condominio di due anno fa completo la fatturazione passando il mese di chiusura
        //dell'esercizio
        if (Integer.valueOf(billingRepresentation.getYear()) < Calendar.getInstance().get(Calendar.YEAR) - 1)
            month = billingRepresentation.getRealMonth();
        this.createBill(billingRepresentation, Calendar.getInstance().get(Calendar.YEAR), month, showAlert);
    }

    //Questo metodo viene chiamato direttamente se è necessario andare ad effettuare una fattura in un mese di un anno specifico
    public boolean createBill(BillingRepresentation billingRepresentation, int billingYear, int billingMonth, boolean showAlert){

        boolean billed = false;


        Calendar condoCalendar = Calendar.getInstance();
        condoCalendar.set(Integer.valueOf(billingRepresentation.getYear()), Months.getInstance().getMonthNumber(billingRepresentation.getMonth()), 1);

        Calendar billingCalendar = Calendar.getInstance();
        billingCalendar.set(billingYear, billingMonth, 1);

        double total = billingRepresentation.getRealTotal();

        double alreadyBill = billingRepresentation.getRealJanuary() + billingRepresentation.getRealFebruary() +
                billingRepresentation.getRealMarch() + billingRepresentation.getRealApril() +
                billingRepresentation.getRealMay() + billingRepresentation.getRealJune() +
                billingRepresentation.getRealJuly() + billingRepresentation.getRealAugust() +
                billingRepresentation.getRealSeptember() + billingRepresentation.getRealOctober() +
                billingRepresentation.getRealNovember() + billingRepresentation.getRealDecember();

        int differenceMonths = (billingCalendar.get(Calendar.YEAR) - condoCalendar.get(Calendar.YEAR)) * 12 +
                (billingCalendar.get(Calendar.MONTH) - condoCalendar.get(Calendar.MONTH));

        double bill;

        Query createNewBill;

        if(differenceMonths > 12 || billingCalendar.get(Calendar.MONTH) == condoCalendar.get(Calendar.MONTH)){
            bill = round(total - alreadyBill, 2);

            //Setto al fattura annuale pagata e creo l'ultima fattura, poi pongo tutto dentro a MultipleQueries
            Query billingSetPaidTrue = new BillingSetPaidTrue(billingRepresentation.getId(), Integer.valueOf(billingRepresentation.getYear()));
            Query billAddNew = new BillAddNew(billingRepresentation.getId(), billingRepresentation.getYear(), billingCalendar.get(Calendar.MONTH), bill);
            ArrayList<Query> queries = new ArrayList<>();
            queries.add(billAddNew);
            queries.add(billingSetPaidTrue);
            createNewBill = new MultipleQueriesRevert(queries);

            if(showAlert){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completata riscossione fatture");
                alert.setHeaderText("Per il condominio con codice " + billingRepresentation.getId() + " hai completato la fatturazione del " +
                        billingRepresentation.getYear() + ". \n" +
                        "L'ultima fattura ammonta a " + bill);
                alert.showAndWait();
            }
        }
        else{
            bill = round((total / 12 * differenceMonths) - alreadyBill, 2);
            createNewBill = new BillAddNew(billingRepresentation.getId(), billingRepresentation.getYear(), billingCalendar.get(Calendar.MONTH), bill);
        }
        try{
            Database.getInstance().executeQuery(createNewBill);
            billed = true;
        }
        catch (DatabaseException exception){
            GraphicUtilities.getInstance().showAlertError(exception);
        }
        catch (SQLException exception){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", exception.getMessage());
        }
        return billed;
    }
}
