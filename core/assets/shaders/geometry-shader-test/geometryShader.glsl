#version 330 core
layout (points) in;
layout (triangle_strip, max_vertices = 5) out;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

in vec4 v_position[];
in vec4 v_color[];

out vec4 f_Color;

void main() {
    gl_Position = u_projViewTrans * u_worldTrans * (v_position[0] + vec4(-0.5, 0.5, 0, 0));
    f_Color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * u_worldTrans * (v_position[0] + vec4(0.5, 0.5, 0, 0));
    f_Color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * u_worldTrans * (v_position[0] + vec4(0.5, -0.5, 0, 0));
    f_Color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * u_worldTrans * (v_position[0] + vec4(-0.5, -0.5, 0, 0));
    f_Color = v_color[0];
    EmitVertex();
    
    gl_Position = u_projViewTrans * u_worldTrans * (v_position[0] + vec4(-0.5, 0.5, 0, 0));
    f_Color = v_color[0];
    EmitVertex();
    
    EndPrimitive();
}  