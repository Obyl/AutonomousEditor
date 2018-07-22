package ca.team4152.autoeditor.utils.display;

import java.util.HashMap;

public class Color {

    private static HashMap<String, Integer> colors = new HashMap<>();

    static {
        colors.put("window_background", 0xEEEEEE);
        colors.put("field_background", 0xffffff);
        colors.put("collision_box", 0x000000);
        colors.put("path_node", 0x366AA6);
        colors.put("path_node_hover", 0x4D96EA);
    }

    public static void setColor(String name, int color){
        colors.put(name, color);
    }

    public static int getColor(String name){
        return colors.get(name);
    }

}