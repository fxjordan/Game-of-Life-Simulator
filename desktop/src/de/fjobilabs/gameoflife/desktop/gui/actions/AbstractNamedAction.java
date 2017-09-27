package de.fjobilabs.gameoflife.desktop.gui.actions;

import javax.swing.AbstractAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 13:30:52
 */
public abstract class AbstractNamedAction extends AbstractAction {
    
    private static final long serialVersionUID = 6697637127644927436L;
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractNamedAction.class);
    
    protected final ActionManager actionManager;
    
    public AbstractNamedAction(ActionManager actionManager, String name, String actionCommand) {
        this.actionManager = actionManager;
        putValue(NAME, name);
        putValue(ACTION_COMMAND_KEY, actionCommand);
        logger.debug("Created named action (name: {}, actionCommand: {})", name, actionCommand);
    }
}
