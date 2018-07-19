package ca.team4152.autoeditor.utils;

public class Field extends EditorComponent{

    private int width;
    private int height;

    public Field(String name, int width, int height){
        super(name);

        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

}