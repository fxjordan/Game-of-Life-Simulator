package de.fjobilabs.gameoflife.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 24.09.2017 - 17:47:09
 */
public class IdleScreen extends ScreenAdapter {
    
    private static final float SCENE_WIDTH = 800;
    private static final float SCENE_HEIGHT = 480;
    
    private static final Logger logger = LoggerFactory.getLogger(IdleScreen.class, Logger.DEBUG);
    
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;
    private Sprite idleSprite;
    
    public IdleScreen(GameOfLifeAssetManager assetManager) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, this.camera);
        this.batch = new SpriteBatch();
        initGUI(assetManager);
    }
    
    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        logger.info("Idle");
    }
    
    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.idleSprite.draw(this.batch);
        this.batch.end();
    }
    
    @Override
    public void dispose() {
        this.batch.dispose();
    }
    
    private void initGUI(GameOfLifeAssetManager assetManager) {
        Texture idleTexture = assetManager.instantLoadAsset(GameOfLifeAssetManager.IDLE_IMAGE);
        this.idleSprite = new Sprite(idleTexture);
        this.idleSprite.setBounds(SCENE_WIDTH * 0.5f - SCENE_WIDTH * 0.25f * 0.5f,
                SCENE_HEIGHT * 0.5f - SCENE_HEIGHT * 0.25f * 0.5f, SCENE_WIDTH * 0.25f, SCENE_HEIGHT * 0.25f);
    }
}
