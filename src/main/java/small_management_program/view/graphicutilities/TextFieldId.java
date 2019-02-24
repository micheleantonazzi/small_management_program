package small_management_program.view.graphicutilities;

import javafx.scene.control.TextField;

public class TextFieldId extends TextField {
    private int id;

    public TextFieldId(String text, int id){
        super(text);
        this.id = id;
    }

    public int retId() {
        return this.id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }

}
