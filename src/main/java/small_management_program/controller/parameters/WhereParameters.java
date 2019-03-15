package small_management_program.controller.parameters;

import java.util.stream.Collectors;

public class WhereParameters extends QueryParameters {

    public WhereParameters(String string){
        super(string);
    }

    public WhereParameters(){
        super("");
    }

    @Override
    public String toString(){
        return super.getParameters().stream().collect(Collectors.joining(" AND "));
    }

    @Override
    public WhereParameters clone(){
        WhereParameters whereParameters = new WhereParameters();
        whereParameters.add(super.getParameters());
        return whereParameters;
    }
}
