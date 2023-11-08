package ctl.ctlformula;

public class Always implements Formula {
    private  Formula formula;
    private Boolean isVerified;
    public Always(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "A(" + formula.toString()+")";
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public Boolean getIsVerified() {
        return this.isVerified;
    }
    public void setIsVerified(Boolean value){
        this.isVerified = value;
    }
}