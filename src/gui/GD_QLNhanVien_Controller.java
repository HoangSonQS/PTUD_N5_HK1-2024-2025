package gui;

import java.io.IOException;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import dao.Enum_ChucVu;
import dao.NhanVien_DAO;
import entity.ChucVu;
import entity.NhanVien;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.App;

public class GD_QLNhanVien_Controller implements Initializable{
	
		@FXML
	    private ImageView avt;
	
	    @FXML
	    private Button btnSua;
	
	    @FXML
	    private Button btnThem;
	
	    @FXML
	    private Button btnTimKiem;
	
	    @FXML
	    private Button btnXoa;
	
	    @FXML
	    private Button btnXoaTrang;
	    
	    @FXML
	    private ComboBox<String> cbbGioiTinh;
	    
	    @FXML
	    private ComboBox<String> cbbChucVu;

	    @FXML
	    private Label lb_MaNV;

	    @FXML
	    private TextField txtCCCD;

	    @FXML
	    private DatePicker txtNgaySinh;

	    @FXML
	    private TextField txtSDT;

	    @FXML
	    private TextField txtTenNV;
	    
	    @FXML
	    private TableView<NhanVien> tableNhanVien;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clSTT;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clIDNV;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clTenNV;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clSDT;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clNgaySinh;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clGioiTinh;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clCCCD;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clChucVu;
	    
	    @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
	        // Set up ComboBox
	        ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ");
	        cbbGioiTinh.setItems(list);
	        cbbGioiTinh.setValue("");
	        
	     // Set up ComboBox
	        ObservableList<String> list1 = FXCollections.observableArrayList("Nhân viên lễ tân", "Người quản lý");
	        cbbChucVu.setItems(list1);
	        cbbChucVu.setValue("");
	        
	        clSTT.setCellFactory(col -> {
	            return new TableCell<NhanVien, String>() {
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
	        
	        // Set style cho cột STT
	        clSTT.setStyle("-fx-alignment: CENTER;");
	        clSTT.setPrefWidth(50);
	        clSTT.setResizable(false);
	        
	        // Đảm bảo STT cập nhật khi data thay đổi
	        tableNhanVien.getItems().addListener((ListChangeListener<? super NhanVien>) c -> {
	            tableNhanVien.refresh();
	        });

	        // Set up các cột cho TableView
	        clIDNV.setCellValueFactory(new PropertyValueFactory<>("idNhanVien"));
	        clTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
	        clSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
	        
	        // Format ngày sinh
	        clNgaySinh.setCellValueFactory(cellData -> {
	            LocalDate ngaySinh = cellData.getValue().getNgaySinh();
	            if (ngaySinh != null) {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                String ngaySinhFormatted = ngaySinh.format(formatter);
	                return new ReadOnlyStringWrapper(ngaySinhFormatted);
	            }
	            return new ReadOnlyStringWrapper("");
	        });

	        clCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));
	        
	        // Format giới tính
	        clGioiTinh.setCellValueFactory(cellData -> {
	            boolean gioiTinh = cellData.getValue().isGioiTinh();
	            return new ReadOnlyStringWrapper(gioiTinh ? "Nam" : "Nữ");
	        });

	        // Format chức vụ
	        clChucVu.setCellValueFactory(cellData -> {
	            Enum_ChucVu chucVu = cellData.getValue().getChucVu();
	            String chucVuString = (chucVu != null) ? chucVu.toString() : "";
	            return new ReadOnlyStringWrapper(chucVuString);
	        });

	        // Load dữ liệu
	        loadTableData();
	        
	        tableNhanVien.setOnMouseClicked(event -> {
	            NhanVien selectedNhanVien = tableNhanVien.getSelectionModel().getSelectedItem();
	            if (selectedNhanVien != null) {
	                // Cập nhật thông tin vào các trường
	                lb_MaNV.setText(selectedNhanVien.getIdNhanVien());
	                txtTenNV.setText(selectedNhanVien.getTenNhanVien());
	                txtSDT.setText(selectedNhanVien.getSoDienThoai());
	                txtCCCD.setText(selectedNhanVien.getCccd());
	                txtNgaySinh.setValue(selectedNhanVien.getNgaySinh()); // Đảm bảo kiểu dữ liệu là LocalDate
	                cbbGioiTinh.setValue(selectedNhanVien.isGioiTinh() ? "Nam" : "Nữ");
	                Enum_ChucVu chucVu = selectedNhanVien.getChucVu();
	                if (chucVu != null) {
	                    cbbChucVu.setValue(chucVu.toString()); // Hoặc sử dụng giá trị tương ứng nếu cần
	                } else {
	                    cbbChucVu.setValue(null); // Hoặc một giá trị mặc định
	                }
	            }
	        });
	    }
	    
