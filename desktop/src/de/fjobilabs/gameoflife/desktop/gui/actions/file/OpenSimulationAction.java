package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractFileMenuAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:36:15
 */
public class OpenSimulationAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = 6301186838021399723L;
    
    public static final String ACTION_COMMAND = "open_simulaton";
    
    public OpenSimulationAction(ActionManager actionManager, SimulatorFrame simulatorFrame,
            Simulator simulator) {
        super(actionManager, "Open", ACTION_COMMAND, simulatorFrame, simulator);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
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
}
