/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package View;

import Misc.GlobalConstants;
import Model.Model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class ToolsPanel extends JPanel implements Observer {

    Model model;
    Color[] colors = (Color[])GlobalConstants.COLORS.toArray();
    Box vertBox = Box.createVerticalBox();
    JPanel colorPalette = new JPanel();
    JPanel strokePalette = new JPanel();
    ButtonGroup colorGroup = new ButtonGroup();
    ImageIcon[] icons = new ImageIcon[colors.length];
    int swatchDim = GlobalConstants.COLOR_SWATCH_DIM;
    JToggleButton colorSwatch;
    ButtonGroup strokeGroup = new ButtonGroup();
    ImageIcon[] strokeIcons = new ImageIcon[GlobalConstants.STROKES.length];
    JButton colorChooser = new JButton();
    JPanel ccPanel = new JPanel();
    JColorChooser colorDialog = new JColorChooser(Color.BLACK);
    //Box strokeBoxLayout = Box.createVerticalBox();

    // Instantiating the tools panel
    public ToolsPanel (Model _model) {
        super();

        model = _model;
        colorDialog.setPreviewPanel(new JPanel());
        ccPanel.setBorder(new EmptyBorder(GlobalConstants.PADDING, GlobalConstants.PADDING, GlobalConstants.PADDING,
                GlobalConstants.PADDING));
        super.setLayout(new GridBagLayout());
        colorChooser.setSize(GlobalConstants.CURRENT_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        // Setup the palletes
        setupColorPalette();
        model.setSelectedColor(colors[0]);
        // Setup strokes palette
        setupStrokePallete();
        // Set look and feel
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EtchedBorder(Color.BLACK,Color.BLACK),
                GlobalConstants.COLOR_PALETTE_NAME);
        TitledBorder strokeBorder = BorderFactory.createTitledBorder(new EtchedBorder(Color.BLACK,Color.BLACK),
                GlobalConstants.STROKE_PALETTE_NAME);
        colorPalette.setBorder(titledBorder);
        strokePalette.setBorder(strokeBorder);
        colorChooser.setMinimumSize(GlobalConstants.CURRENT_COLOR);
        vertBox.add(colorPalette);
        vertBox.add(strokePalette);
        vertBox.setVisible(true);
        colorChooser.setBackground(model.getSelectedColor());
        colorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color backGround = null;
                backGround = colorDialog.showDialog(null, "Pick a color", Color.BLACK);
                if (backGround != null) {
                    model.setSelectedColor(backGround);
                    colorChooser.setBackground(backGround);
                    colorGroup.clearSelection();
                }
            }
        });
        ccPanel.setLayout(new BorderLayout());
        colorChooser.setMinimumSize(new Dimension());
        ccPanel.add(colorChooser, BorderLayout.CENTER);
        super.add(ccPanel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weighty = 3;
        super.add(vertBox, constraints);
        super.setVisible(true);
    }

    private void setupStrokePallete () {
        JToggleButton strokeButton;
        strokePalette.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < strokeIcons.length; i++) {
            BufferedImage img = new BufferedImage(GlobalConstants.STROKE_BUTTON_DIM.width,
                    GlobalConstants.STROKE_BUTTON_DIM.height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) img.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, GlobalConstants.STROKE_BUTTON_DIM.width, GlobalConstants.STROKE_BUTTON_DIM.height);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(GlobalConstants.STROKES[i], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(0,img.getHeight()/2, img.getWidth(), img.getHeight()/2);
            strokeIcons[i] = new ImageIcon(img);
        }
        for (int i = 0; i < strokeIcons.length; i++) {
            StrokeController sc = new StrokeController(i);
            strokeButton = new JToggleButton(strokeIcons[i], i==0);
            strokeButton.setSize(GlobalConstants.STROKE_BUTTON_DIM.width, GlobalConstants.STROKE_BUTTON_DIM.height);
            strokeButton.setBorderPainted(true);
            strokeButton.setOpaque(true);
            strokeButton.addItemListener(sc);
            strokeGroup.add(strokeButton);
            strokePalette.add(strokeButton);
        }

    }

    private void setupColorPalette () {
        colorPalette.setLayout(new GridLayout(0, 2));
        for (int i = 0; i < colors.length; i++) {
            BufferedImage img = new BufferedImage(swatchDim, swatchDim, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            g.setColor(colors[i]);
            g.fillRect(0,0, swatchDim, swatchDim);
            icons[i] = new ImageIcon(img);
        }
        for (int i = 0; i < icons.length; i++) {
            PaletteController pc = new PaletteController(i);
            colorSwatch = new JToggleButton(icons[i], i==0);
            colorSwatch.setMaximumSize(new Dimension(swatchDim, swatchDim));
            colorSwatch.setBackground(colors[i]);
            colorSwatch.setBorderPainted(false);
            colorSwatch.setOpaque(true);
            colorSwatch.addItemListener(pc);
            colorGroup.add(colorSwatch);
            colorPalette.add(colorSwatch);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();

    }

    private class PaletteController implements ItemListener {

        private int index = 0;

        PaletteController (int _index) {
            index = _index;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            JToggleButton source = (JToggleButton) e.getSource();
            if (e.getStateChange() == ItemEvent.SELECTED) {
                model.setSelectedColor(colors[index]);
                colorChooser.setBackground(colors[index]);
                EtchedBorder border = new EtchedBorder(EtchedBorder.LOWERED);
                source.setSelected(false);
                source.setBorder(border);
                source.setRolloverEnabled(true);
            }
        }
    }

    private class StrokeController implements ItemListener {
        private int index = 0;

        StrokeController (int _index) {
            index = _index;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                model.setStrokeThicknes(GlobalConstants.STROKES[index]);
            }
        }
    }
}
