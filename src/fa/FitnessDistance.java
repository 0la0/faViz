package fa;

public class FitnessDistance implements IFitness{

	private int numDims;
	private int[] goal;
	private int max = 1000;
	
	public FitnessDistance (int[] goal) {
		if (goal.length == 0) {
			System.out.println("goal must have at least one dimension");
			return;
		}
		this.numDims = goal.length;
		this.setGoal(goal);
	}
	/*
	@Override
	public float calcFitness(Particle p) {
		float sum = 0f;
		for (int i = 0; i < this.numDims; i++) {
			sum += (float) (Math.pow(p.getPosition().get()[i] - this.goal[i], 2));
		}
		sum = (float) Math.sqrt(sum);
		
		float fitness = this.max - sum;
		//if (fitness < 0) fitness = 0;
		fitness /= this.max;
		return fitness;
	}
	*/
	
	@Override
	public float calcFitness (Particle p) {
		
		int x = (int) p.getPosition().get()[0];
		int y = (int) p.getPosition().get()[1];
		x -= 450;
		y -= 338;
		
		//double val = Math.sin(Math.sqrt(0.001 * (x * x + y * y) ));
		//double val = Math.cos(0.05 * x) + Math.sin(0.05 * y);
		double val = Math.abs(Math.sin(0.01 * x) * Math.cos(0.01 * y));
		
		return (float) val;
	}
	

	@Override
	public void setGoal(int[] goal) {
		this.goal = goal;
	}

	@Override
	public int[] getGoal() {
		return this.goal;
	}

	@Override
	public void setMax(int[] dims) {
		int sum = 0;
		for (int i = 0; i < goal.length; i++) {
			sum += Math.pow(goal[i], 2);
		}
		this.max = (int) Math.sqrt(sum);
	}
	
}
