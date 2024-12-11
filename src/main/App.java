package main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application{
	public static Stage primaryStage;
	public static Scene primaryScene;
	public static String user;
	public static String ma;
	public static TaiKhoan tk;
	private Stage stage;
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = arg0;
		checkPhong();
		openDangNhapWindow();
		checkTrangThai();
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
		launch(App.class, args);
	}
	
	
	private void checkPhong() {
		PhieuThuePhong_DAO ptdao = new PhieuThuePhong_DAO();
		ArrayList<PhieuThuePhong> listAll = ptdao.getAllPhieuThue();
		LocalDate now = LocalDate.now();
		for (PhieuThuePhong pt : listAll) {
			if (pt.getThoiHanGiaoPhong().isBefore(now)) {
				pt.setHieuLuc(Boolean.FALSE);
				ptdao.suaPhieuThue(pt);
			}
		}
	}
	
	
	private void checkTrangThai() {
	    ArrayList<PhieuThuePhong> dspt = new PhieuThuePhong_DAO().layPhieuThueTheoHieuLuc(true);
	    System.out.println(dspt);
	    
	    LocalDateTime now = LocalDateTime.now();
	    
	    for (PhieuThuePhong pt : dspt) {
	        LocalDateTime tgnp = new PhieuThuePhong_DAO().getThoiGianNhanPhong(pt.getIdPhieuThue());
	        LocalDateTime tggp = new PhieuThuePhong_DAO().getThoiGianTraPhong(pt.getIdPhieuThue());
	        
	        Phong p = new Phong_DAO().getPhongTheoMa(pt.getPhong().getIdPhong());
	        
	        // Kiểm tra trạng thái nhận phòng
	        if (now.isAfter(tgnp.minusDays(1)) && now.isBefore(tgnp)) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKIN);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        } else if (now.isBefore(tgnp.minusDays(1))) {
	            p.setTrangThai(TrangThaiPhong.TRONG);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }
	        
	        // Kiểm tra trạng thái trả phòng
	        if (now.isAfter(tggp.minusHours(2)) && now.isBefore(tggp)) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKOUT);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }
	    }
	}
}
