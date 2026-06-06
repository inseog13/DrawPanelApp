import java.util.ArrayList;

public class DrawAction {

    private ArrayList<MyShape> shapes =
            new ArrayList<>();

    public void addShape(MyShape shape) {

        shapes.add(shape);
    }

    public ArrayList<MyShape> getShapes() {

        return shapes;
    }
}