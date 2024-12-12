
package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


import entity.ChiTietHD_DichVu;
import dao.ChiTietHoaDon_DichVu_DAO;
import dao.HoaDon_DAO;
import dao.KhuyenMai_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.DichVu;
import entity.HoaDon;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.App;




public class GD_ThanhToanController implements Initializable{
	@FXML
    private AnchorPane GD_ThanhToan;
	public DecimalFormat df = new DecimalFormat("#,###,###,##0.##");
	public double tongtien;
    @FXML
    private Button btnBack;

    @FXML
    private Button btnThanhToan;

    @FXML
    private CheckBox checkBoxInHD;

    @FXML
    private TableColumn<PhieuThuePhong, String> donGiaCol;

    @FXML
    private TableColumn<ChiTietHD_DichVu, String> donViTinhCol;

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
    private TableColumn<ChiTietHD_DichVu, String> soLuongCol;

    @FXML
    private TableView<ChiTietHD_DichVu> tableDichVu;

    @FXML
    private TableView<PhieuThuePhong> tablePhong;

    @FXML
    private TableColumn<ChiTietHD_DichVu, String> tenDichVuCol;

    @FXML
    private TableColumn<ChiTietHD_DichVu, String> thanhTienDVCol;

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
    public TaiKhoan tk = App.tk;
    public static String maHD = null;

    public static double tienNhan = 0;
	public static double tienGiam = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
		dsPT = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD(maHD);
		loadTableData();
		loadThongTin();
//		handleEventInInput();
//		handleEventInBtn();
	}
	private void loadTableData() {
		maPhongCol.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getPhong().getIdPhong()));
		loaiPhongCol.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getPhong().getLoaiPhong().toString()));
		
		gioVaoCol.setCellValueFactory(new PropertyValueFactory<>("ThoiGianNhanPhong"));
		gioRaCol.setCellValueFactory(new PropertyValueFactory<>("ThoiHanGiaoPhong"));
		donGiaCol.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getPhong().getDonGia())));
		
//		tenDichVuCol.setCellValueFactory(cellData ->
//		new ReadOnlyStringWrapper(cellData.getValue().getDichVu().getTenSanPham()));
//		soLuongCol.setCellValueFactory(cellData ->
//		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getSoLuong())));
//		thanhTienDVCol.setCellValueFactory(cellData ->
//		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDichVu().getDonGia())));
		
		try {
	        PhieuThuePhong_DAO dao = new PhieuThuePhong_DAO();
	        ArrayList<PhieuThuePhong> dsPT = dao.layPhieuThueTheoMaHD(maHD);
	        ObservableList<PhieuThuePhong> observableList = FXCollections.observableArrayList(dsPT);

	        // Gán danh sách vào TableView
	        tablePhong.setItems(observableList);;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
//		try {
//			ChiTietHoaDon_DichVu_DAO dsct = new ChiTietHoaDon_DichVu_DAO();
//			List<ChiTietHD_DichVu> dsCT = dsct.layChiTietHoaDonTheoMaHoaDon(maHD);
//	        ObservableList<ChiTietHD_DichVu> observableList = FXCollections.observableList(dsCT);
//
//	        // Gán danh sách vào TableView
//	        tableDichVu.setItems(observableList);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
		
	    
	}
