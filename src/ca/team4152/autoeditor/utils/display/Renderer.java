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
    private boolean renderedSuccessfully;

    private int xScroll = 0;
    private int yScroll = 0;
    private double scale = 1;

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
    public boolean hasRenderedSuccessfully(){ return renderedSuccessfully; }

    public void render(Graphics g){
        Field currentField = Editor.getCurrentField();
        RobotPath currentPath = Editor.getCurrentPath();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                //Set default colour to draw here, then change it accordingly in the method.
                int properColor = Color.getColor("window_background");

                int fieldImageX = (int) ((1 / scale) * (x - xScroll));
                int fieldImageY = (int) ((1 / scale) * (y - yScroll));

                if(fieldImageX >= 0 && fieldImageX < currentField.getWidth() &&
                        fieldImageY >= 0 && fieldImageY < currentField.getHeight()){
                    properColor = Color.getColor("field_background");

                    CollisionBox currentFieldBox = (CollisionBox) currentField.getNodeAt(fieldImageX, fieldImageY);

                    PathNode currentPathNode = (PathNode) currentPath.getNodeAt(fieldImageX, fieldImageY);

                    if(currentFieldBox != null)
                        properColor = Color.getColor("collision_box");

                    if(currentPathNode != null){
                        if(currentPathNode.isHovered())
                            properColor = Color.getColor("path_node_hover");
                        else
                            properColor = Color.getColor("path_node");
                    }
                }

                pixels[x + y * width] = properColor;
            }
        }

        //Draws the lines connecting the path nodes.
        drawPathNodes();

        g.drawImage(image, 0, 0, null);
        renderedSuccessfully = true;
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
}