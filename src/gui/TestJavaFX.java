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
			Parent root = FXMLLoader.load(getClass().getResource("GD_DatPhong.fxml"));
=======
			Parent root = FXMLLoader.load(getClass().getResource("GD_SoDoPhong.fxml"));
>>>>>>> 49edcb73b38f87e92a219677e8764e01b8538158
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
