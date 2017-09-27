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
import de.fjobilabs.gameoflife.desktop.gui.dialog.NewSimulationDialog;
import de.fjobilabs.gameoflife.desktop.simulator.SimulationConfiguration;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 11:55:07
 */
public class NewSimulationAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = -5497401104673879497L;
    
    public static final String ACTION_COMMAND = "new_simulation";
    
    public NewSimulationAction(ActionManager actionManager, SimulatorFrame simulatorFrame, Simulator simulator) {
        super(actionManager, "New", ACTION_COMMAND, simulatorFrame, simulator);
        putValue(ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
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
        
        // Actually create the new simulation
        SimulationConfiguration config = getSimulationConfiguration();
        if (config != null) {
            this.simulator.createSimulation(config);
            
            this.actionManager.setActionEnabled(StartSimulationAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(PauseSimulationAction.ACTION_COMMAND, false);
            this.actionManager.setActionEnabled(StepForwardAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(StepBackwardAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(CloseSimulationAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(SaveSimulationAction.ACTION_COMMAND, true);
            this.actionManager.setActionEnabled(SaveSimulationAsAction.ACTION_COMMAND, true);
        }
    }
    
    private SimulationConfiguration getSimulationConfiguration() {
        NewSimulationDialog dialog = new NewSimulationDialog();
        dialog.setLocationRelativeTo(this.simulatorFrame);
        dialog.setVisible(true);
        return dialog.getResult();
    }
}
