package gui;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.NhanVien_DAO;
import dao.Phong_DAO;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	    private TextField txt_GiaPhong;

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
	        
	        tablePhong.setOnMouseClicked(event -> {
	        	Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();
	        	if (selectedPhong != null) {
	        		txt_Phong1.setText(selectedPhong.getIdPhong());
	        		cbb.setValue(selectedPhong.getLoaiPhongString());
	        		txt_GiaPhong.setText(String.valueOf(selectedPhong.getDonGia()));
	        		cbb2.setValue(selectedPhong.getTrangThaiString());
	        	}
	        });
	        
	        loadTableData();
	    }
	    private void loadTableData() {
	        try {
	            Phong_DAO pdao = new Phong_DAO();
	            ArrayList<Phong> dsp = pdao.getAllPhong();

	            ObservableList<Phong> observableList = FXCollections.observableArrayList(dsp);
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

	    }

	    @FXML
	    void themPhong(MouseEvent event) {
	        try {
	            // Kiểm tra dữ liệu đầu vào
	            if (txt_Phong1.getText().trim().isEmpty()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Lỗi");
	                alert.setHeaderText(null);
	                alert.setContentText("Vui lòng nhập mã phòng!");
	                alert.showAndWait();
	                return;
	            }

	            if (txt_GiaPhong.getText().trim().isEmpty()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Lỗi");
	                alert.setHeaderText(null);
	                alert.setContentText("Vui lòng nhập giá phòng!");
	                alert.showAndWait();
	                return;
	            }

	            String maP = txt_Phong1.getText();
	            String lp = cbb.getValue();
	            LoaiPhong loaiPhong = null;
	            if (lp.equalsIgnoreCase("Phòng đôi")) {
	                loaiPhong = LoaiPhong.PHONGDOI;
	            } else if (lp.equalsIgnoreCase("Phòng đơn")) {
	                loaiPhong = LoaiPhong.PHONGDON;
	            } else {
	                loaiPhong = LoaiPhong.PHONGGIADINH;
	            }

	            double donGia;
	            try {
	                donGia = Double.parseDouble(txt_GiaPhong.getText());
	                if (donGia <= 0) {
	                    Alert alert = new Alert(AlertType.ERROR);
	                    alert.setTitle("Lỗi");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Giá phòng phải lớn hơn 0!");
	                    alert.showAndWait();
	                    return;
	                }
	            } catch (NumberFormatException e) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Lỗi");
	                alert.setHeaderText(null);
	                alert.setContentText("Giá phòng không hợp lệ!");
	                alert.showAndWait();
	                return;
	            }

	            String tt = cbb2.getValue();
	            TrangThaiPhong trangthai = null;
	            if (tt.equalsIgnoreCase("Trống")) {
	                trangthai = TrangThaiPhong.TRONG;
	            } else if (tt.equalsIgnoreCase("Đang thuê")) {
	                trangthai = TrangThaiPhong.DANGTHUE;
	            } else if (tt.equalsIgnoreCase("Sắp checkin")) {
	                trangthai = TrangThaiPhong.SAPCHECKIN;
	            } else {
	                trangthai = TrangThaiPhong.SAPCHECKOUT;
	            }

	            // Kiểm tra xem phòng đã tồn tại chưa
	            if (pdao.getPhongTheoMa(maP) != null) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Lỗi");
	                alert.setHeaderText(null);
	                alert.setContentText("Mã phòng đã tồn tại!");
	                alert.showAndWait();
	                return;
	            }

	            Phong phong = new Phong(maP, loaiPhong, donGia, trangthai);
	            boolean result = pdao.themPhong(phong);
	            
	            if (result) {
	                Alert alert = new Alert(AlertType.INFORMATION);
	                alert.setTitle("Thành công");
	                alert.setHeaderText(null);
	                alert.setContentText("Thêm phòng thành công!");
	                alert.showAndWait();
	                loadTableData();
	                clearFields(); // Xóa các trường nhập liệu
	            } else {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Lỗi");
	                alert.setHeaderText(null);
	                alert.setContentText("Thêm phòng thất bại!");
	                alert.showAndWait();
	            }
	        } catch (Exception e) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Lỗi");
	            alert.setHeaderText(null);
	            alert.setContentText("Đã xảy ra lỗi trong quá trình thêm phòng!");
	            alert.showAndWait();
	            e.printStackTrace();
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
	    	txt_GiaPhong.setText("");
	    	cbb.setValue("Phòng đơn");
	    	cbb2.setValue("Trống");
	    }

	    @FXML
	    void xoaTrang(MouseEvent event) {
	    	clearFields();
	    }
}
