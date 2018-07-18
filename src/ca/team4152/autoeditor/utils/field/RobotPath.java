package ca.team4152.autoeditor.utils.field;

public class RobotPath extends FieldComponent{

    public RobotPath(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }

    @Override
    public boolean intersects(int x, int y) {
        int rise = getY1() - getY0();
        int run = getX1() - getX0();
        double slope = rise / run;

        for(int xi = 0; xi < run; xi++){
            int yi = (int) (xi * slope);

            if(xi >= x - 5 && xi < x + 5 && yi >= y - 5 && yi < y + 5){
                return true;
            }
        }

        return false;
    }

}