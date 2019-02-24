package small_management_program.model;

public class DatabaseException extends MyException{
    public DatabaseException(String title, String message){
        super(title, message);
    }
}
