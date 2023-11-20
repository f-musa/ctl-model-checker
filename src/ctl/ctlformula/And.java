package ctl.ctlformula;

public class And implements Formula {
    private  Formula left;
    private  Formula right;
    private Boolean isVerified;
    
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Formula getLeft() {
        return left;
    }

    public void setLeft(Formula left) {
        this.left = left;
    }

    public Formula getRight() {
        return right;
    }

    public void setRight(Formula right) {
        this.right = right;
    }

    public And(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +"("+left.toString()+","+ right.toString()+")";
    }

    @Override
    public Boolean getIsVerified() {
        return this.isVerified;
    }
}