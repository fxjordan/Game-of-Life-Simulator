uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;
uniform int u_test;

varying vec4 v_position;
varying vec4 v_color;

void main(){
    // All vertices for a cell are created in geometry shader. No need to transform vertex.
    gl_Position = vec4(0, 0, 0, 0);
    
    v_position = gl_Vertex;
    
    if (u_test == 42) {
        v_color = vec4(1, 0, 0, 1);
    } else {
        v_color = vec4(0.6, 0.3, 0.4, 1.0);
    }
}