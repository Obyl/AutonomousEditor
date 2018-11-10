package ca.team4152.autoeditor.utils.display;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.Listener;
import ca.team4152.autoeditor.utils.ResourceLoader;
import ca.team4152.autoeditor.utils.editor.Control;
import ca.team4152.autoeditor.utils.menu.MenuBar;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(ResourceLoader.getImageIcon("window_icon").getImage());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(Control.savePrompt(true) != Control.CANCEL)
                    e.getWindow().dispose();
            }
        });

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setJMenuBar(new MenuBar());

        frame.setVisible(true);
    }

}