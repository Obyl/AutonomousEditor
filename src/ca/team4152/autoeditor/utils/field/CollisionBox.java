package ca.team4152.autoeditor.utils.field;

public class CollisionBox extends FieldComponent{

    public CollisionBox(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    @Override
    public boolean intersects(int x, int y) {
        return x >= getX0() && x < getX1() && y >= getY0() && y < getY1();
    }

}