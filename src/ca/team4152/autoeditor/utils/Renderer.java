package ca.team4152.autoeditor.utils;

import ca.team4152.autoeditor.Editor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {

    private Editor editor;

    private BufferedImage image;
    private int[] pixels;

    public Renderer(Editor editor){
        this.editor = editor;

        image = new BufferedImage(editor.getWindowWidth(),
                                  editor.getWindowHeight(),
                                  BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void render(Graphics g){
        if(editor.getCurrentField() != null){
            editor.getCurrentField().updateImage();
        }

        g.drawImage(image, 0, 0, null);
    }

}