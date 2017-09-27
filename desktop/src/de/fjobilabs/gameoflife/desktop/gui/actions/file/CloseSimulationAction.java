package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractFileMenuAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.PauseSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StartSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepBackwardAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepForwardAction;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:38:35
 */
public class CloseSimulationAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = 1271686368905745686L;
    
    public static final String ACTION_COMMAND = "close_simulaton";
    
    public CloseSimulationAction(ActionManager actionManager, SimulatorFrame simulatorFrame,
            Simulator simulator) {
        super(actionManager, "Close", ACTION_COMMAND, simulatorFrame, simulator);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        setEnabled(false);
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
        
        setEnabled(false);
        this.actionManager.setActionEnabled(SaveSimulationAction.ACTION_COMMAND, false);
        this.actionManager.setActionEnabled(SaveSimulationAsAction.ACTION_COMMAND, false);
        this.actionManager.setActionEnabled(StartSimulationAction.ACTION_COMMAND, false);
        this.actionManager.setActionEnabled(PauseSimulationAction.ACTION_COMMAND, false);
        this.actionManager.setActionEnabled(StepForwardAction.ACTION_COMMAND, false);
        this.actionManager.setActionEnabled(StepBackwardAction.ACTION_COMMAND, false);
    }
}
