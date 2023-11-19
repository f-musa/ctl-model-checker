package checker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ctl.CTLParser;
import ctl.ctlformula.*;
import dot.*;

public class CTLChecker {

   public static void main(String[] args)  {
    String dotFile = "C:\\Users\\teter\\OneDrive\\Bureau\\ModelChecker\\ctl-model-checker\\example.dot";
    String ctlFile = "C:\\Users\\teter\\OneDrive\\Bureau\\ModelChecker\\ctl-model-checker\\ctlexample.txt";;
    DotParser dotParser = new DotParser();

    try (BufferedReader br = new BufferedReader(new FileReader(ctlFile))) {
            String line;
            Integer i = 1;
            while ((line = br.readLine()) != null) {
                String sanitizedLine = line.replaceAll(" ", "");
                Formula f = CTLParser.parse(sanitizedLine);
                System.out.println(f);
               Automata automata =   dotParser.parseDotFile(dotFile);
                markFormula(f,automata);
                if(f.getIsVerified().booleanValue()==true){

                    System.out.printf("%s Formule "+(i++)+ " : Vrai  :-) .%s%n", "\u001B[32m", "\u001B[0m");
                }
                else
                    System.out.printf("%s Formule "+(i++)+ " : Faux  :-( .%s%n", "\u001B[31m", "\u001B[31m");


            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
    }


   }
    public static void markAtomic(Formula formula , Automata automata )
    {
            for(State state : automata.getStates()) {

                if(formula instanceof Atomic){ 
                if(!state.getMarkings().containsKey(formula)){
                    if(state.getLabels().contains(((Atomic)formula).getName())){
                    state.setMarkage(formula, true);
                    }
                else
                    state.setMarkage(formula, false);
                }       
            }                  
        }
        ;
    }
    public static void markFormula(Formula formula, Automata automata){
       
        
            if(formula instanceof Atomic){
                markAtomic(formula, automata);
            }


            else if(formula instanceof Not){
                Formula innerFormula = ((Not)formula).getFormula();
                markFormula(innerFormula, automata);
                automata.getStates().forEach(state->{
                   Boolean value =  state.getMarkage(innerFormula);
                   state.setMarkage(formula,!value);
                });
                //ISVERIFIED TO HANDLE
                if(! (innerFormula instanceof Atomic))
                   ((Not)formula).setIsVerified(!innerFormula.getIsVerified());

            }

            else if(formula instanceof And){
                
                Formula leftFormula = ((And)formula).getLeft();
                Formula rightFormula = ((And)formula).getRight();
                markFormula(leftFormula,automata);                
                markFormula(rightFormula,automata);
                for(State state : automata.getStates()){
                    Boolean leftValue = state.getMarkage(leftFormula);
                    Boolean rightValue = state.getMarkage(rightFormula);
                    state.setMarkage(formula,leftValue&&rightValue);
                }
        
            }
            else if(formula instanceof Or){
                Formula leftFormula = ((Or)formula).getLeft();
                Formula rightFormula = ((Or)formula).getRight();
                markFormula(leftFormula,automata);                
                markFormula(rightFormula,automata);
               for(State state : automata.getStates()){
                    Boolean leftValue = state.getMarkage(leftFormula);
                    Boolean rightValue = state.getMarkage(rightFormula);
                    state.setMarkage(formula,leftValue||rightValue);
                }
            }
            else if(formula instanceof EX){
                ((EX) formula).setIsVerified(false);
                Formula innerFormula = ((EX) formula).getFormula();
                markFormula(innerFormula, automata);
                automata.getStates().forEach(state->{
                    state.setMarkage(formula,false); 
                });
                automata.getTransitions().forEach(transition->{
                    if(transition.getTo().getMarkage(innerFormula)==true){
                        transition.getFrom().setMarkage(formula, true);
                        ((EX) formula).setIsVerified(true);
                    }
                });
                
            }
            else if(formula instanceof E){
                Formula innerFormula = ((E) formula).getFormula();
                Formula leftFormula = ((Until) innerFormula).getLeft();
                Formula rightFormula = ((Until) innerFormula).getRight();
                HashMap<State, Boolean> SeenBeefore = new HashMap<>();
                List<State> L = new ArrayList<>();
                //We mark all the states that verifies the left and right formulas
                markFormula(leftFormula, automata);
                markFormula(rightFormula, automata);
                //We mark the mainFormula as false on all the states 
                automata.getStates().forEach(state->{
                    state.setMarkage(formula,false); 
                    SeenBeefore.put(state,false);
                });
               
                automata.getStates().forEach(state->{
                    // We retrieve all the states q that satisfies the UNTIL(rightFormula) condition 
                   if(state.getMarkage(rightFormula)==true){
                    L.add(state);
                   }
                });
                //We go through the list and verify the predecessors of q that verify the left formula.
                while(!L.isEmpty()){
                  State q = L.remove(0);
                  //We mark that q satisfy the main formula
                  q.setMarkage(formula,true);
                  
                  automata.getTransitions().forEach(transition->{
                    State q2 = transition.getFrom();
                    if(transition.getTo().equals(q)){
                        if(SeenBeefore.get(q2) == false){
                            SeenBeefore.put(q2, true);
                            if(q2.getMarkage(leftFormula)==true){
                                L.add(q2);
                            }
                        }
                    }
                    
                });
                }
                ((E)formula).setIsVerified(false);
                //We check if for every state that satisfies the right formula if all his predecessors satifies the main formula
                automata.getStates().forEach(state->{
                    if(state.getMarkage(rightFormula)==true){
                        List<State> Predecessors = automata.getPathTo(state);
                        if(!Predecessors.isEmpty()){
                            Boolean result = Predecessors.removeIf( 
                                pred-> pred.getMarkage(formula)==false
                            );
                            if(result==false)
                                ((E)formula).setIsVerified(true);
                        }
                    }
                });
                
            }
            else if(formula instanceof Always){
                Formula innerFormula = ((Always) formula).getFormula();
                Formula leftFormula = ((Until) innerFormula).getLeft();
                Formula rightFormula = ((Until) innerFormula).getRight();
                HashMap<State, Integer> degree = new HashMap<>();
                List<State> L = new ArrayList<>();
                //We mark all the states that verifies the left and right formulas
                markFormula(leftFormula, automata);
                markFormula(rightFormula, automata);
                
                //We calculate the degree of each state and asses the main formula as false on all states
                automata.getStates().forEach(state->{
                    state.setMarkage(formula,false);
                    //Increment the degree
                    automata.getTransitions().forEach(transition->{
                    if(transition.getFrom().equals(state) ){
                        Integer stateDegree = degree.getOrDefault(state,0) + 1;
                        degree.put(state,stateDegree);
                    }
                    }); 
                });
                // We retrieve all the states q that satisfies the UNTIL(rightFormula) condition 
                automata.getStates().forEach(state->{
                   if(state.getMarkage(rightFormula)==true){
                    L.add(state);
                   }
                });
                
                 //We go through the list and verify the predecessors of q that verify the left formula.
                while(!L.isEmpty()){
                     State q = L.remove(0);
                    //We mark that q satisfy the main formula
                    q.setMarkage(formula,true);

                    automata.getTransitions().forEach(transition->{
                        if(transition.getTo().equals(q) ){
                            State q2 = transition.getFrom();
                            Integer q2Degree = degree.get(q2);
                            if(q2Degree<=1)
                                degree.replace(q2,0);
                            else
                                degree.replace(q2,q2Degree-1);
                            if(
                                degree.get(q2) == 0 
                                && q2.getMarkage(leftFormula) == true
                                &&  q2.getMarkage(formula) == false
                            ){
                                L.add(q2);
                            }
                        }
                    }); 
                }
                // VERIFICATION
                ((Always)formula).setIsVerified(checkAU(leftFormula,rightFormula,formula,automata));             
                
            }
    }
    public static Boolean checkAU (Formula leftFormula, Formula rightFormula,Formula formula,Automata automata){
        List<Path> AllPaths = automata.getAllPathFromRoot();
        List<Boolean>PathsVerification = new ArrayList<>();

        for(Path p : AllPaths){
            ArrayList<State> path = p.getPath();
            //if the path is empty the main formula is false for this path 
            if(path.isEmpty())
                PathsVerification.add(false);
            //else we will check the whole path
            else {
                Boolean leftFormulaWasVerified = false;
                State current ; 

                while((current = path.remove(0))!=null){
                    // if the node satisfies the left formula but the not right formula we take a step to check for the "until" 
                    if(current.getMarkage(leftFormula)== true && current.getMarkage(rightFormula)== false ){
                        leftFormulaWasVerified= true;
                    }
                    // if both the left and the right formulas are false, the main formula is false for this path  
                    else if(current.getMarkage(leftFormula)== false && current.getMarkage(rightFormula)== false){
                        PathsVerification.add(false);
                        break;
                    }
                    // if the right formula is verified (considering the left formula was verified for all preceding states), then the main formula is verified
                    else if((current.getMarkage(rightFormula)==true && leftFormulaWasVerified) || (current.getMarkage(rightFormula)==true && current.getMarkage(leftFormula)==true)){
                        PathsVerification.add(true);
                        break;
                    }
                    /*if the right formula is verified but the left formula was not verified on the precedent state (or the root node verifies the right formula
                      but not the right formula), the main formula is not verified for this path*/
                    else if(current.getMarkage(rightFormula)==true && !leftFormulaWasVerified){
                        PathsVerification.add(false);
                        break;
                    }
                }
            }

        }
        //the main formula is true only if all path satisfies it 
        if(PathsVerification.contains(false))
            return false;
        else
            return true;
    }
}

