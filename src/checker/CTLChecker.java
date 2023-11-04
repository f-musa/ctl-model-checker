package checker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ctl.CTLParser;
import ctl.ctlformula.*;
import dot.*;

public class CTLChecker {

   public static void main(String[] args)  {
    String dotFile = args[0];
    String ctlFile = args[1];
    DotParser dotParser = new DotParser();

    try (BufferedReader br = new BufferedReader(new FileReader(ctlFile))) {
            String line;
            Integer i = 1;
            while ((line = br.readLine()) != null) {
               String sanitizedLine = line.replaceAll(" ", "");
                Formula f = CTLParser.parse(sanitizedLine);
                Automata automata =   dotParser.parseDotFile(dotFile);
                markFormula(f,automata);
                if(f.getIsVerified().booleanValue()==true){
                    System.out.println("Formule "+(i++)+" : Vrai :-)");
                }
                else
                    System.out.println("Formule "+(i++)+" : Fausse :-(");
            }

        }
        catch (Exception e) {
            System.out.println(e);
    }


   }
    public static void markAtomic(Formula formula , Automata automata )
    {
            for(State state : automata.getStates()) {

                if(formula instanceof Atomic){ 
                if(!state.getMarkings().containsKey(formula)){
                    if(state.getLabels().contains(((Atomic)formula).getName())){
                    state.getMarkings().put(formula, true);
                    }
                else
                    state.getMarkings().put(formula, false);
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
                   Boolean value =  state.getMarkings().get(innerFormula);
                   if(!state.getMarkings().containsKey(formula))
                        state.getMarkings().put(formula,!value);
                });
            
            }

            else if(formula instanceof And){
                
                Formula leftFormula = ((And)formula).getLeft();
                Formula rightFormula = ((And)formula).getRight();
                markFormula(leftFormula,automata);                
                markFormula(rightFormula,automata);
                for(State state : automata.getStates()){
                    Boolean leftValue = state.getMarkings().get(leftFormula);
                    Boolean rightValue = state.getMarkings().get(rightFormula);
                    if(!state.getMarkings().containsKey(formula))
                        state.getMarkings().put(formula,leftValue&&rightValue);
                }
        
            }
            else if(formula instanceof Or){
                Formula leftFormula = ((Or)formula).getLeft();
                Formula rightFormula = ((Or)formula).getRight();
                markFormula(leftFormula,automata);                
                markFormula(rightFormula,automata);
               for(State state : automata.getStates()){
                    Boolean leftValue = state.getMarkings().get(leftFormula);
                    Boolean rightValue = state.getMarkings().get(rightFormula);
                    if(!state.getMarkings().containsKey(formula))
                        state.getMarkings().put(formula,leftValue||rightValue);
                }
            }
            else if(formula instanceof EX){
                Formula innerFormula = ((EX) formula).getFormula();
                markFormula(innerFormula, automata);
                automata.getStates().forEach(state->{
                    if(!state.getMarkings().containsKey(formula))
                        state.getMarkings().put(formula,false); 
                });
                automata.getTransitions().forEach(transition->{
                    if(transition.getTo().getMarkings().get(innerFormula).booleanValue()==true){
                        // System.out.println("FOUND IN  : " +transition.getFrom().getName() + "->" + transition.getTo().getName()  );
                        transition.getFrom().getMarkings().put(formula, true);
                        if(((EX) formula).getIsVerified() == null)
                            ((EX) formula).setIsVerified(true);
                    }
                });
                if(((EX) formula).getIsVerified() == null)
                        ((EX) formula).setIsVerified(false);
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
                    if(!state.getMarkings().containsKey(formula))
                        state.getMarkings().put(formula,false); 
                        SeenBeefore.put(state,false);
                });
                L.clear();
                automata.getStates().forEach(state->{
                    // We retrieve all the states q that satisfies the UNTIL(rightFormula) condition 
                   if(state.getMarkings().get(rightFormula).booleanValue()==true){
                    L.add(state);
                   }
                });
                //We go through the list and verify the predecessors of q that verify the left formula.
                while(!L.isEmpty()){
                  State q = L.remove(0);
                  //We mark that q satisfy the main formula
                  q.getMarkings().replace(formula,true);
                  
                  automata.getTransitions().forEach(transition->{
                    State q2 = transition.getFrom();
                    if(transition.getTo().equals(q)){
                        if(SeenBeefore.get(q2).booleanValue() == false){
                        SeenBeefore.replace(q2, true);
                        if(q2.getMarkings().get(leftFormula).booleanValue()==true){
                            L.add(q2);
                        }
                    }
                    }
                    
                });
                }

                //We verify if one the states verify the main formula
                automata.getStates().forEach(state->{
                    if (state.getMarkings().get(formula).booleanValue() == true){
                        // System.out.println("FOUND IN  : " +state.getName() );

                        if(((E) formula).getIsVerified() == null)
                            ((E) formula).setIsVerified(true);

                    }
                });
                //If none of the states verify the formula we set the result to false
                if(((E) formula).getIsVerified() == null)
                    ((E) formula).setIsVerified(false);

            }
               
    }
}

