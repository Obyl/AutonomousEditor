package ca.team4152.autoeditor.utils;

public class CollisionBox extends EditorNode{

    public CollisionBox(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }

    @Override
    public boolean intersects(int x, int y) {
        if(x >= getX0() - 3 && x < getX0() + 3 && y >= getY0() - 3 && y < getY0() + 3){
            return true;
        }else if(x >= getX1() - 3 && x < getX1() + 3 && y >= getY0() - 3 && y < getY0() + 3){
            return true;
        }else if(x >= getX0() - 3 && x < getX0() + 3 && y >= getY1() - 3 && y < getY1() + 3){
            return true;
        }else if(x >= getX1() - 3 && x < getX1() + 3 && y >= getY1() - 3 && y < getY1() + 3){
            return true;
        }
        return false;
    }
}