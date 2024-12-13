
package gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import main.App;

public class BillController implements Initializable{
	@FXML
    private TableColumn<?, ?> giaPhongCol;

    @FXML
    private TableColumn<?, ?> giaPhongCol1;

    @FXML
    private TableColumn<?, ?> gioPhongCol;

    @FXML
    private TableColumn<?, ?> gioPhongCol1;

    @FXML
    private TableColumn<?, ?> phongCol;

    @FXML
    private TableColumn<?, ?> phongCol1;

    @FXML
    private TableColumn<?, ?> tienPhongCol;

    @FXML
    private TableColumn<?, ?> tienPhongCol1;

    @FXML
    private Text txtGiamGia;

    @FXML
    private Text txtKhachHang;

    @FXML
    private Text txtLuongGiamGia;

    @FXML
    private Text txtThanhToan;

    @FXML
    private Text txtThoiGianLap;

    @FXML
    private Text txtTienKhach;

    @FXML
    private Text txtTienThua;

    @FXML
    private Text txtTienThueVAT;

    @FXML
    private Text txtTongTien;

    @FXML
    private Text txtTongTienDichVu;

    @FXML
    private Text txtTongTienPhong;

    @FXML
    private Text txt_HoaDon;

    @FXML
    private Text txt_NhanVien;
    DecimalFormat df = new DecimalFormat("#,###,###,###");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String maHD = GD_ThanhToanController.maHD;
		txt_HoaDon.setText(maHD);
		TaiKhoan t = App.tk;
		txt_NhanVien.setText(t.getNhanVien().getIdNhanVien());
		txtThoiGianLap.setText(LocalDateTime.now().toString());
		HoaDon_DAO dsHD = new HoaDon_DAO();
		HoaDon hd = dsHD.layHoaDonTheoMaHoaDon(maHD);
		txtKhachHang.setText(hd.getKhachHang().getTenKhachHang());
	}

}

