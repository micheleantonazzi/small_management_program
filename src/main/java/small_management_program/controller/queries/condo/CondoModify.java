package small_management_program.controller.queries.condo;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;



public class CondoModify implements Query {
    private int idOld;
    private int idNew;
    private String code;
    private int administrator;
    private int month;
    private String name;
    private String province;
    private String city;
    private String address;
    private String cap;
    private int flats;


    public CondoModify(int idOld, int idNew, String code, int administrator, int month, String name, String province, String city,
                       String address, String cap, int flats) {
        this.idOld = idOld;
        this.idNew = idNew;
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
        return "UPDATE condos SET id_condo = " + this.idNew + ", code = '" + this.code + "', id_administrator = " + this.administrator + ", " +
                "month = " + this.month + ", name = '" + this.name + "', province = '" + this.province + "', city = '" + this.city + "', " +
                "address = '" + this.address + "', cap = '" + this.cap + "', flats = " + this.flats + " WHERE id_condo = " + this.idOld;

    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile modificare il condominio desiderato.\n" +
                "Controllare di non aver assegnato un codice già utilizzato per un altro condominio e di non aver settato valori errati.");
    }
}
