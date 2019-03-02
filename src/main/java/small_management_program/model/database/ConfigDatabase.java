package small_management_program.model.database;

import small_management_program.model.PasswordProtector;
import java.util.prefs.Preferences;

public class ConfigDatabase {
    private static ConfigDatabase instance;
    private PasswordProtector passwordProtector;

    private final String DATABASE_ADDRESS_ID = "databaseAddress";
    private final String DATABASE_ADDRESS_DEFAULT = "localhost";
    private final String DATABASE_NAME_ID = "databaseName";
    private final String DATABASE_NAME_DEFAULT = "test_db";
    private final String PORT_ID = "port";
    private final String PORT_DEFAULT = "3306";
    private final String USER_ID = "user";
    private final String USER_DEFAULT = "root";
    private final String PASSWORD_ID = "password";
    private final String PASSWORD_DEFAULT = "";
    private final String SSL_ID = "ssl";
    private final String SSL_DEFAULT = "0";

    private String databaseAddress;
    private String databaseName;
    private String port;
    private String user;
    private String password;
    private String ssl;
    private Preferences preferences;

    private ConfigDatabase(){
        this.passwordProtector = PasswordProtector.getInstance();

        this.preferences = Preferences.userNodeForPackage(this.getClass()).node("database");
        this.databaseAddress = preferences.get(DATABASE_ADDRESS_ID, DATABASE_ADDRESS_DEFAULT);
        this.databaseName = preferences.get(DATABASE_NAME_ID, DATABASE_NAME_DEFAULT);
        this.port = preferences.get(PORT_ID, PORT_DEFAULT);
        this.user = preferences.get(USER_ID, USER_DEFAULT);
        this.ssl = preferences.get(SSL_ID, SSL_DEFAULT);
        try{
            this.password = passwordProtector.decrypt(preferences.get(PASSWORD_ID, PASSWORD_DEFAULT));
        }
        catch (Exception e){
            this.password = PASSWORD_DEFAULT;
        }
    }

    public static ConfigDatabase getInstance(){
        if (instance == null)
            instance = new ConfigDatabase();
        return instance;
    }

    //--------------------- GET -------------------//

    public String getDatabaseAddress (){
        return this.databaseAddress;
    }

    public String getDatabaseName(){
        return this.databaseName;
    }

    public String getPort(){
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword(){
        return this.password;
    }

    public String getSsl() {
        return  this.ssl;
    }

    //------------------- SET --------------------//

    public void setDatabaseAddress(String address){
        this.preferences.put(this.DATABASE_ADDRESS_ID, address);
        this.databaseAddress = address;
    }

    public void setDatabaseName(String name){
        this.preferences.put(this.DATABASE_NAME_ID, name);
        this.databaseName = name;
    }

    public void setPort(String port){
        this.preferences.put(this.PORT_ID, port);
        this.port = port;
    }

    public void setUser(String user){
        this.preferences.put(this.USER_ID, user);
        this.user = user;
    }

    public void setSsl(String ssl){
        this.preferences.put(this.SSL_ID, ssl);
        this.ssl = ssl;
    }

    public void setPassword(String password){
        try {
            this.preferences.put(this.PASSWORD_ID, passwordProtector.encrypt(password));
            this.password = password;
        }
        catch (Exception e){
        }

    }
}