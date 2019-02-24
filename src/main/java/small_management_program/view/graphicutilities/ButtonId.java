package small_management_program.view.graphicutilities;

import javafx.scene.control.Button;

public class ButtonId extends Button {

    private int id;

    public ButtonId(String text){
        this(text, -1);
    }

    public ButtonId(String text, int id){
        super(text);
        this.id = id;
    }

    public int retId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
