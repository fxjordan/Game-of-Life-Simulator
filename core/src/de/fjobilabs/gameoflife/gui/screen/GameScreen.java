package de.fjobilabs.gameoflife.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.GameOfLifeScreenManager;
import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.RuleSet;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.rules.standard.StandardRuleSet;
import de.fjobilabs.gameoflife.model.worlds.FixedSizeBorderedWorld;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 14:55:UNKNOWN
 */
public class GameScreen extends ScreenAdapter {
    
    private static final float SCENE_WIDTH = 800;
    private static final float SCENE_HEIGHT = 480;
    
    private static final Logger logger = LoggerFactory.getLogger(GameScreen.class, Logger.DEBUG);
    
    private GameOfLifeAssetManager assetManager;
    private GameOfLifeScreenManager screenManager;
    
    private Simulation simulation;
    
    public GameScreen(GameOfLifeAssetManager assetManager, GameOfLifeScreenManager screenManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
        
        // Test code:
        World world = new FixedSizeBorderedWorld(20, 20);
        
        world.setCellState(9, 10, Cell.ALIVE);
        world.setCellState(10, 10, Cell.ALIVE);
        world.setCellState(11, 10, Cell.ALIVE);
        world.setCellState(10, 11, Cell.ALIVE);
        world.setCellState(10, 9, Cell.ALIVE);
        
        RuleSet ruleSet = new StandardRuleSet();
        this.simulation = new Simulation(world, ruleSet);
    }
    
    @Override
    public void show() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
    }
    
    @Override
    public void resize(int width, int height) {
        
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
    public void update(float delta) {
        this.simulation.update();
    }
}
