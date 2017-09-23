attribute vec2 a_position;
attribute float a_state;

varying vec4 v_position;
varying vec4 v_color;

void main() {
    if (a_state == 1.0) {
        v_color = vec4(0, 1, 0, 1);
    } else {
        v_color = vec4(1, 0, 0, 1);
    }
    
    v_position = vec4(a_position.x + 0.5, a_position.y + 0.5, 0, 1.0);
    gl_Position = vec4(0, 0, 0, 0);
}