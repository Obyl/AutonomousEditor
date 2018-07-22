package ca.team4152.autoeditor.utils;

import java.util.ArrayList;

public abstract class EditorComponent {

    private static ArrayList<EditorComponent> allComponents = new ArrayList<>();

    private String name;
    private ArrayList<EditorNode> nodes;

    public EditorComponent(String name){
        this.name = name;
        nodes = new ArrayList<>();

        allComponents.add(this);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<EditorNode> getNodes(){
        return nodes;
    }

    public void addNode(EditorNode node){
        nodes.add(node);
        node.setParent(this);
    }

    public void addNode(EditorNode node, EditorNode after){
        int addIndex = 0;

        for(EditorNode n : nodes){
            if(n.equals(after)){
                break;
            }else{
                addIndex++;
            }
        }

        nodes.add(addIndex, node);
        node.setParent(this);
    }

    public void removeNode(EditorNode node){
        nodes.remove(node);
    }

    public EditorNode getNodeAt(int x, int y){
        EditorNode result = null;

        for(EditorNode n : nodes){
            if(n.intersects(x, y)){
                result = n;
            }
        }

        return result;
    }

    public static EditorComponent getComponent(String name){
        EditorComponent result = null;

        for(EditorComponent component : allComponents){
            if(component.getName().equals(name)){
                result = component;
            }
        }

        return result;
    }

}