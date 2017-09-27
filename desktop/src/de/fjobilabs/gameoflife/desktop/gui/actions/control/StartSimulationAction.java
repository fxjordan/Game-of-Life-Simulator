package de.fjobilabs.gameoflife.desktop.gui.actions.control;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 13:51:12
 */
public class StartSimulationAction extends AbstractSimulationAction {
    
    private static final long serialVersionUID = 7569192845899400894L;
    
    public static final String ACTION_COMMAND = "start_simulation";
    
    public StartSimulationAction(ActionManager actionManager, Simulator simulator) {
        super(actionManager, "Start", ACTION_COMMAND, simulator);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_MASK));
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.simulator.hasSimulation()) {
            this.simulator.startSimulation();
            setEnabled(false);
            this.actionManager.setActionEnabled(PauseSimulationAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(StepForwardAction.ACTION_COMMAND, false);
            this.actionManager.setActionEnabled(StepBackwardAction.ACTION_COMMAND, false);
        }
    }
}
