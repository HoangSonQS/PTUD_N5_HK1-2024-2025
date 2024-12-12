
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
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


import entity.ChiTietHD_DichVu;
import dao.ChiTietHoaDon_DichVu_DAO;
import dao.DichVu_DAO;
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
		loadThongTin();
		loadTableData();
		handleEventInInput();
		handleEventInBtn();
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
		
		
		tenDichVuCol.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(cellData.getValue().getDichVu().getTenSanPham()));
		soLuongCol.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getSoLuong())));
		thanhTienDVCol.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDichVu().getDonGia())));
		
		try {
	        PhieuThuePhong_DAO dao = new PhieuThuePhong_DAO();
	        ArrayList<PhieuThuePhong> dsPT = dao.layPhieuThueTheoMaHD(maHD);
	        ObservableList<PhieuThuePhong> observableList = FXCollections.observableArrayList(dsPT);

	        // Gán danh sách vào TableView
	        tablePhong.setItems(observableList);;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		try {
			ChiTietHoaDon_DichVu_DAO ds = new ChiTietHoaDon_DichVu_DAO();
			List<ChiTietHD_DichVu> dsChitiet = ds.layChiTietHoaDonTheoMaHoaDon(maHD);
	        ObservableList<ChiTietHD_DichVu> observableList = FXCollections.observableList(dsChitiet);

	        // Gán danh sách vào TableView
	        tableDichVu.setItems(observableList);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	    
	}
	private void luuHoaDon() {
		PhieuThuePhong pThuePhong = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD_1PT(maHD);
		KhuyenMai km = new KhuyenMai_DAO().layKhuyenMaiTheoMa("KM241001");
		LocalDate thoiHanNhanPhong = pThuePhong.getThoiGianNhanPhong(); // Lấy giá trị LocalDate
		LocalDateTime thoiGianNhanPhongLocalDateTime = thoiHanNhanPhong.atTime(12, 00);
		LocalDate thoiHan = pThuePhong.getThoiHanGiaoPhong(); // Lấy giá trị LocalDate
		LocalDateTime thoiHanLocalDateTime = thoiHan.atTime(12, 00);
		HoaDon hd = new HoaDon(maHD, tk.getNhanVien(),pThuePhong.getKhachHang(),km,thoiHanLocalDateTime,thoiGianNhanPhongLocalDateTime);
		HoaDon_DAO dsHDao = new HoaDon_DAO();
		dsHDao.themHoaDon(hd);
	}
	private void loadThongTin() {
		txtMaHoaDon.setText(maHD);
		txtNhanVien.setText(tk.getNhanVien().getTenNhanVien());
		PhieuThuePhong pThuePhong = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD_1PT(maHD);
		txtKhachHang.setText(pThuePhong.getKhachHang().getTenKhachHang());
		txtNgayLap.setText(LocalDate.now().toString());
		luuHoaDon();
		HoaDon_DAO dsHD = new HoaDon_DAO();
		HoaDon hd = dsHD.layHoaDonTheoMaHoaDon(maHD) ;
		txtTienPhong.setText(String.valueOf(hd.thanhTien()));
		System.out.println(hd.thanhTien());
		txtTienThue.setText(String.valueOf(hd.tinhThue()));
		
		HoaDon hd1 = new HoaDon_DAO().layHoaDonTheoMaHoaDon(maHD);
		DichVu dv1 = new DichVu_DAO().layDichVuTheoMa("SP001");
		DichVu dv2 = new DichVu_DAO().layDichVuTheoMa("SP002");
		Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;
		ChiTietHD_DichVu ct1 = new ChiTietHD_DichVu(hd1,dv1, randomNumber);
		ChiTietHD_DichVu ct2 = new ChiTietHD_DichVu(hd1,dv2, randomNumber);
		ChiTietHoaDon_DichVu_DAO dsChiTiet = new ChiTietHoaDon_DichVu_DAO();
		dsChiTiet.themChiTietHoaDon(ct1);
		dsChiTiet.themChiTietHoaDon(ct2);
		
		
		ChiTietHoaDon_DichVu_DAO dsCT = new ChiTietHoaDon_DichVu_DAO();
		List<ChiTietHD_DichVu> danhSachChiTiet = dsCT.layChiTietHoaDonTheoMaHoaDon(maHD);
		double tong = 0;
		DichVu_DAO dsdv = new DichVu_DAO();
		for (ChiTietHD_DichVu ct : danhSachChiTiet) {
			DichVu dv = dsdv.layDichVuTheoMa(ct.getDichVu().getIdDichVu());
			double tien = dv.getDonGia(); 
		    int soluong = ct.getSoLuong();
		    tong+=(tien*soluong);
		}
		
		txtTienDichVu.setText(String.valueOf(tong));
		tongtien = hd.tongTien()+tong;
		txtTongTien.setText(df.format(tongtien) + " VND");
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
				tienNhan = Double.parseDouble(txtTienNhan.getText().trim());
			} catch (NumberFormatException nf) {
				try {
					tienNhan = df.parse(txtTienNhan.getText().trim()).doubleValue();
				} catch (ParseException ex) {
					Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			String tong = txtTongTien.getText();
			String tong1 = tong.substring(0, tong.length() - 4);
			tong1 = tong1.replace(",", "");
			double tien = Double.valueOf(tong1);
			
			String Giamgia = txtTienDaGiam.getText();
			Giamgia = Giamgia.substring(0, Giamgia.length() - 4);
			Giamgia = Giamgia.replace(",", "");
			double tiengiam = Double.valueOf(Giamgia);
			double tienThua = tienNhan - tien - tiengiam;
			if (tienThua > 0) {
				btnThanhToan.setDisable(false);
				txtTienThua.setText(df.format(tienThua) + " VND");
			} else {
				btnThanhToan.setDisable(true);
				txtTienThua.setText("0 VND");
			}
		});
		txtMaKhuyenMai.setOnKeyReleased((evt) -> {
			String maKM = txtMaKhuyenMai.getText();
			String tongtien = txtTongTien.getText();
			tongtien = tongtien.substring(0, tongtien.length() - 4);
			tongtien = tongtien.replace(",", "");
			double a = Double.valueOf(tongtien);
			KhuyenMai_DAO dsk = new KhuyenMai_DAO();
			ArrayList<KhuyenMai> dsKM = dsk.getAllKhuyenMai();
			boolean co = true;
			for (KhuyenMai km: dsKM) {
				if(!(km.getIdKhuyenMai().equals(maKM))) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại mã khuyến mãi!", ButtonType.OK);
					co = false;
				}
			}
			if(co == true) {
				double b = a*0.01;
				txtTienDaGiam.setText(String.valueOf(a - b));
			}
			
		});
	}
	public void handleEventInBtn() {
		btnThanhToan.setOnAction(evt -> {
			
			if (txtTienThua.getText().equals("0")) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tiền nhận!", ButtonType.OK);
				alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
				alert.setTitle("Lỗi");
				alert.setHeaderText("Tiền nhận không phù hợp");
				alert.showAndWait();
				return;
			}
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
//			luuHoaDon();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Thanh toán phòng thành công");
			alert.setHeaderText("Bạn đã thanh toán phòng thành công!");
			alert.showAndWait();
			
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
