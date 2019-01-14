package ab3;

//Alle Operationen �ndern das Matrixobjekt selbst und geben das eigene Matrixobjekt zur�ck
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {

	public float[][] m = new float[4][4];

	public Matrix4() {
		// TODO mit der Identit�tsmatrix initialisieren
		for (int i = 0; i < 4; i++) { // diagonal 1, Rest 0
			m[i][i] = 1;
		}
	}

	public Matrix4(Matrix4 copy) {
		// TODO neues Objekt mit den Werten von "copy" initialisieren
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				m[i][j] = copy.m[i][j];
			}
		}

	}

	public Matrix4(float near, float far) {//near=d far=f
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand
		// zur fernen Ebene "far", ggf. weitere Parameter hinzuf�gen
		m[0][0] = near;
		m[1][1] = near;
		m[2][2] = (-far-near)/(far-near);
		m[3][2] = (-2*near*far)/(far-near);
		m[2][3] = -1;
		
	}

	public Matrix4 multiply(Matrix4 other) {
		// TODO hier Matrizenmultiplikation "this = other * this" einf�gen
		float[][] result = new float[4][4];
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				float value = 0;
				for (int n = 0; n < 4; n++) {
					value += other.m[n][i] * m[j][n];
				}
				result[j][i] = value;
			}
		}
		m = result;
		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		// TODO Verschiebung um x,y,z zu this hinzuf�gen
		Matrix4 trans = new Matrix4();
		trans.m[3][0] = x;
		trans.m[3][1] = y;
		trans.m[3][2] = z;
		multiply(trans);
		return this;
	}

	public Matrix4 scale(float uniformFactor) {
		// TODO gleichm��ige Skalierung um Faktor "uniformFactor" zu this hinzuf�gen
		Matrix4 scale = new Matrix4();
		scale.m[0][0] = uniformFactor;
		scale.m[1][1] = uniformFactor;
		scale.m[2][2] = uniformFactor;
		multiply(scale);
		return this;
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		// TODO ungleichf�rmige Skalierung zu this hinzuf�gen
		Matrix4 scale = new Matrix4();
		scale.m[0][0] = sx;
		scale.m[1][1] = sy;
		scale.m[2][2] = sz;
		multiply(scale);
		return this;
	}

	public Matrix4 rotateX(float angle) {
		// TODO Rotation um X-Achse zu this hinzuf�gen
		Matrix4 rotateX = new Matrix4();
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		rotateX.m[1][1] = cos;
		rotateX.m[2][1] = -sin;
		rotateX.m[1][2] = sin;
		rotateX.m[2][2] = cos;
		multiply(rotateX);
		return this;
	}

	public Matrix4 rotateY(float angle) {
		// TODO Rotation um Y-Achse zu this hinzuf�gen
		Matrix4 rotateY = new Matrix4();
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		rotateY.m[0][0] = cos;
		rotateY.m[2][0] = -sin;
		rotateY.m[0][2] = sin;
		rotateY.m[2][2] = cos;
		multiply(rotateY);
		return this;
	}

	public Matrix4 rotateZ(float angle) {
		// TODO Rotation um Z-Achse zu this hinzuf�gen
		Matrix4 rotateZ = new Matrix4();
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		rotateZ.m[0][0] = cos;
		rotateZ.m[1][0] = -sin;
		rotateZ.m[0][1] = sin;
		rotateZ.m[1][1] = cos;
		multiply(rotateZ);
		return this;
	}

	public float[] getValuesAsArray() {
		// TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gef�llt)
		// herausgeben
		float[] asArray = new float[16];
		int count=0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				asArray[count] = this.m[i][j];//count betrifft position
				count++;
			}
			
		}
		return asArray;
	}
}