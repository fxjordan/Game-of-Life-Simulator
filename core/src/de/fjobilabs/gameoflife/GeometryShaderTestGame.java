package de.fjobilabs.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.fjobilabs.libgdx.graphics.ExtendedShaderProgram;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 19.09.2017 - 19:57:52
 */
public class GeometryShaderTestGame extends Game {
    
    private float[] vertexPositions;
    private float[] vertexStates;
    private VertexBufferObject positionVertexBufferObject;
    private VertexBufferObject stateVertexBufferObject;
    private ShaderProgram shaderProgram;
    
    private OrthographicCamera camera;
    private FitViewport viewport;
    private int u_projViewTrans;
    
    
    private FPSLogger fpsLogger = new FPSLogger();
    
    @Override
    public void create() {
        int width = 50;
        int height = 50;
        int numVertices = width * height;
        vertexPositions = new float[numVertices * (2)];
        vertexStates = new float[numVertices];
        float x = 0;
        float y = 0;
        for (int i = 0; i < numVertices; i++) {
            vertexPositions[i * 2] = x;
            vertexPositions[i * 2 + 1] = y;
            vertexStates[i] = MathUtils.random(1);
            x++;
            if (x >= width) {
                x = 0;
                y++;
            }
        }
        
        VertexAttribute positionVertexAttribute = new VertexAttribute(Usage.Position, 2, "a_position");
        this.positionVertexBufferObject = new VertexBufferObject(true, numVertices, positionVertexAttribute);
        
        VertexAttribute stateVertexAttribute = new VertexAttribute(Usage.Generic, 1, GL20.GL_FLOAT,
                true, "a_state");
        this.stateVertexBufferObject = new VertexBufferObject(false, numVertices, stateVertexAttribute);
        
        this.camera = new OrthographicCamera();
        this.camera.position.set(width * 0.5f, height * 0.5f, 0);
        this.camera.update();
        this.viewport = new FitViewport(width, height, this.camera);
        
        this.shaderProgram = new ExtendedShaderProgram(
                Gdx.files.internal("shaders/geometry-shader-test-game/vertexShader.glsl"),
                Gdx.files.internal("shaders/geometry-shader-test-game/fragmentShader.glsl"),
                Gdx.files.internal("shaders/geometry-shader-test-game/geometryShader.glsl"));
        
        this.u_projViewTrans = this.shaderProgram.getUniformLocation("u_projViewTrans");
        
        this.positionVertexBufferObject.bind(this.shaderProgram);
        this.positionVertexBufferObject.setVertices(this.vertexPositions, 0, this.vertexPositions.length);
        
        this.stateVertexBufferObject.bind(this.shaderProgram);
        this.stateVertexBufferObject.setVertices(this.vertexStates, 0, this.vertexStates.length);
        
        Gdx.gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }
    
    private boolean update;
    
    @Override
    public void render() {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            this.update = !this.update;
        }
        
        if (this.update) {
            for (int i = 0; i < vertexStates.length; i++) {
                vertexStates[i] = MathUtils.random(1); // Alive or dead
            }
            this.stateVertexBufferObject.bind(this.shaderProgram);
            this.stateVertexBufferObject.setVertices(this.vertexStates, 0, this.vertexStates.length);
        }
        
        this.shaderProgram.begin();
        
        this.shaderProgram.setUniformMatrix(this.u_projViewTrans, this.camera.combined);
        
        Gdx.gl.glDrawArrays(GL20.GL_POINTS, 0, this.positionVertexBufferObject.getNumVertices());
        
        this.shaderProgram.end();
        
        this.fpsLogger.log();
    }
    
    @Override
    public void dispose() {
        this.positionVertexBufferObject.dispose();
        this.stateVertexBufferObject.dispose();
        this.shaderProgram.dispose();
    }
}
