package ca.team4152.autoeditor.utils.display;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.editor.CollisionBox;
import ca.team4152.autoeditor.utils.editor.EditorNode;
import ca.team4152.autoeditor.utils.editor.Field;
import ca.team4152.autoeditor.utils.editor.PathNode;
import ca.team4152.autoeditor.utils.editor.RobotPath;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Renderer {

    private BufferedImage image;
    private int width;
    private int height;
    private int[] pixels;

    private int xScroll = 0;
    private int yScroll = 0;
    private double scale = 1;
    private boolean currentlyRendering = false;

    public Renderer(){
        width = Editor.getWindowWidth();
        height = Editor.getWindowHeight();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public int getXScroll(){ return xScroll; }
    public void setXScroll(int xScroll){ this.xScroll = xScroll; }
    public int getYScroll(){ return yScroll; }
    public void setYScroll(int yScroll){ this.yScroll = yScroll; }
    public double getScale(){ return scale; }
    public void setScale(double scale){ this.scale = scale; }

    public void render(Graphics g){
        currentlyRendering = true;

        Field currentField = Editor.getCurrentField();
        RobotPath currentPath = Editor.getCurrentPath();

        //Fill in the background of the window.
        int backgroundColor = Color.getColor("window_background");
        for(int i = 0; i < pixels.length; i++)
            pixels[i] = backgroundColor;

        //Fill in the background of the field.
        int fieldColor = Color.getColor("field_background");
        for(int y = yScroll; y < yScroll + (currentField.getHeight() * scale); y++){
            for (int x = xScroll; x < xScroll + (currentField.getWidth() * scale); x++){
                if (x >= 0 && y >= 0 && x < width && y < height){
                    pixels[x + y * width] = fieldColor;
                }
            }
        }

        //Draw each of the collision boxes.
        for(EditorNode node : currentField.getNodes()){
            CollisionBox box = (CollisionBox) node;

            int boxColor = Color.getColor("collision_box");
            if(box.isSelected()){
                for (int y = (int) (yScroll + ((box.getY0() - 3) * scale)); y < yScroll + ((box.getY1() + 3) * scale); y++){
                    for (int x = (int) (xScroll + ((box.getX0() - 3) * scale)); x < xScroll + ((box.getX1() + 3) * scale); x++){
                        if (x >= 0 && y >= 0 && x < width && y < height){
                            int fieldX = (int) ((1 / scale) * (x - xScroll));
                            int fieldY = (int) ((1 / scale) * (y - yScroll));
                            if(fieldX >= 0 && fieldX < currentField.getWidth() && fieldY >= 0 && fieldY < currentField.getHeight() && box.intersects(fieldX, fieldY)) {
                                pixels[x + y * width] = boxColor;
                            }
                        }
                    }
                }
            }else{
                for (int y = (int) (yScroll + (box.getY0() * scale)); y < yScroll + (box.getY1() * scale); y++){
                    for (int x = (int) (xScroll + (box.getX0() * scale)); x < xScroll + (box.getX1() * scale); x++){
                        if (x >= 0 && y >= 0 && x < width && y < height){
                            int fieldX = (int) ((1 / scale) * (x - xScroll));
                            int fieldY = (int) ((1 / scale) * (y - yScroll));
                            if(fieldX >= 0 && fieldX < currentField.getWidth() && fieldY >= 0 && fieldY < currentField.getHeight()) {
                                pixels[x + y * width] = boxColor;
                            }
                        }
                    }
                }
            }
        }

        //Draw each of the path nodes.
        for(EditorNode node : currentPath.getNodes()){
            PathNode pathNode = (PathNode) node;

            int nodeColorNormal = Color.getColor("path_node");
            int nodeColorHover = Color.getColor("path_node_hover");
            for (int y = (int) (yScroll + ((pathNode.getY0() - 3) * scale)); y < yScroll + ((pathNode.getY1() + 3) * scale); y++){
                for (int x = (int) (xScroll + ((pathNode.getX0() - 3) * scale)); x < xScroll + ((pathNode.getX1() + 3) * scale); x++){
                    if (x >= 0 && y >= 0 && x < width && y < height){
                        int fieldX = (int) ((1 / scale) * (x - xScroll));
                        int fieldY = (int) ((1 / scale) * (y - yScroll));
                        if(fieldX >= 0 && fieldX < currentField.getWidth() && fieldY >= 0 && fieldY < currentField.getHeight()) {
                            if(pathNode.isHovered())
                                pixels[x + y * width] = nodeColorHover;
                            else
                                pixels[x + y * width] = nodeColorNormal;
                        }
                    }
                }
            }
        }

        //Draws the lines connecting the path nodes.
        drawPathNodes();

        g.drawImage(image, 0, 0, null);

        currentlyRendering = false;
    }

    private void drawPathNodes(){
        ArrayList<EditorNode> nodes = Editor.getCurrentPath().getNodes();

        if(nodes.size() < 1)
            return;

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

                    int arrayIndex = 0;

                    if(xp >= 0 && yp >= 0){
                        if(steep){
                            if(xp < height && yp < width)
                                arrayIndex = yp + xp * width;
                        }else{
                            if(xp < width && yp < height)
                                arrayIndex = xp + yp * width;
                        }

                        int currentColor = pixels[arrayIndex];
                        if(currentColor == Color.getColor("field_background") || currentColor == Color.getColor("collision_box"))
                            pixels[arrayIndex] = Color.getColor("path_node");
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

    public void center(){
        xScroll = (int) ((width / 2) - ((Editor.getCurrentField().getWidth() * scale) / 2));
        yScroll = (int) ((height / 2) - ((Editor.getCurrentField().getHeight() * scale) / 2));
    }

    public boolean isCurrentlyRendering(){
        return currentlyRendering;
    }
}