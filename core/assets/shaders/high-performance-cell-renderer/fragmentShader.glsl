#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif

varying vec4 f_color;

void main() {
    gl_FragColor = f_color;
}