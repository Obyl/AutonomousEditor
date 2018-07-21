package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Renderer {

    private final int WINDOW_BACKGROUND = 0xEEEEEE;
    private final int FIELD_BACKGROUND = 0xffffff;
    private final int COLLISION_BOX = 0x000000;
    private final int PATH_NODE_FILL = 0x366AA6;
    private final int PATH_NODE_NORMAL = 0x366AA6;
    private final int PATH_NODE_HOVER = 0x4D96EA;
    private final int PATH_NODE_SELECT = 0x2F5D91;

    private Editor editor;

    private BufferedImage image;
    private int[] pixels;
    private boolean renderedSuccessfully;

    private int xScroll = 0;
    private int yScroll = 0;
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
                    //Set default colour to draw here, then change it accordingly in the method.
                    int properColor = WINDOW_BACKGROUND;

                    int fieldImageX = (int) ((1 / scale) * (x - xScroll));
                    int fieldImageY = (int) ((1 / scale) * (y - yScroll));

                    if(fieldImageX >= 0 && fieldImageX < currentField.getWidth() &&
                            fieldImageY >= 0 && fieldImageY < currentField.getHeight()){
                        properColor = FIELD_BACKGROUND;

                        CollisionBox currentFieldBox = (CollisionBox) currentField.getNodeAt(fieldImageX, fieldImageY);

                        PathNode currentPathNode = null;
                        if(currentPath != null){
                            currentPathNode = (PathNode) currentPath.getNodeAt(fieldImageX, fieldImageY);
                        }

                        if(currentFieldBox != null){
                            properColor = COLLISION_BOX;
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

        if(currentPath != null){
            //Draws the lines connecting the path nodes.
            drawPathNodes();
        }

        g.drawImage(image, 0, 0, null);
        renderedSuccessfully = true;
    }

    private void drawPathNodes(){
        ArrayList<EditorNode> nodes = editor.getCurrentPath().getNodes();
        PathNode currentNode;
        PathNode nextNode = (PathNode) nodes.get(0);
        for(int i = 0; i < nodes.size() - 1; i++){
            currentNode = nextNode;
            nextNode = (PathNode) nodes.get(i + 1);

            int x0 = (int) ((scale * currentNode.getX0()) + xScroll);
            int y0 = (int) ((scale * currentNode.getY0()) + yScroll);
            int x1 = (int) ((scale * nextNode.getX0()) + xScroll);
            int y1 = (int) ((scale * nextNode.getY0()) + yScroll);

            boolean steep = false;

            if(Math.abs(x0 - x1) < Math.abs(y0 - y1)){
                int mid = x0;
                x0 = y0;
                y0 = mid;

                mid = x1;
                x1 = y1;
                y1 = mid;

                steep = true;
            }

            if(x0 > x1){
                int mid = x0;
                x0 = x1;
                x1 = mid;

                mid = y0;
                y0 = y1;
                y1 = mid;
            }

            int xChange = x1 - x0;
            int yChange = y1 - y0;

            int changeError = Math.abs(yChange) * 2;
            int changeError2 = 0;

            int y = y0;
            for(int x = x0; x < x1; x++){
                for(int p = 0; p < 9; p++){
                    int xp = x + (p % 3);
                    int yp = y + (p / 3);

                    if(xp >= 0 && yp >= 0){
                        if(steep){
                            if(xp < editor.getWindowHeight() && yp < editor.getWindowWidth()){
                                int arrayIndex = yp + xp * editor.getWindowWidth();
                                int currentColor = pixels[arrayIndex];

                                if(currentColor == FIELD_BACKGROUND || currentColor == COLLISION_BOX){
                                    pixels[arrayIndex] = PATH_NODE_FILL;
                                }
                            }
                        }else{
                            if(xp < editor.getWindowWidth() && yp < editor.getWindowHeight()){
                                int arrayIndex = xp + yp * editor.getWindowWidth();
                                int currentColor = pixels[arrayIndex];

                                if(currentColor == FIELD_BACKGROUND || currentColor == COLLISION_BOX){
                                    pixels[arrayIndex] = PATH_NODE_FILL;
                                }
                            }
                        }
                    }
                }

                changeError2 += changeError;
                if(changeError2 > xChange){
                    y += (y1 > y0 ? 1 : -1);
                    changeError2 -= (xChange * 2);
                }
            }
        }
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

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public boolean hasRenderedSuccessfully(){
        return renderedSuccessfully;
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