package small_management_program.controller.queries.billing;

import small_management_program.controller.queries.condo.CondoSelectAll;

public class BillingSelectCondos extends CondoSelectAll {

    @Override
    public String getQuery(){
        return "SELECT * " +
                "FROM condos " +
                "WHERE id_condo IN " +
                "(SELECT id_condo " +
                "FROM billings) " +
                "ORDER BY id_condo";
    }
}
