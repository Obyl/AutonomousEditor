package ca.team4152.autoeditor.utils.menu;

import ca.team4152.autoeditor.utils.ResourceLoader;
import ca.team4152.autoeditor.utils.editor.Clipboard;
import ca.team4152.autoeditor.utils.editor.EditorNode;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;

public class MenuEdit extends JMenu{

    private JMenu newOption;

    private JMenuItem newBoxOption;
    private JMenuItem newNodeOption;
    private JMenuItem deleteNodeOption;
    private JMenuItem cutOption;
    private JMenuItem copyOption;
    private JMenuItem pasteOption;
    private JMenuItem propertiesOption;

    public MenuEdit(){
        super("Edit");

        createMenuItems(null, 0, 0);
    }

    public void createMenuItems(EditorNode currentSelected, int x, int y){
        //new
        //delete
        //line
        //cut
        //copy
        //paste
        //line
        //properties

        //"New" option in edit menu.
        newOption = new JMenu("New");
        newOption.setIcon(ResourceLoader.getImageIcon("new_icon"));

        newBoxOption = new JMenuItem(new AbstractAction("New Collision Box", ResourceLoader.getImageIcon("collision_box_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        newOption.add(newBoxOption);

        newNodeOption = new JMenuItem(new AbstractAction("New Path Node", ResourceLoader.getImageIcon("path_node_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        newOption.add(newNodeOption);

        add(newOption);

        //"Delete" option in edit menu.
        if(deleteNodeOption != null)
            remove(deleteNodeOption);

        deleteNodeOption = new JMenuItem(new AbstractAction("Delete", ResourceLoader.getImageIcon("delete_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        if(currentSelected == null)
            deleteNodeOption.setEnabled(false);

        add(deleteNodeOption);

        addSeparator();

        if(cutOption != null)
            remove(cutOption);
        cutOption = new JMenuItem(new AbstractAction("Cut") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(cutOption);

        if(copyOption != null)
            remove(copyOption);
        copyOption = new JMenuItem(new AbstractAction("Copy") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(copyOption);

        pasteOption = new JMenuItem(new AbstractAction("Paste") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(pasteOption);

        addSeparator();

        propertiesOption = new JMenuItem(new AbstractAction("Properties", ResourceLoader.getImageIcon("properties_icon")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(propertiesOption);

        if(currentSelected == null){
            deleteNodeOption.setEnabled(false);
            cutOption.setEnabled(false);
            copyOption.setEnabled(false);
            propertiesOption.setEnabled(false);
        }
        if(!Clipboard.hasBox()){
            pasteOption.setEnabled(false);
        }
    }

}