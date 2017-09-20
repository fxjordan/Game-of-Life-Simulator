package de.fjobilabs.gameoflife.desktop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ObjectIntMap;

import de.fjobilabs.libgdx.graphics.ExtendedShaderProgram;
import de.fjobilabs.libgdx.graphics.GL32;

public class GeometryShaderProgram {
    
    private int program = 0;
    
    public GeometryShaderProgram() {
        int vertShader = 0;
        int fragShader = 0;
        int geometryShader = 0;
        
        try {
            vertShader = createShader("shaders/geometry-shader-test/vertexShader.glsl", GL20.GL_VERTEX_SHADER);
            geometryShader = createShader("shaders/geometry-shader-test/geometryShader.glsl", GL32.GL_GEOMETRY_SHADER);
            fragShader = createShader("shaders/geometry-shader-test/fragmentShader.glsl", GL20.GL_FRAGMENT_SHADER);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        } finally {
            if (vertShader == 0 || geometryShader == 0 || fragShader == 0)
                return;
        }
        
        GL20 gl = Gdx.gl20;
        program = Gdx.gl.glCreateProgram();
        
        if (program == 0) {
            return;
        }
        
        gl.glAttachShader(program, vertShader);
//        gl.glAttachShader(program, geometryShader);
        gl.glAttachShader(program, fragShader);
        
        System.out.println("linking first time...");
        gl.glLinkProgram(program);
        
        ByteBuffer tmp = ByteBuffer.allocateDirect(4);
        tmp.order(ByteOrder.nativeOrder());
        IntBuffer intbuf = tmp.asIntBuffer();
        
        gl.glGetProgramiv(this.program, GL20.GL_LINK_STATUS, intbuf);
        int linked = intbuf.get(0);
        if (linked == GL20.GL_FALSE) {
            String log = gl.glGetProgramInfoLog(this.program);
            System.err.println(log);
            return;
        }
        
        gl.glValidateProgram(program);
        intbuf.clear();
        gl.glGetProgramiv(this.program, GL20.GL_VALIDATE_STATUS, intbuf);
        int valid = intbuf.get(0);
        if (valid == GL20.GL_FALSE) {
            String log = gl.glGetProgramInfoLog(this.program);
            System.err.println(log);
            return;
        }
        
        fetchUniforms();
        
        // Extra stuff
        
        System.out.println("Attaching geometry shader");
        gl.glAttachShader(this.program, geometryShader);
        
        System.out.println("linking second time...");
        gl.glLinkProgram(program);
        
//        ByteBuffer tmp = ByteBuffer.allocateDirect(4);
//        tmp.order(ByteOrder.nativeOrder());
//        IntBuffer intbuf = tmp.asIntBuffer();
        
        gl.glGetProgramiv(this.program, GL20.GL_LINK_STATUS, intbuf);
        intbuf.clear();
        linked = intbuf.get(0);
        if (linked == GL20.GL_FALSE) {
            String log = gl.glGetProgramInfoLog(this.program);
            System.err.println(log);
            return;
        }
        
        gl.glValidateProgram(program);
        intbuf.clear();
        gl.glGetProgramiv(this.program, GL20.GL_VALIDATE_STATUS, intbuf);
        valid = intbuf.get(0);
        if (valid == GL20.GL_FALSE) {
            String log = gl.glGetProgramInfoLog(this.program);
            System.err.println(log);
            return;
        }
        
        fetchUniforms();
    }
    
    /** uniform lookup **/
    private final ObjectIntMap<String> uniforms = new ObjectIntMap<String>();
    
    /** uniform types **/
    private final ObjectIntMap<String> uniformTypes = new ObjectIntMap<String>();
    
    /** uniform sizes **/
    private final ObjectIntMap<String> uniformSizes = new ObjectIntMap<String>();
    
    /** uniform names **/
    private String[] uniformNames;
    
    IntBuffer params = BufferUtils.newIntBuffer(1);
    IntBuffer type = BufferUtils.newIntBuffer(1);
    
    public void setUniformi(int location, int value) {
        com.badlogic.gdx.graphics.GL20 gl = Gdx.gl20;
        gl.glUniform1i(location, value);
    }
    
    private void fetchUniforms() {
        params.clear();
        Gdx.gl20.glGetProgramiv(program, GL20.GL_ACTIVE_UNIFORMS, params);
        int numUniforms = params.get(0);
        
        uniformNames = new String[numUniforms];
        
        for (int i = 0; i < numUniforms; i++) {
            params.clear();
            params.put(0, 1);
            type.clear();
            String name = Gdx.gl20.glGetActiveUniform(program, i, params, type);
            int location = Gdx.gl20.glGetUniformLocation(program, name);
            uniforms.put(name, location);
            uniformTypes.put(name, type.get(0));
            uniformSizes.put(name, params.get(0));
            uniformNames[i] = name;
        }
    }
    
