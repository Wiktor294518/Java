import java.util.ArrayList;
import java.util.List;

public class Object {
    String name;
    ArrayList<Double> attributes = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void addAttribute(double attribute) {
        this.attributes.add(attribute);
    }
}
