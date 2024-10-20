package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.App;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class DangNhap_Controller{
	@FXML
	private TextField txt_ten;
	@FXML
	private TextField txt_mk;
	@FXML
	private Button btn_LogIn;
	@FXML
    private ImageView avt;
	
	// Event Listener on Button[#btn_LogIn].onAction
	@FXML
	public void LogIn(ActionEvent event) throws IOException {
		connect();
		String userName = txt_ten.getText().trim();
		String password = txt_mk.getText().trim();
		
		TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
		TaiKhoan tk = tkdao.getTaiKhoanTheoUserNameAndPassword(userName, password);
		if (tk == null) {
			Alert alert = new Alert(AlertType.ERROR, "Vui lòng kiểm tra lại tài khoản và mật khẩu của bạn!", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Đăng nhập thất bại");
            alert.setHeaderText("Sai tài khoản hoặc mật khẩu");
            alert.showAndWait();
		} else {
			tkdao.capNhatDangNhap();
			tkdao.capNhatTaiKhoan(new TaiKhoan(userName, password, "Đang đăng nhập", tk.getNhanVien()));
			App.user = tk.getNhanVien().getIdNhanVien();
			Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
			stage.close();
            App.openMainGUI(); // Mở màn hình chính
            
        }
	}
	
	@FXML
    public void handleKeyboardEvent(KeyEvent ke) throws Exception {
        if (ke.getCode() == KeyCode.ENTER) {
            LogIn(new ActionEvent(ke.getSource(), ke.getTarget()));
        }
    }
	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
	public static void main(String[] args) {
		
	}

	

}
