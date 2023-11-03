package ctl.ctlformula;

public class Implication implements Formula {
    private final Formula left;
    private final Formula right;

    public Implication(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Implication(" + left.toString() + " => " + right.toString() + ")";
    }
}