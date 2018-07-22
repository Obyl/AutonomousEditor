package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.editor.Clipboard;
import ca.team4152.autoeditor.utils.editor.CollisionBox;
import ca.team4152.autoeditor.utils.editor.EditorNode;
import ca.team4152.autoeditor.utils.editor.History;
import ca.team4152.autoeditor.utils.editor.HistoryItem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private EditorNode currentHovered;
    private EditorNode currentSelected;
    private int lastMouseX;
    private int lastMouseY;
    private boolean ctrlDown;
    private boolean newDrag;

    @Override
    public void keyPressed(KeyEvent e) {
        if(Editor.getInstance() == null)
            return;

        int keycode = e.getKeyCode();

        if(keycode == KeyEvent.VK_CONTROL)
            ctrlDown = true;
        if(!ctrlDown)
            return;

        switch (keycode){
            case KeyEvent.VK_Z:
                History.undoLast();
                break;
            case KeyEvent.VK_C:
                if(currentSelected instanceof CollisionBox)
                    Clipboard.copy((CollisionBox) currentSelected);
                break;
            case KeyEvent.VK_X:
                if(currentSelected instanceof CollisionBox)
                    Clipboard.cut((CollisionBox) currentSelected);
                break;
            case KeyEvent.VK_V:
                int x = (int) ((lastMouseX - Editor.getRenderer().getXScroll()) / Editor.getRenderer().getScale());
                int y = (int) ((lastMouseY - Editor.getRenderer().getYScroll()) / Editor.getRenderer().getScale());

                if(x >= 0 || x < Editor.getCurrentField().getWidth() || y >= 0 || y < Editor.getCurrentField().getHeight())
                    Clipboard.paste(x, y);
                break;
        }

        Editor.getInstance().render();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
            ctrlDown = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(Editor.getInstance() == null)
            return;

        boolean shouldRender = false;
        newDrag = true;

        //Translate mouse coordinates to coordinates relative to field.
        int x = (int) ((e.getX() - Editor.getRenderer().getXScroll()) / Editor.getRenderer().getScale());
        int y = (int) ((e.getY() - Editor.getRenderer().getYScroll()) / Editor.getRenderer().getScale());

        //When finding selected node, prioritize path nodes.
        EditorNode prospectSelected = Editor.getCurrentPath().getNodeAt(x, y);
        if(prospectSelected == null)
            prospectSelected = Editor.getCurrentField().getNodeAt(x, y);

        if(currentSelected != prospectSelected){
            if(currentSelected != null)
                currentSelected.setSelected(false);

            currentSelected = prospectSelected;

            if(currentSelected != null){
                if(!currentSelected.isAnchored())
                    currentSelected.setSelected(true);
            }

            shouldRender = true;
        }

        if(currentSelected != null){
            History.addHistoryItem(HistoryItem.EDIT, currentSelected);
        }

        if(shouldRender)
            Editor.getInstance().render();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(Editor.getInstance() == null)
            return;

        if(currentSelected != null){
            //Translate mouse coordinates to coordinates relative to field.
            int x = (int) ((e.getX() - Editor.getRenderer().getXScroll()) / Editor.getRenderer().getScale());
            int y = (int) ((e.getY() - Editor.getRenderer().getYScroll()) / Editor.getRenderer().getScale());

            //Don't let them drag nodes off of the field.
            if(x < 0 || x >= Editor.getCurrentField().getWidth() || y < 0 || y >= Editor.getCurrentField().getHeight())
                return;

            if(!currentSelected.isAnchored())
                currentSelected.handleMouseDrag(x, y, newDrag);
        }else{
            Editor.getRenderer().setXScroll(Editor.getRenderer().getXScroll() + (e.getX() - lastMouseX));
            Editor.getRenderer().setYScroll(Editor.getRenderer().getYScroll() + (e.getY() - lastMouseY));
        }

        lastMouseX = e.getX();
        lastMouseY = e.getY();

        if(newDrag)
            newDrag = false;

        Editor.getInstance().render();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(Editor.getInstance() == null)
            return;

        boolean shouldRender = false;

        //Translate mouse coordinates to coordinates relative to field.
        int x = (int) ((e.getX() - Editor.getRenderer().getXScroll()) / Editor.getRenderer().getScale());
        int y = (int) ((e.getY() - Editor.getRenderer().getYScroll()) / Editor.getRenderer().getScale());

        //When finding hovered node, prioritize path nodes.
        EditorNode prospectHovered = Editor.getCurrentPath().getNodeAt(x, y);
        if(prospectHovered == null)
            prospectHovered = Editor.getCurrentField().getNodeAt(x, y);

        if(currentHovered != prospectHovered){
            if(currentHovered != null)
                currentHovered.setHovered(false);

            currentHovered = prospectHovered;

            if(currentHovered != null){
                if(!currentHovered.isAnchored())
                    currentHovered.setHovered(true);
            }

            shouldRender = true;
        }


        lastMouseX = e.getX();
        lastMouseY = e.getY();

        if(shouldRender)
            Editor.getInstance().render();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(Editor.getInstance() == null)
            return;

        if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL){
            double zoomAmount = e.getWheelRotation() * (Editor.getRenderer().getScale() * 0.1);
            double newScale = Editor.getRenderer().getScale() - zoomAmount;

            if(newScale > 0.5 && newScale < 10){
                //Zoom so that the relative position of the mouse on the field remains the same.
                //(aka zoom "towards" the mouse position")
                double oldWidth = (Editor.getCurrentField().getWidth() * Editor.getRenderer().getScale());
                double newWidth = (Editor.getCurrentField().getWidth() * newScale);
                double oldHeight = (Editor.getCurrentField().getHeight() * Editor.getRenderer().getScale());
                double newHeight = (Editor.getCurrentField().getHeight() * newScale);

                double widthDiff = newWidth / oldWidth;
                double heightDiff = newHeight / oldHeight;

                int newXScroll = (int) ((widthDiff * (e.getX() - Editor.getRenderer().getXScroll()) - e.getX()) * -1);
                int newYScroll = (int) ((heightDiff * (e.getY() - Editor.getRenderer().getYScroll()) - e.getY()) * -1);

                Editor.getRenderer().setXScroll(newXScroll);
                Editor.getRenderer().setYScroll(newYScroll);
                Editor.getRenderer().setScale(newScale);

                Editor.getInstance().render();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}