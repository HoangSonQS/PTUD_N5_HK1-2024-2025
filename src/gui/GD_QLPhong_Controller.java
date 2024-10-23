package gui;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import connectDB.ConnectDB;
import dao.Phong_DAO;
import entity.LoaiPhong;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
	    private ComboBox cbb;
	    
	    @FXML
	    private ComboBox cbb2;

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
	    	ObservableList<String> list = FXCollections.observableArrayList(
	    			LoaiPhong.PHONGDON.toString(), LoaiPhong.PHONGDOI.toString(), LoaiPhong.PHONGGIADINH.toString());
	    		cbb.setItems(list);
	    		cbb.setValue("Phòng đơn"); // Make sure you have string values here.
	    		
	    		ObservableList<String> list2 = FXCollections.observableArrayList(
	    			TrangThaiPhong.TRONG.toString(), TrangThaiPhong.DANGTHUE.toString(), TrangThaiPhong.SAPCHECKIN.toString(), TrangThaiPhong.SAPCHECKOUT.toString());
	    			cbb2.setItems(list2);
	    			cbb2.setValue("Trống");
			
			clMaPhong.setCellValueFactory(new PropertyValueFactory<>("idPhong")); // Correct property name
	        clLoaiPhong.setCellValueFactory(cellData -> {
	        	LoaiPhong loaiPhong = cellData.getValue().getLoaiPhong();
	        	String lp = loaiPhong.toString();
	        	return new ReadOnlyStringWrapper(lp);
	        }); // Changed
	        clGiaTien.setCellValueFactory(new PropertyValueFactory<>("donGia"));
	        clTrangThai.setCellValueFactory(cellData -> {
	        	TrangThaiPhong trangThaiPhong = cellData.getValue().getTrangThai();
	        	String tt = trangThaiPhong.toString();
	        	return new ReadOnlyStringWrapper(tt);
	        });
	        
	        tablePhong.setItems(new Phong_DAO().getAllPhongOb());
	        
	        tablePhong.setOnMouseClicked(event -> {
	            if (event.getClickCount() == 2) {
	                Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();
	                if (selectedPhong != null) {
	                    txt_Phong1.setText(selectedPhong.getIdPhong());
	                    txt_GiaPhong.setText(String.valueOf(selectedPhong.getDonGia()));
	        			cbb.setValue(selectedPhong.getLoaiPhongString());
	        			cbb2.setValue(selectedPhong.getTrangThaiString());
	                }
	            }
	        });
	        
	        btnThem.setOnAction(event -> {
	        	String idPhong = txt_Phong1.getText();
				double giaTien = Double.parseDouble(txt_GiaPhong.getText());
				String loaiPhongStr = (String) cbb.getValue();
				LoaiPhong loaiPhong;
				if (loaiPhongStr.equalsIgnoreCase(LoaiPhong.PHONGDON.toString())) {
					loaiPhong = LoaiPhong.PHONGDON;
				} else if (loaiPhongStr.equalsIgnoreCase(LoaiPhong.PHONGDOI.toString())) {
					loaiPhong = LoaiPhong.PHONGDOI;
				} else {
					loaiPhong = LoaiPhong.PHONGGIADINH;
				}
				Phong newPhong = new Phong(idPhong, loaiPhong, giaTien, TrangThaiPhong.TRONG);
				System.out.print(newPhong);
				try {
	                pdao.themPhong(newPhong);
	            	txt_Phong1.clear();
					txt_GiaPhong.clear();
					cbb.setValue("Phòng đơn");
					cbb2.setValue("Trống");
					
					tablePhong.setItems(null);
				}catch (IllegalArgumentException ex) {
					System.err.println("Lỗiiiiiiiiiiii.");
					// Handle the error appropriately, e.g., show an error dialog.
				}
	        });
	        
	        btnXoa.setOnAction(event -> {
	        	Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();

	            if (selectedPhong != null) {
	                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	                confirmationAlert.setTitle("Xác nhận xóa");
	                confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa phòng " + selectedPhong.getIdPhong() + "?");
	                confirmationAlert.setContentText("Các dữ liệu liên quan sẽ bị mất.");

	                Optional<ButtonType> result = confirmationAlert.showAndWait();

	                if (result.get() == ButtonType.OK) {
	                    try {
	                        if (pdao.xoaPhong(selectedPhong.getIdPhong())) {
	                            tablePhong.getItems().remove(selectedPhong); // Xóa trực tiếp từ ObservableList
	                            System.out.println("Xóa phòng thành công!");
	                        } else {
	                            System.err.println("Xóa phòng thất bại!");
	                            new Alert(Alert.AlertType.ERROR, "Lỗi khi xóa phòng.").showAndWait();
	                        }
	                    } catch (SQLException e) {
	                        e.printStackTrace();
	                        new Alert(Alert.AlertType.ERROR, "Lỗi SQL khi xóa phòng: " + e.getMessage()).showAndWait();
	                    }
	                }
	            } else {
	                new Alert(Alert.AlertType.WARNING, "Vui lòng chọn phòng cần xóa.").showAndWait();
	            }
	        });
	        
	    }

	    
	    @FXML
	    void moGiaoDienDichVu(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienHoaDon(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienKhachHang(MouseEvent event) {

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
	    void moGiaoDienThuePhong(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienTimKiem(MouseEvent event) {

	    }

	    @FXML
	    void moGiaoDienUuDai(MouseEvent event) {

	    }
	    
}
