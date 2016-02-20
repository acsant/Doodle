/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package com.company;

import Misc.GlobalConstants;
import sun.net.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    // Breakout Splash Screen
    private String splash_file = "/Resources/dodle.png";
    private final String NAME = "Name: ";
    private final String USER_ID = "User-ID: ";
    private final String STUDENT_NUMBER = "Student Number: ";
    private final String DESCRIPTION = "Description: \n";

    // Splash Screen Constructor
    public SplashScreen (String name, String userId, String studentNumber, String description, Dimension SCREEN_SIZE) {
        // Variable Declaration
        int width = GlobalConstants.MINIMUM_SCREEN_SIZE.width;
        int height = GlobalConstants.MINIMUM_SCREEN_SIZE.height;
        //JPanel container = (JPanel) getContentPane();
        ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                (ResourceManager.class.getResource(splash_file))
        ));
        //ImageIcon img = new ImageIcon(scaledImg);
        JLabel bcgImg = new JLabel(img);
        // Set the container content
        super.add(bcgImg, BorderLayout.CENTER);
        //super.add(displayInfo, BorderLayout.SOUTH);
        super.setName("Doodle");
        setSize(width, height);
        setLocation((SCREEN_SIZE.width - getWidth()) / 2, (SCREEN_SIZE.height - getHeight()) / 2);

        setVisible(true);
    }

}
