import java.util.ArrayList;


public class Object {
    String name;
    ArrayList<Double> atr= new ArrayList<Double>();
    public Object(){
    }
    public void addAtr(double a){
        atr.add(a);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getName() {
        System.out.print(name);
    }

    public void Printatr(){
        for (double e: atr) {
            System.out.print(e + " ");
        }
    }
}
