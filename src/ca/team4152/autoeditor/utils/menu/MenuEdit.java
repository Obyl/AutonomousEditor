package ca.team4152.autoeditor.utils.menu;

import ca.team4152.autoeditor.Editor;
import ca.team4152.autoeditor.utils.ResourceLoader;
import ca.team4152.autoeditor.utils.editor.Clipboard;
import ca.team4152.autoeditor.utils.editor.CollisionBox;
import ca.team4152.autoeditor.utils.editor.EditorNode;
import ca.team4152.autoeditor.utils.editor.PathNode;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;

public class MenuEdit extends EditorMenu{

    private JMenu newOption;

    private JMenuItem newBoxOption;
    private JMenuItem newNodeOption;
    private JMenuItem deleteNodeOption;
    private JMenuItem cutOption;
    private JMenuItem copyOption;
    private JMenuItem pasteOption;
    private JMenuItem propertiesOption;

    protected MenuEdit(){
        super("Edit");
    }

    @Override
    protected void createMenuItems(){
        this.removeAll();

        newOption = new JMenu("New");
        newOption.setIcon(ResourceLoader.getImageIcon("new_icon"));

        newBoxOption = new JMenuItem();
        newOption.add(newBoxOption);

        newNodeOption = new JMenuItem();
        newOption.add(newNodeOption);

        add(newOption);

        deleteNodeOption = new JMenuItem();
        add(deleteNodeOption);

        addSeparator();

        cutOption = new JMenuItem();
        add(cutOption);

        copyOption = new JMenuItem();
        add(copyOption);

        pasteOption = new JMenuItem();
        add(pasteOption);

        addSeparator();

        propertiesOption = new JMenuItem();
        add(propertiesOption);
    }

    @Override
    protected void updateMenuItems(EditorNode currentSelected, int x, int y){
        newBoxOption.setAction(new AbstractAction("New Collision Box", ResourceLoader.getImageIcon("collision_box_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Editor.getCurrentField().addNode(new CollisionBox(x - 25, y - 25, x + 25, y + 25));
                Editor.getInstance().render();
            }
        });

        newNodeOption.setAction(new AbstractAction("New Path Node", ResourceLoader.getImageIcon("path_node_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Editor.getCurrentPath().addNode(new PathNode(x, y));
                Editor.getInstance().render();
            }
        });

        deleteNodeOption.setAction(new AbstractAction("Delete", ResourceLoader.getImageIcon("delete_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSelected instanceof CollisionBox)
                    Editor.getCurrentField().removeNode(currentSelected);
                else if (currentSelected instanceof PathNode)
                    Editor.getCurrentPath().removeNode(currentSelected);
                Editor.getInstance().render();
            }
        });

        cutOption.setAction(new AbstractAction("Cut") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard.cut((CollisionBox) currentSelected, x, y);
                Editor.getInstance().render();
            }
        });

        copyOption.setAction(new AbstractAction("Copy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard.copy((CollisionBox) currentSelected, x, y);
                Editor.getInstance().render();
            }
        });

        pasteOption.setAction(new AbstractAction("Paste") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard.paste(x, y);
                Editor.getInstance().render();
            }
        });

        propertiesOption.setAction(new AbstractAction("Properties") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        deleteNodeOption.setEnabled(currentSelected != null);
        cutOption.setEnabled(currentSelected instanceof CollisionBox);
        copyOption.setEnabled(currentSelected instanceof CollisionBox);
        propertiesOption.setEnabled(currentSelected != null);
        pasteOption.setEnabled(Clipboard.hasBox());
    }
}