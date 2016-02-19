package Model;

import Misc.GlobalConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class Model extends Observable {
    // Variable declarations
    private int prevX = -1;
    private int prevY = -1;
    private int currentX = -1;
    private int currentY = -1;
    private boolean draw = false;
    private ArrayList<Coordinate> drawingCoords = new ArrayList<>();
    private Color selectedColor = Color.WHITE;
    private int strokeCount = 0;
    private boolean timeLineAction = false;
    private int timeLineState = -1;
    private boolean newStroke = true;
    private Timer animationTimer;
    private boolean animate = false;
    private int strokeThicknes = 1;
    private ArrayList<Integer> strokeLengths = new ArrayList<>();
    private int globalStrokeLength = 0;
    private int maxStroke = 0;

    public Model () {
        super.setChanged();
    }

    private void notifyViews () {
        if (draw || timeLineAction) {
            notifyObservers();
        }
    }

    public void drawPoints(int x, int y) {
        if (draw) {
            Coordinate pos = new Coordinate(x, y, selectedColor, strokeThicknes);
            drawingCoords.add(pos);
            if (x == y && x == -1) {
                newStroke = true;
                strokeLengths.add(globalStrokeLength);
                globalStrokeLength = 0;

            } else {
                globalStrokeLength++;
                if (currentX != -1) {
                    prevX = currentX;
                }
                if (currentY != -1) {
                    prevY = currentY;
                }
                currentX = x;
                currentY = y;
                if (prevX != -1) {
                    setChanged();
                    notifyViews();
                }
            }
        }
    }

    public void playAnimation() {
        int tempStrokeCount = strokeCount;
        animate = true;
        strokeCount = 0;
        setTimeLineState(0);
        ActionListener timeLineAnimation = e->animate(tempStrokeCount);
        animationTimer = new Timer(1000/(strokeLengths.size() - globalStrokeLength),timeLineAnimation);
        animationTimer.start();
    }

    private void animate (int maxStroke) {
        setTimeLineState(timeLineState + 1);
        setTimeLineAction(true);
        if (strokeCount < maxStroke) {
            strokeCount = timeLineState / GlobalConstants.TIMELINE_SPACING;
        } else {
            animate = false;
            animationTimer.stop();
        }
    }

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }


    public boolean getDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }


    public ArrayList<Coordinate> getDrawingCoords() {
        return drawingCoords;
    }


    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }


    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(int count) {
        strokeCount = count;
        maxStroke = strokeCount;
    }


    public boolean isTimeLineAction() {
        return timeLineAction;
    }

    public void setTimeLineAction(boolean timeLineAction) {
        this.timeLineAction = timeLineAction;
        setChanged();
        notifyViews();
    }

    public int getTimeLineState() {
        return timeLineState;
    }

    public void setTimeLineState(int timeLineState) {
        this.timeLineState = timeLineState;
    }

    public boolean isNewStroke() {
        return newStroke;
    }

    public void setNewStroke(boolean newStroke) {
        this.newStroke = newStroke;
    }

    public boolean isAnimate() {
        return animate;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }


    public int getStrokeThicknes() {
        return strokeThicknes;
    }

    public void setStrokeThicknes(int strokeThicknes) {
        this.strokeThicknes = strokeThicknes;
    }


    public int getMaxStroke() {
        return maxStroke;
    }

    public void setMaxStroke(int maxStroke) {
        this.maxStroke = maxStroke;
    }


    public ArrayList<Integer> getStrokeLengths() {
        return strokeLengths;
    }

    public void setStrokeLengths(ArrayList<Integer> strokeLengths) {
        this.strokeLengths = strokeLengths;
    }


}
