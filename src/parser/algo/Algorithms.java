package parser.algo;

import parser.ctl.*;
import parser.dot.*;

public class Algorithms {

    public void markAllStates (Formula formula,Automata automata,Boolean value)
    {
        automata.getStates().forEach(state ->{
            state.getMarkings().put(formula, value);
        });
    }
    public void markAtomic(Formula formula , Automata automata )
    {
        automata.getStates().forEach(state -> {
            if(formula instanceof Atomic){ 
                if(state.getLabels().contains(((Atomic)formula).getAtomicProposition())){
                    state.getMarkings().put(formula, true);
                }
                else
                    state.getMarkings().put(formula, false);
            }   
        })
        ;
    }
    public void markFormula(Formula formula, Automata automata){
       
        
        automata.getStates().forEach(state ->{
            if(formula instanceof Atomic){
                markAtomic(formula, automata);
            }
        
            else if(formula instanceof Not){
                Formula innerFormula = ((Not)formula).getFormula();

                
                if(!(state.getMarkings().get(innerFormula)))
                    markFormula(innerFormula,automata);
                
                Boolean value = state.getMarkings().get(innerFormula);
                markAllStates(formula, automata, !value);
            }
            else if(formula instanceof And){
                
                Formula leftFormula = ((And)formula).getLetFormula();
                Formula rightFormula = ((And)formula).getRightFormula();

                if(!(state.getMarkings().get(leftFormula)))
                    markFormula(leftFormula,automata);
                
                if(!(state.getMarkings().get(rightFormula)))
                    markFormula(rightFormula,automata);
                
                Boolean leftValue = state.getMarkings().get(leftFormula);
                Boolean rightValue = state.getMarkings().get(rightFormula);
                markAllStates(formula, automata, leftValue && rightValue);
            }
            else if(formula instanceof Or){
                
                Formula leftFormula = ((Or)formula).getLetFormula();
                Formula rightFormula = ((Or)formula).getRightFormula();

                if(!(state.getMarkings().get(leftFormula)))
                    markFormula(leftFormula,automata);
                
                if(!(state.getMarkings().get(rightFormula)))
                    markFormula(rightFormula,automata);
                
                Boolean leftValue = state.getMarkings().get(leftFormula);
                Boolean rightValue = state.getMarkings().get(rightFormula);
                markAllStates(formula, automata, leftValue || rightValue);
            }
            else if(formula instanceof EX){
                Formula inneFormula = ((EX) formula).getFormula();
                
            }
               
        });
    }
}

