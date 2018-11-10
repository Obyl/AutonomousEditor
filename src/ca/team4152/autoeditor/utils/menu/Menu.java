package ca.team4152.autoeditor.utils.menu;

import ca.team4152.autoeditor.utils.editor.EditorNode;

import javax.swing.JMenu;
import java.util.HashMap;

public abstract class Menu extends JMenu {

    private static HashMap<String, Menu> menus = new HashMap<>();

    static {
        menus.put("file", new FileMenu());
        menus.put("edit", new EditMenu());
        menus.put("popup", new EditMenu());
    }

    protected Menu(String name){
        super(name);

        createMenuItems();
        updateMenuItems(null, 0, 0);
    }

    protected abstract void createMenuItems();
    protected abstract void updateMenuItems(EditorNode currentSelected, int x, int y);

    public static Menu getMenu(String name){
        return menus.get(name);
    }

    public static void updateMenu(String name, EditorNode currentSelected, int x, int y){
        menus.get(name).updateMenuItems(currentSelected, x, y);
    }
}