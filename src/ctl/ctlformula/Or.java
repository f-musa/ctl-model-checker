package ctl.ctlformula;

public class Or implements Formula {
    private  Formula left;
    private  Formula right;


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

  
    public Or(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +"("+left.toString()+","+ right.toString()+")";
    }

    @Override
    public Boolean getIsVerified() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIsVerified'");
    }
}