package dot;

import java.util.ArrayList;
import java.util.List;


public class Automata implements Cloneable{

    List<State> states = new ArrayList<>();
    List<Transition> transitions = new ArrayList<>();
    State initialState;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public List<State> getStates() {
        return states;
    }

    public State getRoot() {
        for (State state : this.getStates()) {
            if (state.isInitial)
                return state;
        }
        return null;
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
        return states.toString() + "\n" + transitions.toString() + "\n L etat initial " + initialState;
    }

    /**
     * Function that return the (ascending) path that leads to a given node
     * 
     * @param s
     * @return
     */
    public List<State> getPathTo(State s) {
        List<State> Predecessors = new ArrayList<>();
        List<State> temp = new ArrayList<>();
        temp.add(s);
        while (!temp.isEmpty()) {
            State current = temp.remove(0);
            this.transitions.forEach((transition) -> {
                if (transition.getTo() == current) {
                    if (!Predecessors.contains(transition.getFrom()) && !transition.getFrom().equals(s)) {
                        Predecessors.add(transition.getFrom());
                        temp.add(transition.getFrom());
                    }

                }
            });
        }
        return Predecessors;
    }

    /**
     * Function that return the direct successors of a given node (if there is a loop, it is omitted from the successors list)
     * 
     * @param s
     * @return
     */
    public List<State> getSuccessors(State s) {
        List<State> Successors = new ArrayList<>();
        this.getTransitions().forEach(t -> {
            if (t.getFrom().equals(s)) {
                if(!t.getTo().equals(s))
                    Successors.add(t.getTo());
            }
        });
        return Successors;
    }
    /**
     * Utilty function of getAllPathFromRoot that use a DFS traversal to get all paths
     * 
     * 
     * @param currentState
     * @param VisitedNodes
     * @param currentPath
     * @param Paths
     */
    public void getAllPathFromRootUtility(State currentState, List<State> VisitedStates, Path currentPath, List<State> currentPathNodes,
            List<Path> Paths) {
                VisitedStates.add(currentState);
                currentPath.addToPath(currentState);
                currentPathNodes.add(currentState);

                
                for (State succ : getSuccessors(currentState)) {
                    if (!VisitedStates.contains(succ) && !currentPathNodes.contains(succ) ) {
                        getAllPathFromRootUtility(succ, VisitedStates, currentPath,currentPathNodes, Paths);
                    }
                }
            
                // Check if the current state is a leaf node (no unvisited successors) 
                if (getSuccessors(currentState).isEmpty() ) {
                    Paths.add(new Path(currentPath));  // Add a copy of the current path
                }
                currentPathNodes.remove(currentState);
                VisitedStates.remove(currentState);
                currentPath.pop();
    }

    /**
     * Function that return all existing paths deriving from the root node
     * 
     * @return a list of path
     */
    public List<Path> getAllPathFromRoot() {
        List<Path> Paths = new ArrayList<>();
        Path currentPath = new Path();
        
        List<State> VisitedStates = new ArrayList<>();
        List<State> currentPathNodes = new ArrayList<>();
        State root = this.getRoot();
        try {
                this.getAllPathFromRootUtility(root, VisitedStates, currentPath, currentPathNodes, Paths);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return Paths;
    }


}
