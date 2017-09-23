package de.fjobilabs.gameoflife.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.GameOfLifeScreenManager;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 19:06:06
 */
public class LoadingScreen extends ScreenAdapter {
    
    private static final float SCENE_WIDTH = 800;
    private static final float SCENE_HEIGHT = 480;
    
    private static final Logger logger = LoggerFactory.getLogger(LoadingScreen.class, Logger.DEBUG);
    
    private GameOfLifeAssetManager assetManager;
    private GameOfLifeScreenManager screenManager;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Batch batch;
    private Sprite loadingSprite;
    private float loadingTime;
    
    public LoadingScreen(GameOfLifeAssetManager assetManager, GameOfLifeScreenManager screenManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, this.camera);
        this.batch = new SpriteBatch();
        initGUI();
    }
    
    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        logger.info("Loading assets...");
    }
    
    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }
    
    @Override
    public void render(float delta) {
        this.loadingTime += delta;
        
        if (this.assetManager.update(delta)) {
            logger.info("Assets loaded in " + this.loadingTime + " seconds");
            this.screenManager.showScreen(GameOfLifeScreenManager.GAME_SCREEN);
            return;
        }
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.loadingSprite.draw(this.batch);
        this.batch.end();
    }
    
    private void initGUI() {
        Texture loadingTexture = this.assetManager.instantLoadAsset(GameOfLifeAssetManager.LOADING_IMAGE);
        this.loadingSprite = new Sprite(loadingTexture);
        this.loadingSprite.setBounds(SCENE_WIDTH * 0.5f - SCENE_WIDTH * 0.75f * 0.5f,
                SCENE_HEIGHT * 0.5f - SCENE_HEIGHT * 0.75f * 0.5f, SCENE_WIDTH * 0.75f, SCENE_HEIGHT * 0.75f);
    }
}
