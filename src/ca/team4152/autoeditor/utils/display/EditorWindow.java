package ca.team4152.autoeditor.utils.display;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.Listener;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

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
        frame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(EditorWindow.class.getResource("/icon.png")));

        frame.setVisible(true);
    }

}