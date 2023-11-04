package ctl.ctlformula;

public class E implements Formula {
    private Formula formula;
    private Boolean isVerified ;

    public E(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "E(" + formula.toString()+")";
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
}