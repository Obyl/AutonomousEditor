package ca.team4152.autoeditor.utils;

public class PathNode extends EditorNode{

    public PathNode(int x, int y) {
        super(x, y, x, y);
    }

    public int getX(){
        return getX0();
    }

    public void setX(int x){
        setX0(x);
        setX1(x);
    }

    public int getY(){
        return getY0();
    }

    public void setY(int y){
        setY0(y);
        setY1(y);
    }

    @Override
    public boolean intersects(int x, int y) {
        //Represent node as a single pixel in code, but draw & interact with it larger.
        return x >= getX0() - 3 && x < getX1() + 3 && y >= getY0() - 3 && y < getY1() + 3;
    }
}