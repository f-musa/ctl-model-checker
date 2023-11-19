package ctl.ctlformula;

public class Atomic implements Formula {
    private  String name;

    public Atomic(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"("+name+")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getIsVerified() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIsVerified' in atomic");
    }
}