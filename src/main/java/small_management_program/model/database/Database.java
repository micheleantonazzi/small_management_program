package small_management_program.model.database;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.QueryWithError;
import small_management_program.controller.queries.QueryWithResults;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Database {
    private Connection connection = null;
    private static Database instance;
    private LinkedList<Query> listRevertQueries = new LinkedList<>();

    private Database() {}

    private String makeUrl(String databaseAddress, String databasePort, String databaseName){
        String url =  "jdbc:mysql://" + databaseAddress + ":" + databasePort + "/" + databaseName + "?verifyServerCertificate=false" +
                "&allowMultiQueries=true&useSSL=true";
        String s = ConfigDatabase.getInstance().getSsl();
        if(Boolean.valueOf(s)){
            url = url + "&requireSSL=true";
        }
        //Aggiunto perché necessario con connector 8
        url = url + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        return url;
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public boolean isConnected(){
        try {
            if(connection != null && !connection.isClosed()){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public void testConnection (String databaseAddress, String databasePort, String databaseName, String user, String password) throws SQLException {
        DriverManager.getConnection(makeUrl(databaseAddress, databasePort, databaseName), user, password);
    }

    public void setConnection(String databaseAddress, String databasePort, String databaseName, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(makeUrl(databaseAddress, databasePort, databaseName), user, password);
    }

    public void executeQuery(Query query) throws DatabaseException, SQLException {
        if(this.connection == null){
            throw new DatabaseException("Errore di connessione", "Attenzione, prima di eseguire qualsiasi operazione ricordati di" +
                    " effettuare la connessione al database");
        }
        Statement statement = this.connection.createStatement();
        try{
            if(query instanceof QueryWithResults){
                QueryWithResults queryWithResults = (QueryWithResults) query;
                queryWithResults.setResultSet(statement.executeQuery(query.getQuery()));
            }
            else{
                statement.execute(query.getQuery());
                if(query instanceof QueryWithError){
                    QueryWithError queryWithError = (QueryWithError) query;
                    statement.execute(queryWithError.commit());
                }
            }
        }
        catch (CommunicationsException exception){
            this.connection.close();
            this.connection = null;
            throw new DatabaseException("Errore di connessione", "Attenzione, il database non è più raggiungibile. Controlla la connessione.");
        }
        catch (SQLException exception){
            if(query instanceof QueryWithError){
                QueryWithError queryWithError = (QueryWithError) query;
                statement.execute(queryWithError.rollback());
            }
            throw query.getException();
        }
    }

    public void saveChanges(){
        this.listRevertQueries.clear();
    }

    public void discardChanges(){
        int i = this.listRevertQueries.size();
        for(; i > 0; --i){
            try{
                this.executeQuery(this.listRevertQueries.pollLast());
            }
            catch (DatabaseException exception){}
            catch (SQLException ex){}
        }
    }

    public boolean discardLastChange(){
        if(this.listRevertQueries.size() > 0){
            try{
                this.executeQuery(this.listRevertQueries.pollLast());
            }
            catch (DatabaseException exception){}
            catch (SQLException ex){}
        }
        return this.listRevertQueries.size() == 0;
    }
}
