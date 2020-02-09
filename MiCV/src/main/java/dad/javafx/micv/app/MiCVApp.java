package dad.javafx.micv.app;

import dad.javafx.micv.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MiCVApp extends Application {
	private static Stage Stage;
	
	public static Stage getStage() {
		return Stage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainController main = new MainController();
		Stage = primaryStage;
		Scene scene = new Scene(main.getView());

		primaryStage.getIcons().add(new Image(this.getClass().getResource("/icons/cv64x64.png").toString()));
		primaryStage.setTitle("MiCV");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
