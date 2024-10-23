package gui;
	
import connectDB.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class TestJavaFX extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			connect();
<<<<<<< HEAD
			Parent root = FXMLLoader.load(getClass().getResource("GD_GiaHanPhong.fxml"));

=======
			Parent root = FXMLLoader.load(getClass().getResource("GD_QLPhong.fxml"));
>>>>>>> be35bf2d4da0b09b43ed77a144e480bbd89b47c0
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
}
