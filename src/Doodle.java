/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

import Misc.GlobalConstants;
import Model.Model;
import View.*;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

// Top level container class
class Doodle {

    Doodle () {

        //Look & Feel

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("This try catch code since the following not found: CrossPlatformLookAndFeel");
        }
        // Create the main window
        JFrame frame = new JFrame(GlobalConstants.APPLICATION_NAME);
        JPanel canvasContainer = new JPanel();
        canvasContainer.setLayout(new GridBagLayout());

        GridBagConstraints gcons = new GridBagConstraints();
        gcons.anchor = GridBagConstraints.CENTER;

        // Defining a model
        Model model = new Model();

        // Defining all the views
        MenuView menu = new MenuView(model);
        ToolsPanel tools = new ToolsPanel(model);
        TimeSlider playBack = new TimeSlider(model);
        DrawingCanvas canvas = new DrawingCanvas(model);
        ScrollView scrollable = new ScrollView(model);
        canvas.setSize(GlobalConstants.DEFAULT_CANVAS_SIZE);
        canvasContainer.add(canvas, gcons);

        model.addObserver(canvas);
        model.addObserver(playBack);
        model.addObserver(scrollable);
        model.notifyObservers();

        frame.setSize(GlobalConstants.SCREEN_SIZE);
        canvas.setPreferredSize(GlobalConstants.DEFAULT_CANVAS_SIZE);
        canvas.setSize(GlobalConstants.DEFAULT_CANVAS_SIZE);
        scrollable.setViewportView(canvasContainer);


        // Setup the views
        frame.setLayout(new BorderLayout());
        frame.add(menu, BorderLayout.NORTH);
        frame.add(playBack, BorderLayout.SOUTH);
        frame.add(tools, BorderLayout.WEST);
        frame.add(scrollable, BorderLayout.CENTER);

        frame.setMinimumSize(GlobalConstants.MINIMUM_SCREEN_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model.setCanvasState(canvas);
        // Make the frame visible

        frame.setVisible(true);

        // Listen for resizing
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                model.setScreenSize(e.getComponent().getSize());
            }
        });
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
