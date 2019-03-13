package small_management_program.view.graphicutilities;


public class ChoiceBoxItemId {
    private int id;
    private String message;

    public ChoiceBoxItemId(int id, String message){
        this.id = id;
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return message;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
