/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package small_management_program.model;


import org.junit.Test;
import small_management_program.model.database.ConfigDatabase;
import small_management_program.model.database.Database;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class TestsModel {
    @Test
    public void testDatabaseTestConnection() {
        ConfigDatabase configDatabase = ConfigDatabase.getInstance();
        try {
            Database.getInstance().testConnection(configDatabase.getDatabaseAddress(), configDatabase.getPort(),
                    configDatabase.getDatabaseName(), configDatabase.getUser(), configDatabase.getPassword());
        }
        catch (SQLException ex){
            fail();
        }
    }
    @Test
    public void testDatabaseConnection() {
        ConfigDatabase configDatabase = ConfigDatabase.getInstance();
        try {
            Database.getInstance().setConnection(configDatabase.getDatabaseAddress(), configDatabase.getPort(),
                    configDatabase.getDatabaseName(), configDatabase.getUser(), configDatabase.getPassword());
        }
        catch (SQLException ex){
            fail();
        }
    }
}
