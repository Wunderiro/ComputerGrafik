#version 330

layout(location=0) in vec3 eckenAusJavaSec;
layout(location=1) in vec3 eckenColorSec;
layout(location=2) in vec3 eckenNormalsSec;
layout(location=3) in vec2 eckenUVSec;

uniform mat4 myMatrixOkta;
uniform mat4 myProjectMatOkta;

out vec3 eckenFarbenSec;
out vec3 eckenNormalenSec;
out vec4 posPSec;
out vec2 eckenUVsSec;

void main(){

	eckenNormalenSec = (inverse(transpose(mat3(myMatrixOkta))))*eckenNormalsSec;
	eckenFarbenSec = eckenColorSec;
	posPSec = myMatrixOkta*vec4(eckenAusJavaSec,1);
	gl_Position =  myProjectMatOkta *posPSec; 
	eckenUVsSec = eckenUVSec;
}