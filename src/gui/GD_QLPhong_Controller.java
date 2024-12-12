package gui;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.Phong_DAO;
import dao.TaiKhoan_DAO;
import entity.LoaiPhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;

public class GD_QLPhong_Controller implements Initializable{

	 	@FXML
	    private ImageView avt;

	    @FXML
	    private Button btnSua;

	    @FXML
	    private Button btnThem;

	    @FXML
	    private Button btnTimNhanVien;

	    @FXML
	    private Button btnXoa;

	    @FXML
	    private Button btnXoaTrang;
	    @FXML
	    private Label maNV;
	    @FXML
	    private Label tenNV;
	    @FXML
	    private ComboBox<String> cbb;
	    
	    @FXML
	    private ComboBox<String> cbb2;

	    @FXML
	    private TableView<Phong> tablePhong;
	    
	    @FXML
	    private TableColumn<Phong, String> clGiaTien;

	    @FXML
	    private TableColumn<Phong, String> clLoaiPhong;

	    @FXML
	    private TableColumn<Phong, String> clMaPhong;

	    @FXML
	    private TableColumn<Phong, String> clSTT;

	    @FXML
	    private TableColumn<Phong, String> clTrangThai;
	    
	    @FXML
	    private TableColumn<Phong, String> clTieuChi;
	    
	    @FXML
	    private TextField txt_GiaPhong;
	    
	    @FXML
	    private TextField txt_TieuChi;

