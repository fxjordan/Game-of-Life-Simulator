package de.fjobilabs.gameoflife;

/**
 * Desktop OpenGL specific constants and functions from OpenGL 3. Necessary to
 * use geometry shader, which is not available in OpenGL ES.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 19.09.2017 - 23:41:13
 */
public interface GL32 {
    
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 0x9123,
            GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 0x9124;
    
    /**
     * Accepted by the &lt;type&gt; parameter of CreateShader and returned by
     * the &lt;params&gt; parameter of GetShaderiv:
     */
    public static final int GL_GEOMETRY_SHADER = 0x8DD9;
    
    /**
     *  Accepted by the &lt;pname&gt; parameter of ProgramParameteriEXT and
     *  GetProgramiv:
     */
    public static final int GL_GEOMETRY_VERTICES_OUT = 0x8DDA,
        GL_GEOMETRY_INPUT_TYPE = 0x8DDB,
        GL_GEOMETRY_OUTPUT_TYPE = 0x8DDC;

    /**
     *  Accepted by the &lt;pname&gt; parameter of GetBooleanv, GetIntegerv,
     *  GetFloatv, and GetDoublev:
     */
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 0x8C29,
        GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 0x8DDF,
        GL_MAX_GEOMETRY_OUTPUT_VERTICES = 0x8DE0,
        GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 0x8DE1;
}
