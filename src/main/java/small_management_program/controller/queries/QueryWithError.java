package small_management_program.controller.queries;


public abstract class QueryWithError implements Query {

    public final String commit(){
        return "COMMIT;";
    }

    public final String rollback(){
        return "ROLLBACK;";
    }
}
