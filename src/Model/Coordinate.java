/*
 * Copyright (c) 2016. - Created by Akash Sant
 * University of Waterloo - CS 349
 */

package Model;

import java.awt.*;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class Coordinate {
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

    public int key()   { return key; }
    public int value() { return value; }
    public Color color() { return color; }
    public int strokeThickness() { return strokeThickness; }
}