	    @FXML
	    private TextField txt_Phong1;
	    private Phong_DAO pdao = new Phong_DAO();

	   
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	        ObservableList<String> list = FXCollections.observableArrayList("Phòng đơn", "Phòng đôi", "Phòng gia đình");
	        cbb.setItems(list);
	        cbb.setValue("Phòng đơn");
	        ObservableList<String> list1 = FXCollections.observableArrayList("Trống", "Đang thuê", "Sắp checkin", "Sắp checkout");
	        cbb2.setItems(list1);
	        cbb2.setValue("Trống");
	        clSTT.setCellFactory(col -> {
	            return new TableCell<Phong, String>() {
	                @Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setText(null);
	                    } else {
	                        // Số thứ tự = index + 1
	                        setText(String.valueOf(getIndex() + 1));
	                    }
	                }
	            };
	        });
	        
	        
	        clMaPhong.setCellValueFactory(new PropertyValueFactory<>("idPhong"));
	        clLoaiPhong.setCellValueFactory(cellData -> {
	        	LoaiPhong lp = cellData.getValue().getLoaiPhong();
	        	return new ReadOnlyStringWrapper(lp.toString());
	        });
	        clGiaTien.setCellValueFactory(new PropertyValueFactory<>("donGia"));
	        clTrangThai.setCellValueFactory(cellData -> {
	        	TrangThaiPhong tt = cellData.getValue().getTrangThai();
	        	return new ReadOnlyStringWrapper(tt.toString());
	        });
	        clTieuChi.setCellValueFactory(new PropertyValueFactory<>("tieuChi"));
	        
	        tablePhong.setOnMouseClicked(event -> {
	        	Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();
	        	if (selectedPhong != null) {
	        		txt_Phong1.setText(selectedPhong.getIdPhong());
	        		cbb.setValue(selectedPhong.getLoaiPhongString());
	        		txt_GiaPhong.setText(String.valueOf(selectedPhong.getDonGia()));
	        		cbb2.setValue(selectedPhong.getTrangThaiString());
	        		txt_TieuChi.setText(selectedPhong.getTieuChi());
	        	}
	        });
	        
	        loadTableData();
	        addUserLogin();
	    }
	    private void loadTableData() {
	        try {
	            Phong_DAO pdao = new Phong_DAO();
	            ArrayList<Phong> dsp = pdao.getAllPhong();

	            ObservableList<Phong> observableList = FXCollections.observableArrayList(dsp);
	            tablePhong.getItems().clear();
	            tablePhong.setItems(observableList);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @FXML
	    void moGiaoDienDichVu(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLDichVu");
	    }

	    @FXML
	    void moGiaoDienHoaDon(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLHoaDon");
	    }

	    @FXML
	    void moGiaoDienKhachHang(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLKhachHang");
	    }

	    @FXML
	    void moGiaoDienPhong(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLPhong");
	    }

	    @FXML
	    void moGiaoDienQLNV(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLNhanVien");
	    }

	    @FXML
	    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLPhong");
	    }

	    @FXML
	    void moGiaoDienTaiKhoan(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLTaiKhoan");
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
	    	App.setRoot("GD_QLUuDai");
	    }
	    @FXML
	    void suaPhong(MouseEvent event) {
	    	if (txt_Phong1.getText().trim().isEmpty()) {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng chọn phòng cần sửa!");
	            alert.showAndWait();
	            return;
	    	}
	    	if (txt_GiaPhong.getText().trim().isEmpty() ||
	    			txt_TieuChi.getText().trim().isEmpty()) {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
	            alert.showAndWait();
	            return;
	    	}
	    	
	    	String maPhong = txt_Phong1.getText();
	        String loaiPhongString = cbb.getValue();
	        LoaiPhong loaiPhong = null;
	        if (loaiPhongString.equals("Phòng đơn")) {
	            loaiPhong = LoaiPhong.PHONGDON;
	        } else if (loaiPhongString.equals("Phòng đôi")) {
	            loaiPhong = LoaiPhong.PHONGDOI;
	        } else if (loaiPhongString.equals("Phòng gia đình")) {
	            loaiPhong = LoaiPhong.PHONGGIADINH;
	        }
	        double donGia = Double.valueOf(txt_GiaPhong.getText());
	        String trangThaiString = cbb2.getValue();
	        TrangThaiPhong trangThai = null;
	        if (trangThaiString.equals("Trống")) {
	            trangThai = TrangThaiPhong.TRONG;
	        } else if (trangThaiString.equals("Đang thuê")) {
	            trangThai = TrangThaiPhong.DANGTHUE;
	        } else if (trangThaiString.equals("Sắp checkin")) {
	            trangThai = TrangThaiPhong.SAPCHECKIN;
	        } else if (trangThaiString.equals("Sắp checkout")) {
	            trangThai = TrangThaiPhong.SAPCHECKOUT;
	        }
	        String tieuChi = txt_TieuChi.getText();
	        
	        Phong p = new Phong(maPhong, loaiPhong, donGia, trangThai, tieuChi);
	        boolean updated = new Phong_DAO().capNhatPhong(p);
	        
            if (updated) {
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Thành công");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Cập nhật thông tin nhân viên thành công!");
                successAlert.showAndWait();

                // Cập nhật lại TableView và xóa trắng form
                loadTableData();
                clearFields();
                } else {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Lỗi");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Không thể cập nhật thông tin khách hàng!");
                errorAlert.showAndWait();
            }
	    }
	    
	    @FXML
	    void themPhong(MouseEvent event) throws Exception {
	    
	        // Kiểm tra các trường bắt buộc
	        if (txt_Phong1.getText().trim().isEmpty() || 
	        	cbb.getValue() == null ||
	        	txt_GiaPhong.getText().trim().isEmpty() ||
	        	cbb2.getValue() == null ||
	            txt_GiaPhong.getText().trim().isEmpty()||
	            txt_TieuChi.getText().trim().isEmpty()
	            ) {
	            
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
	            alert.showAndWait();
	            return;
	        }
	        
	        if(!kiemTraDuLieu()) {
	    		return;
	    	}
	    	
	        // Lấy thông tin từ các trường
	        String idPhong = txt_Phong1.getText();
	        String loaiPhongString = cbb.getValue();
	        LoaiPhong loaiPhong = null;
	        if (loaiPhongString.equals("Phòng đơn")) {
	            loaiPhong = LoaiPhong.PHONGDON;
	        } else if (loaiPhongString.equals("Phòng đôi")) {
	            loaiPhong = LoaiPhong.PHONGDOI;
	        } else if (loaiPhongString.equals("Phòng gia đình")) {
	            loaiPhong = LoaiPhong.PHONGGIADINH;
	        }
	        double donGia = Double.parseDouble(txt_GiaPhong.getText());
	        String trangThaiString = cbb2.getValue();
	        TrangThaiPhong trangThai = null;
	        if (trangThaiString.equals("Trống")) {
	            trangThai = TrangThaiPhong.TRONG;
	        } else if (trangThaiString.equals("Đang thuê")) {
	            trangThai = TrangThaiPhong.DANGTHUE;
	        } else if (trangThaiString.equals("Sắp checkin")) {
	            trangThai = TrangThaiPhong.SAPCHECKIN;
	        } else if (trangThaiString.equals("Sắp checkout")) {
	            trangThai = TrangThaiPhong.SAPCHECKOUT;
	        }
	        String tieuChi = txt_TieuChi.getText();
	        
	        // Tạo đối tượng Phong
	        Phong newPhong = new Phong(idPhong, loaiPhong, donGia, trangThai, tieuChi);

	        // Thêm nhân viên vào cơ sở dữ liệu
	        try {
	            Phong_DAO phongDAO = new Phong_DAO();
	            boolean added = phongDAO.themPhong(newPhong);
	            
	            if (added) {
	                // Thông báo thành công
	                Alert successAlert = new Alert(AlertType.INFORMATION);
	                successAlert.setTitle("Thành công");
	                successAlert.setHeaderText(null);
	                successAlert.setContentText("Thêm phòng mới thành công!\nPhòng: " + idPhong);
	                successAlert.showAndWait();

	                // Cập nhật TableView
	                loadTableData();
	                
	                // Xóa trắng các trường nhập liệu
	                clearFields();
	            } else {
	                Alert errorAlert = new Alert(AlertType.ERROR);
	                errorAlert.setTitle("Lỗi");
	                errorAlert.setHeaderText(null);
	                errorAlert.setContentText("Không thể thêm phòng!");
	                errorAlert.showAndWait();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            Alert errorAlert = new Alert(AlertType.ERROR);
	            errorAlert.setTitle("Lỗi");
	            errorAlert.setHeaderText(null);
	            errorAlert.setContentText("Đã xảy ra lỗi trong quá trình thêm phòng!\n" + e.getMessage());
	            errorAlert.showAndWait();
	        }
	    }

	    @FXML
	    void timKiemPhong(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKPhong");
	    }

	    @FXML
	    void xoaPhong(MouseEvent event) {
	        Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();
	        if (selectedPhong == null) {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng chọn phòng cần xóa!");
	            alert.showAndWait();
	            return;
	        }

	        // Kiểm tra trạng thái phòng
	        if (selectedPhong.getTrangThai() == TrangThaiPhong.DANGTHUE) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Lỗi");
	            alert.setHeaderText(null);
	            alert.setContentText("Không thể xóa phòng đang được thuê!");
	            alert.showAndWait();
	            return;
	        }

	        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
	        confirmAlert.setTitle("Xác nhận xóa");
	        confirmAlert.setHeaderText(null);
	        confirmAlert.setContentText("Bạn có chắc chắn muốn xóa phòng " + selectedPhong.getIdPhong() + "?");

	        Optional<ButtonType> result = confirmAlert.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	            try {
	                boolean deleted = pdao.xoaPhong(selectedPhong.getIdPhong());
	                if (deleted) {
	                    Alert successAlert = new Alert(AlertType.INFORMATION);
	                    successAlert.setTitle("Thành công");
	                    successAlert.setHeaderText(null);
	                    successAlert.setContentText("Đã xóa phòng thành công!");
	                    successAlert.showAndWait();
	                    
	                    // Cập nhật lại bảng
	                    loadTableData();
	                    clearFields();
	                } else {
	                    Alert errorAlert = new Alert(AlertType.ERROR);
	                    errorAlert.setTitle("Lỗi");
	                    errorAlert.setHeaderText(null);
	                    errorAlert.setContentText("Không thể xóa phòng. Vui lòng thử lại!");
	                    errorAlert.showAndWait();
	                }
	            } catch (Exception e) {
	                Alert errorAlert = new Alert(AlertType.ERROR);
	                errorAlert.setTitle("Lỗi");
	                errorAlert.setHeaderText(null);
	                errorAlert.setContentText("Đã xảy ra lỗi trong quá trình xóa phòng!");
	                errorAlert.showAndWait();
	                e.printStackTrace();
	            }
	        }
	    }
	    private void clearFields() {
	    	txt_GiaPhong.setText("");
	    	txt_Phong1.setText("");
	    	txt_TieuChi.setText("");
	    	cbb.setValue("Phòng đơn");
	    	cbb2.setValue("Trống");
	    }

	    @FXML
	    void xoaTrang(MouseEvent event) {
	    	clearFields();
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
	    	if (txt_Phong1.getText().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Mã phòng không được rỗng");
	            return false;
	        }
	    	if (!txt_Phong1.getText().matches("^T\\d{2}P\\d{2}$")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Mã phòng sai định dạng (TxxPyy: xx số tầng, yy số phòng)");
	            return false;
	        }
	     
	        
	        if (cbb.getValue().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Loại phòng không được rỗng");
	            return false;
	        }
	        if (txt_GiaPhong.getText().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Giá phòng không được rỗng");
	            return false;
	        }
	        if (cbb2.getValue().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Trạng thái không được rỗng");
	            return false;
	        }
	        if (txt_TieuChi.getText().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Tiêu chí không được rỗng");
	            return false;
	        }
			return true;
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
	    @FXML
	    void dongUngDung(MouseEvent event) throws IOException {
			App.user = "";
			Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
			stage.close();
			App.openModal("GD_DangNhap");
	    }
}
