package dot;
import java.util.ArrayList;
import java.util.List;

public class Path {
    ArrayList<State> p;

    public ArrayList<State> getPath() {
        return p;
    }

    public Path() {
        this.p = new ArrayList<>();
    }
    public Path(Path path) {
        this.p = new ArrayList<>(path.getPath());;
    }
    public State getLast(){
        if(!this.getPath().isEmpty())
            return this.getPath().get(this.getPath().size() - 1);
        return null;
    }
    
    
    public void setPath(ArrayList<State> p) {
        this.p = p;
    }
    
    public void addAllToPath(List<State> l){
        this.getPath().addAll(l);
    }
    public void addToPath(State s ){
        this.getPath().add(s);
    }
    public void pop(){
        if (!this.getPath().isEmpty()) {
            this.getPath().remove(this.getPath().size() - 1);
        }
    }
    public void printPath(){
        System.out.print("Path: ");
        for (State s : this.getPath()) {
            System.out.print(s.getName() + " ");
        }
        System.out.println();
    }
   
}
