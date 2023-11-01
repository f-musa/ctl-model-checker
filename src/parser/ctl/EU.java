package parser.ctl;

public class EU extends Formula {
    Formula innerFormula;
    public Formula getInnerFormula() {
        return innerFormula;
    }
    public void setInnerFormula(Formula innerFormula) {
        this.innerFormula = innerFormula;
    }
    public Formula getOuterFormula() {
        return outerFormula;
    }
    public void setOuterFormula(Formula outerFormula) {
        this.outerFormula = outerFormula;
    }
    public Boolean getIsVerified() {
        return isVerified;
    }
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
    Formula outerFormula;
    Boolean isVerified;
}
