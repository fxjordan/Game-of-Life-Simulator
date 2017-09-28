package de.fjobilabs.gameoflife.desktop.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 20:56:07
 */
public class UPSController extends JPanel {
    
    private static final long serialVersionUID = -8670694052746186216L;
    
    private JSlider slider;
    private JSpinner spinner;
    private boolean spinnerChangeByListener;
    
    // TODO This component has only limited action support
    private Action action;
    private String actionCommand;
    
    public UPSController() {
        this(null);
    }
    
    public UPSController(Action action) {
        initLayout();
        reset();
        setAction(action);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.slider.setEnabled(enabled);
        this.spinner.setEnabled(enabled);
    }
    
    public void setAction(Action action) {
        this.action = action;
        this.action.addPropertyChangeListener(new ActionProperyChangeListener());
        if (action == null) {
            return;
        }
        setEnabled(action.isEnabled());
        this.actionCommand = (String) action.getValue(Action.ACTION_COMMAND_KEY);
    }
    
    public void setUPS(int value) {
        this.spinner.setValue(value);
    }
    
    public int getUPS() {
        return this.slider.getValue();
    }
    
    /**
     * Resets the controller to the default state, when no simulation is opened.
     */
    public void reset() {
        int ups = Simulator.DEFAULT_UPS;
        configureSlider(ups * 2);
        this.slider.setValue(ups);
        
        this.spinner.setValue(ups);
        setEnabled(false);
    }
    
    private void initLayout() {
        JLabel upsLabel = new JLabel("UPS:");
        upsLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        this.slider = new JSlider();
        this.slider.setMinimum(0);
        this.slider.setPaintTicks(true);
        this.slider.addChangeListener(new SliderChangeListener());
        
        this.spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        this.spinner.addChangeListener(new SpinnerChangeListener());
        disableInvalidSpinnerInput(this.spinner);
        this.spinner.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(upsLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(slider, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(spinner, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                        .addGap(144)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup().addContainerGap()
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup().addGap(14)
                                .addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(35, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(upsLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.slider, Alignment.LEADING,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addContainerGap()))));
        setLayout(groupLayout);
    }
    
    private void configureSlider(int maxUps) {
        this.slider.setMaximum(maxUps);
        int halfMax = maxUps / 2;
        this.slider.setMajorTickSpacing(maxUps / 2);
        if (maxUps <= 10000000) {
            this.slider.setLabelTable(this.slider.createStandardLabels(halfMax));
            this.slider.setPaintLabels(true);
        } else {
            this.slider.setLabelTable(null);
            this.slider.setPaintLabels(false);
        }
        this.slider.setMinorTickSpacing(maxUps / 10);
    }
    
    private void disableInvalidSpinnerInput(JSpinner spinner) {
        JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        NumberFormatter formatter = (NumberFormatter) txt.getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
    }
    
    private void fireStateChangedAction() {
        /*
         * BAD CODE: TODO Implement action support correctly.
         */
        if (this.action != null && this.action.isEnabled()) {
            this.action.actionPerformed(new ActionEvent(this, 42, this.actionCommand));
        }
    }
    
    private class SliderChangeListener implements ChangeListener {
        
        @Override
        public void stateChanged(ChangeEvent e) {
            changSpinner();
            fireStateChangedAction();
        }
        
        private void changSpinner() {
            spinnerChangeByListener = true;
            spinner.setValue(slider.getValue());
            spinnerChangeByListener = false;
        }
    }
    
    private class SpinnerChangeListener implements ChangeListener {
        
        @Override
        public void stateChanged(ChangeEvent e) {
            if (!spinnerChangeByListener) {
                changeSlider();
            }
        }
        
        private void changeSlider() {
            int spinnerValue = (int) spinner.getValue();
            if (spinnerValue > slider.getMaximum()) {
                configureSlider(spinnerValue * 2);
            } else if (spinnerValue < slider.getMaximum() / 2) {
                configureSlider(spinnerValue * 2);
            }
            slider.setValue(spinnerValue);
        }
    }
    
    private class ActionProperyChangeListener implements PropertyChangeListener {
        
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getSource() == action) {
                if ("enabled".equals(event.getPropertyName())) {
                    boolean enabled = (boolean) event.getNewValue();
                    setEnabled(enabled);
                    if (!enabled) {
                        /*
                         * TODO This may not be the best way to reset the
                         * component, because there can be other situations
                         * where the action is disabled, but this component
                         * should not be reset.
                         */
                        reset();
                    }
                }
            }
        }
    }
}
