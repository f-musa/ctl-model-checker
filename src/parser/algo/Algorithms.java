package parser.algo;

import parser.ctl.*;
import parser.dot.*;

public class Algorithms {

   
    public void markAtomic(Formula formula , Automata automata )
    {
        automata.getStates().forEach(state -> {
            if(formula instanceof Atomic){ 
                if(!state.getMarkings().get(formula)){
                    if(state.getLabels().contains(((Atomic)formula).getAtomicProposition())){
                    state.getMarkings().put(formula, true);
                    }
                else
                    state.getMarkings().put(formula, false);
                }   
                
        }
        
        })
        ;
    }
    public void markFormula(Formula formula, Automata automata){
       

        
            if(formula instanceof Atomic){
                markAtomic(formula, automata);
            }

            else if(formula instanceof Not){
                Formula innerFormula = ((Not)formula).getFormula();
                markFormula(innerFormula, automata);
                automata.getStates().forEach(state->{
                   Boolean value =  state.getMarkings().get(innerFormula);
                   if(!state.getMarkings().get(formula))
                        state.getMarkings().put(formula,!value);
                });
            
            }

            else if(formula instanceof And){
                
                Formula leftFormula = ((And)formula).getLetFormula();
                Formula rightFormula = ((And)formula).getRightFormula();
                markFormula(leftFormula,automata);                
                markFormula(rightFormula,automata);
                automata.getStates().forEach(state->{
                    Boolean leftValue = state.getMarkings().get(leftFormula);
                    Boolean rightValue = state.getMarkings().get(rightFormula);
                    if(!state.getMarkings().get(formula))
                        state.getMarkings().put(formula,leftValue&&rightValue); 
                });
            }
            else if(formula instanceof Or){
                Formula leftFormula = ((Or)formula).getLetFormula();
                Formula rightFormula = ((Or)formula).getRightFormula();
                markFormula(leftFormula,automata);                
                markFormula(rightFormula,automata);
                automata.getStates().forEach(state->{
                    Boolean leftValue = state.getMarkings().get(leftFormula);
                    Boolean rightValue = state.getMarkings().get(rightFormula);
                    if(!state.getMarkings().get(formula))
                        state.getMarkings().put(formula,leftValue||rightValue); 
                });
            }
            else if(formula instanceof EX){
                Formula innerFormula = ((EX) formula).getFormula();
                markFormula(innerFormula, automata);
                automata.getStates().forEach(state->{
                    state.getMarkings().put(formula,false); 
                });
                automata.getTransitions().forEach(transition->{
                    if(transition.getFrom().getMarkings().get(innerFormula)==true){
                        transition.getTo().getMarkings().put(formula, true);
                        ((EX) formula).setIsVerified(true);
                    }
                });
            }
               
    }
}

