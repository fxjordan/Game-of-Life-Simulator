package de.fjobilabs.gameoflife.desktop.gui.actions.control;

import java.awt.event.ActionEvent;

import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 14:11:08
 */
public class PauseSimulationAction extends AbstractSimulationAction {
    
    private static final long serialVersionUID = 965158785495008358L;
    
    public static final String ACTION_COMMAND = "pause_simulation";
    
    public PauseSimulationAction(ActionManager actionManager, Simulator simulator) {
        super(actionManager, "Pause", ACTION_COMMAND, simulator);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.simulator.hasSimulation()) {
            this.simulator.pauseSimulation();
            setEnabled(false);
            this.actionManager.setActionEnabled(StartSimulationAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(StepForwardAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(StepBackwardAction.ACTION_COMMAND, true);
        }
    }
}