//	private void luuHoaDon() {
//		PhieuThuePhong pThuePhong = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD_1PT(maHD);
//		String khuyenmai = txtMaKhuyenMai.getText();
//		KhuyenMai km = new KhuyenMai_DAO().layKhuyenMaiTheoMa(khuyenmai);
//		LocalDate thoiHanNhanPhong = pThuePhong.getThoiGianNhanPhong(); // Lấy giá trị LocalDate
//		LocalDateTime thoiGianNhanPhongLocalDateTime = thoiHanNhanPhong.atTime(12, 00);
//		LocalDate thoiHan = pThuePhong.getThoiHanGiaoPhong(); // Lấy giá trị LocalDate
//		LocalDateTime thoiHanLocalDateTime = thoiHan.atTime(12, 00);
//		HoaDon hd = new HoaDon(maHD, tk.getNhanVien(),pThuePhong.getKhachHang(),km,thoiHanLocalDateTime,thoiGianNhanPhongLocalDateTime);
//		HoaDon_DAO dsHDao = new HoaDon_DAO();
//		dsHDao.themHoaDon(hd);
//	}
	private void loadThongTin() {
		txtMaHoaDon.setText(maHD);
		txtNhanVien.setText(tk.getNhanVien().getTenNhanVien());
		PhieuThuePhong pThuePhong = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD_1PT(maHD);
		txtKhachHang.setText(pThuePhong.getKhachHang().getTenKhachHang());
		txtNgayLap.setText(LocalDate.now().toString());
		System.out.println(maHD);
		HoaDon_DAO dsHD = new HoaDon_DAO();
		HoaDon hd = dsHD.layHoaDonTheoMaHoaDon(maHD) ;
		System.out.println(hd);
//		txtTienPhong.setText(String.valueOf(hd.thanhTien()));
//		txtTienThue.setText(String.valueOf(hd.tinhThue()));
//		ChiTietHoaDon_DichVu_DAO dsct = new ChiTietHoaDon_DichVu_DAO();
//		ChiTietHD_DichVu ct = dsct.layChiTietHoaDonTheoMaHoaDon1(maHD);
//		txtTienDichVu.setText(String.valueOf(ct.tongtien_DV()));
//		tongtien = hd.tongTien()+ct.tongtien_DV();
//		txtTongTien.setText(df.format(tongtien) + " VND");
	}

	public void handleEventInInput() {
		txtTienNhan.setOnKeyReleased(evt -> {
			if (txtTienNhan.getText().trim().isEmpty()) {
				return;
			}
			if (!Pattern.matches("[\\d,]*", txtTienNhan.getText().trim())) {
				txtTienNhan.setText(txtTienNhan.getText().trim().replaceAll("[^\\d,]", ""));
				txtTienNhan.positionCaret(txtTienNhan.getText().length());
			}
			try {
				tienNhan = Long.parseLong(txtTienNhan.getText().trim());
			} catch (NumberFormatException nf) {
				try {
					tienNhan = df.parse(txtTienNhan.getText().trim()).longValue();
				} catch (ParseException ex) {
					Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			double tienThua = tienNhan - tongtien;
			if (tienThua > 0) {
				btnThanhToan.setDisable(false);
				txtTienThua.setText(df.format(tienThua) + " VND");
			} else {
				btnThanhToan.setDisable(true);
				txtTienThua.setText("0 VND");
			}
		});
//		txtMaKhuyenMai.setOnKeyReleased((evt) -> {
//			CT_KhuyenMai ctkm = CT_KhuyenMai.getCT_KhuyenMaiTheoMaKM(txtMaKhuyenMai.getText().trim());
//			long tienDV = 0;
//			long tienPhong = 0;
//			for (ChiTietHD_DichVu ct : tableDichVu.getItems()) {
//				tienDV += ct.getThanhTien();
//			}
//			for (ChiTietHD_Phong ct : tablePhong.getItems()) {
//				tienPhong += ct.tinhThanhTien();
//			}
//			long tong = tienPhong + tienDV;
//			long tienVAT = (long) (tong * (App.VAT / 100.0));
//			tong += tienVAT;
//			tongTien = tong;
//			if (checkUseVoucher(ctkm)) {
//				tienGiam = tong * ctkm.getChietKhau() / 100;
//				txtTienDaGiam.setText(df.format(tienGiam) + " VND");
//				imgCheckKM.setImage(new Image("file:src/main/resources/image/check.png"));
//				tongTien = tong - tienGiam;
//			} else {
//				imgCheckKM.setImage(new Image("file:src/main/resources/image/check_false.png"));
//				txtTienDaGiam.setText(0 + " VND");
//			}
			
		}
	public void handleEventInBtn() {
		btnThanhToan.setOnAction(evt -> {
			try {
				if (txtTienThua.getText().equals("0")) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tiền nhận!", ButtonType.OK);
					alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
					alert.setTitle("Lỗi");
					alert.setHeaderText("Tiền nhận không phù hợp");
					alert.showAndWait();
					return;
				}
				KhuyenMai km = new KhuyenMai_DAO().layKhuyenMaiTheoMa(txtMaKhuyenMai.getText().toUpperCase());
				PhieuThuePhong_DAO dsPhieu = new PhieuThuePhong_DAO();
				ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
				dsPT = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD(maHD);
				for(PhieuThuePhong pt: dsPT) {
					pt.setHieuLuc(false);
					dsPhieu.suaPhieuThue(pt);
					Phong_DAO dsP = new Phong_DAO();
					Phong p = dsP.getPhongTheoMa(pt.getPhong().getIdPhong());
					p.setTrangThai(TrangThaiPhong.TRONG);
					dsP.capNhatTrangThaiPhong(p);
				}
//				luuHoaDon();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
				alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
				alert.setTitle("Thanh toán phòng thành công");
				alert.setHeaderText("Bạn đã thanh toán phòng thành công!");
				alert.showAndWait();

//				Xuat hoa don
				if (checkBoxInHD.isSelected()) {
					moGDBill();
				}

				App.setRoot("GD_QLKinhDoanhPhong");
			} catch (IOException | IllegalArgumentException ex) {
				Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
//		btnBack.setOnAction(evt -> {
//			try {
//				App.setRoot("GD_QLKinhDoanhPhong");
//			} catch (IOException ex) {
//				
//			}
//		});
	}
	@FXML
	private void moGDBill() throws IOException {
		App.openModal("Bill", 1280, 740);
	}
}
