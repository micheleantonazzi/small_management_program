package small_management_program.view.stages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import small_management_program.model.database.ConfigDatabase;
import small_management_program.model.database.Database;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StageDatabaseController implements Initializable {

    @FXML
    private TextField textFieldAddress, textFieldDatabaseName, textFieldPort,
            textFieldUser, textFieldPassword;

    @FXML
    private CheckBox checkBoxSSL;

    @FXML
    private Button buttonSaveSettings;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigDatabase configDatabase = ConfigDatabase.getInstance();
        textFieldAddress.setText(configDatabase.getDatabaseAddress());
        textFieldDatabaseName.setText(configDatabase.getDatabaseName());
        textFieldPort.setText(configDatabase.getPort());
        textFieldUser.setText(configDatabase.getUser());
        textFieldPassword.setText(configDatabase.getPassword());
        buttonSaveSettings.setDisable(true);
    }

    //Exception captured by AspectShowAlerts
    public void testConnection() throws SQLException {
        Database.getInstance().testConnection(this.textFieldAddress.getText(), this.textFieldPort.getText(),
                this.textFieldDatabaseName.getText(), this.textFieldUser.getText(), this.textFieldPassword.getText());
    }

    public void saveSettings(){
        ConfigDatabase configDatabase = ConfigDatabase.getInstance();
        configDatabase.setDatabaseAddress(this.textFieldAddress.getText());
        configDatabase.setDatabaseName(this.textFieldDatabaseName.getText());
        configDatabase.setPort(this.textFieldPort.getText());
        configDatabase.setUser(this.textFieldUser.getText());
        configDatabase.setPassword(this.textFieldPassword.getText());
        configDatabase.setSsl(String.valueOf(this.checkBoxSSL.isSelected()));
        buttonSaveSettings.setDisable(true);
    }

    //Exception captured by AspectShowAlerts
    public void connection() throws Exception{
        Database.getInstance().setConnection(this.textFieldAddress.getText(), this.textFieldPort.getText(),
                this.textFieldDatabaseName.getText(), this.textFieldUser.getText(), this.textFieldPassword.getText());
    }

    public void enableButtonSaveSettings(){
        buttonSaveSettings.setDisable(false);
    }
}
