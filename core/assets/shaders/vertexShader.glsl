attribute vec3 a_position;
attribute vec4 a_color;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

varying vec4 v_color;

void main() {
    float state = a_position.z;
    if (state == 1.0) {
        v_color = vec4(0, 1, 0, 1);
    } else {
        v_color = vec4(1, 0, 0, 1);
    }
    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position.x + 0.5, a_position.y + 0.5, 0, 1.0);
    
    // Calc cormalizedDeviceCoordinates
    vec3 ndc = gl_Position.xyz / gl_Position.w; // perspective devide.
    float zDist = 1.0 - ndc.z;
    gl_PointSize = 10 * zDist;
    v_color.w = zDist;
    
    //gl_PointSize = (u_ProjViewTrans * vec4(1, 1, 0, 1)).x;
    
    //float fovy = 60; // degrees
    //int viewport[4];
    //glGetIntegerv(GL_VIEWPORT,viewport);
    //float heightOfNearPlane = (float) abs(viewport[3]-viewport[1]) / (2*tan(0.5*fovy*PI/180.0));
    //gl_PointSize = (heightOfNearPlane * 20) / gl_Position.w;
}