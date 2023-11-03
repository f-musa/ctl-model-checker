package dot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ctl.ctlformula.Formula;

public class  State{
    String name;

    HashMap<Formula,Boolean>  markings = new HashMap<>(); 
    List<String> labels = new ArrayList<>();
    boolean isInitial = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Formula, Boolean> getMarkings() {
        return markings;
    }

    public void setMarkings(HashMap<Formula, Boolean> markings) {
        this.markings = markings;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean isInitial) {
        this.isInitial = isInitial;
    }

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i <labels.size() ; i++) {
             s = s+labels.get(i);
        }
        return  name + " " + s + isInitial;
    }
}