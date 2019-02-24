package small_management_program.model.databaseclasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import small_management_program.model.Months;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class BillingRepresentation {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty month;
    //L'anno viene usato per controllase se la fattura può essere emessa e eventualmente per calcolarla
    private SimpleStringProperty year;
    //total comprende anche l'anno in cui è stata emessa la fattura,
    //il formato è      xxx (AA)
    private SimpleStringProperty total;
    private SimpleStringProperty january;
    private SimpleStringProperty february;
    private SimpleStringProperty march;
    private SimpleStringProperty april;
    private SimpleStringProperty may;
    private SimpleStringProperty june;
    private SimpleStringProperty july;
    private SimpleStringProperty august;
    private SimpleStringProperty september;
    private SimpleStringProperty october;
    private SimpleStringProperty november;
    private SimpleStringProperty december;
    private int billsNumber = 0;
    private List<SimpleStringProperty> listProperties;

    public BillingRepresentation(int id, String name, int month, String year, String total, String january, String february, String march, String april,
                                 String may, String june, String july, String august, String september, String october,
                                 String november, String december) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.month = new SimpleStringProperty(Months.getInstance().getMonthName(month));
        this.year = new SimpleStringProperty(year);
        this.total = new SimpleStringProperty(total);
        this.january = new SimpleStringProperty(january);
        this.february = new SimpleStringProperty(february);
        this.march = new SimpleStringProperty(march);
        this.april = new SimpleStringProperty(april);
        this.may = new SimpleStringProperty(may);
        this.june = new SimpleStringProperty(june);
        this.july = new SimpleStringProperty(july);
        this.august = new SimpleStringProperty(august);
        this.september = new SimpleStringProperty(september);
        this.october = new SimpleStringProperty(october);
        this.november = new SimpleStringProperty(november);
        this.december = new SimpleStringProperty(december);
        this.addPropertyToList();
    }

    public BillingRepresentation(){
        this.id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty("");
        this.month = new SimpleStringProperty("");
        this.year = new SimpleStringProperty("");
        this.total = new SimpleStringProperty("");
        this.january = new SimpleStringProperty("");
        this.february = new SimpleStringProperty("");
        this.march = new SimpleStringProperty("");
        this.april = new SimpleStringProperty("");
        this.may = new SimpleStringProperty("");
        this.june = new SimpleStringProperty("");
        this.july = new SimpleStringProperty("");
        this.august = new SimpleStringProperty("");
        this.september = new SimpleStringProperty("");
        this.october = new SimpleStringProperty("");
        this.november = new SimpleStringProperty("");
        this.december = new SimpleStringProperty("");
        this.addPropertyToList();
    }

    private void addPropertyToList(){
        this.listProperties = new ArrayList<>(12);
        this.listProperties.add(this.january);
        this.listProperties.add(this.february);
        this.listProperties.add(this.march);
        this.listProperties.add(this.april);
        this.listProperties.add(this.may);
        this.listProperties.add(this.june);
        this.listProperties.add(this.july);
        this.listProperties.add(this.august);
        this.listProperties.add(this.september);
        this.listProperties.add(this.october);
        this.listProperties.add(this.november);
        this.listProperties.add(this.december);
    }

    //Questo metodo serve per assegnare le fatture ai mesi corrispondenti in modo semplice
    public void setBills(List<String> bills){

        if(bills.size() % 2 == 0){
            for(int i = 0; i < bills.size(); ++i) {
                int month = Integer.valueOf(bills.get(i++));
                String price = bills.get(i);

                //Controllo che il prezzo abbia due cifre decimali

                if (Pattern.matches("^[0-9]*\\.[0-9]", price)) {
                    price = price + "0";
                }

                if (month >= 0 && month <= 11) {
                    this.listProperties.get(month).set(price);
                    this.billsNumber++;
                }
            }
        }
    }

    //Questo metodo ritorna il numero di fatture che ha il condominio per l'anno in corso.
    //Al conto viene tolta un'eventuale fattura fatta nel mese di chiusura dell'esercizio
    public int billsNumber(){
        int monthNumber = Months.getInstance().getMonthNumber(this.month.get());
        int ret = this.billsNumber;
        SimpleStringProperty closeMonthBill = this.listProperties.get(monthNumber);

        if(!closeMonthBill.isEmpty().get())
            --ret;

        return ret;
    }

    //Questo metodo ritorna il mese dell'ultima fattura (se esiste)
    public int monthLastBill(){
        if (this.billsNumber <= 0)
            return -1;

        int month = Months.getInstance().getMonthNumber(this.month.get());

        int ret = -1;

        //Controllo i mesi prima (in tabella) del mese di chiusura
        for(int i = month -1; i >= 0 && ret == -1; --i){
            if(!this.listProperties.get(i).isEmpty().get())
                ret = i;
        }

        if(ret == -1){
            //Controllo i mesi dopo (in tabella) del mese di chiusura
            for(int i = 11; i > month && ret == -1; --i){
                if(!this.listProperties.get(i).isEmpty().get())
                    ret = i;
            }
        }

        return ret;
    }

    //Questo medoto restituisce true se è presente una fattura nel mese corrente
    public boolean alreadyBilled(){
        return !this.listProperties.get(Calendar.getInstance().get(Calendar.MONTH)).isEmpty().get();
    }

    //Questo metodo ritorna true se il mese in questione è coperto da almeno una fattura
    public boolean isMonthBilled(int month){
        int monthLastBill = this.monthLastBill();
        if(monthLastBill < 0 || month < 0 || month > 11 || month == Months.getInstance().getMonthNumber(this.month.get()))
            return false;

        int closingMonth = Months.getInstance().getMonthNumber(this.month.get());

        boolean ret = false;

        if((closingMonth < month && month <= monthLastBill) ||
                (month <= monthLastBill && monthLastBill < closingMonth) ||
                (monthLastBill < closingMonth && closingMonth < month))
            ret = true;

        return ret;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setMonth(int month) {
        this.month.set(Months.getInstance().getMonthName(month));
    }

    public void setYear(String year) {
        if(year != null && year.length() > 4){
            year = year.substring(0, 4);
        }
        this.year.set(year);
    }

    public void setTotal(String total) {

        //Eseguo i controlli per verificare se total ha due cifre decimali

        if(total != null && !total.equals("")){
            String realTotal = total.substring(0, total.indexOf(' '));
            if(Pattern.matches("^[0-9]*\\.[0-9]", realTotal)){
                realTotal = realTotal + "0";
            }
            this.total.set(realTotal +  " " + total.substring(total.indexOf(' '), total.length()));
        }
    }

    public void setJanuary(String january) {
        this.january.set(january);
    }

    public void setFebruary(String february) {
        this.february.set(february);
    }

    public void setMarch(String march) {
        this.march.set(march);
    }

    public void setApril(String april) {
        this.april.set(april);
    }

    public void setMay(String may) {
        this.may.set(may);
    }

    public void setJune(String june) {
        this.june.set(june);
    }

    public void setJuly(String july) {
        this.july.set(july);
    }

    public void setAugust(String august) {
        this.august.set(august);
    }

    public void setSeptember(String september) {
        this.september.set(september);
    }

    public void setOctober(String october) {
        this.october.set(october);
    }

    public void setNovember(String november) {
        this.november.set(november);
    }

    public void setDecember(String december) {
        this.december.set(december);
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getMonth() {
        return month.get();
    }

    public int getRealMonth() {
        return Months.getInstance().getMonthNumber(month.get());
    }

    public double getRealTotal(){
        Double realTotal = 0.0;
        if (!total.equals(""))
            realTotal = Double.valueOf(this.total.get().substring(0, this.total.get().indexOf(' ')));
        return realTotal;
    }

    public String getYear() {
        return year.get() != null ? year.get() : "" ;
    }

    public String getTotal() {
        return total.get();
    }

    public String getJanuary() {
        return january.get();
    }

    public Double getRealJanuary() {
        return january.get().equals("") ? 0.0: Double.valueOf(january.get());
    }

    public String getFebruary() {
        return february.get();
    }

    public Double getRealFebruary() {
        return february.get().equals("") ? 0.0 : Double.valueOf(february.get());
    }

    public String getMarch() {
        return march.get();
    }

    public Double getRealMarch() {
        return march.get().equals("") ? 0.0 : Double.valueOf(march.get());
    }

    public String getApril() {
        return april.get();
    }

    public Double getRealApril() {
        return april.get().equals("") ? 0.0 : Double.valueOf(april.get());
    }

    public String getMay() {
        return may.get();
    }

    public Double getRealMay() {
        return may.get().equals("") ? 0.0 : Double.valueOf(may.get());
    }

    public String getJune() {
        return june.get();
    }

    public Double getRealJune() {
        return june.get().equals("") ? 0.0 : Double.valueOf(june.get());
    }

    public String getJuly() {
        return july.get();
    }

    public Double getRealJuly() {
        return july.get().equals("") ? 0.0 : Double.valueOf(july.get());
    }

    public String getAugust() {
        return august.get();
    }

    public Double getRealAugust() {
        return august.get().equals("") ? 0.0 : Double.valueOf(august.get());
    }

    public String getSeptember() {
        return september.get();
    }

    public Double getRealSeptember() {
        return september.get().equals("") ? 0.0 : Double.valueOf(september.get());
    }

    public String getOctober() {
        return october.get();
    }

    public Double getRealOctober() {
        return october.get().equals("") ? 0.0 : Double.valueOf(october.get());
    }

    public String getNovember() {
        return november.get();
    }

    public Double getRealNovember() {
        return november.get().equals("") ? 0.0 : Double.valueOf(november.get());
    }

    public String getDecember() {
        return december.get();
    }

    public Double getRealDecember() {
        return december.get().equals("") ? 0.0 : Double.valueOf(december.get());
    }
}
