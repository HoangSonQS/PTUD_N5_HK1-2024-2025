package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Enum_ChucVu;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.NhanVien_DAO;
import entity.HoaDon;
import entity.NhanVien;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GD_QLHoaDon_Controller implements Initializable{
	@FXML
    private ImageView avt;

    @FXML
    private Button btnThem;

    @FXML
    private Button btnTim;

    @FXML
    private Button btnXoaTrang;

    @FXML
    private TableColumn<HoaDon, String> clMaHD;

    @FXML
    private TableColumn<HoaDon, String> clMaKH;

    @FXML
    private TableColumn<HoaDon, String> clMaNV;

    @FXML
    private TableColumn<HoaDon, String> clSTT;

    @FXML
    private TableColumn<HoaDon, String> clTGCI;

    @FXML
    private TableColumn<HoaDon, String> clTGT;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private TableColumn<HoaDon, String> clMaKM;

    @FXML
    private TextField lb_HD;

    @FXML
    private TextField lb_MaKH;

    @FXML
    private TextField lb_MaKM;

    @FXML
    private TextField lb_MaNV;

    @FXML
    private TextField lb_TgCheckIn;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private TextField lb_tgTao;

    @FXML
    private TableView<HoaDon> tableNhanVien;

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
    
    @FXML
    void moGiaoDienTK(MouseEvent event) {

    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clSTT.setCellFactory(col -> {
			return new TableCell<HoaDon, String>() {
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
        clSTT.setStyle("-fx-alignment: CENTER;");
        clSTT.setPrefWidth(70);
        clSTT.setResizable(false);
        
        clMaHD.setCellValueFactory(new PropertyValueFactory<>("idHoaDon"));
        
        clMaNV.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getNhanVienLap().getIdNhanVien();
        	return new ReadOnlyStringWrapper(manv);
        });
        clMaKH.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getKhachHang().getIdKhachHang();
        	return new ReadOnlyStringWrapper(manv);
        });
        clMaKM.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getKhuyenmai().getIdKhuyenMai();
        	return new ReadOnlyStringWrapper(manv);
        });
        
        clTGT.setCellValueFactory(cellData -> {
            LocalDateTime tgTao = cellData.getValue().getThoiGianTao();
            if (tgTao != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                String ngaySinhFormatted = tgTao.format(formatter);
                return new ReadOnlyStringWrapper(ngaySinhFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        
        
        clTGCI.setCellValueFactory(cellData -> {
            LocalDateTime tgCK = cellData.getValue().getThoiGianCheckin();
            if (tgCK != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                String ngaySinhFormatted = tgCK.format(formatter);
                return new ReadOnlyStringWrapper(ngaySinhFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        
        tableNhanVien.setItems(new HoaDon_DAO().getAllHoaDonOb());
        tableNhanVien.setOnMouseClicked(event -> {
            HoaDon selectedHoaDon = tableNhanVien.getSelectionModel().getSelectedItem();
            if (selectedHoaDon != null) {
                // Cập nhật thông tin vào các trường
                lb_HD.setText(selectedHoaDon.getIdHoaDon());
                lb_MaNV.setText(selectedHoaDon.getNhanVienLap().getIdNhanVien());
                lb_MaKH.setText(selectedHoaDon.getKhachHang().getIdKhachHang());
                lb_MaKM.setText(selectedHoaDon.getKhuyenmai().getIdKhuyenMai());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                LocalDateTime tgTao = selectedHoaDon.getThoiGianTao();
                LocalDateTime tgCK = selectedHoaDon.getThoiGianCheckin();
                lb_tgTao.setText(tgTao.format(formatter));
                lb_TgCheckIn.setText(tgCK.format(formatter));
            }
        });
	}
    private void loadTableData() {
        try {
            HoaDon_DAO hdDao = new HoaDon_DAO();
            ArrayList<HoaDon> dshd = hdDao.getAllHoaDon();
            
            
            ObservableList<HoaDon> observableList = FXCollections.observableArrayList(dshd);
            tableNhanVien.setItems(observableList);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Có thể thêm Alert để thông báo lỗi cho người dùng
        }
    }
    @FXML
    void themHD(MouseEvent event) {
    	String maHD = lb_HD.getText();
    	String maNV = lb_MaNV.getText();
    	String maKH = lb_MaKH.getText();
    	String maKM = lb_MaKM.getText();
        LocalDateTime thoiGianTao = LocalDateTime.parse(lb_tgTao.getText(), DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        LocalDateTime thoiGianCheckin = LocalDateTime.parse(lb_TgCheckIn.getText(), DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
    	
    	HoaDon_DAO hddao = new HoaDon_DAO();
    	hddao.themHoaDon(new HoaDon(maHD, new NhanVien_DAO().getNhanVienTheoMa(maNV), new KhachHang_DAO().getKhachHangTheoMa(maKH), new KhuyenMai_DAO().layKhuyenMaiTheoMa(maKM), thoiGianTao, thoiGianCheckin));
    	loadTableData();
    }
    @FXML
    void xoaTrang(MouseEvent event) {
    	lb_HD.setText("");
    	lb_MaKH.setText("");
    	lb_MaKM.setText("");
    	lb_MaNV.setText("");
    	lb_TgCheckIn.setText("");
    	lb_tgTao.setText("");
    	tableNhanVien.getSelectionModel().clearSelection();
    }

}