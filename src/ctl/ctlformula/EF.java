package ctl.ctlformula;

public class EF implements Formula {
    private  Formula formula;
    private Boolean isVerified ;

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public EF(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "EF(" + formula.toString() + ")";
    }
}