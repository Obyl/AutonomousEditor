package ca.team4152.autoeditor.utils.editor;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.menu.Menu;

public class Clipboard {

    private static boolean hasBox;
    private static int storedX0;
    private static int storedY0;
    private static int storedX1;
    private static int storedY1;

    private Clipboard(){}

    public static void copy(CollisionBox box, int x, int y){
        hasBox = true;
        storedX0 = box.getX0();
        storedY0 = box.getY0();
        storedX1 = box.getX1();
        storedY1 = box.getY1();

        Menu.updateMenu("edit", box, x, y);
    }

    public static void cut(CollisionBox box, int x, int y){
        copy(box, x, y);

        Editor.getCurrentField().removeNode(box);
        History.addHistoryItem(HistoryItem.DELETE, box);
        Menu.updateMenu("edit", null, x, y);
    }

    public static void paste(int x, int y){
        if(hasBox){
            int xDiff = (storedX1 - storedX0) / 2;
            int yDiff = (storedY1 - storedY0) / 2;

            CollisionBox pastedBox = new CollisionBox(x - xDiff, y - yDiff, x + xDiff, y + yDiff);

            Editor.getCurrentField().addNode(pastedBox);
            History.addHistoryItem(HistoryItem.CREATE, pastedBox);
            Menu.updateMenu("edit", pastedBox, x, y);
        }
    }

    public static boolean hasBox(){
        return hasBox;
    }
}