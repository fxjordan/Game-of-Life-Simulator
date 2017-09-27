package de.fjobilabs.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.fjobilabs.gameoflife.gui.HighPerformanceCellRenderer;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 19.09.2017 - 19:57:52
 */
public class HighPerformanceCellRendererTest extends Game {
    
    private int[][] cells;
    private HighPerformanceCellRenderer cellRenderer;
    
    private OrthographicCamera camera;
    private FitViewport viewport;
    
    private FPSLogger fpsLogger = new FPSLogger();
    
    @Override
    public void create() {
        int width = 500;
        int height = 500;
        this.cells = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y=0; y<height; y++) {
                this.cells[x][y] = MathUtils.random(1);
            }
        }
        
        this.camera = new OrthographicCamera();
        this.camera.position.set(width * 0.5f, height * 0.5f, 0);
        this.camera.update();
        this.viewport = new FitViewport(width, height, this.camera);
        
        this.cellRenderer = new HighPerformanceCellRenderer();
        this.cellRenderer.configure(width, height);
        
        Gdx.gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }
    
    private boolean update;
    
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            this.update = !this.update;
        }
        
        if (this.update) {
            for (int x = 0; x < this.cells.length; x++) {
                int[] column = this.cells[x];
                for (int y=0; y<column.length; y++) {
                    this.cells[x][y] = MathUtils.random(1);
                }
            }
        }
        
        this.cellRenderer.begin(this.camera);
        
        for (int x = 0; x < this.cells.length; x++) {
            int[] column = this.cells[x];
            for (int y=0; y<column.length; y++) {
                this.cellRenderer.drawCell(x, y, column[y]);
            }
        }
        
        this.cellRenderer.end();
        
        this.fpsLogger.log();
    }
    
    @Override
    public void dispose() {
        this.cellRenderer.dispose();
    }
}
