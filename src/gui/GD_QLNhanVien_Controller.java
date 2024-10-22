package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.App;

public class GD_QLNhanVien_Controller implements Initializable{
	 @FXML
	    private ImageView avt;

	    @FXML
	    private Button btnSua2;

	    @FXML
	    private Button btnThem;

	    @FXML
	    private Button btnXoa2;

	    @FXML
	    private Button btn_TimPhong;

	    @FXML
	    private ComboBox cbbGioiTinh;

	    @FXML
	    private Label lb_MaNV;

	    @FXML
	    private TableView<?> tableNhanVien;

	    @FXML
	    private TextField txtCCCD;

	    @FXML
	    private DatePicker txtNgaySinh;

	    @FXML
	    private TextField txtSDT;

	    @FXML
	    private TextField txtTenNV;
	    
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ");
			cbbGioiTinh.setItems(list);
			cbbGioiTinh.setValue("Nam");
	    }
	    
	    @FXML
	    void moGiaoDienDichVu(MouseEvent event) throws IOException {
	    	App.setRoot("GD_DichVu");
	    }

	    @FXML
	    void moGiaoDienHoaDon(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienKhachHang(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLKhachHang");
	    }

	    @FXML
	    void moGiaoDienPhong(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienQuanLy(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienTaiKhoan(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienThongKe(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienThuePhong(MouseEvent event) throws IOException {
	    	App.setRoot("GD_Chinh");
	    }

	    @FXML
	    void moGiaoDienTimKiem(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienUuDai(MouseEvent event) {

	    }
}
