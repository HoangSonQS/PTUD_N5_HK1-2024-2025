package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import entity.TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.App;


public class GD_Chinh_Controller implements Initializable{

    @FXML
    private ImageView avt;
    @FXML
    private ImageView icon_QuanLy;
    @FXML
    private ImageView icon_ThongKe;
    @FXML
    private ImageView icon_ThuePhong;
    @FXML
    private ImageView icon_TimKiem;
    @FXML
    private Label lb_QuanLy;
    @FXML
    private Label lb_ThongKe;
    @FXML
    private Label lb_ThuePhong;
    @FXML
    private Label lb_TimKiem;
    @FXML
    private Pane pn_NoiDung;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;

    
    @FXML
    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLPhong");
    }

    @FXML
    void moGiaoDienThongKe(MouseEvent event) throws IOException {
    	App.setRoot("GD_ThongKeDoanhThu");
    }

    @FXML
    void moGiaoDienThuePhong(MouseEvent event) throws IOException {
    	App.setRoot("GD_SoDoPhong");
    }

    @FXML
    void moGiaoDienTimKiem(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKPhong");
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addUserLogin();
	}
    @FXML
    void moHuongDan(MouseEvent event) {
		String initial = "data\\TaiLieu\\5_7_ApplicationDevelopment_UserManual-trang.html";
		Path initialDirectory = Paths.get(initial).toAbsolutePath();
		File file = new File(initial);

        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private void addUserLogin() {
		TaiKhoan tk = App.tk;
		maNV.setText(String.valueOf(tk.getNhanVien().getIdNhanVien()));
		tenNV.setText(String.valueOf(tk.getNhanVien().getTenNhanVien()));
	}
}
