package ca.team4152.autoeditor;

import ca.team4152.autoeditor.utils.Renderer;
import ca.team4152.autoeditor.utils.field.Field;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Editor{

    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 700;

    private EditorWindow window;
    private Renderer renderer;

    private Field currentField;

    public Editor(){
        window = new EditorWindow(this);
        renderer = new Renderer(this);
    }

    public void render(){
        if(window == null){
            return;
        }
        if(renderer == null){
            return;
        }

        BufferStrategy bs = window.getBufferStrategy();
        if(bs == null){
            window.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWindowWidth(), getWindowHeight());

        renderer.render(g);

        g.dispose();
        bs.show();
    }

    public int getWindowWidth(){
        return WINDOW_WIDTH;
    }

    public int getWindowHeight(){
        return WINDOW_HEIGHT;
    }

    public Field getCurrentField(){
        return currentField;
    }

}