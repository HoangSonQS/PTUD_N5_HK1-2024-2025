package main;

import java.io.IOException;

import connectDB.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application{
	public static Stage primaryStage;
	public static Scene primaryScene;
	public static String user;
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = arg0;
	}
	
	@Override
	public void init() throws Exception {
		connectDB.ConnectDB.getInstance().connect();
	}
	public static void openMainGUI() throws IOException {
//      Open Main GUI
		primaryScene = new Scene(loadFXML("GD_Chinh"));
//		Stage stage = new Stage();
//		primaryStage.setTitle("Quản Lý Khách sạn Flower on the sea");
//		primaryStage.setResizable(false);
		primaryStage.setScene(primaryScene);
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	public static void openModal(String fxml, int width, int height) throws IOException {
		Scene sceneModal = new Scene(loadFXML(fxml), width, height);
		Stage stageModal = new Stage();
		stageModal.setResizable(false);
		stageModal.initModality(Modality.APPLICATION_MODAL);
		stageModal.setScene(sceneModal);
		if (fxml.equals("GD_DangNhap")) {
			stageModal.setOnCloseRequest(event -> {
				Platform.exit();
				System.exit(0);
			});
		}
		stageModal.showAndWait();
	}

	public static void setRoot(String fxmlNewChild) throws IOException {
		Parent child = loadFXML(fxmlNewChild);
		Parent root = primaryScene.getRoot();
		ObservableList<Node> paneChildren = ((Pane) root.getChildrenUnmodifiable().get(0)).getChildren();
		paneChildren.clear();
		paneChildren.add(child);
	}
	public static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlFrame = new FXMLLoader(App.class.getResource("/gui/" + fxml + ".fxml"));
		return fxmlFrame.load();
	}
	public static void main(String[] args) {
		System.setProperty("javafx.preloader", AppPreloader.class.getName());
		launch(App.class, args);
	}
}