	    private void loadTableData() {
	        try {
	            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
	            ArrayList<NhanVien> dsNV = nhanVienDAO.getAllNhanVien();
	            
	            // Debug: in ra số lượng nhân viên
	            System.out.println("Số lượng nhân viên: " + dsNV.size());
	            
	            ObservableList<NhanVien> observableList = FXCollections.observableArrayList(dsNV);
	            tableNhanVien.setItems(observableList);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Có thể thêm Alert để thông báo lỗi cho người dùng
	        }
	    }
	    
	    @FXML
	    void chonNV(MouseEvent event) {

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
	    
	    @FXML
	    void suaTTNV(MouseEvent event) {

	    }
	    
	    private String generateMaNV() {
	        // Lấy ngày hiện tại
	        LocalDate today = LocalDate.now();
	        String day = String.format("%02d", today.getDayOfMonth());
	        String month = String.format("%02d", today.getMonthValue());
	        String year = String.valueOf(today.getYear()).substring(2); // Lấy 2 chữ số cuối của năm

	        NhanVien_DAO nv = new NhanVien_DAO();
	        // Đếm số nhân viên đã thêm trong ngày hiện tại
	        int countToday = nv.getCountOfNhanVienInDay(today);
	        
	        // Tạo mã nhân viên với số thứ tự là countToday + 1
	        String maNV = String.format("NV%s%s%s%02d", year, month, day, countToday + 1);
	        
	        return maNV;
	    }

	    

	    @FXML
	    void themNV(MouseEvent event) {
	        // Lấy thông tin từ các trường
	        String tenNV = txtTenNV.getText();
	        String sdt = txtSDT.getText();
	        String cccd = txtCCCD.getText();
	        LocalDate ngaySinh = txtNgaySinh.getValue();
	        boolean gioiTinh = cbbGioiTinh.getValue().equals("Nam");

	        // Lấy giá trị chức vụ từ ComboBox
	        String chucVuString = cbbChucVu.getValue();
	        Enum_ChucVu chucVu = null;

	        // Chuyển đổi giá trị chức vụ thành Enum_ChucVu
	        if (chucVuString != null) {
	            if (chucVuString.equals("Nhân viên lễ tân")) {
	                chucVu = Enum_ChucVu.NHANVIENLETAN;
	            } else if (chucVuString.equals("Người quản lý")) {
	                chucVu = Enum_ChucVu.NGUOIQUANLY;
	            }
	        }

	        // Tạo mã nhân viên
	        String maNV = generateMaNV();
	        NhanVien_DAO nv = new NhanVien_DAO();

	        // Kiểm tra mã nhân viên đã tồn tại
	        if (nv.isMaNVExists(maNV)) {
	            System.out.println("Mã nhân viên đã tồn tại: " + maNV);
	            // Có thể hiển thị thông báo cho người dùng
	            return; // Dừng lại nếu mã đã tồn tại
	        }

	        // Tạo đối tượng NhanVien
	        NhanVien newNhanVien = new NhanVien(maNV, tenNV, sdt, ngaySinh, gioiTinh, cccd, chucVu);

	        // Thêm nhân viên vào cơ sở dữ liệu
	        try {
	            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
	            nhanVienDAO.themNhanVien(newNhanVien);

	            // Cập nhật bảng
	            loadTableData();
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Có thể thêm Alert để thông báo lỗi cho người dùng
	        }
	    }

	    @FXML
	    void xoaNV(MouseEvent event) {

	    }

	    @FXML
	    void xoaTrang(MouseEvent event) {
	    	txtTenNV.setText("");
	    	txtNgaySinh.setValue(null);
	    	txtSDT.setText("");
	    	txtCCCD.setText("");
	    }
	    
	    
	    
}
