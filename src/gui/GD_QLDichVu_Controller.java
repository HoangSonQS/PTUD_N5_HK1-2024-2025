package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import dao.DichVu_DAO;
import dao.KhuyenMai_DAO;
import entity.DichVu;
import entity.KhuyenMai;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GD_QLDichVu_Controller implements Initializable{
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
    private TableColumn <DichVu, String> clDonGia;

    @FXML
    private TableColumn<DichVu, String> clMaDichVu;

    @FXML
    private TableColumn<DichVu, String> clSL;

    @FXML
    private TableColumn<DichVu, String> clSTT;

    @FXML
    private TableColumn<DichVu, String> clTenDV;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private Label lbIDDV;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label maNV;

    @FXML
    private TableView tableDichVu;

    @FXML
    private Label tenNV;

    @FXML
    private TextField txtDonGia;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtTenDichVu;
    
    @FXML
    void chonUD(MouseEvent event) {

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
    void moGiaoDienQLNV(MouseEvent event) {

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
    void moGiaoDienTimKiemUD(MouseEvent event) {

    }

    @FXML
    void moGiaoDienUuDai(MouseEvent event) {

    }

    @FXML
    void suaKM(MouseEvent event) {

    }

    private String generateMaDV() {
        // Format cơ bản: SPXXX
        String baseFormat = "SP";
        
        try {
            DichVu_DAO dvDAO = new DichVu_DAO();
            List<DichVu> listDV = dvDAO.getAllDichVu();
            
            int maxNumber = 0;
            
            for (DichVu dv : listDV) {
                String maDV = dv.getIdDichVu();
                // Kiểm tra mã có đúng format không
                if (maDV.startsWith(baseFormat)) {
                    try {
                        // Lấy 2 số cuối của mã
                        int number = Integer.parseInt(maDV.substring(2));
                        if (number > maxNumber) {
                            maxNumber = number;
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
            
            // Tăng số thứ tự lên 1
            maxNumber++;
            
            // Format số thứ tự thành 2 chữ số
            String numberStr = String.format("%03d", maxNumber);
            
            // Trả về mã khuyến mãi mới
            return baseFormat + numberStr;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    void themKM(MouseEvent event) {
    	try {
            // Lấy dữ liệu từ các trường nhập
            String tenDV = txtTenDichVu.getText().trim();
            String SoLuong = txtSoLuong.getText().trim();
            String DonGia = txtDonGia.getText().trim();
            // Kiểm tra dữ liệu
            if (tenDV.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập tên dịch vụ!");
                alert.showAndWait();
                txtTenDichVu.requestFocus();
                return;
            }
            
            if (SoLuong.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập số lượng!");
                alert.showAndWait();
                txtSoLuong.requestFocus();
                return;
            }
            int soluong;
            try {
                soluong = Integer.parseInt(SoLuong);
                if (soluong <= 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Số lượng phải lớn hơn 0!");
                    alert.showAndWait();
                    txtDonGia.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Số lượng phải là số!");
                alert.showAndWait();
                txtDonGia.requestFocus();
                return;
            }
            
            if (DonGia.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đơn giá!");
                alert.showAndWait();
                txtDonGia.requestFocus();
                return;
            }
            
            double dongia;
            try {
                dongia = Double.parseDouble(DonGia);
                if (dongia <= 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Đơn giá phải lớn hơn 0!");
                    alert.showAndWait();
                    txtDonGia.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đơn giá phải là số!");
                alert.showAndWait();
                txtDonGia.requestFocus();
                return;
            }
            
            // Tự động sinh mã khuyến mãi
            String maDV = generateMaDV();
            if (maDV == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Lỗi khi tạo mã dịch vụ!");
                alert.showAndWait();
                return;
            }
            
            // Tạo đối tượng khuyến mãi mới
            DichVu dv = new DichVu(maDV, tenDV, soluong, dongia);
            
            // Thêm vào database
            DichVu_DAO dvDAO = new DichVu_DAO();
            boolean result = dvDAO.themDichVu(dv);
            
            if (result) {
                // Hiển thị thông báo thành công
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Thêm dịch vụ thành công!");
                alert.showAndWait();
                
                // Clear các trường nhập liệu
                clearFields();
                
                // Reload lại dữ liệu trong bảng
                loadTableData();
                
                // Reset focus
                txtTenDichVu.requestFocus();
            } else {
                // Hiển thị thông báo lỗi
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null );
                alert.setContentText("Thêm khuyến mãi thất bại!");
                alert.showAndWait();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadTableData() {
        try {
            // Tạo DAO object
           DichVu_DAO dvDAO = new DichVu_DAO();
            
            // Xóa dữ liệu cũ trong table
            tableDichVu.getItems().clear();
            
            // Lấy danh sách khuyến mãi từ database
            ObservableList<DichVu> listDV = FXCollections.observableArrayList(dvDAO.getAllDichVu());
            
            // Thiết lập cell value factory cho các cột
            clMaDichVu.setCellValueFactory(new PropertyValueFactory<>("idDichVu"));
            clTenDV.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
            clSL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
            clDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
            
            // Cập nhật dữ liệu vào table
            tableDichVu.setItems(listDV);
            
            // Refresh table view
            tableDichVu.refresh();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {
    	lbIDDV.setText("");
    	txtTenDichVu.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
    }
    @FXML
    void xoaKM(MouseEvent event) {

    }

    @FXML
    void xoaTrang(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		generateMaDV();
		
	}
}
