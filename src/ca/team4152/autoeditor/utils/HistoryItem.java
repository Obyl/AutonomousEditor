package ca.team4152.autoeditor.utils;

public class HistoryItem {

    private int changedId;
    private int previousX0;
    private int previousY0;
    private int previousX1;
    private int previousY1;

    public HistoryItem(int id, int x0, int y0, int x1, int y1) {
        this.changedId = id;
        this.previousX0 = x0;
        this.previousY0 = y0;
        this.previousX1 = x1;
        this.previousY1 = y1;
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