package ca.team4152.autoeditor.utils.menu;

import ca.team4152.autoeditor.utils.ResourceLoader;
import ca.team4152.autoeditor.utils.editor.Control;
import ca.team4152.autoeditor.utils.editor.EditorNode;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;

public class FileMenu extends Menu {

    private JMenu newOption;
    private JMenu openOption;
    private JMenu saveOption;

    private JMenuItem newFieldOption;
    private JMenuItem newPathOption;
    private JMenuItem openFieldOption;
    private JMenuItem openPathOption;
    private JMenuItem saveFieldOption;
    private JMenuItem savePathOption;

    protected FileMenu(){
        super("File");
    }

    @Override
    protected void createMenuItems(){
        //"New" option in file menu.
        newOption = new JMenu("New");
        newOption.setIcon(ResourceLoader.getImageIcon("new_icon"));

        newFieldOption = new JMenuItem(new AbstractAction("New Field", ResourceLoader.getImageIcon("field_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.newField();
            }
        });
        newOption.add(newFieldOption);

        newPathOption = new JMenuItem(new AbstractAction("New Path", ResourceLoader.getImageIcon("path_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.newPath();
            }
        });
        newOption.add(newPathOption);

        add(newOption);

        //"Open" option in file menu.
        openOption = new JMenu("Open");
        openOption.setIcon(ResourceLoader.getImageIcon("open_icon"));

        openFieldOption = new JMenuItem(new AbstractAction("Open Field", ResourceLoader.getImageIcon("field_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.openField();
            }
        });
        openOption.add(openFieldOption);

        openPathOption = new JMenuItem(new AbstractAction("Open Path", ResourceLoader.getImageIcon("path_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.openPath();
            }
        });
        openOption.add(openPathOption);

        add(openOption);

        //"Save" option in file menu.
        saveOption = new JMenu("Save");
        saveOption.setIcon(ResourceLoader.getImageIcon("save_icon"));

        saveFieldOption = new JMenuItem(new AbstractAction("Save Field", ResourceLoader.getImageIcon("field_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.saveField();
            }
        });
        saveOption.add(saveFieldOption);

        savePathOption = new JMenuItem(new AbstractAction("Save Path", ResourceLoader.getImageIcon("path_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.savePath();
            }
        });
        saveOption.add(savePathOption);

        add(saveOption);
    }

    @Override
    protected void updateMenuItems(EditorNode currentSelected, int x, int y){}
}