package ca.team4152.autoeditor.utils.field;

import java.util.ArrayList;
import java.util.HashMap;

public class Field {
    private static HashMap<String, Field> fields = new HashMap<>();

    private int width;
    private int height;
    private int[] fieldImage;
    private boolean shouldUpdateImage;

    private ArrayList<FieldComponent> components = new ArrayList<>();

    public Field(String name, int width, int height) {
        this.width = width;
        this.height = height;

        this.fieldImage = new int[width * height];
        shouldUpdateImage = true;
        updateImage();

        fields.put(name, this);
    }

    public ArrayList<FieldComponent> getComponents(){
        return components;
    }

    public void addComponent(FieldComponent component){
        components.add(component);
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

        for(FieldComponent component : components){
            if(component instanceof CollisionBox){
                CollisionBox box = (CollisionBox) component;

                for(int y = box.getY0(); y < box.getY1(); y++){
                    for(int x = box.getX0(); x < box.getX1(); x++){
                        if(x < 0 || x >= width || y < 0 || y >= height){
                            continue;
                        }

                        fieldImage[x + y * width] = 0x000000;
                    }
                }
            }else if(component instanceof RobotPath){
                RobotPath path = (RobotPath) component;

                int rise = path.getY1() - path.getY0();
                int run = path.getX1() - path.getX0();
                double slope = rise / run;

                for(int x = 0; x < run; x++){
                    int y = (int) (x * slope);
                    fieldImage[x + y * width] = 0x366AA6;
                }
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