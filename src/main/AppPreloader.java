
package main;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppPreloader extends Preloader {
	
	private Stage preloaderStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.preloaderStage = primaryStage;
		primaryStage.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(App.loadFXML("GD_Chinh"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {
		if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
			preloaderStage.hide();
		}
	}	
}
