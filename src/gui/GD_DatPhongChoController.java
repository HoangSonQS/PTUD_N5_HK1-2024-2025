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
    private TextField txtMaPhong;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtKH;
    @FXML
    private TextField txtCCCD;
    @FXML
    private DatePicker dpNgayNhan;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private DatePicker dpTra;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnBookWaitingRoom;
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
			String id = "KH24100317";
			String CCCD = txtCCCD.getText();
			int tichdiem = 0;
			
			try {
				if(dsKH.getKhachHangTheoMa(id)== null) {
					KhachHang kh = new KhachHang(id, tenKH, sdt, ngaysinh, CCCD, tichdiem);
					dsKH.themKhachHang(kh);
					Phong p = dsP.getPhongTheoMa(MaPhong);
					p.setTrangThai(TrangThaiPhong.SAPCHECKIN);
					dsP.capNhatTrangThaiPhong(p);
					NhanVien nv = new NhanVien("NV24100301");
					PhieuThuePhong pt = new PhieuThuePhong("PT241003009", kh, p,nv , ngayNhan, ngayTra);
					dsPT.themPhieuThue(pt);
					new Alert(Alert.AlertType.CONFIRMATION, "Success").showAndWait();
				}else {
					new Alert(Alert.AlertType.ERROR, "Fail").showAndWait();
				}
                
            	txtKH.clear();
				txtSDT.clear();
				txtCCCD.clear();
				dpNgayNhan.setValue(null);
				dpNgaySinh.setValue(null);
				dpTra.setValue(null);
			}catch (IllegalArgumentException ex) {
				System.err.println("Lỗiiiiiiiiiiii.");
			}
			Stage stage = (Stage) btnClose.getScene().getWindow();
		    stage.close();
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
