package ca.team4152.autoeditor.utils.editor;

import java.util.ArrayList;

public abstract class EditorComponent {

    private String name;
    private ArrayList<EditorNode> nodes = new ArrayList<>();

    public EditorComponent(String name){
        this.name = name;
    }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public ArrayList<EditorNode> getNodes(){
        return nodes;
    }

    public void addNode(EditorNode node){
        nodes.add(node);
    }

    public void addNode(EditorNode node, EditorNode after){
        int addIndex = 0;

        for(EditorNode n : nodes){
            if(n.equals(after))
                break;
            else
                addIndex++;
        }

        nodes.add(addIndex, node);
    }

    public void removeNode(EditorNode node){
        nodes.remove(node);
    }

    public EditorNode getNodeAt(int x, int y){
        EditorNode result = null;

        for(EditorNode n : nodes){
            if(n.intersects(x, y))
                result = n;
        }

        return result;
    }
}