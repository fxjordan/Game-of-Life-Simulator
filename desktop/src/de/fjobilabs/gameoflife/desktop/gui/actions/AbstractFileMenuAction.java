package de.fjobilabs.gameoflife.desktop.gui.actions;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.PauseSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.dialog.SaveConfirmationDialog;
import de.fjobilabs.gameoflife.desktop.simulator.SimulationState;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:14:38
 */
public abstract class AbstractFileMenuAction extends AbstractSimulationAction {
    
    private static final long serialVersionUID = -7562007696125009937L;
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractFileMenuAction.class);
    
    protected SimulatorFrame simulatorFrame;
    
    public AbstractFileMenuAction(ActionManager actionManager, String name, String actionCommand,
            SimulatorFrame simulatorFrame, Simulator simulator) {
        super(actionManager, name, actionCommand, simulator);
        this.simulatorFrame = simulatorFrame;
    }
    
    /**
     * Pauses the simulation if there is currently a running one. This prevents
     * a running simulation while the user interacts with a dialog window.
     */
    protected void pauseSimulation() {
        if (this.simulator.getCurrentSimulationState() == SimulationState.Running) {
            // TODO BAD CODE. Do not invoke actions this way!
            this.actionManager.getAction(PauseSimulationAction.ACTION_COMMAND).actionPerformed(null);
        }
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
    protected boolean handleUnsavedChanges() {
        SaveConfirmationDialog dialog = new SaveConfirmationDialog("UnnamedSimulation");
        dialog.setLocationRelativeTo(this.simulatorFrame);
        dialog.setVisible(true);
        int result = dialog.getResult();
        if (result == SaveConfirmationDialog.CANCEL || result == SaveConfirmationDialog.NO_RESULT) {
            return false;
        }
        if (result == SaveConfirmationDialog.SAVE) {
            if (!saveCurrentSimulation()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Saves the current simulation to a file. Either the simulation has an
     * associated file or the user can chose one to save.
     * 
     * @return <code>true</code> if the simulation was saved successfully,
     *         <code>false</code> if not.
     */
    protected boolean saveCurrentSimulation() {
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
        } catch (IOException e) {
            logger.error("Exception while saving simulation to file :" + file, e);
            JOptionPane.showMessageDialog(this.simulatorFrame,
                    "Error when saving simulation to file '" + file + "'", "Save error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Lets the user select a file where a simulation should be saved to.
     * 
     * @return The file to save or <code>null</code> if the user has not
     *         selected a file.
     */
    protected File getFileToSave() {
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
}
