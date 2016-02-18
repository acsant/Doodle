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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class TimeSlider extends JPanel implements Observer {

    Model model;
    JSlider timeLine;
    TitledBorder timeLineBorder;
    SliderController sc = new SliderController();

    // Initializing Time Slider
    public TimeSlider (Model _model) {
        super();
        model = _model;
        timeLine = new JSlider(JSlider.HORIZONTAL);
        timeLineBorder = BorderFactory.createTitledBorder(new EtchedBorder(Color.BLACK,Color.BLACK),
                GlobalConstants.TIMELINE_NAME);
        timeLine.setBorder(timeLineBorder);
        timeLine.setValue(0);
        timeLine.setEnabled(false);
        timeLine.addChangeListener(sc);
        timeLine.setMaximum(0);
        super.setLayout(new BorderLayout());
        super.add(timeLine, BorderLayout.CENTER);
        super.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (model.getStrokeCount() > 0) {
            int sliderVal = GlobalConstants.TIMELINE_SPACING * model.getStrokeCount();
            timeLine.setEnabled(true);
            timeLine.setPaintTicks(true);
            timeLine.setPaintLabels(true);
            if (timeLine.getMaximum() < sliderVal) {
                timeLine.setMaximum(sliderVal);
                timeLine.setValue(sliderVal);
                model.setTimeLineAction(false);
            }
            timeLine.setMajorTickSpacing(GlobalConstants.TIMELINE_SPACING);
        }
    }

    private class SliderController implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                System.out.println(source.getValue());
                model.setTimeLineState(source.getValue());
                model.setTimeLineAction(true);
            }
        }
    }
}
