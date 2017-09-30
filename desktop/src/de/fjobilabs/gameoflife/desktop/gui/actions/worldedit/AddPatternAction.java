package de.fjobilabs.gameoflife.desktop.gui.actions.worldedit;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractWorldAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.WorldEditorException;
import de.fjobilabs.gameoflife.model.simulation.ca.Pattern;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 14:15:54
 */
public class AddPatternAction extends AbstractWorldAction {
    
    private static final long serialVersionUID = -3813290572233800261L;
    
    public static final String ACTION_COMMAND = "add_pattern_to_world";
    
    public AddPatternAction(ActionManager actionManager, SimulatorFrame simulatorFrame) {
        super(actionManager, "Add to World", ACTION_COMMAND, simulatorFrame);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.worldEditor.isEditingEnabled()) {
            String currentPattern = this.worldEditor.getCurrentPattern();
            if (currentPattern != null) {
                addPatternToWorld(currentPattern);
            }
        }
    }
    
    private void addPatternToWorld(String patternId) {
        Pattern pattern = this.worldEditor.getPatternManager().getLoadedPattern(patternId).getPattern();
        if (isPatternTooBig(pattern)) {
            JOptionPane.showMessageDialog(this.simulatorFrame, "Pattern is too big for this world",
                    "Too big pattern", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            this.worldEditor.addPatternToWorld(patternId);
        } catch (WorldEditorException e1) {
            JOptionPane.showMessageDialog(simulatorFrame, "Failed to add pattern to world",
                    "World edit error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean isPatternTooBig(Pattern pattern) {
        return pattern.getWidth() > this.worldEditor.getWorldWidth()
                || pattern.getHeight() > this.worldEditor.getWorldHeight();
    }
}
