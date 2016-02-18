/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package com.company;import javax.swing.*;
import java.awt.*;
public class SplashScreen extends JWindow {
    // Breakout Splash Screen
    //private String splash_file = "Resources/CS349.PNG";
    private final String NAME = "Name: ";
    private final String USER_ID = "User-ID: ";
    private final String STUDENT_NUMBER = "Student Number: ";
    private final String DESCRIPTION = "Description: \n";

    // Splash Screen Constructor
    public SplashScreen (String name, String userId, String studentNumber, String description, Dimension SCREEN_SIZE) {
        // Variable Declaration
        JPanel container = (JPanel) getContentPane();

       // ImageIcon img = new ImageIcon(splash_file);
       // JLabel bcgImg = new JLabel(img);
        JLabel displayInfo = new JLabel("<html>" + NAME + name + "<br>" + USER_ID + userId + "<br>" +
                STUDENT_NUMBER + studentNumber + "<br>" + DESCRIPTION + description + "</html>");

        // Set the container content
       // container.add(bcgImg, BorderLayout.CENTER);
        container.add(displayInfo, BorderLayout.SOUTH);
        setSize((SCREEN_SIZE.width - getWidth()) / 2, (SCREEN_SIZE.height - getHeight()) / 2);
        setLocation((SCREEN_SIZE.width - getWidth()) / 2, (SCREEN_SIZE.height - getHeight()) / 2);

        setVisible(true);
    }

}
