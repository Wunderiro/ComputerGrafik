#version 330

bool istImKreis(vec2 a, float range){
	vec2 b = gl_FragCoord.xy;	
	float dist = distance (a,b);
	if (dist <= range) {return true;}
	else {return false;}
}

bool isSquare (int borderA, int borderB){
	vec2 coord = gl_FragCoord.xy;
	if ((coord.x > borderA && coord.x < borderB) && ( coord.y > borderA && coord.y < borderB )) {
		return true;
	}  else {return false;}
}

// Aufgabe 1

out vec3 pixelFarbe; //Name beliebig

// main umbenennen um rauszukommentieren
void main2() {
	vec2 coord = gl_FragCoord.xy;
	if ((coord.x > 300 && coord.x < 400) && ( coord.y > 300 && coord.y < 400 )) {
		pixelFarbe = vec3(1.0, 0.0, 0.0);
	} else {
		pixelFarbe = vec3(0.31, 0.66, 0.69);
	}
}
 

void main() {
		vec3 squareColor = vec3 (0.24,0.54,0.56);
		vec3 circleColor = vec3 (0.51,0.23,0.53);
		vec3 background = vec3 (0.0,0.0,0.0);
		if (isSquare(100,200)){pixelFarbe = squareColor;}
		else if(isSquare(400,500)){pixelFarbe = squareColor;}
		else if(isSquare(700,800)){pixelFarbe = squareColor;}
		else if(isSquare(1000,1100)){pixelFarbe = squareColor;}
		else if(istImKreis(vec2(500,500), 100)){pixelFarbe = circleColor;}
		else if(istImKreis(vec2(800,800), 100)){pixelFarbe = circleColor;}
		else {pixelFarbe=background;}
}

void main3(){
	
	vec3 squareColor = vec3 (1.0, 0.0, 0.0);
	vec3 background = vec3 (0.0,0.0,0.0);
	if (isSquare(400, 600)){}
	else {pixelFarbe = background;}
}

















	

 