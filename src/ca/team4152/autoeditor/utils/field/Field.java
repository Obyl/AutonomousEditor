package ca.team4152.autoeditor.utils.field;

import java.util.ArrayList;
import java.util.HashMap;

public class Field {
    private static HashMap<String, Field> fields = new HashMap<>();

    private int width;
    private int height;
    private int[] fieldImage;
    private boolean shouldUpdateImage;

    private ArrayList<CollisionBox> collisionBoxes;
    private ArrayList<RobotPath> robotPaths;

    public Field(String name, int width, int height) {
        this.width = width;
        this.height = height;

        this.fieldImage = new int[width * height];
        shouldUpdateImage = true;
        updateImage();

        fields.put(name, this);
    }

    public ArrayList<CollisionBox> getCollisionBoxes(){
        return collisionBoxes;
    }

    public void addCollisionBox(CollisionBox box){
        collisionBoxes.add(box);
        shouldUpdateImage = true;
    }

    public ArrayList<RobotPath> getRobotPaths(){
        return robotPaths;
    }

    public void addRobotPath(RobotPath path){
        robotPaths.add(path);
        shouldUpdateImage = true;
    }

    public void setShouldUpdateImage(boolean shouldUpdateImage){
        this.shouldUpdateImage = shouldUpdateImage;
    }

    public void updateImage(){
        if(!shouldUpdateImage){
            return;
        }

        for(int i = 0; i < fieldImage.length; i++){
            fieldImage[i] = 0xFFFFFF;
        }

        for(CollisionBox box : collisionBoxes){
            for(int y = box.getY(); y < (box.getY() + box.getHeight()); y++){
                for(int x = box.getX(); x < (box.getX() + box.getWidth()); x++){
                    if(x < 0 || x >= width || y < 0 || y >= height){
                        continue;
                    }

                    fieldImage[x + y * width] = 0x000000;
                }
            }
        }

        for(RobotPath path : robotPaths){
            int rise = path.getY1() - path.getY0();
            int run = path.getX1() - path.getX0();
            double slope = rise / run;

            for(int x = 0; x < run; x++){
                int y = (int) (x * slope);
                fieldImage[x + y * width] = 0x366AA6;
            }
        }

        shouldUpdateImage = false;
    }

    public static Field getField(String name){
        if(!fields.containsKey(name)){
            return null;
        }

        return fields.get(name);
    }
}