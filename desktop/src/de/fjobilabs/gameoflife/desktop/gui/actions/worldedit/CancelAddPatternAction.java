package de.fjobilabs.gameoflife.desktop.gui.actions.worldedit;

import java.awt.event.ActionEvent;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractWorldAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 16:00:57
 */
public class CancelAddPatternAction extends AbstractWorldAction {
    
    private static final long serialVersionUID = 3118539793681381910L;
    
    public static final String ACTION_COMMAND = "cancel_and_pattern";
    
    public CancelAddPatternAction(ActionManager actionManager, SimulatorFrame simulatorFrame) {
        super(actionManager, "Cancel", ACTION_COMMAND, simulatorFrame);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.worldEditor.isEditingEnabled()) {
            this.worldEditor.cancelAddPattern();
        }
    }
    
}
