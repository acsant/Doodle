/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package View;

import Misc.GlobalConstants;
import Model.Model;
import sun.net.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class MenuView extends JMenuBar implements Observer {

    JMenu file = new JMenu(GlobalConstants.FILE_MENU);
    JMenu view = new JMenu(GlobalConstants.VIEW_MENU);
    JMenuItem exit = new JMenuItem(GlobalConstants.EXIT_TEXT);
    JMenuItem txtSave = new JMenuItem(GlobalConstants.TXT_SAVE_TEXT);
    JMenuItem binSave = new JMenuItem(GlobalConstants.BIN_SAVE_TEXT);
    JMenuItem createnew = new JMenuItem(GlobalConstants.CREATE_NEW_TEXT);
    JMenu save = new JMenu(GlobalConstants.SAVE_TEXT);
    JMenuItem load = new JMenuItem(GlobalConstants.LOAD_TEXT);
    ButtonGroup radioGroup = new ButtonGroup();
    JMenuItem fullSize = new JMenuItem(GlobalConstants.FULL_SIZE_TEXT, null);
    JMenuItem fitScreen = new JMenuItem(GlobalConstants.FIT_SCREEN_TEXT, null);
    JFileChooser fileChooser = new JFileChooser();
    final String EXIT_IMAGE = "exit.png";
    final String SAVE_IMAGE = "save.png";
    final String LOAD_IMAGE = "load.png";
    final String CHECK_IMAGE = "check.png";
    final String BIN_SAVE_IMAGE = "bin_save.png";
    final String TXT_SAVE_IMAGE = "textsave.png";
    final String CREATE_NEW_IMAGE = "createnew.png";
    ImageIcon checkIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
            ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + CHECK_IMAGE)));
    MenuController mc = new MenuController();
    Model model;

    public MenuView (Model _model) {
        // Setup the menus
        model = _model;
        // Get all the resources for the menus
        ImageIcon exitIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + EXIT_IMAGE)));
        ImageIcon saveIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + SAVE_IMAGE)));
        ImageIcon loadIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + LOAD_IMAGE)));
        ImageIcon binSaveIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + BIN_SAVE_IMAGE)));
        ImageIcon txtSaveIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + TXT_SAVE_IMAGE)));
        ImageIcon createNewIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                ResourceManager.class.getResource(GlobalConstants.RESOURCES_PATH + CREATE_NEW_IMAGE)));

        // View menu
        radioGroup.add(fullSize);
        radioGroup.add(fitScreen);
        fullSize.setSelected(true);
        fullSize.setIcon(checkIcon);
        fullSize.addActionListener(mc);
        fitScreen.addActionListener(mc);
        fitScreen.setHorizontalTextPosition(SwingConstants.LEFT);
        fullSize.setHorizontalTextPosition(SwingConstants.LEFT);
        // File menus
        createnew.setIcon(createNewIcon);
        createnew.addActionListener(mc);
        load.setIcon(loadIcon);
        load.addActionListener(mc);
        save.setIcon(saveIcon);
        save.add(txtSave);
        save.add(binSave);
        binSave.setIcon(binSaveIcon);
        txtSave.setIcon(txtSaveIcon);
        txtSave.addActionListener(mc);
        binSave.addActionListener(mc);
        exit.setIcon(exitIcon);
        exit.addActionListener(mc);
        file.add(createnew);
        file.add(load);
        file.add(save);
        file.addSeparator();
        file.add(exit);
        view.add(fullSize);
        view.add(fitScreen);
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
            Object obj = e.getSource();
            if (obj instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) obj;
                if (item.equals(createnew)) {
                    model.reset();
                } else if (item.equals(txtSave) || item.equals(binSave)) {
                    returnCode = fileChooser.showSaveDialog(MenuView.this);
                    if (returnCode == JFileChooser.APPROVE_OPTION) {
                        File toSave = fileChooser.getSelectedFile();
                        String filePath = toSave.getAbsolutePath();
                        if (item.equals(binSave)) {
                            model.saveFile(filePath);
                        } else if (item.equals(txtSave)) {
                            model.saveToTextFile(filePath);
                        }

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
                } else if (item.equals(fullSize)) {
                    fullSize.setIcon(checkIcon);
                    fitScreen.setIcon(null);
                    model.setDrawIsFit(false);
                } else if (item.equals(fitScreen)) {
                    fitScreen.setIcon(checkIcon);
                    fullSize.setIcon(null);
                    model.setDrawIsFit(true);
                }
            }
        }
    }
}
