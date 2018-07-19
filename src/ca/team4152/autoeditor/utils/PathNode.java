package ca.team4152.autoeditor.utils;

public class PathNode extends EditorNode{

    public PathNode(int x, int y) {
        super(x, y, x, y);
    }

    @Override
    public boolean intersects(int x, int y) {
        return x >= getX0() - 3 && x < getX1() + 3 && y >= getY0() - 3 && y < getY1() + 3;
    }
}