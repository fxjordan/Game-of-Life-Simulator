package de.fjobilabs.gameoflife.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.GameOfLifeScreenManager;
import de.fjobilabs.gameoflife.gui.GameController;
import de.fjobilabs.gameoflife.gui.WorldRenderer;
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
    
    private static final int TICKS_PER_SECOND = 3;
    private static final float TICK = 1.0f / (float) TICKS_PER_SECOND;
    private static final float MAX_UPDATES_PER_FRAME = 5;
    
    private static final Logger logger = LoggerFactory.getLogger(GameScreen.class, Logger.DEBUG);
    
    private GameOfLifeAssetManager assetManager;
    private GameOfLifeScreenManager screenManager;
    
    private Simulation simulation;
    private WorldRenderer worldRenderer;
    private GameController gameController;
    private float time;
    
    public GameScreen(GameOfLifeAssetManager assetManager, GameOfLifeScreenManager screenManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
        
        // Test code:
        World world = new FixedSizeBorderedWorld(20, 20);
        
        world.setCellState(10, 11, Cell.ALIVE);
        world.setCellState(10, 10, Cell.ALIVE);
        world.setCellState(10, 9, Cell.ALIVE);
        world.setCellState(9, 10, Cell.ALIVE);
        world.setCellState(11, 11, Cell.ALIVE);
        
        RuleSet ruleSet = new StandardRuleSet();
        this.simulation = new Simulation(world, ruleSet);
        
        this.worldRenderer = new WorldRenderer(assetManager, world);
        
        this.gameController = new GameController(this.worldRenderer);
        Gdx.input.setInputProcessor(this.gameController);
    }
    
    @Override
    public void show() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 1, 1);
    }
    
    @Override
    public void resize(int width, int height) {
        this.worldRenderer.resize(width, height);
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        this.worldRenderer.render();
    }
    
    public void update(float delta) {
        // Fixed step updates
        this.time += delta;
        int updates = 0;
        while (time >= TICK && updates < MAX_UPDATES_PER_FRAME) {
            tick();
            updates++;
            this.time -= TICK;
        }
        
        // Normal updates
        this.gameController.update(delta);
    }
    
    public void tick() {
        this.simulation.update();
    }
}
