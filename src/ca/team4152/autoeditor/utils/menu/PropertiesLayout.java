package ca.team4152.autoeditor.utils.menu;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class PropertiesLayout implements LayoutManager{

    @Override
    public void layoutContainer(Container parent) {
        int labelX = 10;
        int textFieldX = 170;
        int boxX = 222;
        int buttonX = 17;
        int labelY = 6;
        int textFieldY = 5;
        int labelWidth = 125;
        int textFieldWidth = 70;
        int boxWidth = 20;
        int buttonWidth = 100;
        int labelHeight = 20;
        int textFieldHeight = 25;
        int boxHeight = 20;
        int buttonHeight = 30;
        int yInc = 30;
        int xInc = 110;

        for(Component component : parent.getComponents()){
            if(component instanceof JLabel){
                component.setBounds(labelX, labelY, labelWidth, labelHeight);
                labelY += yInc;
            }else if(component instanceof JTextField){
                component.setBounds(textFieldX, textFieldY, textFieldWidth, textFieldHeight);
                textFieldY += yInc;
            }else if(component instanceof JCheckBox){
                component.setBounds(boxX, textFieldY, boxWidth, boxHeight);
                textFieldY += yInc;
            }else if(component instanceof JButton){
                component.setBounds(buttonX, labelY, buttonWidth, buttonHeight);
                buttonX += xInc;
            }
        }
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }
}