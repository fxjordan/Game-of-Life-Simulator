attribute vec2 a_position;
attribute float a_cellState;

uniform vec4 u_deadCellColor;
uniform vec4 u_aliveCellColor;
uniform vec4 u_cellAliveOverlayDeadColor;
uniform vec4 u_overlayAliveColor;

varying vec4 v_position;
varying vec4 v_color;

void main() {
    if (a_cellState == 1.0) {
        v_color = u_aliveCellColor;
    } else if (a_cellState == 2.0) {
        v_color = u_cellAliveOverlayDeadColor;
    } else if (a_cellState == 3.0) {
        v_color = u_overlayAliveColor;
    } else {
        v_color = u_deadCellColor;
    }
    
    v_position = vec4(a_position.x + 0.5, a_position.y + 0.5, 0, 1.0);
    gl_Position = vec4(0, 0, 0, 0);
}