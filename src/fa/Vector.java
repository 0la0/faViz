package fa;

public class Vector {
	
	private int numDimensions;
	private double[] vector;
	
	public Vector (double[] vector) {
		this.numDimensions = vector.length;
		this.set(vector);
	}
	
	public Vector (int[] vector) {
		this.numDimensions = vector.length;
		double[] v = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			v[i] = vector[i];
		}
		this.vector = v;
	}
	
	public void set (double[] vector) {
		this.vector = vector;
	}
	
	public double[] get () {
		return this.vector;
	}
	
	public int getNumDimensions () {
		return this.numDimensions;
	}
	
	public Vector copy () {
		return new Vector(this.vector);
	}

}
