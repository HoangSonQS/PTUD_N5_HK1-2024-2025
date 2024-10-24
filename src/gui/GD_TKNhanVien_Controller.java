package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.App;

public class GD_TKNhanVien_Controller {
	 @FXML
	    private ImageView avt;

	    @FXML
	    private Button btnXoa2;

	    @FXML
	    private ImageView icon_TimKiem;

	    @FXML
	    private ImageView icon_TimKiem1;

	    @FXML
	    private Label lb_MaNV;

	    @FXML
	    private Label lb_TimKiem;

	    @FXML
	    private Label lb_cccd;

	    @FXML
	    private Label lb_chucVu;

	    @FXML
	    private Label lb_gioiTinh;

	    @FXML
	    private Label lb_nsNV;

	    @FXML
	    private Label lb_sdtNV;

	    @FXML
	    private Label lb_tenNV;

	    @FXML
	    private TableView tableNhanVien;

	    @FXML
	    private TextField txt_maNV;

	    @FXML
	    void moGiaoDienDichVu(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKDichVu");
	    }

	    @FXML
	    void moGiaoDienHoaDon(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKHoaDon");
	    }

	    @FXML
	    void moGiaoDienKhachHang(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKKhachHang");
	    }

	    @FXML
	    void moGiaoDienNhanVien(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKNhanVien");
	    }

	    @FXML
	    void moGiaoDienPhong(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKPhong");
	    }

	    @FXML
	    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLPhong");
	    }

	    @FXML
	    void moGiaoDienTaiKhoan(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKTaiKhoan");
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

	    @FXML
	    void moGiaoDienUuDai(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKUuDai");
	    }

	    

}
