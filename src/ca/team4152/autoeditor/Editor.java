package ca.team4152.autoeditor;

import ca.team4152.autoeditor.utils.CollisionBox;
import ca.team4152.autoeditor.utils.PathNode;
import ca.team4152.autoeditor.utils.Renderer;
import ca.team4152.autoeditor.utils.RobotPath;
import ca.team4152.autoeditor.utils.Field;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Editor{

    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 700;

    private EditorWindow window;
    private Renderer renderer;

    private Field currentField;
    private RobotPath currentPath;

    public Editor(){
        window = new EditorWindow(this);
        renderer = new Renderer(this);

        renderer.setScale(2);

        setCurrentField(new Field("unnamed_field", 200, 200));
        currentField.addNode(new CollisionBox(10, 10, 100, 100));

        setCurrentPath(new RobotPath("unnamed_path"));
        currentPath.addNode(new PathNode(30, 130));
        currentPath.addNode(new PathNode(130, 180));
        currentPath.addNode(new PathNode(170, 100));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!renderer.hasRenderedSuccessfully()){
                    render();

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "First Render").start();
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

    public void setCurrentField(Field field){
        this.currentField = field;
        if(field != null){
            renderer.center();
        }
    }

    public RobotPath getCurrentPath(){
        return currentPath;
    }

    public void setCurrentPath(RobotPath path){
        this.currentPath = path;
    }

    public Renderer getRenderer(){
        return renderer;
    }

}