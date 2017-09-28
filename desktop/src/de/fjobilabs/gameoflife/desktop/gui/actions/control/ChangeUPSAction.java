package de.fjobilabs.gameoflife.desktop.gui.actions.control;

import java.awt.event.ActionEvent;

import de.fjobilabs.gameoflife.desktop.gui.UPSController;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 28.09.2017 - 01:58:08
 */
public class ChangeUPSAction extends AbstractSimulationAction {
    
    private static final long serialVersionUID = -3401253661087293274L;
    
    public static final String ACTION_COMMAND = "change_ups";
    
    public ChangeUPSAction(ActionManager actionManager, Simulator simulator) {
        super(actionManager, "Change UPS", ACTION_COMMAND, simulator);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof UPSController) {
            upsChanged(((UPSController) source).getUPS());
        }
    }
    
    private void upsChanged(int ups) {
        if (this.simulator.hasSimulation()) {
            this.simulator.setUPS(ups);
        }
    }
}
