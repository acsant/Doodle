/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package View;

import Misc.GlobalConstants;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class MenuView extends JMenuBar implements Observer {

    public MenuView () {
        // Setup the menus
        JMenu file = new JMenu(GlobalConstants.FILE_MENU);
        JMenu view = new JMenu(GlobalConstants.VIEW_MENU);

        super.add(file);
        super.add(view);
        super.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
