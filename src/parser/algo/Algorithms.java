package parser.algo;

import parser.ctl.*;
import parser.dot.*;

public class Algorithms {

   
    public static void markAtomic(Formula formula , Automata automata )
    {
            for(State state : automata.getStates()) {

                if(formula instanceof Atomic){ 
                if(!state.getMarkings().containsKey(formula)){
                    if(state.getLabels().contains(((Atomic)formula).getAtomicProposition())){
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
                
                Formula leftFormula = ((And)formula).getLetFormula();
                Formula rightFormula = ((And)formula).getRightFormula();
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
                Formula leftFormula = ((Or)formula).getLetFormula();
                Formula rightFormula = ((Or)formula).getRightFormula();
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
                        System.out.println("FOUND IN  : " +transition.getFrom().getName() + "->" + transition.getTo().getName()  );
                        transition.getTo().getMarkings().put(formula, true);
                        if(((EX) formula).getIsVerified() == null)
                            ((EX) formula).setIsVerified(true);
                    }
                });
                if(((EX) formula).getIsVerified() == null)
                        ((EX) formula).setIsVerified(false);
            }
               
    }
}

