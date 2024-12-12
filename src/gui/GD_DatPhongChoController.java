package gui;



import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.App;

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
		Collections.sort(dsMaPhong);
		for(String maPhongDuocChon : dsMaPhong) {
			txtMaPhong.appendText(maPhongDuocChon +" ");
		}
		btnBookWaitingRoom.setOnAction(event -> {
			if (txtCCCD.getText().trim().isEmpty() ||
					txtKH.getText().trim().isEmpty() ||
					txtSDT.getText().trim().isEmpty() ||
					dpNgaySinh.getValue() == null ||
					dpNgayNhan.getValue() == null ||
					dpTra.getValue() == null) {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng nhập đầy đủ thông tin!");
	            alert.showAndWait();
	            return;
	    	}
			try {
				if(!kiemTraDuLieu()) {
					return ;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		            	NhanVien nv = App.tk.getNhanVien();
		                String idPT = PhieuThuePhong.autoIdPhieuThue(); // Tạo mã phiếu thuê tự động
		                PhieuThuePhong pt = new PhieuThuePhong(idPT, dsKH.getKhachHangTheoCCCD(CCCD), p, nv, ngayNhan, ngayTra, true);
		                PhieuThuePhong_DAO ptdao = new PhieuThuePhong_DAO();
		                ArrayList<PhieuThuePhong> dsPhong = ptdao.getPhieuThueTheoMaPhong(MaPhong, ngayNhan, ngayTra);
		                if(dsPhong.isEmpty()) {
		                	Boolean them = dsPT.themPhieuThue(pt); // Thêm phiếu thuê vào danh sách
			                if (them) {
			                    System.out.println("Thêm phiếu thuê thành công.");
			                    p.setTrangThai(TrangThaiPhong.SAPCHECKIN); // Cập nhật trạng thái phòng
			                    dsP.capNhatTrangThaiPhong(p); // Cập nhật trạng thái phòng vào hệ thống
			                    new Alert(Alert.AlertType.CONFIRMATION, "Đặt phòng thành công").showAndWait();
			                    checkTrangThai();
			                } else {
			                    System.out.println("Lỗi khi thêm phiếu thuê cho phòng " + MaPhong);
			                }
		                }else {
		                	new Alert(Alert.AlertType.CONFIRMATION, "Phòng " + MaPhong + " đã được đặt vào ngày " + ngayNhan).showAndWait();
		                }
		            } else {
	                	new Alert(Alert.AlertType.CONFIRMATION, "Phòng " + MaPhong + " không hợp lệ hoặc đã được đặt.").showAndWait();
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
		        Stage stage = (Stage) btnClose.getScene().getWindow();
		        dsMaPhong.clear(); // Xóa danh sách phòng đã chọn
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
					new Alert(Alert.AlertType.CONFIRMATION, "Khách hàng không tồn tại trong hệ thống").showAndWait();
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
	
	private void checkTrangThai() {
	    ArrayList<PhieuThuePhong> dspt = new PhieuThuePhong_DAO().layPhieuThueTheoHieuLuc(true);
	    LocalDateTime now = LocalDateTime.now();

	    for (PhieuThuePhong pt : dspt) {
	        LocalDateTime tgnp = new PhieuThuePhong_DAO().getThoiGianNhanPhong(pt.getIdPhieuThue());
	        LocalDateTime tggp = new PhieuThuePhong_DAO().getThoiGianTraPhong(pt.getIdPhieuThue());

	        Phong p = new Phong_DAO().getPhongTheoMa(pt.getPhong().getIdPhong());

	     // Kiểm tra trạng thái sắp nhận phòng (SẮP CHECKIN)
	        if (!now.isAfter(tgnp) && !now.isBefore(tgnp.minusHours(24))) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKIN);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }
	        // Trạng thái trống nếu thời gian nhận phòng còn trên 12 giờ
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
	
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
    private void showAlertLoi(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
    public boolean isNameFormatValid(String name) {
        String[] words = name.split("\\s+");
        for (String word : words) {
            if (!word.matches("\\p{Lu}\\p{Ll}*")) {
                return false;
            }
        }
        return true;
    }
    public boolean kiemTraDuLieu() throws Exception{
    	if (!txtCCCD.getText().matches("\\d{12}")) {
            showAlertLoi("Lỗi nhập dữ liệu", "CCCD là một dãy gồm 12 số");
            return false;
        }
        if (txtCCCD.getText().equals("")) {
            showAlertLoi("Lỗi nhập dữ liệu", "CCCD nhân viên không được rỗng");
            return false;
        }
        
    	if (txtKH.getText().equals("")) {
            showAlertLoi("Lỗi nhập dữ liệu", "Họ tên không được rỗng");
            return false;
        }
    	if (!isNameFormatValid(txtKH.getText())) {
            showAlertLoi("Lỗi nhập dữ liệu", "Họ tên phải in hoa ký tự đầu");
            return false;
        }
    	if (!txtSDT.getText().matches("0[23789]\\d{8}")) {
            showAlertLoi("Lỗi nhập dữ liệu", "Số điện thoại nhân viên là dãy gồm 10 ký số. 2 ký số đầu là {02, 03, 05, 07, 08, 09}");
            return false;
        }
    	if (dpNgaySinh.getValue() == null) {
            showAlert("Lỗi nhập dữ liệu", "Ngày sinh không được rỗng");
            return false;
        }

        if ((LocalDate.now().getYear() - dpNgaySinh.getValue().getYear()) < 18) {
            showAlertLoi("Lỗi nhập dữ liệu", "Khách hàng phải từ 18 trở lên");
            return false;
        }
     
        if(dpNgayNhan.getValue().isBefore(LocalDate.now())) {
        	showAlertLoi("Lỗi nhập dữ liệu","Ngày nhận không được trước ngày hiện tại");
        	return false;
        }
        
        if(dpTra.getValue().isBefore(dpNgayNhan.getValue())) {
        	showAlertLoi("Lỗi nhập dữ liệu","Ngày trả không được trước ngày nhận");
        	return false;
        }
		return true;
    }  	
}