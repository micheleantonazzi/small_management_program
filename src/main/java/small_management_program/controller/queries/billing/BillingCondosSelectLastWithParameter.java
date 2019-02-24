
package small_management_program.controller.queries.billing;

import small_management_program.controller.parameters.WhereParameters;

/**
 * Ritorna, per ogni condominio, l'ultima fattura ancora da saldare.
 * Viene usato in DataCondos per popolare la tabella TableViewBilling
 *
 * @author Michele Antonazzi
 *
 */

public class BillingCondosSelectLastWithParameter extends BillingCondosSelectLast {

    private WhereParameters whereParameters;

    public BillingCondosSelectLastWithParameter(WhereParameters whereParameters) {
        this.whereParameters = whereParameters;
    }

    public BillingCondosSelectLastWithParameter(String whereParameters){
        this(new WhereParameters(whereParameters));
    }

    public BillingCondosSelectLastWithParameter(){
        this("1 = 1");
    }

    public String getQuery(){
        //Ritorna, per ogni condominio che corrisponde ai parametri, l'ultima fattura ancora da saldare
        return "SELECT c.id_condo, c.name, c.month, lb.year, lb.total, b.month, b.price " +
                "FROM (condos c LEFT JOIN last_billings lb ON c.id_condo = lb.id_condo) LEFT JOIN bills b ON lb.id_condo = b.id_condo AND lb.year = b.year " +
                "WHERE " + this.whereParameters + " " +
                "ORDER BY c.id_condo, b.month";
    }
}
