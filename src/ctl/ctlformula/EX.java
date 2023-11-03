package ctl.ctlformula;

public class EX implements Formula {
    private  Formula formula;
    private Boolean isVerified ;

    public EX(Formula formula) {
        this.formula = formula;
    }

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

    @Override
    public String toString() {
        return "EX(" + formula.toString() + ")";
    }
}