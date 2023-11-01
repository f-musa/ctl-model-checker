package parser.ctl;

public class And extends Formula{
    Formula letFormula;
    Formula rightFormula;

    public Formula getLetFormula() {
        return letFormula;
    }

    public void setLetFormula(Formula letFormula) {
        this.letFormula = letFormula;
    }

    public Formula getRightFormula() {
        return rightFormula;
    }

    public void setRightFormula(Formula rightFormula) {
        this.rightFormula = rightFormula;
    }

    public And(Formula letFormula, Formula rightFormula) {
        this.letFormula = letFormula;
        this.rightFormula = rightFormula;
    }
    
}
