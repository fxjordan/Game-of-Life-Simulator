package de.fjobilabs.libgdx.graphics;

import java.lang.reflect.Field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.utils.reflect.Method;

/**
 * An {@code ExtendedShaderProgram} encapsulates a vertex, fragment and geometry
 * shader linked to form a shader program.<br>
 * <br>
 * <b>Important:</b> Geometry shaders can only be used with the
 * 'gdx-backend-lwjgl' backend, because OpenGL is used for rendering. The other
 * backends use OpenGL ES, which currently does not support geometry
 * shaders.<br>
 * <br>
 * Have a look at the {@link ShaderProgram} documentation for a detailed
 * description of shader programs.<br>
 * <br>
 * <i>Implementation note:</i><br>
 * Since the {@code ShaderProgram} uses private fields and methods to load/link
 * shaders and access uniform variables this class has to use reflection to
 * access some data. This is bad practice, but necessary in this case. Otherwise
 * this class could not extend {@code ShaderProgram}, which would made it
 * impossible to use other APIs, which work with a {@code ShaderProgram} (e.g.
 * {@link VertexBufferObject}, {@link SpriteBatch}).<br>
 * It's also unusual to create, load and link a shader program and after that
 * attach another shader and link the program a second time. But for the same
 * reason it's necessary, because the creation, loading and linking of the
 * program is done in the constructor of {@code ShaderProgram} which has to be
 * called before the constructor of this class. So there is no other way to
 * solve this without using bytecode modification, which would make the code
 * even worse.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 20.09.2017 - 22:56:02
 */
public class ExtendedShaderProgram extends ShaderProgram {
    
    /**
     * Code that is always added to the geometry shader code, typically used to
     * inject a #version line. Note that this is added as-is, you should include
     * a newline (`\n`) if needed.
     */
    public static String prependGeometryCode = "";
    
    private ShaderProgrammAccessor shaderProgrammAccessor;
    
    /** Geometry shader handle **/
    private String geometryShaderSource;
    
    /** Geometry shader source **/
    private int geometryShaderHandle;
    
    public ExtendedShaderProgram(String vertexShader, String fragmentShader, String geometryShader) {
        super(vertexShader, fragmentShader);
        this.shaderProgrammAccessor = new ShaderProgrammAccessor(this);
        
        if (geometryShader == null) {
            throw new IllegalArgumentException("Geometry shader must not be null");
        }
        if (prependGeometryCode != null && prependGeometryCode.length() > 0) {
            geometryShader = prependGeometryCode + geometryShader;
        }
        this.geometryShaderSource = geometryShader;
        
        compileShaders(geometryShader);
        if (isCompiled()) {
            this.shaderProgrammAccessor.fetchAttributes();
            this.shaderProgrammAccessor.fetchUniforms();
        }
        
        // TODO Add geometry shader to existing shader program
        // See parent constructor for reference
        // 1. Load and compile geometry shader
        // 2. Access underlying shader program though reflection.
        // 3. Attach geometry shader to existing program
        // 4. Link shader program (+check status and validate)
        // [5. Fetch attributes again (reflection)] (first test vertex
        // attributes)
        // 5. Fetch uniforms again (reflection)
    }
    
    public ExtendedShaderProgram(FileHandle vertexShader, FileHandle fragmentShader,
            FileHandle geometryShader) {
        this(vertexShader.readString(), fragmentShader.readString(), geometryShader.readString());
    }
    
    /**
     * Compiles the geometry shader and uses the already compiled vertex and
     * fragment shaders from the {@code ShaderProgram} class to create a shader
     * program.
     * 
     * @param geometryShader The geometry shader source.
     */
    private void compileShaders(String geometryShader) {
        int vertexShaderHandle = this.shaderProgrammAccessor.getVertexShaderHandle();
        int fragmentShaderHandle = this.shaderProgrammAccessor.getFragmentShaderHandle();
        
        this.geometryShaderHandle = this.shaderProgrammAccessor.loadShader(GL32.GL_GEOMETRY_SHADER,
                geometryShader);
        
        if (vertexShaderHandle == -1 || fragmentShaderHandle == -1 || this.geometryShaderHandle == -1) {
            this.shaderProgrammAccessor.setIsCompiled(false);
            return;
        }
        
        int program = this.shaderProgrammAccessor.getProgramm();
        if (program == -1) {
            program = createProgram();
        } else {
            detachShaders(program);
        }
        
        // Only attach geometry shader. linkProgram(int) will do the rest.
        Gdx.gl.glAttachShader(program, this.geometryShaderHandle);
        program = this.shaderProgrammAccessor.linkProgram(program);
        if (program == -1) {
            this.shaderProgrammAccessor.setIsCompiled(false);
            return;
        }
        
        this.shaderProgrammAccessor.setIsCompiled(true);
    }
    
    private void detachShaders(int program) {
        GL20 gl = Gdx.gl;
        gl.glDetachShader(program, this.shaderProgrammAccessor.getVertexShaderHandle());
        gl.glDetachShader(program, this.shaderProgrammAccessor.getFragmentShaderHandle());
    }
    
}
