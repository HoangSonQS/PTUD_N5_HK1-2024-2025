package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.HoaDon_DAO;
import dao.PhieuThuePhong_DAO;
import entity.HoaDon;
import entity.LoaiPhong;
import entity.PhieuThuePhong;
import entity.Phong;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GD_ThanhToanController implements Initializable{
	@FXML
    private AnchorPane GD_ThanhToan;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnThanhToan;

    @FXML
    private CheckBox checkBoxInHD;

    @FXML
    private TableColumn<PhieuThuePhong, String> donGiaCol;

    @FXML
    private TableColumn<?, ?> donViTinhCol;

    @FXML
    private TableColumn<PhieuThuePhong, LocalDate> gioRaCol;

    @FXML
    private TableColumn<PhieuThuePhong, LocalDate> gioVaoCol;

    @FXML
    private ImageView imgCheckKM;

    @FXML
    private TableColumn<PhieuThuePhong, String> loaiPhongCol;

    @FXML
    private TableColumn<PhieuThuePhong, String> maPhongCol;

    @FXML
    private TableColumn<?, ?> soLuongCol;

    @FXML
    private TableView<?> tableDichVu;

    @FXML
    private TableView<PhieuThuePhong> tablePhong;

    @FXML
    private TableColumn<?, ?> tenDichVuCol;

  

    @FXML
    private TableColumn<?, ?> thanhTienDVCol;

    @FXML
    private Text txtKhachHang;

    @FXML
    private Text txtMaHoaDon;

    @FXML
    private TextField txtMaKhuyenMai;

    @FXML
    private Text txtNgayLap;

    @FXML
    private Text txtNhanVien;

    @FXML
    private Text txtTienDaGiam;

    @FXML
    private Text txtTienDichVu;

    @FXML
    private TextField txtTienNhan;

    @FXML
    private Text txtTienPhong;

    @FXML
    private Text txtTienThua;

    @FXML
    private Text txtTienThue;

    @FXML
    private Text txtTongTien;
    
    public static String maHD = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
		dsPT = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD(maHD);
		
		maPhongCol.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getPhong().getIdPhong()));
		loaiPhongCol.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getPhong().getLoaiPhong().toString()));
		
		gioVaoCol.setCellValueFactory(new PropertyValueFactory<>("ThoiGianNhanPhong"));
		gioRaCol.setCellValueFactory(new PropertyValueFactory<>("ThoiHanGiaoPhong"));
		donGiaCol.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getPhong().getDonGia())));
		System.out.println(maHD);
		loadTableData();
	}
	private void loadTableData() {
	    try {
	        PhieuThuePhong_DAO dao = new PhieuThuePhong_DAO();
	        ArrayList<PhieuThuePhong> dsPT = dao.layPhieuThueTheoMaHD(maHD);
	        ObservableList<PhieuThuePhong> observableList = FXCollections.observableArrayList(dsPT);

	        // Gán danh sách vào TableView
	        tablePhong.setItems(observableList);;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
