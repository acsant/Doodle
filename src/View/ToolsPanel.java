/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package View;

import Misc.GlobalConstants;
import Model.Model;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class ToolsPanel extends JPanel implements Observer {

    Model model;
    Color[] colors = (Color[])GlobalConstants.COLORS.toArray();

    // Instantiating the tools panel
    public ToolsPanel (Model _model) {
        super();
        model = _model;
        super.setLayout(new BorderLayout());
        Box vertBox = Box.createVerticalBox();
        JPanel colorPalette = new JPanel();
        ButtonGroup colorGroup = new ButtonGroup();
        // Setup the palletes
        colorPalette.setLayout(new GridLayout(0, 2));
        ImageIcon[] icons = new ImageIcon[colors.length];
        int swatchDim = GlobalConstants.COLOR_SWATCH_DIM;
        for (int i = 0; i < colors.length; i++) {
            BufferedImage img = new BufferedImage(swatchDim, swatchDim, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            g.setColor(colors[i]);
            g.fillRect(0,0, swatchDim, swatchDim);
            icons[i] = new ImageIcon(img);
        }
        JToggleButton colorSwatch;
        for (int i = 0; i < icons.length; i++) {
            PaletteController pc = new PaletteController(i);
            colorSwatch = new JToggleButton(icons[i], i==0);
            colorSwatch.setAlignmentX(SwingConstants.CENTER);
            colorSwatch.setAlignmentY(SwingConstants.CENTER);
            colorSwatch.setSize(new Dimension(swatchDim, swatchDim));
            colorSwatch.setBorderPainted(true);
            colorSwatch.setOpaque(true);
            colorSwatch.addItemListener(pc);
            colorGroup.add(colorSwatch);
            colorPalette.add(colorSwatch);
        }
        model.setSelectedColor(colors[0]);

        // Set look and feel
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EtchedBorder(Color.BLACK,Color.BLACK),
                GlobalConstants.COLOR_PALETTE_NAME);
        colorPalette.setBorder(titledBorder);
        vertBox.add(colorPalette);
        vertBox.setVisible(true);
        super.add(vertBox, BorderLayout.NORTH);
        super.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private class PaletteController implements ItemListener {

        private int index = 0;

        PaletteController (int _index) {
            index = _index;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                model.setSelectedColor(colors[index]);
            }
        }
    }
}
