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
	    	String maP = txt_Phong1.getText();
	    	String lp = (String) cbb.getValue();
	    	LoaiPhong loaiPhong = null;
	    	if (lp.equalsIgnoreCase(LoaiPhong.PHONGDOI.toString())) {
	    		loaiPhong = LoaiPhong.PHONGDOI;
	    	} else if (lp.equalsIgnoreCase(LoaiPhong.PHONGDON.toString())) {
	    		loaiPhong = LoaiPhong.PHONGDON;
	    	} else {
	    		loaiPhong = LoaiPhong.PHONGGIADINH;
	    	}
	    	double donGia = Double.parseDouble(txt_GiaPhong.getText()) ;
	    	String tt = (String) cbb2.getValue();
	    	TrangThaiPhong trangthai = null;
	    	if (tt.equalsIgnoreCase(TrangThaiPhong.TRONG.toString())) {
	    		trangthai = TrangThaiPhong.TRONG;
	    	} else if (tt.equalsIgnoreCase(TrangThaiPhong.DANGTHUE.toString())) {
	    		trangthai = TrangThaiPhong.DANGTHUE;
	    	} else if (tt.equalsIgnoreCase(TrangThaiPhong.SAPCHECKIN.toString())) {
	    		trangthai = TrangThaiPhong.SAPCHECKIN;
	    	} else {
	    		trangthai = TrangThaiPhong.SAPCHECKOUT;
	    	}
	    	Phong_DAO pdao = new Phong_DAO();
	    	pdao.themPhong(new Phong(maP, loaiPhong, donGia, trangthai));
	    	loadTableData();
	    }

	    @FXML
	    void timKiemPhong(MouseEvent event) {

	    }

	    @FXML
	    void xoaPhong(MouseEvent event) {
	    	Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();
	    	if (selectedPhong != null) {
	            Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.setTitle("Xác nhận xóa");
	            alert.setHeaderText(null);
	            alert.setContentText("Bạn có chắc chắn muốn xóa nhân viên này?");

	            Optional<ButtonType> result = alert.showAndWait();
	            if (result.get() == ButtonType.OK) {
	            	try {
	            		Phong_DAO pdao = new Phong_DAO();
	            		boolean deleteP = pdao.xoaPhong(selectedPhong.getIdPhong());
	            		if (deleteP) {
	                        Alert successAlert = new Alert(AlertType.INFORMATION);
	                        successAlert.setTitle("Thông báo");
	                        successAlert.setHeaderText(null);
	                        successAlert.setContentText("Đã xóa phòng thành công!");
	                        successAlert.showAndWait();
	                        loadTableData();
	                        clearFields();
	                        
	            		} else {
	                        Alert errorAlert = new Alert(AlertType.ERROR);
	                        errorAlert.setTitle("Lỗi");
	                        errorAlert.setHeaderText(null);
	                        errorAlert.setContentText("Không thể xóa phòng!");
	                        errorAlert.showAndWait();
	            		}
	            	} catch (Exception e) {
	                    e.printStackTrace();
	                    Alert errorAlert = new Alert(AlertType.ERROR);
	                    errorAlert.setTitle("Lỗi");
	                    errorAlert.setHeaderText(null);
	                    errorAlert.setContentText("Đã xảy ra lỗi trong quá trình xóa phòng!");
	                    errorAlert.showAndWait();
					}
	            }
	    	} else {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng chọn phòng cần xóa!");
	            alert.showAndWait();
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
