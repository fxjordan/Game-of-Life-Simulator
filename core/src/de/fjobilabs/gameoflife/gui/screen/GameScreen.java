package de.fjobilabs.gameoflife.gui.screen;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.GameOfLifeScreenManager;
import de.fjobilabs.gameoflife.gui.SimulationController;
import de.fjobilabs.gameoflife.gui.WorldRenderer;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.simulation.CellularAutomatonSimulation;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEParser;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEPattern;
import de.fjobilabs.gameoflife.model.simulation.ca.RuleSet;
import de.fjobilabs.gameoflife.model.simulation.ca.rules.StandardGameOfLifeRuleSet;
import de.fjobilabs.gameoflife.model.worlds.FixedSizeTorusWorld;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 14:55:UNKNOWN
 */
@Deprecated
public class GameScreen extends ScreenAdapter {
    
    private static final int TICKS_PER_SECOND = 10;
    private static final float TICK = 1.0f / (float) TICKS_PER_SECOND;
    private static final float MAX_UPDATES_PER_FRAME = 1;
    
    private static final Logger logger = LoggerFactory.getLogger(GameScreen.class, Logger.DEBUG);
    
    private GameOfLifeAssetManager assetManager;
    private GameOfLifeScreenManager screenManager;
    
    private Simulation simulation;
    private WorldRenderer worldRenderer;
    private SimulationController gameController;
    private float time;
    
    private FPSLogger fpsLogger;
    
    public GameScreen(GameOfLifeAssetManager assetManager, GameOfLifeScreenManager screenManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
        
        // Test code:
        // World world = new FixedSizeBorderedWorld(10, 10);
        
        World world = new FixedSizeTorusWorld(50, 50);
        
//        Pattern pattern = new ArrayPattern(3, 3,
//                new int[][] {
//            new int[] {0, 1, 0},
//            new int[] {0, 0, 1},
//            new int[] {1, 1, 1}});
//        
//        pattern.apply(world, 250, 250);
        
        RLEParser parser = new RLEParser(Gdx.files.internal("patterns/turingmachine.rle").read());
        try {
            parser.parse();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse RLE file", e);
        }
        RLEPattern pattern = parser.createPattern();
        int x = world.getCenterX() - pattern.getWidth() / 2;
        int y = world.getCenterY() + pattern.getHeight() / 2;
//        pattern.apply(world, x, y);
        
//         RuleSet ruleSet = new GameOfLifeRuleSet("1357/1357", new int[] {1, 3,
        // 5, 7}, new int[] {1, 3, 5, 7});
//         RuleSet ruleSet = GameOfLifeRuleSet.parse("1357/1357");
        RuleSet ruleSet = new StandardGameOfLifeRuleSet();
        
        this.simulation = new CellularAutomatonSimulation(world, ruleSet);
        // this.simulation = new LangtonsAntSimulation(world);
        
        this.worldRenderer = new WorldRenderer(assetManager);
        
        this.worldRenderer.setWorld(world);
        
//        this.worldRenderer.setZoom(46);
        
        this.gameController = new SimulationController(this.worldRenderer);
        this.gameController.setSimulation(this.simulation);
        Gdx.input.setInputProcessor(this.gameController);
        
//        this.simulation.setRunning(true);
//        for (int i=0; i<11040; i++) {
//            this.simulation.update();
//        }
//        this.simulation.setRunning(false);
        
        this.fpsLogger = new FPSLogger();
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
        
        this.fpsLogger.log();
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
    
    @Override
    public void dispose() {
        this.worldRenderer.dispose();
    }
}
