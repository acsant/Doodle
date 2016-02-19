/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package View;

import Misc.GlobalConstants;
import sun.net.ResourceManager;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class MenuView extends JMenuBar implements Observer {

    JMenu file = new JMenu(GlobalConstants.FILE_MENU);
    JMenu view = new JMenu(GlobalConstants.VIEW_MENU);
    JMenuItem exit = new JMenuItem(GlobalConstants.EXIT_TEXT);
    JMenuItem save = new JMenuItem(GlobalConstants.SAVE_TEXT);
    JMenuItem load = new JMenuItem(GlobalConstants.LOAD_TEXT);
    JFileChooser fileChooser = new JFileChooser();
    final String EXIT_IMAGE = "exit.png";
    final String SAVE_IMAGE = "save.png";
    final String LOAD_IMAGE = "load.png";
    MenuController mc = new MenuController();
    Model model;

    public MenuView (Model _model) {
        // Setup the menus
        model = _model;
        ImageIcon exitIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + EXIT_IMAGE)));
        ImageIcon saveIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + SAVE_IMAGE)));
        ImageIcon loadIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + LOAD_IMAGE)));
        save.setIcon(saveIcon);
        save.addActionListener(mc);
        load.setIcon(loadIcon);
        load.addActionListener(mc);
        exit.setIcon(exitIcon);
        exit.addActionListener(mc);
        file.add(save);
        file.add(load);
        file.addSeparator();
        file.add(exit);
        super.add(file);
        super.add(view);
        super.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private class MenuController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnCode;
            JMenuItem item = (JMenuItem) e.getSource();
            if (item.equals(save)) {
                returnCode = fileChooser.showSaveDialog(MenuView.this);
                if (returnCode == JFileChooser.APPROVE_OPTION) {
                    File toSave = fileChooser.getSelectedFile();
                    model.saveFile(toSave.getAbsolutePath());
                } else {
                    System.err.println("File Saving operation cancelled.");
                }
            } else if (item.equals(load)) {
                returnCode = fileChooser.showOpenDialog(MenuView.this);
                if (returnCode == JFileChooser.APPROVE_OPTION) {
                    File toLoad = fileChooser.getSelectedFile();
                    model.loadFile(toLoad.getAbsolutePath());
                }
            } else if (item.equals(exit)) {
                System.exit(0);
            }
        }
    }
}
