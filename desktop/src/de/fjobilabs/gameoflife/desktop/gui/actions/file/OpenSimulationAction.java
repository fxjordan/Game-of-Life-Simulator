package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractFileMenuAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.ChangeUPSAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.PauseSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StartSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepBackwardAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepForwardAction;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;
import de.fjobilabs.gameoflife.desktop.simulator.SimulatorException;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:36:15
 */
public class OpenSimulationAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = 6301186838021399723L;
    
    private static final Logger logger = LoggerFactory.getLogger(OpenSimulationAction.class);
    
    public static final String ACTION_COMMAND = "open_simulaton";
    
    public OpenSimulationAction(ActionManager actionManager, SimulatorFrame simulatorFrame,
            Simulator simulator) {
        super(actionManager, "Open", ACTION_COMMAND, simulatorFrame, simulator);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (this.simulator.hasSimulation() && this.simulator.hasSimulationChanged()) {
            pauseSimulation();
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
            if (openSimulation(file)) {
                // SImulation was opened successful
                configureActions();
            }
        }
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
    
    /**
     * Opens the simulation stored in the given file.
     * 
     * @param file
     * @return <code>true</code> if the simulation was opened successfully,
     *         <code>false</code> if not.
     */
    private boolean openSimulation(File file) {
        try {
            this.simulator.openSimulation(file);
            return true;
        } catch (SimulatorException e) {
            logger.error("Cannot create simulation from file: " + file, e);
            JOptionPane.showMessageDialog(this.simulatorFrame, "Cannot cerate simulation from file",
                    "Simulation creation error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException e) {
            logger.error("Failed to read simulation from file: " + file, e);
            JOptionPane.showMessageDialog(this.simulatorFrame, "Cannot load simulation from file",
                    "Simulation loading error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void configureActions() {
        this.actionManager.setActionEnabled(StartSimulationAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(PauseSimulationAction.ACTION_COMMAND, false);
        this.actionManager.setActionEnabled(StepForwardAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(StepBackwardAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(ChangeUPSAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(CloseSimulationAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(SaveSimulationAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(SaveSimulationAsAction.ACTION_COMMAND, true);
    }
}
