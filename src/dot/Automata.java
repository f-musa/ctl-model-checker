package dot;

import java.util.ArrayList;
import java.util.List;

public class Automata {
 
    List<State> states = new ArrayList<>();
    List<Transition> transitions = new ArrayList<>();
    State initialState;
    
    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }


    public List<Transition> getTransitions() {
        return transitions;
    }


    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }



    public State getInitialState() {
        return initialState;
    }


    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    @Override
    public String toString() {
        this.getStates().forEach(state -> {
            System.out.println(state);
        });
        return  states.toString() + "\n"+transitions.toString() +"\n L etat initial "+initialState;
    }
   
}
