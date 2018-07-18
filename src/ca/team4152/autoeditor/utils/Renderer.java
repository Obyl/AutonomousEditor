package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.field.Field;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {

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
        if(editor.getCurrentField() != null){
            Field currentField = editor.getCurrentField();
            currentField.updateImage();

            for(int y = 0; y < editor.getWindowHeight(); y++){
                for(int x = 0; x < editor.getWindowWidth(); x++){
                    int fieldImageX = (int) ((1 / scale) * (x - xScroll - xOrigin) + xOrigin);
                    int fieldImageY = (int) ((1 / scale) * (y - yScroll - yOrigin) + yOrigin);

                    if(fieldImageX >= 0 && fieldImageX < currentField.getWidth() &&
                            fieldImageY >= 0 && fieldImageY < currentField.getHeight()){
                        pixels[x + y * editor.getWindowWidth()] = currentField.getPixelAt(fieldImageX, fieldImageY);
                    }
                }
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    public int getxScroll() {
        return xScroll;
    }

    public void setxScroll(int xScroll) {
        this.xScroll = xScroll;
    }

    public int getyScroll() {
        return yScroll;
    }

    public void setyScroll(int yScroll) {
        this.yScroll = yScroll;
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public void setxOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }

    public void setyOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

}