    /**
     * Sets the uniform matrix with the given name. The
     * {@link ExtendedShaderProgram} must be bound for this to work.
     * 
     * @param name the name of the uniform
     * @param matrix the matrix
     */
    public void setUniformMatrix(String name, Matrix4 matrix) {
        setUniformMatrix(name, matrix, false);
    }
    
    /**
     * Sets the uniform matrix with the given name. The
     * {@link ExtendedShaderProgram} must be bound for this to work.
     * 
     * @param name the name of the uniform
     * @param matrix the matrix
     * @param transpose whether the matrix should be transposed
     */
    public void setUniformMatrix(String name, Matrix4 matrix, boolean transpose) {
        setUniformMatrix(fetchUniformLocation(name), matrix, transpose);
    }
    
    public void setUniformMatrix(int location, Matrix4 matrix) {
        setUniformMatrix(location, matrix, false);
    }
    
    public void setUniformMatrix(int location, Matrix4 matrix, boolean transpose) {
        com.badlogic.gdx.graphics.GL20 gl = Gdx.gl20;
//        checkManaged();
        gl.glUniformMatrix4fv(location, 1, transpose, matrix.val, 0);
    }
    
    private int fetchUniformLocation(String name) {
        return fetchUniformLocation(name, true); // truie shoudl be pedantic
    }
    
    public int fetchUniformLocation(String name, boolean pedantic) {
        com.badlogic.gdx.graphics.GL20 gl = Gdx.gl20;
        // -2 == not yet cached
        // -1 == cached but not found
        int location;
        if ((location = uniforms.get(name, -2)) == -2) {
            location = gl.glGetUniformLocation(program, name);
            if (location == -1 && pedantic)
                throw new IllegalArgumentException("no uniform with name '" + name + "' in shader");
            uniforms.put(name, location);
        }
        return location;
    }
    
    /**
     * @param name the name of the uniform
     * @return the location of the uniform or -1.
     */
    public int getUniformLocation(String name) {
        return uniforms.get(name, -1);
    }
    
    /**
     * Makes OpenGL ES 2.0 use this vertex and fragment shader pair. When you
     * are done with this shader you have to call
     * {@link ExtendedShaderProgram#end()}.
     */
    public void begin() {
        com.badlogic.gdx.graphics.GL20 gl = Gdx.gl20;
        gl.glUseProgram(program);
    }
    
    /**
     * Disables this shader. Must be called when one is done with the shader.
     * Don't mix it with dispose, that will release the shader resources.
     */
    public void end() {
        com.badlogic.gdx.graphics.GL20 gl = Gdx.gl20;
        gl.glUseProgram(0);
    }
    
    private int createShader(String filename, int shaderType) throws Exception {
        GL20 gl = Gdx.gl;
        int shader = 0;
        try {
            shader = gl.glCreateShader(shaderType);
            
            if (shader == 0)
                return 0;
            
            gl.glShaderSource(shader, readFileAsString(filename));
            gl.glCompileShader(shader);
            
            IntBuffer intbuf = BufferUtils.newIntBuffer(1);
            gl.glGetShaderiv(shader, GL20.GL_COMPILE_STATUS, intbuf);
            int compiled = intbuf.get(0);
            if (compiled == GL20.GL_FALSE) {
                String log = gl.glGetShaderInfoLog(shader);
                throw new RuntimeException("Error creating shader '" + filename + "' : " + log);
            }
            return shader;
        } catch (Exception exc) {
            gl.glDeleteShader(shader);
            throw exc;
        }
    }
    
    private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        
        FileInputStream in = new FileInputStream(filename);
        
        Exception exception = null;
        
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            
            Exception innerExc = null;
            try {
                String line;
                while ((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            } catch (Exception exc) {
                exception = exc;
            } finally {
                try {
                    reader.close();
                } catch (Exception exc) {
                    if (innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }
            
            if (innerExc != null)
                throw innerExc;
        } catch (Exception exc) {
            exception = exc;
        } finally {
            try {
                in.close();
            } catch (Exception exc) {
                if (exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }
            
            if (exception != null)
                throw exception;
        }
        
        return source.toString();
    }

    public void dispose() {
    }
}
