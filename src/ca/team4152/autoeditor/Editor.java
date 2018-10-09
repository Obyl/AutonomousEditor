package ca.team4152.autoeditor;

import ca.team4152.autoeditor.utils.display.EditorWindow;
import ca.team4152.autoeditor.utils.display.Renderer;
import ca.team4152.autoeditor.utils.editor.CollisionBox;
import ca.team4152.autoeditor.utils.editor.Field;
import ca.team4152.autoeditor.utils.editor.PathNode;
import ca.team4152.autoeditor.utils.editor.RobotPath;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Editor {

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;

    private static Editor instance;

    private EditorWindow window;
    private Renderer renderer;

    private Field currentField;
    private RobotPath currentPath;

    private Editor(){
        window = new EditorWindow();
        renderer = new Renderer();

        //Keep trying to render field until we succeed.
        new Thread(() -> {
            while(!renderer.hasRenderedSuccessfully()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                render();
            }
        }, "First Render").start();
    }

    public void render(){
        if(window == null || renderer == null)
            return;

        BufferStrategy bs = window.getBufferStrategy();
        if(bs == null){
            window.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        renderer.render(g);

        g.dispose();
        bs.show();
    }

    public static Editor getInstance(){ return instance; }
    public static EditorWindow getWindow() { return instance.window; }
    public static int getWindowWidth(){ return WINDOW_WIDTH; }
    public static int getWindowHeight(){ return WINDOW_HEIGHT; }
    public static Renderer getRenderer(){ return instance.renderer; }
    public static Field getCurrentField(){ return instance.currentField; }
    public static RobotPath getCurrentPath(){ return instance.currentPath; }
    public static void setCurrentPath(RobotPath path){ instance.currentPath = path; }

    public static void setCurrentField(Field field){
        instance.currentField = field;
        if(field != null)
            getRenderer().center();
    }

    public static void init(){
        instance = new Editor();

        getRenderer().setScale(2);

        setCurrentField(new Field("unnamed_field", 200, 200));
        getCurrentField().addNode(new CollisionBox(10, 10, 100, 100));

        setCurrentPath(new RobotPath("unnamed_path"));
        getCurrentPath().addNode(new PathNode(30, 130));
        getCurrentPath().addNode(new PathNode(130, 180));
        getCurrentPath().addNode(new PathNode(170, 100));
    }
}