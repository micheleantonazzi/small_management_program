package small_management_program.model.database;

import small_management_program.model.MyException;

public class DatabaseException extends MyException {
    public DatabaseException(String title, String message){
        super(title, message);
    }
}
