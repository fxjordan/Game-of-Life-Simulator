package de.fjobilabs.gameoflife.desktop.gui.actions;

import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 13:53:07
 */
public abstract class AbstractSimulationAction extends AbstractNamedAction {
    
    private static final long serialVersionUID = -8463003613315619988L;
    
    protected final Simulator simulator;
    
    public AbstractSimulationAction(ActionManager actionManager, String name, String actionCommand,
            Simulator simulator) {
        super(actionManager, name, actionCommand);
        this.simulator = simulator;
    }
}
