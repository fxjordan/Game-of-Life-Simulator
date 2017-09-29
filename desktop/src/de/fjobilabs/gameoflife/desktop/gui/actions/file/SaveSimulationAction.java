package de.fjobilabs.gameoflife.desktop.gui.actions.file;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractFileMenuAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:24:03
 */
public class SaveSimulationAction extends AbstractFileMenuAction {
    
    private static final long serialVersionUID = -6047312077875840251L;
    
    public static final String ACTION_COMMAND = "save_simulaton";
    
    public SaveSimulationAction(ActionManager actionManager, SimulatorFrame simulatorFrame,
            Simulator simulator) {
        super(actionManager, "Save", ACTION_COMMAND, simulatorFrame, simulator);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        setEnabled(false);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        pauseSimulation();
        saveCurrentSimulation();
    }
}
