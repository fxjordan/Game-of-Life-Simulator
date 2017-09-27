package de.fjobilabs.gameoflife.desktop.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.dialog.NewSimulationDialog;
import de.fjobilabs.gameoflife.desktop.gui.dialog.SaveConfirmationDialog;
import de.fjobilabs.gameoflife.desktop.simulator.SimulationConfiguration;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 25.09.2017 - 18:14:25
 */
public class SimulatorActionListener implements ActionListener {
    
    private static final Logger logger = LoggerFactory.getLogger(SimulatorActionListener.class);
    
    public static final String NEW_SIMULATION_ACTION = "new_simulation";
    public static final String OPEN_SIMULATION_ACTION = "open_simulation";
    public static final String CLOSE_SIMULATION_ACTION = "close_simulation";
    public static final String SAVE_SIMULATION_ACTION = "save_simulation";
    public static final String SAVE_SIMULATION_AS_ACTION = "save_simulation_as";
    public static final String EXIT_ACTION = "exit";
    
    public static final String START_SIMULATION = "start_simulation";
    public static final String PAUSE_SIMULATION = "pause_simulation";
    public static final String STEP_SIMULATION_FORWARD = "step_simulation_forward";
    public static final String STEP_SIMULATION_BACKWARD = "step_simulation_backward";
    
    private SimulatorFrame simulatorFrame;
    private Simulator simulator;
    
    public SimulatorActionListener(SimulatorFrame simulatorFrame, Simulator simulator) {
        this.simulatorFrame = simulatorFrame;
        this.simulator = simulator;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * TODO Maybe the simulation should be paused before any action is
         * executed, so that important states do not change for example during
         * saving.
         */
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
        case NEW_SIMULATION_ACTION:
            newSimulation();
            break;
        case OPEN_SIMULATION_ACTION:
            openSimulation();
            break;
        case CLOSE_SIMULATION_ACTION:
            closeSimulation();
            break;
        case SAVE_SIMULATION_ACTION:
            saveSimulation();
            break;
        case SAVE_SIMULATION_AS_ACTION:
            saveSimulationAs();
            break;
        case EXIT_ACTION:
            exit();
            break;
        default:
            logger.warn("Unknown menu bar action: " + actionCommand);
        }
    }
    
    private void newSimulation() {
        if (this.simulator.hasSimulation() && this.simulator.hasSimulationChanged()) {
            if (!handleUnsavedChanges()) {
                return;
            }
            /*
             * Simulation was saved successfully or user doesn't want to save
             * the old simulation.
             */
        }
        
        // Actually create the new simulation
        SimulationConfiguration config = getSimulationConfiguration();
        if (config != null) {
            this.simulator.createSimulation(config);
        }
    }
    
    private void openSimulation() {
        if (this.simulator.hasSimulation() && this.simulator.hasSimulationChanged()) {
            if (!handleUnsavedChanges()) {
                return;
            }
            /*
             * Simulation was saved successfully or user doesn't want to save
             * the old simulation.
             */
        }
        File file = getFileToOpen();
        if (file != null) {
            this.simulator.openSimulation(file);
        }
    }
    
    private void closeSimulation() {
        if (!this.simulator.hasSimulation()) {
            return;
        }
        /*
         * TODO Maybe we should only close if the simulation is not running, but
         * this is nearly the same as hasSimulationChanged(), because if the
         * simulation runs it changes multiple times per second.
         */
        if (this.simulator.hasSimulationChanged()) {
            if (!handleUnsavedChanges()) {
                return;
            }
            /*
             * Simulation was saved successfully or user doesn't want to save
             * the simulation.
             */
        }
        this.simulator.closeSimulation();
    }
    
    /**
     * Saves the current simulation to a file. Either the simulation has an
     * associated file or the user can chose one to save.
     * 
     * @return <code>true</code> if the simulation was saved successfully,
     *         <code>false</code> if not.
     */
    private boolean saveSimulation() {
        if (!this.simulator.hasSimulation() || !this.simulator.hasSimulationChanged()) {
            return false;
        }
        File file = this.simulator.getCurrentSimulationFile();
        if (file == null) {
            file = getFileToSave();
        }
        if (file == null) {
            return false;
        }
        try {
            this.simulator.saveSimulation(file);
            return true;
        } catch (Exception e) {
            logger.error("Exception while saving simulation to file", e);
            return false;
        }
    }
    
    /**
     * Saves the current simulation to a fiel choosen by the user.
     * 
     * @return <code>true</code> if the simulation was saved successfully,
     *         <code>false</code> if not.
     */
    private void saveSimulationAs() {
        if (!this.simulator.hasSimulation() || !this.simulator.hasSimulationChanged()) {
            return;
        }
        File file = getFileToSave();
        if (file == null) {
            return;
        }
        this.simulator.saveSimulation(file);
    }
    
    private void exit() {
        // First close the simulation if there is any.
        closeSimulation();
        this.simulatorFrame.dispose();
    }
    
    /**
     * Asks the user whether he wants to save the unsaved changes or not. If the
     * user accepts, this method saves the current simulation.<br>
     * <br>
     * This method returns <code>true</code> if and only if the user doesn't
     * want to save the changes or the changes were really saved successfully.
     * 
     * @return <code>true</code> if the action can continue, <code>false</code>
     *         if not.
     */
    private boolean handleUnsavedChanges() {
        SaveConfirmationDialog dialog = new SaveConfirmationDialog("UnnamedSimulation");
        dialog.setLocationRelativeTo(this.simulatorFrame);
        dialog.setVisible(true);
        int result = dialog.getResult();
        if (result == SaveConfirmationDialog.CANCEL || result == SaveConfirmationDialog.NO_RESULT) {
            return false;
        }
        if (result == SaveConfirmationDialog.SAVE) {
            if (!saveSimulation()) {
                return false;
            }
        }
        return true;
    }
    
    private File getFileToSave() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As...");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Simulation (SIM) (*.sim)", "sim"));
        fileChooser.setSelectedFile(new File("Unnamed.sim"));
        int state = fileChooser.showSaveDialog(this.simulatorFrame);
        if (state == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    
    private File getFileToOpen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Simulation (SIM) (*.sim)", "sim"));
        int state = fileChooser.showOpenDialog(this.simulatorFrame);
        if (state == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    
    private SimulationConfiguration getSimulationConfiguration() {
        NewSimulationDialog dialog = new NewSimulationDialog();
        dialog.setLocationRelativeTo(this.simulatorFrame);
        dialog.setVisible(true);
        return dialog.getResult();
    }
}
