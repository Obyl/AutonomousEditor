package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {

    private final int WINDOW_BACKGROUND = 0xEEEEEE;
    private final int FIELD_BACKGROUND = 0xffffff;
    private final int COLLISION_BOX_NORMAL = 0x000000;
    private final int COLLISION_BOX_HOVER = 0x7A7A7A;
    private final int COLLISION_BOX_SELECT = 0x505050;
    private final int PATH_NODE_NORMAL = 0x366AA6;
    private final int PATH_NODE_HOVER = 0x4D96EA;
    private final int PATH_NODE_SELECT = 0x2F5D91;

    private Editor editor;

    private BufferedImage image;
    private int[] pixels;

    private int xScroll = 0;
    private int yScroll = 0;
    private int xOrigin = 0;
    private int yOrigin = 0;
    private double scale = 1;

    public Renderer(Editor editor){
        this.editor = editor;

        image = new BufferedImage(editor.getWindowWidth(),
                                  editor.getWindowHeight(),
                                  BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void render(Graphics g){
        Field currentField = editor.getCurrentField();
        RobotPath currentPath = editor.getCurrentPath();

        if(currentField != null){
            for(int y = 0; y < editor.getWindowHeight(); y++){
                for(int x = 0; x < editor.getWindowWidth(); x++){
                    int properColor = WINDOW_BACKGROUND;

                    int fieldImageX = (int) ((1 / scale) * (x - xScroll - xOrigin) + xOrigin);
                    int fieldImageY = (int) ((1 / scale) * (y - yScroll - yOrigin) + yOrigin);

                    if(fieldImageX >= 0 && fieldImageX < currentField.getWidth() &&
                            fieldImageY >= 0 && fieldImageY < currentField.getHeight()){
                        properColor = FIELD_BACKGROUND;

                        CollisionBox currentFieldBox = (CollisionBox) currentField.getNodeAt(fieldImageX, fieldImageY);

                        PathNode currentPathNode = null;
                        if(currentPath != null){
                            currentPathNode = (PathNode) currentPath.getNodeAt(fieldImageX, fieldImageY);
                        }

                        if(currentFieldBox != null){
                            if(currentFieldBox.isSelected()){
                                properColor = COLLISION_BOX_SELECT;
                            }else if(currentFieldBox.isHovered()){
                                properColor = COLLISION_BOX_HOVER;
                            }else{
                                properColor = COLLISION_BOX_NORMAL;
                            }
                        }
                        if(currentPathNode != null){
                            if(currentPathNode.isSelected()){
                                properColor = PATH_NODE_SELECT;
                            }else if(currentPathNode.isHovered()){
                                properColor = PATH_NODE_HOVER;
                            }else{
                                properColor = PATH_NODE_NORMAL;
                            }
                        }
                    }

                    pixels[x + y * editor.getWindowWidth()] = properColor;
                }
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    public int getXScroll() {
        return xScroll;
    }

    public void setXScroll(int xScroll) {
        this.xScroll = xScroll;
    }

    public int getYScroll() {
        return yScroll;
    }

    public void setYScroll(int yScroll) {
        this.yScroll = yScroll;
    }

    public int getXOrigin() {
        return xOrigin;
    }

    public void setXOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public int getYOrigin() {
        return yOrigin;
    }

    public void setYOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void center(){
        if(editor.getCurrentField() != null){
            xScroll = (int) ((editor.getWindowWidth() / 2) -
                    ((editor.getCurrentField().getWidth() * scale) / 2));

            yScroll = (int) ((editor.getWindowHeight() / 2) -
                    ((editor.getCurrentField().getHeight() * scale) / 2));
        }
    }

}