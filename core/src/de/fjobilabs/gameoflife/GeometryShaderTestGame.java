package de.fjobilabs.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectWithVAO;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.fjobilabs.libgdx.graphics.ExtendedShaderProgram;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 19.09.2017 - 19:57:52
 */
public class GeometryShaderTestGame extends Game {
    
    private float[] vertices;
    private VertexBufferObject vertexBufferObject;
    private ShaderProgram shaderProgram;
    
    private Matrix4 transformationMatrix;
    
    private OrthographicCamera camera;
    private FitViewport viewport;
    private int u_projViewTrans;
    private int u_worldTrans;
    private VertexAttributes vertexAttributes;
    
    private FPSLogger fpsLogger = new FPSLogger();
    
    @Override
    public void create() {
        this.transformationMatrix = new Matrix4().idt();
        
        int width = 5000;
        int height = 5000;
        int numVertices = width * height;
        vertices = new float[numVertices * (3)];
        float x = 0;
        float y = 0;
        for (int i = 0; i < vertices.length; i += 3) {
            vertices[i] = x;
            vertices[i + 1] = y;
            vertices[i + 2] = MathUtils.random(1); // Alive or dead
            x++;
            if (x >= width) {
                x = 0;
                y++;
            }
        }
        
        VertexAttribute positionVertexAttribute = new VertexAttribute(Usage.Position, 3, "a_position");
        this.vertexAttributes = new VertexAttributes(positionVertexAttribute);
        
        this.vertexBufferObject = new VertexBufferObject(false, this.vertices.length / 3,
                this.vertexAttributes);
        
        this.camera = new OrthographicCamera();
        this.camera.position.set(width * 0.5f, height * 0.5f, 0);
        this.camera.update();
        this.viewport = new FitViewport(width, height, this.camera);
        
        this.shaderProgram = new ExtendedShaderProgram(Gdx.files.internal("shaders/geometry-shader-test-game/vertexShader.glsl"),
                Gdx.files.internal("shaders/geometry-shader-test-game/fragmentShader.glsl"),
                Gdx.files.internal("shaders/geometry-shader-test-game/geometryShader.glsl"));
        
        this.u_projViewTrans = this.shaderProgram.getUniformLocation("u_projViewTrans");
        this.u_worldTrans = this.shaderProgram.getUniformLocation("u_worldTrans");
        
        this.vertexBufferObject.bind(this.shaderProgram);
        this.vertexBufferObject.setVertices(this.vertices, 0, this.vertices.length);
        
        Gdx.gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }
    
    private boolean update;
    
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            this.update = !this.update;
        }
        
        if (this.update) {
            for (int i = 0; i < vertices.length; i += 3) {
                vertices[i + 2] = MathUtils.random(1); // Alive or dead
            }
            this.vertexBufferObject.bind(this.shaderProgram);
            this.vertexBufferObject.setVertices(this.vertices, 0, this.vertices.length);
        }
        
        this.shaderProgram.begin();
        
        this.shaderProgram.setUniformMatrix(this.u_projViewTrans, this.camera.combined);
        this.shaderProgram.setUniformMatrix(this.u_worldTrans, this.transformationMatrix);
        
        Gdx.gl.glDrawArrays(GL20.GL_POINTS, 0, this.vertexBufferObject.getNumVertices());
        
        this.shaderProgram.end();
        
        this.fpsLogger.log();
    }
    
    @Override
    public void dispose() {
        this.vertexBufferObject.dispose();
        this.shaderProgram.dispose();
    }
}
