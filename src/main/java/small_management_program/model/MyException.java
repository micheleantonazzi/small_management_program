package small_management_program.model;

public abstract class MyException extends Throwable {
    private String title;
    private String message;

    public MyException(String title, String message){
        this.title = title;
        this.message = message;
    }

    public MyException(){
        this("","");
    }

    public String getTitle(){
        return this.title;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
