package de.fjobilabs.gameoflife;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 18:42:51
 */
public class GameOfLifeGame extends Game {
    
    private static final Logger logger = LoggerFactory.getLogger(GameOfLifeGame.class);
    
    private GameOfLifeAssetManager assetManager;
    private GameOfLifeScreenManager screenManager;
    
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        
        this.assetManager = new GameOfLifeAssetManager();
        this.screenManager = new GameOfLifeScreenManager(this);
        
        this.assetManager.load();
        
        this.screenManager.showScreen(GameOfLifeScreenManager.LOADING_SCREEN);
        
        logger.info("Game created");
    }
    
    @Override
    public void dispose() {
        this.screenManager.dispose();
    }
    
    public GameOfLifeAssetManager getAssetManager() {
        return assetManager;
    }
    
    public GameOfLifeScreenManager getScreenManager() {
        return screenManager;
    }
}
