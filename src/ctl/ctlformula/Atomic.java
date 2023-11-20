package ctl.ctlformula;

public class Atomic implements Formula {
    private  String name;
    private Boolean isVerified;
    public Atomic(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"("+name+")";
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getIsVerified() {
       return this.isVerified;
       
    }
}