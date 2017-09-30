package de.fjobilabs.gameoflife.desktop.gui.actions;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.simulator.WorldEditor;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 13:36:34
 */
public abstract class AbstractWorldAction extends AbstractSimulationGUIAction {
    
    private static final long serialVersionUID = 3552811596079147283L;
    
    protected WorldEditor worldEditor;
    
    public AbstractWorldAction(ActionManager actionManager, String name, String actionCommand,
            SimulatorFrame simulatorFrame) {
        super(actionManager, name, actionCommand, simulatorFrame, simulatorFrame.getSimulator());
        this.worldEditor = simulator.getWorldEditor();
    }
}
