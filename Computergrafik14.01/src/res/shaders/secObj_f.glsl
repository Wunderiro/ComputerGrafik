#version 330

out vec3 color;

in vec3 eckenFarbenSec;
in vec3 eckenNormalenSec;
in vec4 posPSec;
in vec2 eckenUVsSec;

uniform sampler2D smplrSec;


vec3 lightPosSec = vec3(-5,5,10);

float kA = 0.05f;
float kD = 0.2f;
float kS = 0.9f;
float sum = kA+kD+kS;
float iL = 0.7f;
float iA = 0.06f;

void main(){
	kA = kA/sum;
	kD = kD/sum;
	kS = kS/sum;
	vec3 n = normalize(eckenNormalenSec);
	vec3 l = normalize(lightPosSec - posPSec.xyz);
	vec3 v = normalize(-posPSec.xyz);
	vec3 r = reflect(-l,n);	
	
	float light = iA*kA+iL*max(0,(dot(l,n))*kD + (pow(max(0,dot(r,v)), 20))*kS);

	vec3 texelSec = texture(smplrSec, eckenUVsSec).rgb;

	color = (texelSec+light)/2; //eckenFarben*
	
	

}
