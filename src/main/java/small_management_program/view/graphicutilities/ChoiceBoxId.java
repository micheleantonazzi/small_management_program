package small_management_program.view.graphicutilities;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxId extends ChoiceBox{

    private int id;

    public ChoiceBoxId(ObservableList items, int id){
        super(items);
        this.id = id;
    }

    public int retId(){
        return this.id;
    }
}
