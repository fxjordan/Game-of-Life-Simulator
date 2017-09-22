attribute vec3 a_position;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

varying vec4 v_position;
varying vec4 v_color;

void main() {
    float state = a_position.z;
    if (state == 1.0) {
        v_color = vec4(0, 1, 0, 1);
    } else {
        v_color = vec4(1, 0, 0, 1);
    }
    
    v_position = vec4(a_position.x + 0.5, a_position.y + 0.5, 0, 1.0);
    //gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position.x + 0.5, a_position.y + 0.5, 0, 1.0);
    gl_Position = vec4(0, 0, 0, 0);
}