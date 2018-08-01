package ca.team4152.autoeditor.utils.display;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.Listener;
import ca.team4152.autoeditor.utils.ResourceLoader;
import ca.team4152.autoeditor.utils.menu.EditorMenuBar;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Canvas;
import java.awt.Dimension;

public class EditorWindow extends Canvas{

    public EditorWindow(){
        setPreferredSize(new Dimension(Editor.getWindowWidth(), Editor.getWindowHeight()));

        Listener listener = new Listener();
        addKeyListener(listener);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);

        JFrame frame = new JFrame("Robot Path Editor - Hoya Robotics Tools");
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(ResourceLoader.getImageIcon("window_icon").getImage());

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setJMenuBar(new EditorMenuBar());

        frame.setVisible(true);
    }

}