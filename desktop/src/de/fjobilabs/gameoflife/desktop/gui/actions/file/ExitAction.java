package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractFileMenuAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:41:25
 */
public class ExitAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = 1366728315656889417L;
    
    public static final String ACTION_COMMAND = "exit";
    
    public ExitAction(ActionManager actionManager, SimulatorFrame simulatorFrame, Simulator simulator) {
        super(actionManager, "Exit", ACTION_COMMAND, simulatorFrame, simulator);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
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
        this.simulatorFrame.dispose();
    }
}
