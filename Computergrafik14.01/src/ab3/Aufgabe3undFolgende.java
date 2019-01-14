package ab3;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;


import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {

	private ShaderProgram shaderProgram;
	private ShaderProgram shaderProgramOkta;
	private Matrix4 matrix = new Matrix4().translate(0, 0, -2);
	private Matrix4 matrixOkta = new Matrix4().translate(0f, 0, -2f).scale(0.6f);
	
	private int ticker;
	private int vaoId;
	private int vaOktaId;
	private int vboId;
	private int texOktaId;
	private int texId;
	float[] uvKoords = {0,0,  1,0,  0,1,
						0,0,  1,0,  0,1,  
						0,0,  1,0,  0,1,
						0,0,  1,0,  0,1,};

	public static void main(String[] args) {
		new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("aufgabe3");
		shaderProgramOkta = new ShaderProgram("secObj");
		
		defineTetraeder(); //objekte anlegen und im speicher ablegen
		defineOktaeder();
		
		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
//		glEnable(GL_CULL_FACE); // backface culling aktivieren //punkte anders rum zeichnen, damit an bleiben
								// kann

		float[] nearFar = new Matrix4(0.7f, 100).getValuesAsArray(); // 0.7f,10

		glUseProgram(shaderProgram.getId());
		int locat = glGetUniformLocation(shaderProgram.getId(), "myProjectMat");

		glUniformMatrix4fv(locat, false, nearFar);

	}

	@Override
	public void update() {
		// Transformation durchführen (Matrix anpassen)

		if (ticker < 360)
			matrix.translate(0, 0, 1.2f).rotateX(0.03f).rotateY(0.02f).rotateZ(0.03f).translate(0, 0, -1.2f);
		if (ticker > 80 && ticker < 260)
			matrixOkta.translate(0, 0, 1.4f).rotateX(-0.02f).rotateY(0.02f).rotateZ(0.02f).translate(0, 0, -1.4f);
//		if (ticker < 80)
//			matrixSec.translate(0, 0, 1.8f).rotateX(0.04f).rotateY(0.04f).translate(0, 0, -1.8f);
		
		ticker = (ticker + 1) % 360;

	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		// Matrix1 an Shader übertragen

		float[] matrixFloatArray = matrix.getValuesAsArray();
		int locat = glGetUniformLocation(shaderProgram.getId(), "myMatrix");
		glUniformMatrix4fv(locat, false, matrixFloatArray);

		// VAOs zeichnen
//		erstes Vao und dessen Textur binden, dann zeichnen 

		glBindVertexArray(vaoId);
		glBindTexture(GL_TEXTURE_2D, texId);

		glDrawArrays(GL_TRIANGLES, 0, 12);

		// Matrix2 an Shader übertragen

		float[] matrixFloatArrayOkta = matrixOkta.getValuesAsArray();
		int locatSec = glGetUniformLocation(shaderProgramOkta.getId(), "myMatrixOkta");
		glUniformMatrix4fv(locatSec, false, matrixFloatArrayOkta);

//		Zweites Vao und dessen Textur binden, dann zeichnen 

		glBindVertexArray(vaOktaId);
		glBindTexture(GL_TEXTURE_2D, texOktaId);

		glDrawArrays(GL_TRIANGLES, 0, 24); 
		glBindVertexArray(0);
	}

	// }

	public void defineTetraeder() {
	// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		float[] dreieck = new float[] { 0.25f, 0, 0.5f, 0, 0.5f, 0.5f, -0.25f, 0, 0.5f, // vorn
										0.25f, 0, 0.5f, 0, 0, 1, 0, 0.5f, 0.5f, // rechts
										0, 0.5f, 0.5f, 0, 0, 1, -0.25f, 0, 0.5f, // links
										-0.25f, 0, 0.5f, 0, 0, 1, 0.25f, 0, 0.5f, }; // unten
		
		float[] normalen = calculateNormals(dreieck);

		float[] dreieckColor = new float[] { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0,
				0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0 };

		glUseProgram(shaderProgram.getId());
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId); //1. vao binden und vbos definieren
		vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, dreieck, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vboColors = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboColors);
		glBufferData(GL_ARRAY_BUFFER, dreieckColor, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		int vbNormals = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbNormals);
		glBufferData(GL_ARRAY_BUFFER, normalen, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

		Texture myTex = new Texture("bild.jpg", 8);
		texId = myTex.getId();
		glBindTexture(GL_TEXTURE_2D, texId);

		int vboUV = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboUV);
		glBufferData(GL_ARRAY_BUFFER, uvKoords, GL_STATIC_DRAW);
		glVertexAttribPointer(3, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(3);

	}
	
	public void defineOktaeder() {
		float[] firstPyr = pyramid(); //eine Pyramide ohne Boden
		float[] secondPyr = flipPyr(pyramid());// naechste Pyramide, auf den kopf stellen und mergen
		float[] oktaeder = mergeArrays(firstPyr, secondPyr);

		for(int i=0;i<oktaeder.length;i++)	// positionsArray ausdrucken
		System.out.println(oktaeder[i]+" pos-> "+i);
				
		float[] normalenOkta = calculateNormals(oktaeder);
		
		float[] uvKoordsOkta = mergeArrays(uvKoords,uvKoords);
		
		// Zweites Vao binden und dann vbos definieren

		vaOktaId = glGenVertexArrays();
		glBindVertexArray(vaOktaId);
		int vboZweiId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboZweiId);
		glBufferData(GL_ARRAY_BUFFER, oktaeder, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vbNormalsOkta = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbNormalsOkta);
		glBufferData(GL_ARRAY_BUFFER, normalenOkta, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

		Texture myTexOkta = new Texture("bild4.jpg", 8);
		texOktaId = myTexOkta.getId();
		glBindTexture(GL_TEXTURE_2D, texOktaId);

		int vboUVOkta = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboUVOkta);
		glBufferData(GL_ARRAY_BUFFER, uvKoordsOkta, GL_STATIC_DRAW);
		glVertexAttribPointer(3, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(3);
	}
	
    public static float[] calculateNormals(float[] vertices) {
        float[] normals = new float[vertices.length];
        for (int i = 0; i < vertices.length; i += 9) {
            float[] vector1 = {vertices[i + 3] - vertices[i], vertices[i + 4] - vertices[i + 1], vertices[i + 5] - vertices[i + 2]};
            float[] vector2 = {vertices[i + 6] - vertices[i], vertices[i + 7] - vertices[i + 1], vertices[i + 8] - vertices[i + 2]};
            float[] normal = { vector1[1]*vector2[2]-vector1[2]*vector2[1], vector1[0]*vector2[2]-vector1[2]*vector2[0], vector1[1]*vector2[1]-vector1[1]*vector2[0]    }; 
            		
            float length=  (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

            for (int j = 0; j < 3; j++) {
                normals[i + j * 3] = normal[0]/length;
                normals[i + j * 3 + 1] = normal[1]/length;
                normals[i + j * 3 + 2] = normal[2]/length;
            }
        }

        return normals;
    }

    public float[] pyramid() {
    	float startVertX = 0; float startVertY = -0.5f; float startVertZ = 0.5f;
       float[] pyr = new float[36];
               
        for(int i=0;i<pyr.length;i++) { //von X aus alle Werte pro Vertex(x,y,z)
        	if(i%9==0&&i<28 || i==0) { //vertex oben fuer jedes Dreieck
        		pyr[i] = startVertX;
        		pyr[i+1] = startVertY;
        		pyr[i+2] = startVertZ;
        	}
        	if(i==3 || i==12 || i== 21||i==30) { //vertex links für jedes Dreieck
        		if(i<13) pyr[i+2]=startVertZ/2;
        		else pyr[i+2]=startVertZ*1.5f;
        		if(i==3 || i== 30) pyr[i] = pyr[i-2]/2;
        		else pyr[i] = pyr[i-1]/2;
        		pyr[i+1] = startVertY-startVertY;
}
        		
            	if(i==6 || i==15 || i== 24||i==33) { //vertex rechts für jedes Dreieck
            		if(i<16) pyr[i] = pyr[3]*-1;
            		if( i>16) pyr[i] = pyr[3];
               		pyr[i+1] = pyr[4];
               		if(i==6 || i==33) pyr[i+2]=startVertZ/2;
               		else pyr[i+2] = startVertZ*1.5f;
            		}
        }
        return pyr;

    }
    public float[] flipPyr(float[] toFlip) {
    	float[] copy = toFlip;
    	float[] dummy = toFlip;
    	int top=1;
    	copy[1]=copy[1]*-1;

    	for(int i=top; i<copy.length;i++) { //Vertex oben nach unten setzen
    		if(i-9==top) {		// i und top tauschen ist witzig
    		copy[i] = copy[i]*-1;
    		top+=9;}
   	}	
    	return copy;
    }
    
    public float[] mergeArrays(float[] first, float[] second) {
    	float[] comb = new float[first.length+second.length];
		for(int i=0; i<comb.length;i++) {
			if(i<first.length)
			comb[i] = first[i];
			else comb[i] = second[i-first.length];
		}
		return comb;
    }
 
    
	@Override
	public void destroy() {
	}

}
