package parser.ctl;

public class Not extends Formula {
    Formula formula;

    public Not(Formula p) {
        this.formula = p;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula p) {
        this.formula = p;
    }
}
