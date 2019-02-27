package small_management_program.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    MenuItem menuItemQuit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setMenuItemQuit(){
        Platform.exit();
    }

    @ShowFXML(FXMLName = "/FXML/stages/StageDatabase.fxml", Tilte = "Database")
    public void showStageDatabase() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/stages/StageDatabase.fxml"));
        fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(fxmlLoader.getRoot()));
        stage.showAndWait();
    }
}
