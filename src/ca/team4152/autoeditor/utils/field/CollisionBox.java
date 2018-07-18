package ca.team4152.autoeditor.utils.field;

public class CollisionBox {

    private int x;
    private int y;
    private int width;
    private int height;

    public CollisionBox(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}