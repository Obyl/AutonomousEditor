package ca.team4152.autoeditor.utils.editor;

public class PathNode extends EditorNode{

    public PathNode(int x, int y) {
        super(x, y, x, y);
    }

    @Override
    public boolean intersects(int x, int y) {
        //Represent node as a single pixel in code, but draw & interact with it larger.
        return x >= getX0() - 3 && x < getX1() + 3 && y >= getY0() - 3 && y < getY1() + 3;
    }

    @Override
    public void handleMouseDrag(int x, int y, boolean newDrag) {
        setX0(x);
        setX1(x);
        setY0(y);
        setY1(y);
    }
}