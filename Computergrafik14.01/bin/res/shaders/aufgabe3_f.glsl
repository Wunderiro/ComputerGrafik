#version 330

out vec3 color;

in vec3 eckenFarben;
in vec3 eckenNormalen;
in vec4 posP;
in vec2 eckenUVs;

uniform sampler2D smplr;


vec3 lightPos = vec3(-5,5,10);

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
	vec3 n = normalize(eckenNormalen);
	vec3 l = normalize(lightPos - posP.xyz);
	vec3 v = normalize(-posP.xyz);
	vec3 r = reflect(-l,n);	
	
	float light = iA*kA+iL*max(0,(dot(l,n))*kD + (pow(max(0,dot(r,v)), 20))*kS);

	vec3 texel = texture(smplr, eckenUVs).rgb;

	color = (texel+light)/2; //eckenFarben*
	
	

}
