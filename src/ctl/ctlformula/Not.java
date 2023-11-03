package ctl.ctlformula;

public class Not implements Formula {
    private  Formula formula;

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public Not(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+ "("+formula.toString()+")";
    }
}