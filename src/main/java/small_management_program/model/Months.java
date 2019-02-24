package small_management_program.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;

public class Months {

    private String[] names = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre",
            "Ottobre", "Novembre", "Dicembre"};

    private static Months instance;

    private Months(){}

    public static Months getInstance(){
        if(instance == null)
            instance = new Months();
        return instance;
    }

    public String getMonthName(int index){
        return index > -1 && index < 12 ? this.names[index] : "";
    }

    public int getMonthNumber(String month){
        int number;
        for(number = 0; number < 12 && month != this.names[number]; ++number);
        if(number > 11)
            return -1;
        return number;
    }

    public int compareMonths(String month1, String month2){
        int m1 = this.getMonthNumber(month1);
        int m2 = this.getMonthNumber(month2);
        if(m1 > m2)
            return 1;
        if(m1 < m2)
            return -1;
        return 0;
    }

    public ObservableList<ChoiceBoxItemId> getListAllMonths(){
        return this.getListMonths(0, 11);
    }

    public ObservableList<ChoiceBoxItemId> getListMonths(int i){
        return this.getListMonths(i, 11);
    }

    public ObservableList<ChoiceBoxItemId> getListMonths(int i, int z){
        ObservableList<ChoiceBoxItemId> items = FXCollections.observableArrayList();
        if(i >= 0 && i <= z && z <= 11){
            for(; i <= z; ++i){
                items.add(new ChoiceBoxItemId(i, this.getMonthName(i)));
            }
        }

        return items;
    }

}
