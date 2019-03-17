package small_management_program.view.aspects;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import small_management_program.controller.left.TreeViewSubject;

@Aspect
public class AspectCloseStage {

    @After("execution(* *..*.closeStage(..)) && args(event)")
    public void exit(ActionEvent event){

        TreeViewSubject.getInstance().updateAll();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
