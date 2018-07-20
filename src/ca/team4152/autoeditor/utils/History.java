package ca.team4152.autoeditor.utils;

import java.util.ArrayList;

public class History {

    private static int maxHistoryLength = 20;
    private static ArrayList<HistoryItem> changeHistory = new ArrayList<>();

    public static void addHistoryItem(HistoryItem newItem){
        if(changeHistory.size() >= maxHistoryLength){
            changeHistory.remove(changeHistory.size() - 1);
        }

        if(changeHistory.size() > 0){
            HistoryItem item = changeHistory.get(0);
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
        changedNode.setX0(newestItem.getPreviousX0());
        changedNode.setY0(newestItem.getPreviousY0());
        changedNode.setX1(newestItem.getPreviousX1());
        changedNode.setY1(newestItem.getPreviousY1());
    }
}