package fa;

import java.util.ArrayList;
import java.util.Arrays;


public class Population {

	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int velMultiplier = 1;
	private IFitness fitnessFunction;
	private int size;
	private Vector searchSpaceSize;
	private int numDimensions;
	private double[] dimWeight;
	
	public Population (Vector searchSpaceSize, int numParticles, IFitness fitnessFunction) {
		this.searchSpaceSize = searchSpaceSize;
		this.fitnessFunction = fitnessFunction;
		this.numDimensions = this.searchSpaceSize.getNumDimensions();
		this.dimWeight = new double[this.numDimensions];
		Arrays.fill(this.dimWeight, 1);
		this.size = numParticles;
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(
				this.getRandomPosition(),
				this.getRandomVelocity(false),
				fitnessFunction
			));
		}
	}
	
	public void update () {
		//Evaluate new solutions and update light intensity;
		for (Particle p : particles) p.evaluateFitness();
		//for i = 1 : n (all n fireflies)
		for (Particle pI : particles) {
			//for j = 1 : n (n fireflies)
			for (Particle pJ : particles) {
				//if (I_j > I_i )
				if (pJ.getFitness() > pI.getFitness()) {
					//move firefly i towards j;
					pI.update(pJ, dimWeight);
				}
		    //end for j
			}
			pI.applySpeedLimit();
			pI.updateVector();
		//end for i
		}
		//Rank fireflies and find the current best;
		//Post-processing the results and visualization;
	}
	
	public ArrayList<Particle> getParticles () {
		return this.particles;
	}
	
	private int getPosNeg () {
		if (Math.random() < 0.5) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public void resetGoal (int[] goal) {
		this.fitnessFunction.setGoal(goal);
	}

	private Vector getRandomPosition () {
		int[] randPos = new int[this.numDimensions]; 
		for (int i = 0; i < this.numDimensions; i++) {
			randPos[i] = (int) Math.floor(this.searchSpaceSize.get()[i] * Math.random());
		}
		return new Vector(randPos);
	}
	
	private Vector getRandomVelocity (boolean isScatter) {
		double[] velocity = new double[this.numDimensions];
		int scatterMultiplier = 1;
		if (isScatter) scatterMultiplier = 5;
		for (int i = 0; i < this.numDimensions; i++) {
			velocity[i] = this.getPosNeg() * velMultiplier * scatterMultiplier * Math.random();
		}
		return new Vector(velocity);
	}
	
	public void scatter () {
		for (Particle p : this.particles) {
			p.scatter(this.getRandomVelocity(true));
		}
	}

	public void setDimWeight (int index, double val) {
		if (index < 0 || index >= this.dimWeight.length) {
			System.out.println("Population.setDimWeight outOfBounds");
			return;
		}
		this.dimWeight[index] = val;
	}
	
}
