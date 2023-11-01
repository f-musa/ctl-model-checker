package parser.ctl;

public class Or extends Formula {
    Formula letFormula;
    Formula rightFormula;
    public Or(Formula letFormula, Formula rightFormula) {
        this.letFormula = letFormula;
        this.rightFormula = rightFormula;
    }
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
}
