package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractFileMenuAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:40:27
 */
public class SaveSimulationAsAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = -6195429605089602289L;
    
    private static final Logger logger = LoggerFactory.getLogger(SaveSimulationAsAction.class);
    
    public static final String ACTION_COMMAND = "save_simulation_as";
    
    public SaveSimulationAsAction(ActionManager actionManager, SimulatorFrame simulatorFrame,
            Simulator simulator) {
        super(actionManager, "Save As...", ACTION_COMMAND, simulatorFrame, simulator);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (!this.simulator.hasSimulation() || !this.simulator.hasSimulationChanged()) {
            return;
        }
        File file = getFileToSave();
        if (file == null) {
            return;
        }
        try {
            this.simulator.saveSimulation(file);
        } catch (IOException e) {
            logger.error("Exception while saving simulation to file :" + file, e);
            JOptionPane.showMessageDialog(this.simulatorFrame,
                    "Error when saving simulation to file '" + file + "'", "Save error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
