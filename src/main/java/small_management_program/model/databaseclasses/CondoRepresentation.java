package small_management_program.model.databaseclasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import small_management_program.model.Months;

public class CondoRepresentation {
    private SimpleIntegerProperty id;
    private SimpleStringProperty code;
    private SimpleStringProperty administrator;
    private SimpleStringProperty idMonth;
    private SimpleStringProperty name;
    private SimpleStringProperty province;
    private SimpleStringProperty city;
    private SimpleStringProperty address;
    private SimpleStringProperty cap;
    private SimpleIntegerProperty flats;

    public CondoRepresentation(int id, String code, String administrator, int idMonth, String name, String province, String city, String address, String cap, int flats){
        this.id = new SimpleIntegerProperty(id);
        this.code = new SimpleStringProperty(code);
        this.administrator = new SimpleStringProperty(administrator);
        this.idMonth = new SimpleStringProperty(Months.getInstance().getMonthName(idMonth));
        this.name = new SimpleStringProperty(name);
        this.province = new SimpleStringProperty(province);
        this.city = new SimpleStringProperty(city);
        this.address = new SimpleStringProperty(address);
        this.cap = new SimpleStringProperty(cap);
        this.flats = new SimpleIntegerProperty(flats);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setAdministrator(String administrator) {
        this.administrator.set(administrator);
    }

    public void setIdMonth(int idMonth) {
        this.idMonth.set(Months.getInstance().getMonthName(idMonth));
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setProvince(String province) {
        this.province.set(province);
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setCap(String cap) {
        this.cap.set(cap);
    }

    public void setFlats(int flats) {
        this.flats.set(flats);
    }

    public int getId() {
        return id.get();
    }


    public String getCode() {
        return code.get();
    }

    public String getAdministrator() {
        return administrator.get();
    }

    public String getIdMonth() {
        return idMonth.get();
    }

    public String getName() {
        return name.get();
    }

    public String getProvince() {
        return province.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getCap() {
        return cap.get();
    }

    public int getFlats() {
        return flats.get();
    }
}
