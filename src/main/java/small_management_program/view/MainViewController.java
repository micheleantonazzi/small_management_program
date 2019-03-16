package small_management_program.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.view.annotation.AnnotationShowFXML;
import small_management_program.view.left.ChoiceBoxTreeView;
import small_management_program.view.left.TreeViewObserver;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private VBox vBoxLeft;
    @FXML
    MenuItem menuItemQuit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.vBoxLeft.getChildren().add(ChoiceBoxTreeView.getInstance());

        TreeViewObserver treeViewObserver = TreeViewObserver.getInstance();
        TreeViewSubject.getInstance().attach(treeViewObserver);
        this.vBoxLeft.getChildren().add(treeViewObserver);
        this.vBoxLeft.setVgrow(treeViewObserver, Priority.ALWAYS);
    }

    public void setMenuItemQuit(){
        Platform.exit();
    }

    @AnnotationShowFXML(FXMLName = "/FXML/stages/StageDatabase.fxml", Tilte = "Database")
    public void showStageDatabase(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/StageAddCondo.fxml", Tilte = "Aggiungi condominio")
    public void showStageAddCondo(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/StageAddAdministrator.fxml", Tilte = "Aggiungi Amministratore")
    public void showStageAddAdministrator(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/StageModifyAdministrator.fxml", Tilte = "Modifica Amministratore")
    public void showStageModifyAdministrator(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/StageModifyCondo.fxml", Tilte = "Modifica Condominio")
    public void showStageModifyCondo(){}
}
