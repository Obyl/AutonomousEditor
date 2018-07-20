package ca.team4152.autoeditor;

import ca.team4152.autoeditor.utils.Mouse;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

public class EditorWindow extends Canvas{

    private int width;
    private int height;

    private Editor editor;
    private JFrame frame;

    public EditorWindow(Editor editor){
        this.editor = editor;

        width = editor.getWindowWidth();
        height = editor.getWindowHeight();
        setPreferredSize(new Dimension(width, height));

        Mouse mouse = new Mouse(editor);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);

        frame = new JFrame("Robot Path Editor - Hoya Robotics Tools");
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