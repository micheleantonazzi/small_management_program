package small_management_program.controller.parameters;

import java.util.ArrayList;

public abstract class QueryParameters {

    private boolean setIdentifier = false;

    private ArrayList<String> parameters = new ArrayList<>(1);

    public QueryParameters(String parameter){
        if(parameter != "")
            this.parameters.add(parameter);
    }

    public void add(String parameter){
        if(parameter != "")
            this.parameters.add(parameter);
    }

    public void add(ArrayList<String> parameters){
        parameters.removeIf(string -> string.equals(""));
        this.parameters.addAll(parameters);
    }

    public void add(QueryParameters queryParameters){
        ArrayList<String> parameters = queryParameters.getParameters();
        this.add(parameters);
    }

    public void setIdentifier(String s, int from, int to){
        if(from <= to && from >= 0 && !this.setIdentifier){
            for(int i = from; from < this.parameters.size() && i < to; ++i){
                String old = this.parameters.get(i);
                if(!old.equals("1 = 1")){
                    this.parameters.set(i, s + "." + old);
                }
            }
            this.setIdentifier = true;
        }
    }

    public int size(){
        return this.parameters.size();
    }

    public String get(int index){
        return this.parameters.get(index);
    }

    public ArrayList<String> getParameters(){
        return (ArrayList<String>) this.parameters.clone();
    }

}
