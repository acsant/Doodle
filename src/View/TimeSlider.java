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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    Box buttonBox = Box.createHorizontalBox();
    JButton startButton = new JButton(GlobalConstants.START_BUTTON);
    JButton endButton = new JButton(GlobalConstants.END_BUTTON);
    JButton playButton = new JButton(GlobalConstants.PLAY_BUTTON);

    // Initializing Time Slider
    public TimeSlider (Model _model) {
        super();
        model = _model;
        buttonBox.add(startButton);
        buttonBox.add(endButton);
        timeLine = new JSlider(JSlider.HORIZONTAL);
        timeLineBorder = BorderFactory.createTitledBorder(new EtchedBorder(Color.BLACK,Color.BLACK),
                GlobalConstants.TIMELINE_NAME);
        timeLine.setBorder(timeLineBorder);
        timeLine.setValue(0);
        timeLine.setEnabled(false);
        timeLine.addChangeListener(sc);
        timeLine.setMaximum(0);
        playButton.setEnabled(false);
        playButton.addActionListener(sc);
        startButton.setEnabled(false);
        startButton.addActionListener(sc);
        endButton.setEnabled(false);
        endButton.addActionListener(sc);
        super.setLayout(new BorderLayout());
        super.add(playButton, BorderLayout.WEST);
        super.add(buttonBox, BorderLayout.EAST);
        super.add(timeLine, BorderLayout.CENTER);
        super.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        timeLine.removeChangeListener(sc);
        if (model.getStrokeCount() > 0) {
            int sliderVal = GlobalConstants.TIMELINE_SPACING * model.getStrokeCount();
            playButton.setEnabled(true);
            startButton.setEnabled(true);
            endButton.setEnabled(true);
            timeLine.setEnabled(true);
            timeLine.setPaintTicks(true);
            timeLine.setPaintLabels(true);
            if (model.isAnimate() || timeLine.getMaximum() < sliderVal) {
                if (timeLine.getMaximum() < sliderVal) {
                    timeLine.setMaximum(sliderVal);
                }
                if (timeLine.getMaximum() != sliderVal) {
                    // Disable play in this case
                    playButton.setEnabled(false);
                }

                //model.setTimeLineAction(false);
            }
            timeLine.setMajorTickSpacing(GlobalConstants.TIMELINE_SPACING);
        }
        timeLine.setValue(model.getTimeLineState());
        System.out.println(model.getTimeLineState());
        timeLine.addChangeListener(sc);
    }

    private class SliderController implements ChangeListener, ActionListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            //if (!source.getValueIsAdjusting()) {
                model.setTimeLineState(source.getValue());
                model.setTimeLineAction(true);
           // }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source.equals(playButton)) {
                timeLine.setValue(0);
                playButton.setEnabled(false);
                model.playAnimation();
            } else if (source.equals(startButton)) {
                timeLine.setValue(0);
            } else if (source.equals(endButton)) {
                timeLine.setValue(timeLine.getMaximum());
            }
        }
    }
}
