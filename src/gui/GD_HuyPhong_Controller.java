package gui;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.KhachHang_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.KhachHang;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.App;

public class GD_HuyPhong_Controller implements Initializable{
	
	@FXML
    private ImageView avt;

    @FXML
    private Button btnTim;

    @FXML
    private TableColumn<Object[], String> clMaPhong;

    @FXML
    private TableColumn<Object[], Integer> clSTT;

    @FXML
    private TableColumn<Object[], LocalDate> clTGNP;

    @FXML
    private TableColumn<Object[], LocalDate> clTGTP;

    @FXML
    private TableColumn<Object[], String> clmaPhieuThue;

    @FXML
    private TableColumn<Object[], String> cltenKH;
    
    @FXML
    private TableColumn<Object[], Void> clHuy;

    @FXML
    private ImageView icon_QuanLy;

    @FXML
    private ImageView icon_ThongKe;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label l;

    @FXML
    private Label lb_DonGia;

    @FXML
    private Label lb_LoaiPhong;

    @FXML
    private Label lb_QuanLy;

    @FXML
    private Label lb_ThongKe;

    @FXML
    private Label lb_TieuChi;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label maNV;

    @FXML
    private TableView<Object[]> tbPhieuThue;

    @FXML
    private Label tenNV;

    @FXML
    private TextField txt_CCCD;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private String cccd;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    lb_LoaiPhong.setText("");
	    lb_TieuChi.setText("");
	    lb_DonGia.setText("");
 
	    loadDuLieu();
	    btnTim.setOnAction(event -> timPhong(txt_CCCD.getText().trim()));

