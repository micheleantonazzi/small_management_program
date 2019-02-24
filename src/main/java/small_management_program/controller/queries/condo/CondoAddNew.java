package small_management_program.controller.queries.condo;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class CondoAddNew implements Query {

    private int id;
    private String code;
    private int administrator;
    private int month;
    private String name;
    private String province;
    private String city;
    private String address;
    private String cap;
    private int flats;


    public CondoAddNew(int id, String code, int administrator, int month, String name, String province, String city,
                       String address, String cap, int flats) {
        this.id = id;
        this.code = code;
        this.administrator = administrator;
        this.month = month;
        this.name = name;
        this.province = province;
        this.city = city;
        this.address = address;
        this.cap = cap;
        this.flats = flats;
    }

    @Override
    public String getQuery(){
        return "INSERT INTO condos (id_condo, code, id_administrator, month, name, province, city, address, cap, flats) VALUES" +
                "(" + this.id + ", '" + this.code + "', " + this.administrator + ", " + this.month + ", '" + this.name + "', " +
                "'" + this.province +"', '" + this. city + "', '" + this.address + "', '" + this.cap + "', " + this.flats +")";
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile inserire un nuovo condominio.\n" +
                "Controllare di non aver utilizzato un codice già impiegato per un altro condominio");
    }
}
