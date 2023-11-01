package parser.ctl;

public class Atomic extends Formula {
    String atomicProposition;

    public Atomic(String atomicProposition) {
        this.atomicProposition = atomicProposition;
    }

    public String getAtomicProposition() {
        return atomicProposition;
    }

    public void setAtomicProposition(String atomicProposition) {
        this.atomicProposition = atomicProposition;
    }
    
}
