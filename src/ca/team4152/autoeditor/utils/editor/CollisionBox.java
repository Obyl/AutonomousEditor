package ca.team4152.autoeditor.utils.editor;

public class CollisionBox extends EditorNode{

    private int oldMouseX = -1;
    private int oldMouseY = -1;
    private int xMidpoint;
    private int yMidpoint;
    private boolean draggingCorner;
    private int draggingSide;
    private boolean inCorner;
    private boolean inSide;

    public CollisionBox(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);

        xMidpoint = getX0() + (getX1() - getX0()) / 2;
        yMidpoint = getY0() + (getY1() - getY0()) / 2;
    }

    @Override
    public boolean intersects(int x, int y) {
        if(isSelected()){
            //If it's selected, extend the corners and side midpoints a little bit to make it clear where you need to click.
            inCorner = (x >= getX0() - 3 && x < getX0() + 3 && y >= getY0() - 3 && y < getY0() + 3) ||
                    (x >= getX1() - 3 && x < getX1() + 3 && y >= getY0() - 3 && y < getY0() + 3) ||
                    (x >= getX0() - 3 && x < getX0() + 3 && y >= getY1() - 3 && y < getY1() + 3) ||
                    (x >= getX1() - 3 && x < getX1() + 3 && y >= getY1() - 3 && y < getY1() + 3);
            inSide = (x >= xMidpoint - 3 && x < xMidpoint + 3 && y >= getY0() - 3 && y < getY0() + 3) ||
                    (x >= getX1() - 3 && x < getX1() + 3 && y >= yMidpoint - 3 && y < yMidpoint + 3) ||
                    (x >= xMidpoint - 3 && x < xMidpoint + 3 && y >= getY1() - 3 && y < getY1() + 3) ||
                    (x >= getX0() - 3 && x < getX0() + 3 && y >= yMidpoint - 3 && y < yMidpoint + 3);
        }
        return inCorner || inSide || (x >= getX0() && x < getX1() && y >= getY0() && y < getY1());
    }

    @Override
    public void handleMouseDrag(int x, int y, boolean newDrag) {
        if(newDrag){
            oldMouseX = x;
            oldMouseY = y;
            draggingCorner  = false;
            draggingSide = -1;
        }

        int x0 = getX0();
        int y0 = getY0();
        int x1 = getX1();
        int y1 = getY1();

        if(draggingCorner || inCorner){
            draggingCorner = true;

            if(Math.abs(x0 - x) < Math.abs(x1 - x))
                x0 = x;
            else
                x1 = x;

            if(Math.abs(y0 - y) < Math.abs(y1 - y))
                y0 = y;
            else
                y1 = y;
        }else if(draggingSide >= 0 || inSide){
            if(x >= xMidpoint - 3 && x < xMidpoint + 3 && y >= getY0() - 3 && y < getY0() + 3)
                draggingSide = 0;
            else if(x >= getX1() - 3 && x < getX1() + 3 && y >= yMidpoint - 3 && y < yMidpoint + 3)
                draggingSide = 1;
            else if(x >= xMidpoint - 3 && x < xMidpoint + 3 && y >= getY1() - 3 && y < getY1() + 3)
                draggingSide = 2;
            else if(x >= getX0() - 3 && x < getX0() + 3 && y >= yMidpoint - 3 && y < yMidpoint + 3)
                draggingSide = 3;

            if(draggingSide == 0)
                y0 = y;
            else if(draggingSide == 1)
                x1 = x;
            else if(draggingSide == 2)
                y1 = y;
            else if(draggingSide == 3)
                x0 = x;
        }else{
            int xInc = x - oldMouseX;
            int yInc = y - oldMouseY;

            x0 += xInc;
            y0 += yInc;
            x1 += xInc;
            y1 += yInc;
        }

        setX0(x0);
        setY0(y0);
        setX1(x1);
        setY1(y1);

        oldMouseX = x;
        oldMouseY = y;
        xMidpoint = x0 + (x1 - x0) / 2;
        yMidpoint = y0 + (y1 - y0) / 2;
    }
}