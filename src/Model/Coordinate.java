/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package Model;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class Coordinate implements Serializable {
    private final int key;
    private final int value;
    private final Color color;
    private int strokeThickness;

    public Coordinate (int _key, int _value, Color _color, int _thickness) {
        key = _key;
        value = _value;
        color = _color;
        strokeThickness = _thickness;
    }

    @Override
    public String toString() {
        return key + " " + value + " " + String.valueOf(color.getRGB()) + " " + strokeThickness + "\n";
    }

    public int key()   { return key; }
    public int value() { return value; }
    public Color color() { return color; }
    public int strokeThickness() { return strokeThickness; }
}