	    addUserLogin();
	}
	
	private void loadDuLieu() {
	    // Tạo danh sách chứa dữ liệu kết hợp
	    ObservableList<Object[]> data = FXCollections.observableArrayList();

	    // Lấy dữ liệu từ DAO
	    PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
	    ArrayList<PhieuThuePhong> dsPhieuThue = phieuThueDAO.layPhieuThueTheoHieuLuc(true);

	    KhachHang_DAO khachHangDAO = new KhachHang_DAO();

	    // Kết hợp dữ liệu từ hai bảng
	    for (PhieuThuePhong pt : dsPhieuThue) {
	        KhachHang kh = khachHangDAO.getKhachHangTheoMa(pt.getKhachHang().getIdKhachHang());
	        data.add(new Object[]{
	            pt.getIdPhieuThue(), // Thêm ID phiếu thuê
	            kh.getTenKhachHang(), 
	            pt.getPhong().getIdPhong(), 
	            pt.getThoiGianNhanPhong(), 
	            pt.getThoiHanGiaoPhong()
	        });
	    }

	    // Gắn dữ liệu vào TableView
	    tbPhieuThue.setItems(data);

	    // Cột STT tự tăng
	    clSTT.setCellValueFactory(cellData -> 
	        new ReadOnlyObjectWrapper<>(tbPhieuThue.getItems().indexOf(cellData.getValue()) + 1)
	    );

	 // Gắn giá trị cột

	    clmaPhieuThue.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[1])); // ID khách hàng
	    cltenKH.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[2])); // Tên khách hàng
	    clMaPhong.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[3])); // ID phòng
	    clTGNP.setCellValueFactory(cellData -> new SimpleObjectProperty<>((LocalDate) cellData.getValue()[4])); // Thời gian nhận phòng
	    clTGTP.setCellValueFactory(cellData -> new SimpleObjectProperty<>((LocalDate) cellData.getValue()[5])); // Thời hạn giao phòng
	    clHuy.setCellFactory(col -> new TableCell<>() {
	        private final Button btnHuy = new Button("Huỷ");

	        {
	            // Căn chỉnh và đặt màu nền cho nút
	            btnHuy.setStyle("-fx-background-color: red; -fx-text-fill: white;");
	            btnHuy.setOnAction(event -> {
	                // Hiển thị thông báo xác nhận
	                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	                alert.setTitle("Xác nhận huỷ");
	                alert.setHeaderText("Bạn có chắc chắn muốn huỷ phiếu thuê này?");
	                alert.setContentText("Thao tác này không thể hoàn tác.");

	                // Xử lý kết quả xác nhận
	                Optional<ButtonType> result = alert.showAndWait();
	                if (result.isPresent() && result.get() == ButtonType.OK) {
	                    // Lấy dữ liệu phiếu thuê hiện tại
	                    Object[] selectedItem = getTableView().getItems().get(getIndex());

	                    // Tạo đối tượng PhieuThuePhong từ dữ liệu
	                    PhieuThuePhong phieuThue = new PhieuThuePhong();
	                    phieuThue.setIdPhieuThue((String) selectedItem[0]); // ID phiếu thuê
	                    phieuThue.setKhachHang(new KhachHang((String) selectedItem[1])); // ID khách hàng
	                    phieuThue.setPhong(new Phong((String) selectedItem[3])); // ID phòng
	                    phieuThue.setThoiGianNhanPhong((LocalDate) selectedItem[4]); // Thời gian nhận phòng
	                    phieuThue.setThoiHanGiaoPhong((LocalDate) selectedItem[5]); // Thời hạn giao phòng
	                    phieuThue.setHieuLuc(false); // Đặt hiệu lực thành false

	                    // Gọi phương thức suaPhieuThue để huỷ hiệu lực phiếu thuê
	                    PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
	                    boolean isUpdated = phieuThueDAO.suaPhieuThue(phieuThue);

	                    if (isUpdated) {
	                        // Tải lại danh sách phiếu thuê
	                        loadDuLieu();

	                        // Hiển thị thông báo thành công
	                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                        successAlert.setTitle("Thành công");
	                        successAlert.setHeaderText(null);
	                        successAlert.setContentText("Phiếu thuê đã được huỷ thành công.");
	                        successAlert.show();
	                    } else {
	                        // Hiển thị thông báo lỗi
	                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	                        errorAlert.setTitle("Lỗi");
	                        errorAlert.setHeaderText(null);
	                        errorAlert.setContentText("Không thể huỷ phiếu thuê. Vui lòng thử lại.");
	                        errorAlert.show();
	                    }
	                }
	            });
	        }

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setGraphic(null);
	            } else {
	                // Căn giữa nút trong cột
	                setGraphic(btnHuy);
	                setAlignment(Pos.CENTER);
	            }
	        }
	    });
	}



	
    private void timPhong(String cccd) {
        // Kiểm tra xem người dùng đã nhập CCCD chưa
        if (cccd.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Vui lòng nhập CCCD.").showAndWait();
            return;
        }

        // Tìm kiếm khách hàng theo CCCD
        KhachHang_DAO khachHangDAO = new KhachHang_DAO();
        KhachHang khachHang = khachHangDAO.getKhachHangTheoCCCD(cccd);

        // Kiểm tra xem khách hàng có tồn tại không
        if (khachHang == null) {
            new Alert(Alert.AlertType.WARNING, "Không tìm thấy khách hàng với CCCD này.").showAndWait();
            return;
        }

        // Lấy danh sách phiếu thuê của khách hàng
        PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
        ArrayList<PhieuThuePhong> phieuThueList = phieuThueDAO.layPhieuThueTheoMaKH(khachHang.getIdKhachHang());

        // Lọc danh sách phiếu thuê còn hiệu lực
        ArrayList<PhieuThuePhong> filteredPhieuThue = new ArrayList<>();
        for (PhieuThuePhong pt : phieuThueList) {
            if (pt.getHieuLuc()) {
                filteredPhieuThue.add(pt);
            }
        }

        // Kiểm tra xem khách hàng có phiếu thuê phòng nào không
        if (filteredPhieuThue.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Khách hàng này chưa đặt phòng.").showAndWait();
            return;
        }

        // Sắp xếp danh sách phiếu thuê theo ngày gần nhất
        LocalDate currentDate = LocalDate.now();
        filteredPhieuThue.sort((pt1, pt2) -> {
            long diff1 = ChronoUnit.DAYS.between(currentDate, pt1.getThoiGianNhanPhong());
            long diff2 = ChronoUnit.DAYS.between(currentDate, pt2.getThoiGianNhanPhong());
            return Long.compare(diff1, diff2);
        });

        // Render danh sách phòng đã lọc
//        renderArrayPhong(filteredPhieuThue);
    }	

    @FXML
    void moGiaoDienGiaHanPhong(MouseEvent event) throws IOException {
		App.setRoot("GD_GiaHanPhong");
    }

    @FXML
    void moGiaoDienHuyPhong(MouseEvent event) throws IOException {
		App.setRoot("GD_HuyPhong");
    }

    @FXML
    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
		App.setRoot("GD_QLPhong");
    }

    @FXML
    void moGiaoDienSoDoPhong(MouseEvent event) throws IOException {
		App.setRoot("GD_SoDoPhong");
    }

    @FXML
    void moGiaoDienThongKe(MouseEvent event) throws IOException {
		App.setRoot("GD_ThongKeDoanhThu");
    }

    @FXML
    void moGiaoDienTimKiem(MouseEvent event) throws IOException {
		App.setRoot("GD_TKPhong");
    }
	@FXML
	private void moGDDoiPhong() throws IOException {
		App.setRoot("GD_DoiPhong");
	}

	@FXML
	private void moGDDatPhong() throws IOException {
		App.openModal("GD_DatPhong", 800, 684);
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
