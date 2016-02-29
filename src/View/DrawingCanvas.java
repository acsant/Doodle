/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package View;

import Misc.GlobalConstants;
import Model.Coordinate;
import Model.Model;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class DrawingCanvas extends JPanel implements Observer {

    Model model;
    private DrawController controller;

    // Initialization of Drawing Canvas
    public DrawingCanvas(Model _model) {
        super();
        model = _model;
        super.setBackground(Color.WHITE);
        controller = new DrawController();
        super.addMouseListener(controller);
        super.addMouseMotionListener(controller);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(2));
        // Anti-aliasing, looks nicer
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ArrayList<Coordinate> coord = model.getDrawingCoords();

        int prevX = -1;
        int prevY = -1;
        int strokes = model.getTimeLineState() / GlobalConstants.TIMELINE_SPACING;
        int temp = 0;
        int currStroke = 0;
        int lastRatio = model.getTimeLineState() - (strokes*GlobalConstants.TIMELINE_SPACING);
        int lastPoint = -1;

        if (model.getTimeLineState() % GlobalConstants.TIMELINE_SPACING != 0) {
            strokes++;
        }
        temp = strokes;
        for (Coordinate pos : coord) {
            graphics2D.setColor(pos.color());
            graphics2D.setStroke(new BasicStroke(pos.strokeThickness(),
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            if (strokes == 0 && model.isTimeLineAction()) {
                break;
            }
            if (strokes == 1 && model.isTimeLineAction() && lastPoint == -1) {
                if (model.getStrokeLengths().size() == model.getMaxStroke()) {
                    lastPoint = model.getStrokeLengths().get(temp - 1) * (lastRatio * 10);
                    lastPoint = lastPoint/100;
                }
            }

            if (temp*GlobalConstants.TIMELINE_SPACING == model.getTimeLineState()) {
                lastPoint = -1;
            }

            if (lastPoint == 0) {
                break;
            }

            if (prevX == -1) {
                prevX = pos.key();
                prevY = pos.value();
                if (model.getStrokeLengths().size() > currStroke && model.getStrokeLengths().get(currStroke) == 1) {
                    // Draw a point and exit
                    graphics2D.fillOval(pos.key(), pos.value(), pos.strokeThickness(), pos.strokeThickness());
                }
            } else {
                if (pos.key() != -1) {
                    drawResizedLines(graphics2D, prevX, prevY, pos);
                    //graphics2D.drawLine(prevX, prevY, pos.key(), pos.value());
                } else {
                    strokes--;
                    currStroke++;
                    lastPoint = 0;
                }
                prevX = pos.key();
                prevY = pos.value();
            }
            lastPoint--;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        double sizeRatioX = model.screenRatioX;
        double sizeRatioY = model.screenRatioY;
        Dimension change = new Dimension(((Double)(GlobalConstants.DEFAULT_CANVAS_SIZE.width*sizeRatioX)).intValue(),
                ((Double)(GlobalConstants.DEFAULT_CANVAS_SIZE.height*sizeRatioY)).intValue());
        if (model.isDrawIsFit()) {
            super.setSize(change);
        }
        repaint();
    }

    private void drawResizedLines (Graphics2D g2, int prevX, int prevY, Coordinate pos) {
        if (!model.isDrawIsFit()) {
            g2.drawLine(prevX, prevY, pos.key(), pos.value());
        } else {
            g2.drawLine(((Double)(prevX*model.screenRatioX)).intValue(),
                    ((Double)(prevY*model.screenRatioY)).intValue(), ((Double)(pos.key()*model.screenRatioX)).intValue(),
                    ((Double)(pos.value()*model.screenRatioY)).intValue());
        }
    }

    /**
     * private class for the controller
     */
    private class DrawController extends MouseInputAdapter implements MouseMotionListener {
        // Constructor for initialization

        @Override
        public void mousePressed(MouseEvent e) {
            model.setDraw(true);
            strokeInit();
            if (model.timelinePos % GlobalConstants.TIMELINE_SPACING != 0) {
               // model.checkForContinuity();
                model.timelinePos = model.getTimeLineState();
            }
            resizedDraw(e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            strokeInit();
            model.timelinePos = model.getTimeLineState();
            resizedDraw(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //if (!model.isAnimate()) {
            model.drawPoints(-1, -1);
            model.setDraw(false);
            model.setPrevX(-1);
            model.setPrevY(-1);
            model.setCurrentX(-1);
            model.setCurrentY(-1);
            //}
        }

        private void resizedDraw(int x, int y) {
            if (model.isDrawIsFit()) {
                model.drawPoints(((Double) (x / model.screenRatioX)).intValue(),
                        ((Double) (y / model.screenRatioY)).intValue());
            } else {
                model.drawPoints(x,y);
            }
        }

        private void strokeInit() {
            if (model.isNewStroke() && !model.isAnimate()) {
                int count = model.getStrokeCount() + 1;
                model.setStrokeCount(count);
                model.setNewStroke(false);
                model.setTimeLineState(count * GlobalConstants.TIMELINE_SPACING);
            }
        }
    }
}
