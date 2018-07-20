package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{

    private Editor editor;
    private EditorNode currentHovered;
    private EditorNode currentSelected;
    private int lastMouseX;
    private int lastMouseY;
    private boolean ctrlDown;

    public Listener(Editor editor){
        this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean shouldRender = false;

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

            if(currentSelected != prospectSelected){
                if(currentSelected != null){
                    currentSelected.setSelected(false);
                }

                currentSelected = prospectSelected;

                if(currentSelected != null){
                    currentSelected.setSelected(true);
                }

                shouldRender = true;
            }
        }

        if(shouldRender){
            editor.render();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        boolean shouldRender = false;

        if(editor.getCurrentField() != null){
            if(currentSelected != null){
                int x = (int) ((e.getX() - editor.getRenderer().getXScroll()) / editor.getRenderer().getScale());
                int y = (int) ((e.getY() - editor.getRenderer().getYScroll()) / editor.getRenderer().getScale());

                if(x < 0 || x >= editor.getCurrentField().getWidth() || y < 0 || y >= editor.getCurrentField().getHeight()){
                    return;
                }

                if(currentSelected instanceof CollisionBox){
                    CollisionBox box = (CollisionBox) currentSelected;

                    int x0 = box.getX0();
                    int y0 = box.getY0();
                    int x1 = box.getX1();
                    int y1 = box.getY1();

                    if(Math.abs(x0 - x) < Math.abs(x1 - x)){
                        x0 = x;
                        if(ctrlDown){
                            x1 = x + (box.getX1() - box.getX0());
                        }
                    }else{
                        x1 = x;
                        if(ctrlDown){
                            x0 = x - (box.getX1() - box.getX0());
                        }
                    }

                    if(Math.abs(y0 - y) < Math.abs(y1 - y)){
                        y0 = y;
                        if(ctrlDown){
                            y1 = y + (box.getY1() - box.getY0());
                        }
                    }else{
                        y1 = y;
                        if(ctrlDown){
                            y0 = y - (box.getY1() - box.getY0());
                        }
                    }

                    box.setX0(x0);
                    box.setY0(y0);
                    box.setX1(x1);
                    box.setY1(y1);
                }else if(currentSelected instanceof PathNode){
                    PathNode node = (PathNode) currentSelected;
                    node.setX(x);
                    node.setY(y);
                }

                shouldRender = true;
            }else{
                editor.getRenderer().setXScroll(editor.getRenderer().getXScroll() + (e.getX() - lastMouseX));
                editor.getRenderer().setYScroll(editor.getRenderer().getYScroll() + (e.getY() - lastMouseY));

                shouldRender = true;
            }
        }

        lastMouseX = e.getX();
        lastMouseY = e.getY();

        if(shouldRender){
            editor.render();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean shouldRender = false;

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

            if(currentHovered != prospectHovered){
                if(currentHovered != null){
                    currentHovered.setHovered(false);
                }

                currentHovered = prospectHovered;

                if(currentHovered != null){
                    currentHovered.setHovered(true);
                }

                shouldRender = true;
            }
        }

        lastMouseX = e.getX();
        lastMouseY = e.getY();

        if(shouldRender){
            editor.render();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(editor.getCurrentField() != null && e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL){
            double zoomAmount = e.getWheelRotation() * (editor.getRenderer().getScale() * 0.1);
            double newScale = editor.getRenderer().getScale() - zoomAmount;

            if(newScale > 0.5 && newScale < 10){
                double oldWidth = (editor.getCurrentField().getWidth() * editor.getRenderer().getScale());
                double newWidth = (editor.getCurrentField().getWidth() * newScale);
                double oldHeight = (editor.getCurrentField().getHeight() * editor.getRenderer().getScale());
                double newHeight = (editor.getCurrentField().getHeight() * newScale);

                double widthDiff = newWidth / oldWidth;
                double heightDiff = newHeight / oldHeight;

                int newXScroll = (int) ((widthDiff * (e.getX() - editor.getRenderer().getXScroll()) - e.getX()) * -1);
                int newYScroll = (int) ((heightDiff * (e.getY() - editor.getRenderer().getYScroll()) - e.getY()) * -1);

                editor.getRenderer().setXScroll(newXScroll);
                editor.getRenderer().setYScroll(newYScroll);
                editor.getRenderer().setScale(newScale);

                editor.render();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            ctrlDown = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            ctrlDown = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
    public void keyTyped(KeyEvent e) {

    }
}