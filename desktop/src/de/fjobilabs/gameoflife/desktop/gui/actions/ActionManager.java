package de.fjobilabs.gameoflife.desktop.gui.actions;

import java.util.HashMap;

import javax.swing.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 12:45:23
 */
public class ActionManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ActionManager.class);
    
    private HashMap<String, Action> actions;
    
    public ActionManager() {
        this.actions = new HashMap<>();
    }
    
    public void registerAction(Action action) {
        String key = (String) action.getValue(Action.ACTION_COMMAND_KEY);
        if (key == null) {
            throw new IllegalArgumentException("Action must have an unique action command (not null)");
        }
        if (this.actions.containsKey(key)) {
            throw new IllegalArgumentException("Action with key '" + key + "' already registered");
        }
        this.actions.put(key, action);
        logger.info("New action registered: " + key);
    }
    
    public Action getAction(String actionCommand) {
        Action action = this.actions.get(actionCommand);
        if (action == null) {
            throw new IllegalArgumentException("Unknown action command: " + actionCommand);
        }
        return action;
    }
    
    public void setActionEnabled(String actionCommand, boolean enabled) {
        getAction(actionCommand).setEnabled(enabled);
        logger.info("Disabled action: " + actionCommand);
    }
}
