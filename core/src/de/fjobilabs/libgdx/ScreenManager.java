package de.fjobilabs.libgdx;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 15:03:22
 */
public class ScreenManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreenManager.class, Logger.DEBUG);
    
    private final Game game;
    private final Map<String, Screen> screens;
    
    public ScreenManager(Game game) {
        this.game = game;
        this.screens = new HashMap<>();
    }
    
    /**
     * Adds a new {@link Screen} to the manager.
     * 
     * @param id The screen id.
     * @param screen The screen to add.
     */
    public void addScreen(String id, Screen screen) {
        checkIdAlreadyExists(id);
        checkAlreadyManaged(screen);
        this.screens.put(id, screen);
        logger.debug("Screen added: " + createScreenDetails(id, screen));
    }
    
    /**
     * Return the {@link Screen} for the given id.
     * 
     * @param id The screen id.
     * @param screen The screen for the id.
     */
    public Screen getScreen(String id) {
        return this.screens.get(id);
    }
    
    /**
     * Removes a managed {@link Screen} without disposing it.
     * 
     * @param id The screen id.
     * @return The screen associated with the id, or <code>null</code> if there
     *         was no screen.
     */
    public Screen removeScreen(String id) {
        Screen screen = this.screens.remove(id);
        logger.debug("Screen removed " + createScreenDetails(id, screen));
        return screen;
    }
    
    /**
     * Sets the screen with the given id as the current screen in the game.<br>
     * {@link Screen#hide()} is called on the old screen, and
     * {@link Screen#show()} is called on the new screen.
     * 
     * @param id The screen id.
     * @return The new active screen for the given id.
     */
    public Screen showScreen(String id) {
        Screen screen = this.screens.get(id);
        if (screen == null) {
            throw new ScreenManagerException("Screen with id '" + id + "' does not exist");
        }
        logger.debug("Showing screen " + createScreenDetails(id, screen));
        try {
            this.game.setScreen(screen);
        } catch (Exception e) {
            logger.error("Failed to show screen " + createScreenDetails(id, screen));
            throw new ScreenManagerException("Exception while showing screen", e);
        }
        logger.debug("Screen was shown successfully " + createScreenDetails(id, screen));
        return screen;
    }
    
    /**
     * Adds the screen to the manager and sets it as the current screen in the
     * game.<br>
     * {@link Screen#hide()} is called on the old screen, and
     * {@link Screen#show()} is called on the new screen.
     * 
     * @param id The screen id.
     * @return The new active screen.
     */
    public Screen showScreen(String id, Screen screen) {
        addScreen(id, screen);
        return showScreen(id);
    }
    
    /**
     * Disposes the {@link Screen} with the given id.
     * 
     * @param id The screen id.
     * @return <code>true</code> if a screen with the id has existed,
     *         <code>false</code> if not.
     */
    public boolean disposeScreen(String id) {
        Screen screen = this.screens.get(id);
        if (screen == null) {
            logger.debug("No managed screen to dispose for id '" + id + "'");
            return false;
        }
        this.screens.remove(id);
        disposeScreen(id, screen);
        return true;
    }
    
    private void disposeScreen(String id, Screen screen) {
        logger.debug("Disposing screen " + createScreenDetails(id, screen));
        try {
            screen.dispose();
        } catch (Exception e) {
            logger.error("Failed to dispose screen " + createScreenDetails(id, screen));
            throw new ScreenManagerException("Exception while disposing screen", e);
        }
        logger.debug("Screen disposed successfully " + createScreenDetails(id, screen));
    }
    
    /**
     * Disposes all screens managed by this instance.
     */
    public void disposeAll() {
        logger.debug("Disposing all screens");
        this.screens.forEach(this::disposeScreen);
        this.screens.clear();
        logger.debug("All screens disposed successfully");
    }
    
    private void checkIdAlreadyExists(String id) {
        if (this.screens.containsKey(id)) {
            throw new ScreenManagerException("Screen with id '" + id + "' already exists");
        }
    }
    
    private void checkAlreadyManaged(Screen screen) {
        if (this.screens.containsValue(screen)) {
            throw new ScreenManagerException(
                    "Screen '" + screen + "' is already managed under id '" + getIdForScreen(screen) + "'");
        }
    }
    
    private String getIdForScreen(Screen screen) {
        Entry<String, Screen> screenEntry = this.screens.entrySet().stream()
                .filter(entry -> entry.getValue().equals(screen)).findFirst().orElse(null);
        if (screenEntry != null) {
            return screenEntry.getKey();
        }
        return null;
    }
    
    private static String createScreenDetails(String id, Screen screen) {
        StringBuilder builder = new StringBuilder("(id: ");
        builder.append(id);
        builder.append(", screen: ");
        builder.append(screen);
        builder.append(')');
        return builder.toString();
    }
}
