package Model;

import Misc.GlobalConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class Model extends Observable implements Serializable {
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
    private boolean reset = false;

    public Model() {
        super.setChanged();
    }

    private void notifyViews() {
        if (draw || timeLineAction || reset) {
            notifyObservers();
        }
    }

    public void drawPoints(int x, int y) {
        if (draw && !animate) {
            if (x == y && x == -1) {
                newStroke = true;
                if (globalStrokeLength > 0) {
                    strokeLengths.add(globalStrokeLength);
                }
                globalStrokeLength = 0;
                currentX = -1;
                currentY = -1;
                if (prevX != -1 && prevY != -1 && maxStroke > 0) {
                    Coordinate pos = new Coordinate(currentX, currentY, selectedColor, strokeThicknes);
                    drawingCoords.add(pos);
                }
            } else {
                if (currentX != -1 && currentY != -1) {
                    prevX = currentX;
                    prevY = currentY;
                    Coordinate pos = new Coordinate(prevX, prevY, selectedColor, strokeThicknes);
                    drawingCoords.add(pos);
                    globalStrokeLength++;
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
        ActionListener timeLineAnimation = e -> animate(tempStrokeCount);
        animationTimer = new Timer(1000 / (maxStroke * GlobalConstants.TIMELINE_SPACING), timeLineAnimation);
        animationTimer.start();
    }

    private void animate(int maxStroke) {
        setTimeLineState(timeLineState + 1);
        setTimeLineAction(true);
        if (strokeCount < maxStroke) {
            strokeCount = timeLineState / GlobalConstants.TIMELINE_SPACING;
        } else {
            animate = false;
            animationTimer.stop();
        }
    }

    public void saveFile (String filePath) {
        try {
            FileOutputStream fOut = new FileOutputStream(filePath);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            objOut.writeObject(this);

        } catch (IOException ex) {
            System.err.println("File not found: " + filePath);
        }
    }

    public void saveToTextFile (String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(this.toString());
            bufferedWriter.close();
        } catch (IOException ex) {
            System.err.println("File save was unsuccessful.");
        }
    }

    public void loadFile (String filePath) {
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            byte[] lookAhead = new byte[GlobalConstants.BYTE_LOOKAHEAD];
            inputStream.read(lookAhead, 0, lookAhead.length);
            int binaryByteCount = 0;
            for (byte curr : lookAhead) {
                // Mark as binary file if chars are non asci and not newline
                if ((curr < 32 || curr > 127) && curr != 10) {
                    binaryByteCount ++;
                }
            }
            inputStream.close();
            // If 10% binary, then its a binary file - should be ~0% for text file
            double binRatio = (binaryByteCount*100) / GlobalConstants.BYTE_LOOKAHEAD;
            if (binRatio > 10) {
                loadBinaryFile(filePath);
            } else {
                loadTextFile(filePath);
            }
        } catch (IOException ex) {
            System.err.println("Could not determine file type: " + filePath);
        }
    }

    private void loadBinaryFile (String filePath) {
        Model readData;
        try {
            FileInputStream fIn = new FileInputStream(filePath);
            ObjectInputStream objIn = new ObjectInputStream(fIn);
            try {
                Object readObject = objIn.readObject();
                if (readObject instanceof Model) {
                    readData = (Model) readObject;
                    this.drawingCoords = readData.getDrawingCoords();
                    this.draw = readData.getDraw();
                    this.timeLineAction = readData.isTimeLineAction();
                    this.timeLineState = readData.getTimeLineState();
                    this.strokeCount = readData.getStrokeCount();
                    this.maxStroke = readData.getMaxStroke();
                    this.strokeLengths = readData.getStrokeLengths();
                    setChanged();
                    notifyViews();
                }
            } catch (ClassNotFoundException ex) {
                System.err.println("This file may not be supported by the application: " + filePath);
            }
        } catch (IOException ex) {
            System.err.println("File not found: " + filePath);
        }
    }

    private void loadTextFile (String filePath) {
        try {
            // Attributes to be read
            drawingCoords = new ArrayList<>();
            strokeLengths = new ArrayList<>();
            FileInputStream fIn = new FileInputStream(filePath);
            InputStreamReader iStream = new InputStreamReader(fIn);
            BufferedReader bufferedReader = new BufferedReader(iStream);
            String line;
            String[] dataArray;
            int numLines = Integer.parseInt(bufferedReader.readLine());
            while (numLines > 0) {
                line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    dataArray = line.split(" ");
                    int key = Integer.parseInt(dataArray[0]);
                    int value = Integer.parseInt(dataArray[1]);
                    Color color = new Color(Integer.parseInt(dataArray[2]));
                    int thickness = Integer.parseInt(dataArray[3]);
                    Coordinate toAdd = new Coordinate(key, value, color, thickness);
                    drawingCoords.add(toAdd);
                    numLines--;
                }
            }
            numLines = Integer.parseInt(bufferedReader.readLine());
            while (numLines > 0) {
                line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    strokeLengths.add(Integer.parseInt(line));
                    numLines--;
                }
            }
            draw = Boolean.valueOf(bufferedReader.readLine());
            timeLineAction = Boolean.valueOf(bufferedReader.readLine());
            timeLineState = Integer.parseInt(bufferedReader.readLine());
            strokeCount = Integer.parseInt(bufferedReader.readLine());
            maxStroke = Integer.parseInt(bufferedReader.readLine());
            setChanged();
            notifyViews();

        } catch (IOException ex) {
            System.out.println("This file may not be supported by the application: " + filePath);
        }
    }

    @Override
    public String toString() {
        String toRet = this.drawingCoords.size() + "\n";
        // Convert the arraylist to string
        for (Coordinate c : drawingCoords) {
            toRet += c;
        }
        toRet += this.strokeLengths.size() + "\n";
        for (int l : strokeLengths) {
            toRet += String.valueOf(l) + "\n";
        }
        toRet += this.draw + "\n"
                + this.timeLineAction + "\n"
                + this.timeLineState + "\n"
                + this.strokeCount + "\n"
                + this.maxStroke;
        return toRet;
    }

    public void reset() {
        prevX = -1;
        prevY = -1;
        currentX = -1;
        currentY = -1;
        draw = false;
        drawingCoords = new ArrayList<>();
        strokeCount = 0;
        timeLineAction = false;
        timeLineState = 0;
        newStroke = true;
        animate = false;
        strokeThicknes = 1;
        strokeLengths = new ArrayList<>();
        globalStrokeLength = 0;
        maxStroke = 0;
        reset = true;
        setChanged();
        notifyViews();
        reset = false;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
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

    public boolean isReset() {
        return reset;
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

    public void setStrokeThicknes(int strokeThicknes) {
        this.strokeThicknes = strokeThicknes;
    }


    public int getMaxStroke() {
        return maxStroke;
    }

    public ArrayList<Integer> getStrokeLengths() {
        return strokeLengths;
    }


}
