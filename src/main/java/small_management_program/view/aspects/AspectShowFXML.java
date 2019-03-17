package small_management_program.view.aspects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.aspectj.lang.annotation.After;

import org.aspectj.lang.annotation.Aspect;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.view.annotation.AnnotationShowFXML;

import java.io.IOException;


@Aspect
public class AspectShowFXML {

    @After("execution(public void small_management_program.view..showStage*()) && " +
            "@annotation(annotation)")
    public void showFXML(AnnotationShowFXML annotation){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(annotation.FXMLName()));
            fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(annotation.Tilte());
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(fxmlLoader.getRoot());
            scene.getStylesheets().add(this.getClass().getResource("/style/MainViewStyle.css").toString());
            stage.setScene(scene);
            stage.setOnCloseRequest((WindowEvent event1) -> TreeViewSubject.getInstance().updateAll());
            stage.showAndWait();
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
