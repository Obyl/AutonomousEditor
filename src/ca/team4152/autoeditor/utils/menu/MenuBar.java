package ca.team4152.autoeditor.utils.menu;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar{

    public MenuBar(){
        add(Menu.getMenu("file"));
        add(Menu.getMenu("edit"));
    }

}