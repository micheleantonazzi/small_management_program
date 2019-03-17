package small_management_program.controller.queries.bill;

import small_management_program.controller.queries.condo.CondoSelectAll;

public class BillSelectCondos extends CondoSelectAll {

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
