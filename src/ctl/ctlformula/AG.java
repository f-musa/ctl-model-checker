package ctl.ctlformula;

public class AG implements Formula {
    private  Formula formula;
    private Boolean isVerified ;

    public AG(Formula formula) {
        this.formula = formula;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "AG(" + formula.toString() + ")";
    }
}