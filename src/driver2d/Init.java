package driver2d;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class Init extends Application {
	
	private long lastTime;
	private VBox mainPane = new VBox();
	private boolean isFullscreen = false;
	
	@Override
	public void start (Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 906, 680, Color.WHITE);
		stage.setScene(scene);
		stage.setTitle("Firefly Algorithm Visualization");
		
		Basic2dDriver basic2D = new Basic2dDriver();
		
		//---CREATE TIMER AND START---//
		this.lastTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				float elapsedTime = (float) ((now - lastTime) / 1000000.0);
				lastTime = now;
				basic2D.update(elapsedTime);
			}
		};
		
		//---KEY LISTENERS---//
		scene.setOnKeyPressed((KeyEvent e) -> {
			//---FULLSCREEN: F / ESC---//
			if (e.getCode() == KeyCode.F) {
				isFullscreen = !isFullscreen;
				stage.setFullScreen(isFullscreen);
				basic2D.setFullscreen(isFullscreen, stage.getWidth(), stage.getHeight());
			}	
		});
		
		this.mainPane.getChildren().add(basic2D.getUiNode());
		scene.setRoot(mainPane);
		stage.show();
		timer.start();
	}

	public static void main (String[] args) {
		launch(args);
	}

}