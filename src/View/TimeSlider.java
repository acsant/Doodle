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
    JButton startButton = new JButton();
    JButton endButton = new JButton();
    JButton playButton = new JButton();
    final String PLAY = "play.png";
    final String START = "rewind.png";
    final String END = "forward.png";
    ImageIcon playIcon = new ImageIcon(GlobalConstants.RESOURCES_PATH + PLAY);
    ImageIcon rewindIcon = new ImageIcon(GlobalConstants.RESOURCES_PATH + START);
    ImageIcon forwardIcon = new ImageIcon(GlobalConstants.RESOURCES_PATH + END);

    // Initializing Time Slider
    public TimeSlider (Model _model) {
        super();
        model = _model;
        buttonBox.add(startButton);
        buttonBox.add(endButton);
        playButton.setIcon(playIcon);
        startButton.setIcon(rewindIcon);
        endButton.setIcon(forwardIcon);
        timeLine = new JSlider(JSlider.HORIZONTAL);
        timeLineBorder = BorderFactory.createTitledBorder(new EtchedBorder(Color.BLACK,Color.BLACK),
                GlobalConstants.TIMELINE_NAME);
        timeLine.setBorder(timeLineBorder);
        timeLine.setValue(0);
        timeLine.addChangeListener(sc);
        timeLine.setMaximum(0);
        playButton.addActionListener(sc);
        startButton.addActionListener(sc);
        setupTimeLine(false);
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
        if (model.isReset()) {
            setupTimeLine(false);
        } else if (model.getStrokeCount() > 0) {
            int sliderVal = GlobalConstants.TIMELINE_SPACING * model.getStrokeCount();
            setupTimeLine(true);
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
        timeLine.addChangeListener(sc);
    }

    private void setupTimeLine(boolean setup) {
        timeLine.setEnabled(setup);
        playButton.setEnabled(setup);
        startButton.setEnabled(setup);
        endButton.setEnabled(setup);
        timeLine.setPaintTicks(setup);
        timeLine.setPaintLabels(setup);
    }

    private class SliderController implements ChangeListener, ActionListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            //if (!source.getValueIsAdjusting()) {
                model.timelinePos = source.getValue();
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
