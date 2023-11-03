package ctl.ctlformula;

public class EG implements Formula {
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

    public EG(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "EG(" + formula.toString() + ")";
    }
}