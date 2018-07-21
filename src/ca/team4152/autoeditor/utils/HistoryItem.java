package ca.team4152.autoeditor.utils;

public class HistoryItem {

    public static final int EDIT = 0;
    public static final int CREATE = 1;
    public static final int DELETE = 2;

    private int itemType;
    private int changedId;
    private int previousX0;
    private int previousY0;
    private int previousX1;
    private int previousY1;

    public HistoryItem(int itemType, EditorNode node) {
        this.itemType = itemType;
        this.changedId = node.getId();
        this.previousX0 = node.getX0();
        this.previousY0 = node.getY0();
        this.previousX1 = node.getX1();
        this.previousY1 = node.getY1();
    }

    public int getItemType(){
        return itemType;
    }

    public int getChangedId() {
        return changedId;
    }

    public int getPreviousX0() {
        return previousX0;
    }

    public int getPreviousY0() {
        return previousY0;
    }

    public int getPreviousX1() {
        return previousX1;
    }

    public int getPreviousY1() {
        return previousY1;
    }
}