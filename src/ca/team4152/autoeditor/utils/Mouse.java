package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.field.FieldComponent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{

    private Editor editor;
    private FieldComponent currentHovered;
    private FieldComponent currentSelected;

    public Mouse(Editor editor){
        this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(editor.getCurrentField() != null){
            FieldComponent prospectSelected = editor.getCurrentField()
                    .getComponentAt(e.getX(), e.getY());

            if(currentSelected != null){
                currentSelected.setSelected(false);
            }

            if(currentSelected != prospectSelected){
                editor.getCurrentField().setShouldUpdateImage(true);
            }

            currentSelected = prospectSelected;

            if(currentSelected != null){
                currentSelected.setSelected(true);
            }

            editor.render();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(editor.getCurrentField() != null){
            FieldComponent prospectHovered = editor.getCurrentField()
                    .getComponentAt(e.getX(), e.getY());

            if(currentHovered != null){
                currentHovered.setHovered(false);
            }

            if(currentHovered != prospectHovered){
                editor.getCurrentField().setShouldUpdateImage(true);
            }

            currentHovered = prospectHovered;

            if(currentHovered != null){
                currentHovered.setHovered(true);
            }

            editor.render();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}