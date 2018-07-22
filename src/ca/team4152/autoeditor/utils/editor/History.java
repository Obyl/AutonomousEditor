package ca.team4152.autoeditor.utils.editor;

import ca.team4152.autoeditor.Editor;

import java.util.ArrayList;

public class History {

    private static ArrayList<HistoryItem> changeHistory = new ArrayList<>();

    public static void addHistoryItem(int itemType, EditorNode regarding){
        HistoryItem newItem = new HistoryItem(itemType, regarding);

        if(changeHistory.size() > 0 && newItem.getItemType() == HistoryItem.EDIT){
            HistoryItem item = changeHistory.get(0);
            //Don't add the item if it's the same as the most recent one.
            //This happens, for example, when you select a CollisionBox but don't move it,
            //and then move it with your next action.
            if(item.getChangedId() == newItem.getChangedId() &&
                    item.getPreviousX0() == newItem.getPreviousX0() &&
                    item.getPreviousY0() == newItem.getPreviousY0() &&
                    item.getPreviousX1() == newItem.getPreviousX1() &&
                    item.getPreviousY1() == newItem.getPreviousY1()){
                return;
            }
        }

        changeHistory.add(0, newItem);
    }

    public static void undoLast(){
        if(changeHistory.size() < 1){
            return;
        }

        HistoryItem newestItem = changeHistory.get(0);
        changeHistory.remove(0);

        EditorNode changedNode = EditorNode.getNode(newestItem.getChangedId());

        switch (newestItem.getItemType()){
            case HistoryItem.EDIT:
                changedNode.setX0(newestItem.getPreviousX0());
                changedNode.setY0(newestItem.getPreviousY0());
                changedNode.setX1(newestItem.getPreviousX1());
                changedNode.setY1(newestItem.getPreviousY1());
                break;
            case HistoryItem.CREATE:
                if(changedNode instanceof CollisionBox)
                    Editor.getCurrentField().removeNode(changedNode);
                break;
            case HistoryItem.DELETE:
                if(changedNode instanceof CollisionBox)
                    Editor.getCurrentField().addNode(changedNode);
                break;
        }
    }
}