package ca.team4152.autoeditor.utils.editor;

public class CollisionBox extends EditorNode{

    private int oldMouseX = -1;
    private int oldMouseY = -1;
    private int xMidpoint;
    private int yMidpoint;
    private boolean changeX0;
    private boolean changeY0;
    private boolean changeX1;
    private boolean changeY1;

    public CollisionBox(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);

        updateMidpoints();
    }

    @Override
    public boolean intersects(int x, int y) {
        //If it's selected, extend the points where you can grab.
        boolean extend = isSelected() && (
                (x >= getX0() - 3 && x < getX0() + 3 && y >= getY0() - 3 && y < getY0() + 3) ||
                (x >= getX1() - 3 && x < getX1() + 3 && y >= getY0() - 3 && y < getY0() + 3) ||
                (x >= getX0() - 3 && x < getX0() + 3 && y >= getY1() - 3 && y < getY1() + 3) ||
                (x >= getX1() - 3 && x < getX1() + 3 && y >= getY1() - 3 && y < getY1() + 3) ||
                (x >= xMidpoint - 3 && x < xMidpoint + 3 && y >= getY0() - 3 && y < getY0() + 3) ||
                (x >= getX1() - 3 && x < getX1() + 3 && y >= yMidpoint - 3 && y < yMidpoint + 3) ||
                (x >= xMidpoint - 3 && x < xMidpoint + 3 && y >= getY1() - 3 && y < getY1() + 3) ||
                (x >= getX0() - 3 && x < getX0() + 3 && y >= yMidpoint - 3 && y < yMidpoint + 3)
                );
        return extend || (x >= getX0() && x < getX1() && y >= getY0() && y < getY1());
    }

    @Override
    public void handleMouseDrag(int x, int y, boolean newDrag) {
        if(newDrag){
            oldMouseX = x;
            oldMouseY = y;

            //Check which values we should change depending on where you grabbed.
            //Only do this when you start a new drag so that the points don't mess
            //with each other when they overlap.
            changeX0 = x >= getX0() - 3 && x < getX0() + 3;
            changeX1 = x >= getX1() - 3 && x < getX1() + 3;
            changeY0 = y >= getY0() - 3 && y < getY0() + 3;
            changeY1 = y >= getY1() - 3 && y < getY1() + 3;
        }

        //Store current values.
        int x0 = getX0();
        int y0 = getY0();
        int x1 = getX1();
        int y1 = getY1();

        //Flip minimum and maximum x values if necessary.
        //(always keep x0 < x1)
        if(x1 < x0) {
            int mid = x1;
            x1 = x0;
            x0 = mid;

            changeX0 = !changeX0;
            changeX1 = !changeX1;
        }
        //Flip minimum and maximum y values if necessary.
        //(always keep y0 < y1)
        if(y1 < y0) {
            int mid = y1;
            y1 = y0;
            y0 = mid;

            changeY0 = !changeY0;
            changeY1 = !changeY1;
        }

        //Change values as necessary.
        if(changeX0 || changeY0 || changeX1 || changeY1){
            x0 = changeX0 ? x : x0;
            y0 = changeY0 ? y : y0;
            x1 = changeX1 ? x : x1;
            y1 = changeY1 ? y : y1;
        }else{
            int xInc = x - oldMouseX;
            int yInc = y - oldMouseY;

            x0 += xInc;
            y0 += yInc;
            x1 += xInc;
            y1 += yInc;
        }

        //Apply changed values.
        setX0(x0);
        setY0(y0);
        setX1(x1);
        setY1(y1);

        //Update the old mouse position and midpoints of box.
        oldMouseX = x;
        oldMouseY = y;
        updateMidpoints();
    }

    public void updateMidpoints(){
        xMidpoint = getX0() + (getX1() - getX0()) / 2;
        yMidpoint = getY0() + (getY1() - getY0()) / 2;
    }
}