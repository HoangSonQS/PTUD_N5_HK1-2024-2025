package gui;



import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    
    
    public static ArrayList<String> dsMaPhong = new ArrayList<>();
    KhachHang_DAO dsKH = new KhachHang_DAO();
    PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
    Phong_DAO dsP = new Phong_DAO();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for(String maPhongDuocChon : dsMaPhong) {
			txtMaPhong.appendText(maPhongDuocChon +" ");
		}
		btnBookWaitingRoom.setOnAction(event -> {
		    String tenKH = txtKH.getText();
		    String sdt = txtSDT.getText();
		    LocalDate ngaysinh = dpNgaySinh.getValue();
		    LocalDate ngayNhan = dpNgayNhan.getValue();
		    LocalDate ngayTra = dpTra.getValue();
		    String idKH = KhachHang.autoIdKhachHang(); // Có thể cần cải thiện cách tạo id khách hàng
		    String CCCD = txtCCCD.getText();
		    int tichdiem = 0;

		    try {
		        // Tìm khách hàng theo CCCD
		        KhachHang kh = dsKH.getKhachHangTheoCCCD(CCCD);
		        
		        // Nếu không tìm thấy khách hàng, tạo khách hàng mới
		        if (kh == null) {
		            kh = new KhachHang(idKH, tenKH, sdt, ngaysinh, CCCD, tichdiem);
		            dsKH.themKhachHang(kh); // Thêm khách hàng mới vào hệ thống
		            System.out.println("Thêm khách hàng mới thành công.");
		        } else {
		            System.out.println("Khách hàng đã tồn tại.");
		        }
		        
		        // Duyệt qua từng mã phòng và tạo phiếu thuê
		        for (String MaPhong : dsMaPhong) {
		            Phong p = dsP.getPhongTheoMa(MaPhong);
		            
		            if (p != null && p.getTrangThai() != TrangThaiPhong.SAPCHECKIN) { // Kiểm tra trạng thái phòng
		                NhanVien nv = new NhanVien("NV24100301"); // Cần cải thiện cách lấy thông tin nhân viên
		                String idPT = PhieuThuePhong.autoIdPhieuThue(); // Tạo mã phiếu thuê tự động
		                PhieuThuePhong pt = new PhieuThuePhong(idPT, dsKH.getKhachHangTheoCCCD(CCCD), p, nv, ngayNhan, ngayTra, true);
		                Boolean them = dsPT.themPhieuThue(pt); // Thêm phiếu thuê vào danh sách

		                if (them) {
		                    System.out.println("Thêm phiếu thuê thành công.");
		                    p.setTrangThai(TrangThaiPhong.SAPCHECKIN); // Cập nhật trạng thái phòng
		                    dsP.capNhatTrangThaiPhong(p); // Cập nhật trạng thái phòng vào hệ thống
		                } else {
		                    System.out.println("Lỗi khi thêm phiếu thuê cho phòng " + MaPhong);
		                }
		            } else {
		                System.out.println("Phòng " + MaPhong + " không hợp lệ hoặc đã được đặt.");
		            }
		        }

		        // Làm sạch các trường nhập liệu
		        txtKH.clear();
		        txtSDT.clear();
		        txtCCCD.clear();
		        dpNgayNhan.setValue(null);
		        dpNgaySinh.setValue(null);
		        dpTra.setValue(null);

		        // Đóng cửa sổ hiện tại
		        dsMaPhong.clear(); // Xóa danh sách phòng đã chọn
		        Stage stage = (Stage) btnClose.getScene().getWindow();
		        stage.close();

		    } catch (Exception ex) {
		        ex.printStackTrace();
		        System.err.println("Có lỗi xảy ra: " + ex.getMessage());
		    }
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
			dsMaPhong.removeAll(dsMaPhong);
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