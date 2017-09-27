package de.fjobilabs.gameoflife.desktop;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.fjobilabs.libgdx.graphics.ExtendedShaderProgram;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 19.09.2017 - 19:57:52
 */
public class GeometryShaderRenderingTest extends Game {
    
    private OrthographicCamera camera;
    private FitViewport viewport;
//    private GeometryShaderProgram box;
    private ExtendedShaderProgram shaderProgram;
    
    private int u_projViewTrans;
    private int u_worldTrans;
    
    private Matrix4 transformationMatrix;
    private int u_test;
    
    @Override
    public void create() {
        this.transformationMatrix = new Matrix4().idt();
        
        int width = 100;
        int height = 100;
        
        this.camera = new OrthographicCamera();
        this.camera.position.set(width / 2, height / 2, 0);
        this.camera.zoom = 0.25f;
        this.camera.update();
        this.viewport = new FitViewport(width, height, this.camera);
        
//        this.box = new GeometryShaderProgram();
        
        this.shaderProgram = new ExtendedShaderProgram(
                Gdx.files.internal("shaders/geometry-shader-test/vertexShader.glsl"),
                Gdx.files.internal("shaders/geometry-shader-test/fragmentShader.glsl"),
                Gdx.files.internal("shaders/geometry-shader-test/geometryShader.glsl"));
        
//        this.u_projViewTrans = this.box.getUniformLocation("u_projViewTrans");
//        this.u_worldTrans = this.box.getUniformLocation("u_worldTrans");
//        this.u_test = this.box.getUniformLocation("u_test");
        
        this.u_projViewTrans = this.shaderProgram.getUniformLocation("u_projViewTrans");
        this.u_worldTrans = this.shaderProgram.getUniformLocation("u_worldTrans");
        this.u_test = this.shaderProgram.getUniformLocation("u_test");
        
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        System.out.println("created");
    }
    
    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
//        box.begin();
        this.shaderProgram.begin();
        
//        this.box.setUniformMatrix(this.u_projViewTrans, this.camera.combined);
//        this.box.setUniformMatrix(this.u_worldTrans, this.transformationMatrix);
//        this.box.setUniformi(this.u_test, 42);
        this.shaderProgram.setUniformMatrix(this.u_projViewTrans, this.camera.combined);
        this.shaderProgram.setUniformMatrix(this.u_worldTrans, this.transformationMatrix);
        this.shaderProgram.setUniformi(this.u_test, 42);
        
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex3f(50.0f, 52.0f, 0.0f);
        GL11.glVertex3f(70.0f, 80.0f, 0.0f);
        GL11.glVertex3f(50.0f, 50.0f, 0.0f);
        GL11.glVertex3f(80.0f, 40.0f, 0.0f);
        GL11.glEnd();
        
//        box.end();
        this.shaderProgram.end();
    }
    
    @Override
    public void dispose() {
//        this.box.dispose();
        this.shaderProgram.dispose();
    }
}
