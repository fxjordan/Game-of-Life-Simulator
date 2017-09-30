package de.fjobilabs.gameoflife.desktop.gui.actions.worldedit;

import java.awt.event.ActionEvent;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractWorldAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 13:44:05
 */
public class ClearWorldAction extends AbstractWorldAction {
    
    private static final long serialVersionUID = 1343752458043043687L;
    
    public static final String ACTION_COMMAND = "clear_world";
    
    public ClearWorldAction(ActionManager actionManager, SimulatorFrame simulatorFrame) {
        super(actionManager, "Clear World", ACTION_COMMAND, simulatorFrame);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.worldEditor.isEditingEnabled()) {
            this.worldEditor.clearWorld();
        }
    }
}
