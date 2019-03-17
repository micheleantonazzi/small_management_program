package small_management_program.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.view.annotation.AnnotationShowFXML;
import small_management_program.view.left.ChoiceBoxTreeView;
import small_management_program.view.left.TreeViewObserver;
import small_management_program.view.right.ChoiceBoxTableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private VBox vBoxLeft;

    @FXML
    private VBox vBoxRight;

    @FXML
    private HBox hBoxRight;

    @FXML
    MenuItem menuItemQuit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //left

        this.vBoxLeft.getChildren().add(ChoiceBoxTreeView.getInstance());
        //Instance of main subject
        TreeViewObserver treeViewObserver = TreeViewObserver.getInstance();
        //Attach first observer
        TreeViewSubject.getInstance().attach(treeViewObserver);
        this.vBoxLeft.getChildren().add(treeViewObserver);
        this.vBoxLeft.setVgrow(treeViewObserver, Priority.ALWAYS);

        //right

        ChoiceBoxTableView choiceBoxTableView = new ChoiceBoxTableView(this);
        this.hBoxRight.getChildren().add(choiceBoxTableView);
    }

    public void setRightComponents(TableView tableView, HBox hBoxActions){
        HBox hBoxTopRight = (HBox) this.vBoxRight.getChildren().get(0);
        if(hBoxTopRight.getChildren().size() > 1){
            hBoxTopRight.getChildren().remove(1);
        }

        HBox.setHgrow(hBoxActions, Priority.ALWAYS);
        hBoxTopRight.getChildren().add(1, hBoxActions);

        //Aggiungo la tabella

        if(this.vBoxRight.getChildren().size() == 2){
            this.vBoxRight.getChildren().remove(1);
        }
        this.vBoxRight.getChildren().add(tableView);
        this.vBoxRight.setVgrow(tableView, Priority.ALWAYS);
    }

    public void setMenuItemQuit(){
        Platform.exit();
    }

    @AnnotationShowFXML(FXMLName = "/FXML/stages/StageDatabase.fxml", Tilte = "Database")
    public void showStageDatabase(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/condo/StageAddCondo.fxml", Tilte = "Aggiungi condominio")
    public void showStageAddCondo(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/administrator/StageAddAdministrator.fxml", Tilte = "Aggiungi Amministratore")
    public void showStageAddAdministrator(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/administrator/StageModifyAdministrator.fxml", Tilte = "Modifica Amministratore")
    public void showStageModifyAdministrator(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/condo/StageModifyCondo.fxml", Tilte = "Modifica Condominio")
    public void showStageModifyCondo(){}

    @AnnotationShowFXML(FXMLName = "/FXML/stages/bill/StageAddBill.fxml", Tilte = "Aggiungi fatturato")
    public void showStageAddBill(){}
}
