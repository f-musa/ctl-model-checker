package parser.ctl;

public class AU extends Formula{
    Formula innerFormula;
    Formula outerFormula;
    Boolean isVerified;
    public Formula getInnerFormula() {
        return innerFormula;
    }
    public AU(Formula innerFormula, Formula outerFormula) {
        this.innerFormula = innerFormula;
        this.outerFormula = outerFormula;
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
}
