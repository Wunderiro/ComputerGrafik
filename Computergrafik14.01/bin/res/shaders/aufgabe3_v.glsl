#version 330

layout(location=0) in vec3 eckenAusJava;
layout(location=1) in vec3 eckenColor;
layout(location=2) in vec3 eckenNormals;
layout(location=3) in vec2 eckenUV;

uniform mat4 myMatrix;
uniform mat4 myProjectMat;

out vec3 eckenFarben;
out vec3 eckenNormalen;
out vec4 posP;
out vec2 eckenUVs;

void main(){

	eckenNormalen = (inverse(transpose(mat3(myMatrix))))*eckenNormals;
	eckenFarben = eckenColor;
	posP = myMatrix*vec4(eckenAusJava,1);
	gl_Position =  myProjectMat *posP; 
	eckenUVs = eckenUV;
}