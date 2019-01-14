#version 330

layout(location=0) in vec2 eckenAusJava;
layout(location=1) in vec3 eckenColor;

float w = 1.5708f;
out vec3 eckenFarben;


void main(){
	mat2 m = mat2(cos(w), sin(w), -sin(w), cos(w));

	gl_Position = vec4(m*eckenAusJava,0.0,1.0);	
	eckenFarben = eckenColor;
	
}