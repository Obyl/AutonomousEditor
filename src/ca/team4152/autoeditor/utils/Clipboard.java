package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;

public class Clipboard{

    private static boolean hasBox;
    private static int storedX0;
    private static int storedY0;
    private static int storedX1;
    private static int storedY1;

    private Clipboard(){}

    public static void copy(CollisionBox box){
        hasBox = true;
        storedX0 = box.getX0();
        storedY0 = box.getY0();
        storedX1 = box.getX1();
        storedY1 = box.getY1();
    }

    public static void cut(CollisionBox box, Editor editor){
        copy(box);

        editor.getCurrentField().removeNode(box);
        EditorNode.removeNode(box.getId());

        History.addHistoryItem(new HistoryItem(HistoryItem.DELETE, box));
    }

    public static void paste(int x, int y, Editor editor){
        if(hasBox){
            int xDiff = (storedX1 - storedX0) / 2;
            int yDiff = (storedY1 - storedY0) / 2;

            CollisionBox pastedBox = new CollisionBox(x - xDiff, y - yDiff, x + xDiff, y + yDiff);
            editor.getCurrentField().addNode(pastedBox);

            History.addHistoryItem(new HistoryItem(HistoryItem.CREATE, pastedBox));
        }
    }
}