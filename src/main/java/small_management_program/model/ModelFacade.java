package small_management_program.model;

import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.QueryRevert;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ModelFacade {
    private static ModelFacade instance;

    private ConfigDatabase configDatabase;
    private Database database;

    private LinkedList<Query> listRevertQueries = new LinkedList<>();

    private ModelFacade(){
        configDatabase = ConfigDatabase.getInstance();
        database = Database.getInstance();
    }

    public static ModelFacade getInstance(){
        if (instance == null)
            instance = new ModelFacade();
        return instance;
    }

    //----------------- SAVE DISCARD ---------------//

    public void saveChanges(){
        this.listRevertQueries.clear();
    }

    public void discardChanges(){
        int i = this.listRevertQueries.size();
        for(; i > 0; --i){
            try{
                this.executeQuery(this.listRevertQueries.pollLast());
            }catch (DatabaseException exception){}
        }
    }

    public boolean discardLastChange(){
        if(this.listRevertQueries.size() > 0){
            try{
                this.executeQuery(this.listRevertQueries.pollLast());
            }
            catch (DatabaseException exception){}
        }
        return this.listRevertQueries.size() == 0;
    }

    //--------------- DATABASE OPERATIONS ------------//

    public void executeQuery(Query query) throws DatabaseException {
        try{
            this.database.executeQuery(query);

            //Se la query ha necessità di un revert aggiungo la query contraria alla lista

            if(query instanceof QueryRevert){
                QueryRevert queryRevert = (QueryRevert) query;
                this.listRevertQueries.add(queryRevert.getQueryRevert());
            }
        }
        catch (SQLException exception){
            throw query.getException();
        }
    }

    //--------------- DATABASE OPERATION -------------//

    public boolean testConnection (String databaseAddress, String databasePort, String databaseName, String user, String password){
        return database.testConnection(databaseAddress, databasePort, databaseName, user, password);
    }

    public boolean setConnection(String databaseAddress, String databasePort, String databaseName, String user, String password){
        return this.database.setConnection(databaseAddress, databasePort, databaseName, user, password);
    }

    public boolean connectWithMemorizedCredential(){
        return this.database.setConnection(this.configDatabase.getDatabaseAddress(), this.configDatabase.getPort(),
                this.configDatabase.getDatabaseName(), this.configDatabase.getUser(), this.configDatabase.getPassword());
    }

    //--------------- GET CONFIG -----------------//

    public String getDatabaseAddress(){
        return configDatabase.getDatabaseAddress();
    }

    public String getDatabaseName(){
        return configDatabase.getDatabaseName();
    }

    public String getDatabasePort() {
        return configDatabase.getPort();
    }

    public String getDatabaseUser(){
        return configDatabase.getUser();
    }

    public String getDatabaseSsl() {
        return this.configDatabase.getSsl();
    }

    public String getDatabasePassword(){
        return configDatabase.getPassword();
    }

    //--------------- SET CONFIG -----------------//

    public void setDatabaseAddress(String address){
        this.configDatabase.setDatabaseAddress(address);
    }

    public void setDatabaseName(String name){
        this.configDatabase.setDatabaseName(name);
    }

    public void setDatabasePort(String port){
        this.configDatabase.setPort(port);
    }

    public void setDatabaseUser(String user){
        this.configDatabase.setUser(user);
    }

    public void setDatabaseSsl(String ssl){
        this.configDatabase.setSsl(ssl);
    }

    public void setDatabasePassword(String password){
        this.configDatabase.setPassword(password);
    }
}
