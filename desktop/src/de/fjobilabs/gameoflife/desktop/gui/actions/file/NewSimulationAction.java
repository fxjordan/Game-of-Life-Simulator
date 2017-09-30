package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

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
import de.fjobilabs.gameoflife.desktop.gui.actions.worldedit.AddPatternAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.worldedit.ClearWorldAction;
import de.fjobilabs.gameoflife.desktop.gui.dialog.NewSimulationDialog;
import de.fjobilabs.gameoflife.desktop.simulator.SimulationConfiguration;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;
import de.fjobilabs.gameoflife.desktop.simulator.SimulatorException;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 11:55:07
 */
public class NewSimulationAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = -5497401104673879497L;
    
    private static final Logger logger = LoggerFactory.getLogger(NewSimulationAction.class);
    
    public static final String ACTION_COMMAND = "new_simulation";
    
    public NewSimulationAction(ActionManager actionManager, SimulatorFrame simulatorFrame,
            Simulator simulator) {
        super(actionManager, "New", ACTION_COMMAND, simulatorFrame, simulator);
        putValue(ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
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
        
        // Actually create the new simulation
        SimulationConfiguration config = getSimulationConfiguration();
        if (config != null) {
            if (newSimulation(config)) {
                // SImulation was created successful
                configureActions();
            }
        }
    }
    
    private SimulationConfiguration getSimulationConfiguration() {
        NewSimulationDialog dialog = new NewSimulationDialog();
        dialog.setLocationRelativeTo(this.simulatorFrame);
        dialog.setVisible(true);
        return dialog.getResult();
    }
    
    private boolean newSimulation(SimulationConfiguration config) {
        try {
            this.simulator.newSimulation(config);
            return true;
        } catch (SimulatorException e) {
            logger.error("Cannot create simulation with config: " + config, e);
            JOptionPane.showMessageDialog(this.simulatorFrame, "Cannot cerate simulation",
                    "Simulation creation error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void configureActions() {
        // Control actions
        this.actionManager.setActionEnabled(StartSimulationAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(PauseSimulationAction.ACTION_COMMAND, false);
        this.actionManager.setActionEnabled(StepForwardAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(StepBackwardAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(ChangeUPSAction.ACTION_COMMAND, true);
        // File menu actions
        this.actionManager.setActionEnabled(CloseSimulationAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(SaveSimulationAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(SaveSimulationAsAction.ACTION_COMMAND, true);
        // World edit actions
        this.actionManager.setActionEnabled(ClearWorldAction.ACTION_COMMAND, true);
        this.actionManager.setActionEnabled(AddPatternAction.ACTION_COMMAND, true);
    }
}
