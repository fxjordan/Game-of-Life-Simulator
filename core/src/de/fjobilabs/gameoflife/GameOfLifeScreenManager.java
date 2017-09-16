package de.fjobilabs.gameoflife;

import com.badlogic.gdx.Screen;

import de.fjobilabs.gameoflife.gui.screen.GameScreen;
import de.fjobilabs.gameoflife.gui.screen.LoadingScreen;
import de.fjobilabs.libgdx.ScreenManager;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 21:05:56
 */
public class GameOfLifeScreenManager {
    
    public static final String LOADING_SCREEN = "loading_screen";
    public static final String MAIN_MENU_SCREEN = "main_menu_screen";
    public static final String GAME_SCREEN = "game_screen";
    
    private final GameOfLifeGame game;
    private final ScreenManager screenManager;
    
    protected GameOfLifeScreenManager(GameOfLifeGame game) {
        this.game = game;
        this.screenManager = new ScreenManager(game);
    }
    
    public Screen getScreen(String id) {
        Screen screen = this.screenManager.getScreen(id);
        if (screen == null) {
            screen = createScreen(id);
            this.screenManager.addScreen(id, screen);
        }
        return screen;
    }
    
    public Screen showScreen(String id) {
        // Ensures that the screen already is created
        getScreen(id);
        return this.screenManager.showScreen(id);
    }
    
    public void dispose() {
        this.screenManager.disposeAll();
    }
    
    private Screen createScreen(String id) {
        switch (id) {
        case LOADING_SCREEN:
            return new LoadingScreen(this.game.getAssetManager(), this);
        case GAME_SCREEN:
             return new GameScreen(this.game.getAssetManager(), this);
        }
        throw new IllegalArgumentException("Unknown screen id: " + id);
    }
}
