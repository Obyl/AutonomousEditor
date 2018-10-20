package ca.team4152.autoeditor.utils.menu;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.ResourceLoader;
import ca.team4152.autoeditor.utils.editor.CollisionBox;
import ca.team4152.autoeditor.utils.editor.EditorNode;
import ca.team4152.autoeditor.utils.editor.PathNode;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class MenuProperties {

    private JMenuItem asItem;
    private JFrame frame;

    private EditorNode currentlyEditing;

    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel anchoredLabel;
    private NumberTextField xField;
    private NumberTextField yField;
    private NumberTextField widthField;
    private NumberTextField heightField;
    private JCheckBox anchoredBox;

    public MenuProperties(){
        asItem = new JMenuItem();
        asItem.setAction(new AbstractAction("Properties") {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewFrame();
            }
        });

        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        xLabel = new JLabel("X:");
        xLabel.setFont(labelFont);
        yLabel = new JLabel("Y:");
        yLabel.setFont(labelFont);
        widthLabel = new JLabel("Width:");
        widthLabel.setFont(labelFont);
        heightLabel = new JLabel("Height:");
        heightLabel.setFont(labelFont);
        anchoredLabel = new JLabel("Anchored:");
        anchoredLabel.setFont(labelFont);

        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        xField = new NumberTextField();
        xField.setFont(fieldFont);
        yField = new NumberTextField();
        yField.setFont(fieldFont);
        widthField = new NumberTextField();
        widthField.setFont(fieldFont);
        heightField = new NumberTextField();
        heightField.setFont(fieldFont);
        anchoredBox = new JCheckBox();
    }

    public void updateCurrentEditing(EditorNode editing){
        if(Editor.getInstance() == null || editing == null)
            return;

        currentlyEditing = editing;
    }

    public JMenuItem getAsItem(){
        return asItem;
    }

    private void createNewFrame(){
        if(frame != null)
            frame.dispose();

        int frameHeight = 0;
        if(currentlyEditing instanceof CollisionBox)
            frameHeight = 230;
        else if(currentlyEditing instanceof PathNode)
            frameHeight = 300;

        frame = new JFrame("Properties");
        frame.setSize(new Dimension(250, frameHeight));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setIconImage(ResourceLoader.getImageIcon("window_icon").getImage());
        frame.setAlwaysOnTop(true);
        frame.setLayout(new PropertiesLayout());
        frame.setLocationRelativeTo(null);

        xField.setText(String.valueOf(currentlyEditing.getX0()));
        yField.setText(String.valueOf(currentlyEditing.getY0()));
        widthField.setText(String.valueOf(currentlyEditing.getX1() - currentlyEditing.getX0()));
        heightField.setText(String.valueOf(currentlyEditing.getY1() - currentlyEditing.getY0()));
        anchoredBox.setSelected(currentlyEditing.isAnchored());

        frame.add(xLabel);
        frame.add(xField);
        frame.add(yLabel);
        frame.add(yField);

        if(currentlyEditing instanceof CollisionBox){
            frame.add(widthLabel);
            frame.add(widthField);
            frame.add(heightLabel);
            frame.add(heightField);
        }

        frame.add(anchoredLabel);
        frame.add(anchoredBox);

        frame.setVisible(true);
    }
}