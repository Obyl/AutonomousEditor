package ca.team4152.autoeditor.utils.menu;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.ResourceLoader;
import ca.team4152.autoeditor.utils.editor.CollisionBox;
import ca.team4152.autoeditor.utils.editor.EditorNode;
import ca.team4152.autoeditor.utils.editor.PathNode;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class PropertiesMenu {

    private JMenuItem asItem;
    private EditorNode currentlyEditing;

    public PropertiesMenu(){
        asItem = new JMenuItem();
        asItem.setAction(new AbstractAction("Properties") {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewFrame();
            }
        });
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
        int frameHeight = 0;
        if(currentlyEditing instanceof CollisionBox)
            frameHeight = 230;
        else if(currentlyEditing instanceof PathNode)
            frameHeight = 300;

        JFrame frame = new JFrame("Properties");
        frame.setSize(new Dimension(250, frameHeight));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setIconImage(ResourceLoader.getImageIcon("window_icon").getImage());
        frame.setAlwaysOnTop(true);
        frame.setLayout(new PropertiesLayout());
        frame.setLocationRelativeTo(null);

        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        JLabel xLabel = new JLabel("X:");
        xLabel.setFont(labelFont);
        JLabel yLabel = new JLabel("Y:");
        yLabel.setFont(labelFont);
        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setFont(labelFont);
        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(labelFont);
        JLabel anchoredLabel = new JLabel("Anchored:");
        anchoredLabel.setFont(labelFont);

        NumberTextField xField = new NumberTextField();
        xField.setFont(fieldFont);
        xField.setText(String.valueOf(currentlyEditing.getX0()));
        xField.setCeiling(Editor.getCurrentField().getWidth());

        NumberTextField yField = new NumberTextField();
        yField.setFont(fieldFont);
        yField.setText(String.valueOf(currentlyEditing.getY0()));
        yField.setCeiling(Editor.getCurrentField().getHeight());

        NumberTextField widthField = new NumberTextField();
        widthField.setFont(fieldFont);
        widthField.setText(String.valueOf(currentlyEditing.getX1() - currentlyEditing.getX0()));
        widthField.setCeiling(Editor.getCurrentField().getWidth());

        NumberTextField heightField = new NumberTextField();
        heightField.setFont(fieldFont);
        heightField.setText(String.valueOf(currentlyEditing.getY1() - currentlyEditing.getY0()));
        heightField.setCeiling(Editor.getCurrentField().getHeight());

        JCheckBox anchoredBox = new JCheckBox();
        anchoredBox.setSelected(currentlyEditing.isAnchored());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(buttonFont);
        cancelButton.addActionListener((e) -> {
            frame.dispose();
        });

        JButton okButton = new JButton("OK");
        okButton.setFont(buttonFont);
        okButton.addActionListener((e) -> {
            currentlyEditing.setX0(Integer.valueOf(xField.getText()));
            currentlyEditing.setY0(Integer.valueOf(yField.getText()));
            currentlyEditing.setX1(Integer.valueOf(xField.getText()) + Integer.valueOf(widthField.getText()));
            currentlyEditing.setY1(Integer.valueOf(yField.getText()) + Integer.valueOf(heightField.getText()));
            currentlyEditing.setAnchored(anchoredBox.isSelected());

            frame.dispose();
            if(currentlyEditing instanceof CollisionBox)
                ((CollisionBox) currentlyEditing).updateMidpoints();
        });

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

        frame.add(cancelButton);
        frame.add(okButton);

        frame.setVisible(true);
    }
}