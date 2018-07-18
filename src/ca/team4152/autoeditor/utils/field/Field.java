package ca.team4152.autoeditor.utils.field;

import ca.team4152.autoeditor.Editor;

import java.util.ArrayList;
import java.util.HashMap;

public class Field {
    private static HashMap<String, Field> fields = new HashMap<>();

    private int width;
    private int height;
    private int[] fieldImage;
    private boolean shouldUpdateImage;

    private Editor editor;
    private ArrayList<FieldComponent> components = new ArrayList<>();

    public Field(String name, int width, int height, Editor editor) {
        this.width = width;
        this.height = height;
        this.editor = editor;

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

                        if(box.isSelected()){
                            fieldImage[x + y * width] = 0x505050;
                        }else if(box.isHovered()){
                            fieldImage[x + y * width] = 0x7A7A7A;
                        }else{
                            fieldImage[x + y * width] = 0x000000;
                        }
                    }
                }
            }else if(component instanceof RobotPath){
                RobotPath path = (RobotPath) component;

                double rise = path.getY1() - path.getY0();
                double run = path.getX1() - path.getX0();
                double slope = rise / run;

                for(int x = path.getX0(); x < path.getX1(); x++){
                    int y = (int) (x * slope);

                    for(int p = 0; p < 9; p++){
                        int xi = x + (p % 3);
                        int yi = y + (p / 3);

                        if(path.isSelected()){
                            fieldImage[xi + yi * width] = 0x2F5D91;
                        }else if(path.isHovered()){
                            fieldImage[xi + yi * width] = 0x4D96EA;
                        }else{
                            fieldImage[xi + yi * width] = 0x366AA6;
                        }
                    }
                }
            }
        }

        shouldUpdateImage = false;
    }

    public FieldComponent getComponentAt(int x, int y){
        FieldComponent result = null;

        for(FieldComponent component : components){
            if(component.intersects((int) ((x - editor.getRenderer().getxScroll()) / editor.getRenderer().getScale()),
                    (int) ((y - editor.getRenderer().getyScroll()) / editor.getRenderer().getScale()))){
                result = component;
            }
        }

        return result;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getPixelAt(int x, int y){
        return fieldImage[x + y * width];
    }

    public static Field getField(String name){
        if(!fields.containsKey(name)){
            return null;
        }

        return fields.get(name);
    }
}