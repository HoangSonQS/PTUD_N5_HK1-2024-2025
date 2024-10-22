package main;

import java.io.IOException;
import java.util.Objects;

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
	private Stage stage;
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = arg0;
		openDangNhapWindow();
	}
	
	@Override
	public void init() throws Exception {
		connectDB.ConnectDB.getInstance().connect();
	}
	public static void openMainGUI() throws IOException {
		try {
			 FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/gui/GD_Chinh.fxml")); //Đường dẫn chính xác
	            Parent root = fxmlLoader.load();
	            primaryScene = new Scene(root);
	            primaryStage.setScene(primaryScene);
	            primaryStage.show();
	    } catch (NullPointerException ex) {
	        System.err.println("Không tìm thấy file GD_Chinh.fxml: " + ex.getMessage());
	        ex.printStackTrace();
	        throw ex; 
	    }
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
	public static void openDangNhapWindow() throws IOException {
		try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("/gui/GD_DangNhap.fxml"))); // THAY ĐỔI ĐƯỜNG DẪN NẾU CẦN
            Scene scene = new Scene(root);
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (NullPointerException ex) {
            System.err.println("Không tìm thấy file DangNhap.fxml: " + ex.getMessage());
            ex.printStackTrace();
            throw ex; // Ném ngoại lệ để xử lý ở cấp cao hơn nếu cần
        }// Hiện màn hình đăng nhập
    }
	public static void setRoot(String fxml) throws IOException {
		
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/gui/" + fxml + ".fxml"));
        System.out.println("Loading FXML: " + loader.getLocation());
		try {
            Parent newRoot = loader.load(); //Đọc giao diện
			if(primaryScene!=null)
				primaryScene.setRoot(newRoot); // Thay đổi root
            primaryStage.setScene(primaryScene); // Cập nhật Scene cho Stage
			primaryStage.sizeToScene(); // Cập nhật kích thước của Stage
			primaryStage.show(); // Hiện Stage
			
			
		} catch (IOException ex){
			System.err.println("Không tìm thấy file: "+fxml);
			throw ex;
		}

    }
	public static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlFrame = new FXMLLoader(App.class.getResource("/gui/" + fxml + ".fxml"));
		return fxmlFrame.load();
	}
	public static void main(String[] args) {
		launch(App.class, args);
	}
}
