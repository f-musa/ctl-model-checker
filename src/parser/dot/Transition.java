package parser.dot;

public class  Transition{
    State from;
    State to;

    public State getFrom() {
        return from;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public State getTo() {
        return to;
    }

    public void setTo(State to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return from +"->"+to;
    }
}
  