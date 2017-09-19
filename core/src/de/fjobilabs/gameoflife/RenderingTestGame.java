package de.fjobilabs.gameoflife;

import java.nio.IntBuffer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 19.09.2017 - 19:57:52
 */
public class RenderingTestGame extends Game {
    
    private float[] vertices;
    private VertexBufferObject vertexBufferObject;
    private String vertexShader;
    private String fragmentShader;
    private ShaderProgram shaderProgramm;
    
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
        
        int width = 10;
        int height = 10;
        int numVertices = width * height;
        vertices = new float[numVertices * (3)];
        float x = 0;
        float y = 0;
        for (int i=0; i<vertices.length; i += 3) {
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
        
        this.vertexBufferObject = new VertexBufferObject(false, this.vertices.length / 3, this.vertexAttributes);
        
        this.camera = new OrthographicCamera();
        this.camera.position.set(width / 2, height / 2, 0);
        this.camera.update();
        this.viewport = new FitViewport(width, height, this.camera);
        
        this.vertexShader = Gdx.files.internal("shaders/vertexShader.glsl").readString();
        this.fragmentShader = Gdx.files.internal("shaders/fragmentShader.glsl").readString();
        this.shaderProgramm = new ShaderProgram(this.vertexShader, this.fragmentShader);
        
        // Create geometry shader
        String geometryShaderSource = Gdx.files.internal("shaders/geometryShader.glsl").readString();
        int geometryShader = Gdx.gl.glCreateShader(GL32.GL_GEOMETRY_SHADER);
        System.out.println("geometry shader id: " + geometryShader);
        if (geometryShader == 0) {
            throw new RuntimeException("Failed to create geometry shader");
        }
        Gdx.gl.glShaderSource(geometryShader, geometryShaderSource);
        Gdx.gl.glCompileShader(geometryShader);
        
        IntBuffer intBuf = BufferUtils.newIntBuffer(1);
        Gdx.gl.glGetShaderiv(geometryShader, GL20.GL_COMPILE_STATUS, intBuf);
        int compiled = intBuf.get(0);
        System.out.println("geometry shader compile status: " + compiled);
        if (compiled == 0) {
            Gdx.gl.glGetShaderiv(geometryShader, GL20.GL_INFO_LOG_LENGTH, intBuf);
            int logLength = intBuf.get(0);
            System.out.println("info_log_length=" + logLength);
            
            String log = Gdx.gl.glGetShaderInfoLog(geometryShader);
            System.out.println("SHADER_INFO_LOG_START=====");
            System.out.println(log);
            System.out.println("SHADER_INFO_LOG_END========");
            
            throw new RuntimeException("Geometry shader compillation failed");
        }
        
        
        this.u_projViewTrans = this.shaderProgramm.getUniformLocation("u_projViewTrans");
        this.u_worldTrans = this.shaderProgramm.getUniformLocation("u_worldTrans");
        
        this.vertexBufferObject.bind(this.shaderProgramm);
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
            for (int i=0; i<vertices.length; i += 3) {
                vertices[i + 2] = MathUtils.random(1); // Alive or dead
            }
            this.vertexBufferObject.bind(this.shaderProgramm);
            this.vertexBufferObject.setVertices(this.vertices, 0, this.vertices.length);
        }
        
        this.shaderProgramm.begin();
        
        this.shaderProgramm.setUniformMatrix(this.u_projViewTrans, this.camera.combined);
        this.shaderProgramm.setUniformMatrix(this.u_worldTrans, this.transformationMatrix);
        
        Gdx.gl.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        
        Gdx.gl.glDrawArrays(GL20.GL_POINTS, 0, this.vertexBufferObject.getNumVertices());
        
        this.shaderProgramm.end();
        
        this.fpsLogger.log();
    }
    
    @Override
    public void dispose() {
        this.vertexBufferObject.dispose();
        this.shaderProgramm.dispose();
    }
}
