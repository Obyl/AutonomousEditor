package ca.team4152.autoeditor;

public class Editor {

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;

    private EditorWindow window;

    public Editor(){
        window = new EditorWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

}