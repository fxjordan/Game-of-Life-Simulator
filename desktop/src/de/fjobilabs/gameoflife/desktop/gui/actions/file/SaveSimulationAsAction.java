package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;

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
    
    public static final String ACTION_COMMAND = "save_simulation_as";
    
    public SaveSimulationAsAction(ActionManager actionManager, SimulatorFrame simulatorFrame,
            Simulator simulator) {
        super(actionManager, "Save As...", ACTION_COMMAND, simulatorFrame, simulator);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.simulator.hasSimulation() || !this.simulator.hasSimulationChanged()) {
            return;
        }
        File file = getFileToSave();
        if (file == null) {
            return;
        }
        this.simulator.saveSimulation(file);
    }
}
