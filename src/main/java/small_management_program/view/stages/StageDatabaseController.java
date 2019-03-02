package small_management_program.view.stages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import small_management_program.model.database.ConfigDatabase;
import small_management_program.model.database.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class StageDatabaseController implements Initializable {

    @FXML
    private TextField textFieldAddress, textFieldDatabaseName, textFieldPort,
            textFieldUser, textFieldPassword;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigDatabase configDatabase = ConfigDatabase.getInstance();
        textFieldAddress.setText(configDatabase.getDatabaseAddress());
        textFieldDatabaseName.setText(configDatabase.getDatabaseName());
        textFieldPort.setText(configDatabase.getPort());
        textFieldUser.setText(configDatabase.getUser());
        textFieldPassword.setText(configDatabase.getPassword());

    }

    public void testConnection(){
        try{
            Database.getInstance().testConnection(this.textFieldAddress.getText(), this.textFieldPort.getText(),
                    this.textFieldDatabaseName.getText(), this.textFieldUser.getText(), this.textFieldPassword.getText());


        }
    catch (Exception e){}
    }
}
