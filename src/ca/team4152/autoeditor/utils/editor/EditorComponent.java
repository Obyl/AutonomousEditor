package ca.team4152.autoeditor.utils.editor;

import ca.team4152.autoeditor.Editor;

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
        int attempts = 0;

        while(attempts < 100){
            if(!Editor.getRenderer().isCurrentlyRendering()){
                nodes.add(node);
                attempts = 100;
            }else{
                attempts++;
            }
        }
    }

    public void removeNode(EditorNode node){
        int attempts = 0;

        while(attempts < 100){
            if(!Editor.getRenderer().isCurrentlyRendering()){
                nodes.remove(node);
                attempts = 100;
            }else{
                attempts++;
            }
        }
    }

    public EditorNode getNodeAt(int x, int y){
        int attempts = 0;

        while(attempts < 10){
            try{
                EditorNode result = null;

                for(EditorNode n : nodes){
                    if(n.intersects(x, y))
                        result = n;
                }

                return result;
            }catch (Exception e){
                attempts++;
            }
        }

        return null;
    }
}