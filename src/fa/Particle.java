package fa;

public class Particle {

	private Vector position;
	private Vector lastPosition1;
	private Vector lastPosition2;
	private Vector velocity;
	private IFitness fitnessFunction;
	private int numDimensions;
	private float fitness;
	private float gamma = 1f;
	private float alpha = 1f;
	private int speedLimit = 5;
	
	public Particle (Vector position, Vector velocity, IFitness fitnessFunction) {
		this.position = position;
		this.velocity = velocity;
		this.numDimensions = position.getNumDimensions();
		this.fitnessFunction = fitnessFunction;
		this.lastPosition1 = position.copy();
		this.lastPosition2 = position.copy();
	}
	
	public void evaluateFitness () {
		this.fitness = fitnessFunction.calcFitness(this);
	}
	
	private double getBeta (Particle that, int index) {
		// Intensity_0 * e ^ (-gamma * d^2)
		double d2 = Math.pow( (that.getPosition().get()[index] - this.getPosition().get()[index]), 2);
		return that.fitness * Math.exp(-this.gamma * Math.sqrt(d2));
	}
	
	private double getDistance (Particle that) {
		double sum = 0;
		for (int i = 0; i < this.numDimensions; i++) {
			sum += Math.pow(that.getPosition().get()[i] - this.getPosition().get()[i], 2);
		}
		return Math.sqrt(sum);
	}
	
	public void update (Particle that, double[] dimWeight) {
		for (int i = 0; i < this.numDimensions; i++) {
			//v[] = v[] + Beta(that - this) + Alpha(Rand - 0.5)
			double beta = this.getBeta(that, i);

			this.velocity.get()[i] += 1 * (
				10000 * beta *
				(that.getPosition().get()[i] - this.getPosition().get()[i]) +
				this.alpha * (Math.random() - 0.5));
			
			/*
			this.velocity.get()[i] = 
				(1 - beta) * this.velocity.get()[i] +
				beta * that.velocity.get()[i] +
				this.alpha * (Math.random() - 0.5);
			*/
			//this.velocity.get()[i] *= dimWeight[i];
		}
	}
	
	public void applySpeedLimit () {
		for (int i = 0; i < this.numDimensions; i++) {
			if (this.velocity.get()[i] > this.speedLimit)
				this.velocity.get()[i] = this.speedLimit;
			else if (this.velocity.get()[i] < -this.speedLimit)
				this.velocity.get()[i] = -this.speedLimit;
		}
	}
	
	public void updateVector () {
		this.lastPosition2 = this.lastPosition1.copy();
		this.lastPosition1 = this.position.copy();
		
		double[] newPos = new double[this.numDimensions];
		for (int i = 0; i < this.numDimensions; i++) {
			newPos[i] = this.position.get()[i] + this.velocity.get()[i];
		}
		this.position.set(newPos);
	}
	
	public Vector getPosition () {
		return this.position;
	}
	
	public Vector getLastPosition1 () {
		return this.lastPosition1;
	}
	
	public Vector getLastPosition2 () {
		return this.lastPosition2;
	}

	public void scatter (Vector velocity) {
		this.velocity = velocity;
	}
	
	public void setPosition (Vector position) {
		this.position = position;
		this.lastPosition1 = position.copy();
		this.lastPosition2 = position.copy();
	}
	
	public void setVelocity (Vector velocity) {
		this.velocity = velocity;
	}
	
	public float getFitness () {
		return this.fitness;
	}
	
}
