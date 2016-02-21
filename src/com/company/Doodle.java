/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package com.company;
import Misc.GlobalConstants;
import Model.Model;
import View.DrawingCanvas;
import View.MenuView;
import View.TimeSlider;
import View.ToolsPanel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

// Top level container class
class Doodle {

    Doodle () {

        //Look & Fell

        JFrame frame = new JFrame(GlobalConstants.APPLICATION_NAME);
        JScrollPane scrollable = new JScrollPane();

        // Defining a model
        Model model = new Model();

        // Defining all the views
        MenuView menu = new MenuView(model);
        ToolsPanel tools = new ToolsPanel(model);
        TimeSlider playBack = new TimeSlider(model);
        DrawingCanvas canvas = new DrawingCanvas(model);

        model.addObserver(canvas);
        model.addObserver(playBack);
        model.notifyObservers();

        canvas.setPreferredSize(GlobalConstants.SCREEN_SIZE);
        scrollable.setPreferredSize(GlobalConstants.MINIMUM_SCREEN_SIZE);
        scrollable.setViewportView(canvas);

        // Setup the views
        frame.setLayout(new BorderLayout());
        frame.add(menu, BorderLayout.NORTH);
        frame.add(playBack, BorderLayout.SOUTH);
        frame.add(tools, BorderLayout.WEST);
        frame.add(scrollable, BorderLayout.CENTER);

        frame.setMinimumSize(GlobalConstants.MINIMUM_SCREEN_SIZE);
        frame.setSize(GlobalConstants.MINIMUM_SCREEN_SIZE.width, GlobalConstants.MINIMUM_SCREEN_SIZE.height + 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SplashScreen splash = new SplashScreen(GlobalConstants.NAME, GlobalConstants.USER_ID,
                        GlobalConstants.STUDENT_NUMBER, GlobalConstants.DESCRIPTION,
                        GlobalConstants.SCREEN_SIZE);
                splash.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        splash.setVisible(false);
                        splash.dispose();
                        Doodle doodleApp = new Doodle();
                    }
                });
            }
        });
    }
}
