package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{

    private Editor editor;
    private EditorNode currentHovered;
    private EditorNode currentSelected;

    public Mouse(Editor editor){
        this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(editor.getCurrentField() != null){
            int x = (int) ((e.getX() - editor.getRenderer().getXScroll()) / editor.getRenderer().getScale());
            int y = (int) ((e.getY() - editor.getRenderer().getYScroll()) / editor.getRenderer().getScale());

            EditorNode prospectSelected = null;
            if(editor.getCurrentPath() != null){
                prospectSelected = editor.getCurrentPath().getNodeAt(x, y);
            }
            if(prospectSelected == null){
                prospectSelected = editor.getCurrentField().getNodeAt(x, y);
            }

            if(currentSelected != null){
                currentSelected.setSelected(false);
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
            int x = (int) ((e.getX() - editor.getRenderer().getXScroll()) / editor.getRenderer().getScale());
            int y = (int) ((e.getY() - editor.getRenderer().getYScroll()) / editor.getRenderer().getScale());

            EditorNode prospectHovered = null;
            if(editor.getCurrentPath() != null){
                prospectHovered = editor.getCurrentPath().getNodeAt(x, y);
            }
            if(prospectHovered == null){
                prospectHovered = editor.getCurrentField().getNodeAt(x, y);
            }

            if(currentHovered != null){
                currentHovered.setHovered(false);
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