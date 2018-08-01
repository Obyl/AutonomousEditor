package ca.team4152.autoeditor.utils;

import javax.swing.ImageIcon;
import java.util.HashMap;

public class ResourceLoader {

    private static HashMap<String, ImageIcon> imageIcons = new HashMap<>();

    static {
        registerImage("window_icon");
        registerImage("new_icon");
        registerImage("open_icon");
        registerImage("save_icon");
        registerImage("field_icon");
        registerImage("path_icon");
        registerImage("collision_box_icon");
        registerImage("path_node_icon");
        registerImage("delete_icon");
        registerImage("properties_icon");
    }

    private static void registerImage(String name){
        imageIcons.put(name, new ImageIcon(ResourceLoader.class.getResource("/" + name + ".png")));
    }

    public static ImageIcon getImageIcon(String name){
        return imageIcons.get(name);
    }

}