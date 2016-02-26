/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package View;

import Misc.GlobalConstants;
import Model.Model;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 2016-02-25.
 */
public class ScrollView extends JScrollPane implements Observer {
    Model model;
    public ScrollView (Model _model) {
        super.setPreferredSize(GlobalConstants.MINIMUM_SCREEN_SIZE);
        model = _model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (model.isDrawIsFit()) {
            super.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            super.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        } else {
            super.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            super.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
    }
}
