package dot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

   public List<State> getPath(State s){
        List<State> Predecessors = new ArrayList<>();
        List<State> temp = new ArrayList<>();
        temp.add(s);
        while(!temp.isEmpty()){
            State current = temp.remove(0);
            this.transitions.forEach((transition) ->{
            if(transition.getTo()==current){
                if(!Predecessors.contains(transition.getFrom()))
                     Predecessors.add(transition.getFrom());
                temp.add(transition.getFrom());
            }
        });
        }
        return Predecessors;
   }
}
