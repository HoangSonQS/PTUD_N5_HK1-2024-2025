package gui;



import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import dao.KhachHang_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TrangThaiPhong;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GD_DatPhongChoController implements Initializable{
	@FXML
    private Button btnBookWaitingRoom;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btn_TimCCCD;
    @FXML
    private DatePicker dpNgayNhan;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private DatePicker dpTra;
    @FXML
    private TextField txtCCCD;
    @FXML
    private TextField txtKH;
    @FXML
    private TextField txtMaPhong;
    @FXML
    private TextField txtSDT;
    
    
    public static String MaPhong;
    KhachHang_DAO dsKH = new KhachHang_DAO();
    PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
    Phong_DAO dsP = new Phong_DAO();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtMaPhong.setText(MaPhong);
		btnBookWaitingRoom.setOnAction(even -> {
			String tenKH = txtKH.getText();
			String sdt = txtSDT.getText();
			LocalDate ngaysinh = dpNgaySinh.getValue();
			LocalDate ngayNhan = dpNgayNhan.getValue();
			LocalDate ngayTra = dpTra.getValue();
			String id = "KH24100320";
			String CCCD = txtCCCD.getText();
			int tichdiem = 0;
			
			try {
				if(dsKH.getKhachHangTheoMa(id) == null) {
					KhachHang kh = new KhachHang(id, tenKH, sdt, ngaysinh, CCCD, tichdiem);
					dsKH.themKhachHang(kh);
					Phong p = dsP.getPhongTheoMa(MaPhong);
					NhanVien nv = new NhanVien("NV24100301");
					PhieuThuePhong pt = new PhieuThuePhong("PT241003014", dsKH.getKhachHangTheoCCCD(CCCD), p,nv , ngayNhan, ngayTra);
					Boolean them = dsPT.themPhieuThue(pt);
					if (them == true) {
						new Alert(Alert.AlertType.CONFIRMATION, "Thêm thành công").showAndWait();
						p.setTrangThai(TrangThaiPhong.SAPCHECKIN);
						dsP.capNhatTrangThaiPhong(p);
		            	txtKH.clear();
						txtSDT.clear();
						txtCCCD.clear();
						dpNgayNhan.setValue(null);
						dpNgaySinh.setValue(null);
						dpTra.setValue(null);
					} else {
						new Alert(Alert.AlertType.ERROR, "Thêm thất bại").showAndWait();
					}
				} else {
					new Alert(Alert.AlertType.ERROR, "Fail").showAndWait();
				}
			}catch (IllegalArgumentException ex) {
				System.err.println("Lỗiiiiiiiiiiii.");
			}
			Stage stage = (Stage) btnClose.getScene().getWindow();
		    stage.close();
		});
		btn_TimCCCD.setOnAction(event -> {
			String CCCD = txtCCCD.getText();
			KhachHang khachHangTonTai = new KhachHang_DAO().getKhachHangTheoCCCD(CCCD);
			try {
				if (khachHangTonTai != null) {
					txtKH.setText(khachHangTonTai.getTenKhachHang());
					txtSDT.setText(khachHangTonTai.getSoDienThoai());
					dpNgaySinh.setValue(khachHangTonTai.getNgaySinh());
				} else {
					new Alert(Alert.AlertType.CONFIRMATION, "Khách hàng không tồn tại").showAndWait();
				}
			} catch (Exception e) {
			}
		});
		btnClose.setOnAction(even ->{
			 Stage stage = (Stage) btnClose.getScene().getWindow();
			    stage.close();
		});
		btnRefresh.setOnAction(even ->{
			txtKH.setText("");
			txtCCCD.setText("");
			txtSDT.setText("");
			dpNgayNhan.setValue(null);
			dpNgaySinh.setValue(null);
			dpTra.setValue(null);
		});
	}
}