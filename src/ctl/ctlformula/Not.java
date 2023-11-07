package ctl.ctlformula;

public class Not implements Formula {
    private  Formula formula;
    private Boolean isVerified;
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

    @Override
    public Boolean getIsVerified() {
        return this.isVerified;
    }
    public void setIsVerified(Boolean value){
        this.isVerified = value;
    }
}