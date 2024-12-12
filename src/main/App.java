package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import gui.SplashController;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application{
	public static final int widthModalLogin = 732;
	public static final int heightModalLogin = 517;
	public static Stage primaryStage;
	public static Scene primaryScene;
	public static String user;
	public static String ma;
	public static TaiKhoan tk;
	private Stage stage;
	@Override
	public void start(Stage arg0) throws Exception {

		this.primaryStage = arg0;
		checkPhong();
		checkTrangThai();
	}

	@Override
	public void init() throws Exception {
		gui.SplashController splash = new SplashController();
		splash.checkFuntions();
		connectDB.ConnectDB.getInstance().connect();
	}
	public static void openMainGUI() throws IOException {
		try {
			 FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/gui/GD_Chinh.fxml")); //Đường dẫn chính xác
	            Parent root = fxmlLoader.load();
	            primaryScene = new Scene(root);
	            primaryStage.setScene(primaryScene);
	            primaryStage.setMaximized(true);
	            primaryStage.show();
	    } catch (NullPointerException ex) {
	        System.err.println("Không tìm thấy file GD_Chinh.fxml: " + ex.getMessage());
	        ex.printStackTrace();
	        throw ex; 
	    }
	}
	public static void openModal(String fxml) throws IOException {
		Scene sceneModal = new Scene(loadFXML(fxml));
		Stage stageModal = new Stage();
		stageModal.setMaximized(true);
		stageModal.setScene(sceneModal);
		if (fxml.equals("GD_DangNhap")) {
			stageModal.setOnCloseRequest(event -> {
				Platform.exit();
				System.exit(0);
			});
		}
		stageModal.showAndWait();
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
	public static void setRootNho(String fxml) throws IOException {
		
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/gui/" + fxml + ".fxml"));
        System.out.println("Loading FXML: " + loader.getLocation());
		try {
            Parent newRoot = loader.load(); //Đọc giao diện
			if(primaryScene!=null)
				primaryScene.setRoot(newRoot); // Thay đổi root
            primaryStage.setScene(primaryScene); // Cập nhật Scene cho Stage
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
		System.setProperty("javafx.preloader", AppPreloader.class.getName());
		launch(App.class, args);
	}
	
	
	private void checkTrangThai() {
	    ArrayList<PhieuThuePhong> dspt = new PhieuThuePhong_DAO().layPhieuThueTheoHieuLuc(true);
	    LocalDateTime now = LocalDateTime.now();

	    for (PhieuThuePhong pt : dspt) {
	        LocalDateTime tgnp = new PhieuThuePhong().getThoiGianNhanPhong(pt.getIdPhieuThue());
	        LocalDateTime tggp = new PhieuThuePhong_DAO().getThoiGianTraPhong(pt.getIdPhieuThue());

	        Phong p = new Phong_DAO().getPhongTheoMa(pt.getPhong().getIdPhong());

	        // Kiểm tra trạng thái sắp nhận phòng (SẮP CHECKIN)
	        if (!now.isAfter(tgnp) && !now.isBefore(tgnp.minusHours(24))) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKIN);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        } 
	        // Trạng thái trống nếu thời gian nhận phòng còn trên 24 giờ
	        else if (now.isBefore(tgnp.minusHours(24))) {
	            p.setTrangThai(TrangThaiPhong.TRONG);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }

	        // Kiểm tra trạng thái đang thuê (DANGTHUE)
	        if (now.isAfter(tgnp) && now.isBefore(tggp.minusHours(2))) {
	            p.setTrangThai(TrangThaiPhong.DANGTHUE);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }

	        // Kiểm tra trạng thái sắp trả phòng (SẮP CHECKOUT)
	        if (!now.isAfter(tggp) && !now.isBefore(tggp.minusHours(2))) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKOUT);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }

	        // Kiểm tra trạng thái sau khi trả phòng (TRỐNG)
	        if (now.isAfter(tggp)) {
	            p.setTrangThai(TrangThaiPhong.TRONG);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	            pt.setHieuLuc(Boolean.FALSE);
	            new PhieuThuePhong_DAO().suaPhieuThue(pt);
	        }
	    }
	}



	private void checkPhong() {
	    PhieuThuePhong_DAO ptdao = new PhieuThuePhong_DAO();
	    ArrayList<PhieuThuePhong> listAll = ptdao.getAllPhieuThue();
	    LocalDateTime now = LocalDateTime.now();
	    
	    for (PhieuThuePhong pt : listAll) {
	        LocalDateTime tggp = new PhieuThuePhong_DAO().getThoiGianTraPhong(pt.getIdPhieuThue());
	        
	        if (now.isAfter(tggp)) {
	            pt.setHieuLuc(Boolean.FALSE);
	            ptdao.suaPhieuThue(pt);
	        }
	    }
	}
}
