package small_management_program.controller;

import small_management_program.controller.left.itemstrategy.TreeViewItemStrategy;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;
import small_management_program.model.ModelFacade;

public class ControllerFacade {
    private static ControllerFacade instance;

    private ModelFacade model;
    private TreeViewSubject treeViewSubject;
    private Observer tableViewData;

    private ControllerFacade(){
        this.model = ModelFacade.getInstance();
        this.treeViewSubject = new TreeViewSubject();
    }

    public static ControllerFacade getInstance(){
        if (instance == null)
            instance = new ControllerFacade();
        return instance;
    }

    //----------------- SAVE DISCARD ---------------//

    public void saveChanges(){
        this.model.saveChanges();
    }

    public void discardChanges(){
        this.model.discardChanges();
    }

    public boolean discardLastChange(){
        return this.model.discardLastChange();
    }

    //---------------- LEFT OPERATION --------------//

    public void setTreeViewItemStrategy(TreeViewItemStrategy treeViewItemStrategy){
        this.treeViewSubject.setItemStrategy(treeViewItemStrategy);
    }

    public void treeViewSubjectAttach(Observer observer){
        this.treeViewSubject.attach(observer);
    }

    public void treeViewSubjectDetach(Observer observer){
        this.treeViewSubject.detach(observer);
    }

    public void setTreeViewSubjectWhereParameters(WhereParameters whereParameters){
        this.treeViewSubject.setWhereParameters(whereParameters);
    }

    public void updateAll(){
        if(this.treeViewSubject != null)
            this.treeViewSubject.updateAll();
    }

    //--------------- RIGHT OPERATION ---------------//

    public void setTableViewData(Observer tableViewData){
        if(this.tableViewData != null){
            this.treeViewSubject.detach(this.tableViewData);
        }
        this.tableViewData = tableViewData;
        this.treeViewSubject.attach(tableViewData);
    }

    //--------------- DATABASE OPERATION -------------//

    public boolean testConnection (String databaseAddress, String databasePort, String databaseName, String user, String password){
        return model.testConnection(databaseAddress, databasePort, databaseName, user, password);
    }

    public boolean setConnection (String databaseAddress, String databasePort, String databaseName, String user, String password){
        return model.setConnection(databaseAddress, databasePort, databaseName, user, password);
    }

    public boolean connectWithMemorizedCredential(){
        return this.model.connectWithMemorizedCredential();
    }

    public void executeQuery(Query query) throws DatabaseException{
        this.model.executeQuery(query);
    }

    //--------------- GET CONFIG -----------------//

    public String getDatabaseAddress(){
        return model.getDatabaseAddress();
    }

    public String getDatabaseName(){
        return model.getDatabaseName();
    }

    public String getDatabasePort() {
        return model.getDatabasePort();
    }

    public String getDatabaseUser(){
        return model.getDatabaseUser();
    }

    public String getDatabaseSsl(){
        return this.model.getDatabaseSsl();
    }

    public String getDatabasePassword(){
        return model.getDatabasePassword();
    }

    //--------------- SET CONFIG -----------------//

    public void setDatabaseAddress(String address){
        this.model.setDatabaseAddress(address);
    }

    public void setDatabaseName(String name){
        this.model.setDatabaseName(name);
    }

    public void setDatabasePort(String port){
        this.model.setDatabasePort(port);
    }

    public void setDatabaseUser(String user){
        this.model.setDatabaseUser(user);
    }

    public void setDatabaseSsl(String ssl){
        this.model.setDatabaseSsl(ssl);
    }

    public void setDatabasePassword(String password){
        this.model.setDatabasePassword(password);
    }
}
