package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by  TETEREOU Aboudourazakou   on  10/21/2023
 * Project name ModelChecker
 */


class  State{
    String name;
    List<String> labels = new ArrayList<>();
    boolean isInitial = false;

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i <labels.size() ; i++) {
             s = s+labels.get(i);
        }
        return  name + " " + s + isInitial;
    }
}

class  Transition{
    State from;
    State to;

    @Override
    public String toString() {
        return from +"->"+to;
    }
}

class  Automata{
    List<State> states = new ArrayList<>();
    List<Transition> transitions = new ArrayList<>();
    State initialState;

    @Override
    public String toString() {
        return  states.toString() + "\n"+transitions.toString() +"\n L etat initial "+initialState;
    }
}

public class DotParser {

    public State  parseState(String line){
        State state = new State();

        //Get state name
        Pattern statePattern = Pattern.compile("(\\w+)\\[");
        Matcher stateNameMatcher = statePattern.matcher(line);
        if(stateNameMatcher.find())state.name = stateNameMatcher.group(1);

        //Get the labels
        Pattern labelsPattern = Pattern.compile("\\[label=\"(.*?)\"\\]");
        Matcher labelsMatcher = labelsPattern.matcher(line);

        if (labelsMatcher.find()) {

            String labelsChain = labelsMatcher.group(1);
            for (int i = 0; i <labelsChain.length() ; i++) {
                 state.labels.add(labelsChain.charAt(i)+"");
            }

        }

        return  state;
    }

   public void parseTransition(String line , Automata automata) {

       List<String> states = new ArrayList<>();
       Pattern pattern = Pattern.compile("\\w+");
       Matcher matcher = pattern.matcher(line);

       while (matcher.find()) {
           states.add(matcher.group());
       }

       for (int i = 0; i < states.size() - 1; i++) {
           Transition transition = new Transition();
           transition.from = getState(states.get(i), automata);
           transition.to = getState(states.get(i + 1), automata);

           if(transition.from != null && transition.to != null){  automata.transitions.add(transition);}

       }



   }

   public  State getState(String name , Automata automata){
        for(State state : automata.states){
            if(state.name.equals(name)){
                return  state;
            }
        }
        return null;
   }

    private void setIntialState(String line, Automata automata) {

        //Get state name
        Pattern statePattern = Pattern.compile("(\\w+)\\[");
        Matcher stateNameMatcher = statePattern.matcher(line);

        if(stateNameMatcher.find()){
            String stateName = stateNameMatcher.group(1);
            State state = getState(stateName,automata);
            state.isInitial = true;
            automata.initialState = state;
        }

    }


    public Automata parseDotFile(String path) throws IOException {
        Automata automata = new Automata();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
               String sanitizedLine = line.replaceAll(" ", "");

                if(sanitizedLine.contains("[label")){
                   automata.states.add(parseState(sanitizedLine));
                }
                else if(sanitizedLine.contains("shape")){
                      setIntialState(sanitizedLine, automata);
                } else if(sanitizedLine.contains("->")){
                    parseTransition(sanitizedLine,automata);
                }
            }

            System.out.println( automata + "My automata");

            return automata;
        }
    }



    public  static  void main(String args []) {

        DotParser c = new DotParser();
        try {
            c.parseDotFile("C:\\Users\\teter\\OneDrive\\Bureau\\ModelChecker\\automata.dot");
        }catch (IOException e){}

    }


}
