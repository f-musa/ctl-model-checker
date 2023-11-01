package parser.ctl;

public class EX extends Formula{
    Formula formula;
    Boolean isVerified;
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
}
