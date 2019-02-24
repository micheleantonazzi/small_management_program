package small_management_program.controller.queries.condo;

import small_management_program.controller.parameters.WhereParameters;

public class CondoSelectWithParameters extends CondoSelectAll {

    private WhereParameters whereParameters;

    public CondoSelectWithParameters(WhereParameters whereParameters){
        this.whereParameters = whereParameters;
    }

    @Override
    public String getQuery(){
        return "SELECT * FROM condos WHERE " + whereParameters.toString();
    }
}
