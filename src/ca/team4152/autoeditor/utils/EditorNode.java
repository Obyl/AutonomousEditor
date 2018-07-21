package ca.team4152.autoeditor.utils;

import java.util.ArrayList;

public abstract class EditorNode {

    private static ArrayList<EditorNode> allNodes = new ArrayList<>();
    private static int nextId = 0;
    private int id;

    private int x0;
    private int y0;
    private int x1;
    private int y1;

    private boolean hovered;
    private boolean selected;
    private boolean anchored;

    public EditorNode(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;

        id = nextId++;
        allNodes.add(this);
    }

    public int getId(){
        return id;
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0){
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0){
        this.y0 = y0;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1){
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1){
        this.y1 = y1;
    }

    public boolean isHovered(){
        return hovered;
    }

    public void setHovered(boolean hovered){
        this.hovered = hovered;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isAnchored(){
        return anchored;
    }

    public EditorNode setAnchored(boolean anchored){
        this.anchored = anchored;
        return this;
    }

    public abstract boolean intersects(int x, int y);

    public abstract void handleMouseDrag(int x, int y, boolean newDrag);

    public static EditorNode getNode(int id){
        EditorNode result = null;

        for(EditorNode node : allNodes){
            if(node.getId() == id){
                result = node;
                break;
            }
        }

        return result;
    }

    public static void removeNode(int id){
        int index = 0;

        for(EditorNode node : allNodes){
            if(node.getId() == id){
                allNodes.remove(index);
                return;
            }
            index++;
        }
    }
}