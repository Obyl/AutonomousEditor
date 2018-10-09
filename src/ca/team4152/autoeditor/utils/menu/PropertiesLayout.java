package ca.team4152.autoeditor.utils.menu;

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
        int fieldX = 170;
        int boxX = 222;
        int labelY = 6;
        int fieldY = 5;
        int labelWidth = 125;
        int fieldWidth = 70;
        int boxWidth = 20;
        int labelHeight = 20;
        int fieldHeight = 25;
        int boxHeight = 20;
        int yInc = 30;

        for(Component component : parent.getComponents()){
            if(component instanceof JLabel){
                component.setBounds(labelX, labelY, labelWidth, labelHeight);
                labelY += yInc;
            }else if(component instanceof JTextField){
                component.setBounds(fieldX, fieldY, fieldWidth, fieldHeight);
                fieldY += yInc;
            }else if(component instanceof JCheckBox){
                component.setBounds(boxX, fieldY, boxWidth, boxHeight);
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