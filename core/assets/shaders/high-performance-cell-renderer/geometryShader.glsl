#version 330 core
layout (points) in;
layout (triangle_strip, max_vertices = 5) out;

uniform mat4 u_projViewTrans;

in vec4 v_position[];
in vec4 v_color[];

out vec4 f_color;

const float HALF_CELL_SIZE = 0.5;

void main() {
    gl_Position = u_projViewTrans * (v_position[0] + vec4(-HALF_CELL_SIZE, HALF_CELL_SIZE, 0, 0));
    f_color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * (v_position[0] + vec4(HALF_CELL_SIZE, HALF_CELL_SIZE, 0, 0));
    f_color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * (v_position[0] + vec4(HALF_CELL_SIZE, -HALF_CELL_SIZE, 0, 0));
    f_color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * (v_position[0] + vec4(-HALF_CELL_SIZE, -HALF_CELL_SIZE, 0, 0));
    f_color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * (v_position[0] + vec4(-HALF_CELL_SIZE, HALF_CELL_SIZE, 0, 0));
    f_color = v_color[0];
    EmitVertex();
    
    EndPrimitive();
}  