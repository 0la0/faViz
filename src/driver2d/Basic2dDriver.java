package driver2d;

import java.util.Arrays;

import fa.FitnessDistance;
import fa.IFitness;
import fa.Particle;
import fa.Population;
import fa.Vector;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;


public class Basic2dDriver {
	
	private IFitness fitnessFunction;
	private Population population;
	private int numDimensions = 0; //Number of dimensions to represent
	private double[] paramMult;	 //individual parameter multipliers
	//private String[] paramList;	 //parameter labels
	//private int numPopulations;	 //number of populations
	private int width = 900;
	private int height = 675;
	
	private int goalRadius = 20;
	private GraphicsContext g2d;
	//private Color goalColor = Color.BLACK;
	private Canvas canvas = new Canvas(width, height);
	
	private Color goalColor = Color.color(0.8, 0.3, 0.2);
	private Color particleColor = Color.BLACK;
	
	private WritableImage wImage = new WritableImage(width, height);
	private PixelWriter pixelWriter = wImage.getPixelWriter();
	
	public Basic2dDriver () {
		int populationSize = 100;
		int[] searchSpace = new int[]{900, 675};
		int[] initGoal = new int[]{255, 255};
		
		
		//this.paramList = new String[]{"X", "Y", "R", "G", "B"};
		this.numDimensions = searchSpace.length;
		this.paramMult = new double[this.numDimensions]; 
		Arrays.fill(this.paramMult, 0.001);
		
		Vector size = new Vector(searchSpace.clone());
		this.fitnessFunction = new FitnessDistance(initGoal.clone());
		this.population = new Population(size, populationSize, this.fitnessFunction);
		
		
		this.setUpUi();
		this.g2d.setLineWidth(3);
		
		this.paintFunction();
	}
	
	private void paintFunction () {
		Color color;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double val = this.bgFunction(j, i);
				//val /= 10;
				if (val > 1) val = 1;
				else if (val < 0) val = 0;
				color = Color.color(val, 0, 1 - val);
				this.pixelWriter.setColor(j, i, color);
			}
		}
	}
	
	//---CURRENTLY NEED TO MANUALL SYNC WITH FITNESS FUNCTION---//
	private double bgFunction (int x, int y) {
		x -= this.g2d.getCanvas().getWidth() / 2;
		y -= this.g2d.getCanvas().getHeight() / 2;
		
		//double val = Math.sin(Math.sqrt(0.001 * (x * x + y * y) ));
		//double val = Math.cos(0.05 * x) + Math.sin(0.05 * y);
		double val = Math.abs(Math.sin(0.01 * x) * Math.cos(0.01 * y));
		return val;
	}
	
	public void update (float elapsedTime) {
		g2d.clearRect(0, 0, g2d.getCanvas().getWidth(), g2d.getCanvas().getHeight());
		g2d.drawImage(this.wImage, 0, 0);

		population.update();
		//---RENDER GOAL STATE---//
		/*
		g2d.fillOval(
			this.fitnessFunction.getGoal()[0] - this.goalRadius, 
			this.fitnessFunction.getGoal()[1] - this.goalRadius, 
			this.goalRadius * 2, this.goalRadius * 2
		);
		 */
			
		//---RENDER PARTICLES---//
		for (Particle particle : population.getParticles()) {

			//---ADJUST PARTICLE COLOR TO REPRESENT FITNESS VALUE---//
			//float fitness = particle.getFitness();
			//if (fitness < 0) fitness = 0;
			//g2d.setStroke(Color.color(0, fitness, 1 - fitness));
				
			g2d.strokeLine(
				particle.getPosition().get()[0], particle.getPosition().get()[1],
				particle.getLastPosition1().get()[0], particle.getLastPosition1().get()[1]
			);
			g2d.strokeLine(
				particle.getLastPosition1().get()[0], particle.getLastPosition1().get()[1],
				particle.getLastPosition2().get()[0], particle.getLastPosition2().get()[1]
			);
		}	
	}
	
	private void setUpUi () {
		this.canvas = new Canvas(900, 675);
		this.g2d = canvas.getGraphicsContext2D();
		
		//---SET INITIAL GOAL STATE COLOR---//
		/*
		goalColor = Color.color(
			fitnessFunction.getGoal()[2] / 255.0,
			fitnessFunction.getGoal()[3] / 255.0,
			fitnessFunction.getGoal()[4] / 255.0
		);
		*/
		
		this.g2d.setFill(this.goalColor);
		this.g2d.setStroke(this.particleColor);
		
		//---MOUSE LISTENER TO CHANGE GOAL STATE---//
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			this.population.resetGoal(new int[]{(int) e.getX(), (int) e.getY()});
		});
	}
	
	public void setFullscreen (boolean isFullscreen, double w, double h) {
		if (isFullscreen) {
			this.canvas.setWidth(w);
			this.canvas.setHeight(h);
		}
		else {
			this.canvas.setWidth(width);
			this.canvas.setHeight(height);
		}
	}
	
	public Node getUiNode () {
		return this.canvas;
	}
	
	public String toString () {
		return "basic2D";
	}

}
