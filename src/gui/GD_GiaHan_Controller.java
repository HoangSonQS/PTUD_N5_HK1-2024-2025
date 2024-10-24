package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TrangThaiPhong;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;

public class GD_GiaHan_Controller implements Initializable{
	@FXML
    private GridPane GridPane_GiaHan;

    @FXML
    private ImageView avt;

    @FXML
    private Button btnGiaHan;

    @FXML
    private Label kb_UuDai;

    @FXML
    private Label lbCCCD;

    @FXML
    private Label lbNgayNhan;

    @FXML
    private DatePicker dpTra;

    @FXML
    private Label lbSDT;

    @FXML
    private Label lb_DichVu;

    @FXML
    private Label lb_DoiPhong;

    @FXML
    private Label lb_GiaHanPhong;

    @FXML
    private Label lb_HoaDon;

    @FXML
    private Label lb_HuyPhong;

    @FXML
    private Label lb_KhachHang;

    @FXML
    private Label lb_NhanVien;

    @FXML
    private Label lb_Phong;

    @FXML
    private Label lb_SoDoPhong;

    @FXML
    private Label lb_TKDoanhThu;

    @FXML
    private Label lb_TKKhachHang;

    @FXML
    private Label lb_TKSanPham;

    @FXML
    private Label lb_TaiKhoan;

    @FXML
    private Label lb_maPhong;

    @FXML
    private Label lb_tenKH;

    @FXML
    private Pane p_DSPT;
    
    public ArrayList<PhieuThuePhong> list;
    public PhieuThuePhong[] pthople;
    public String maphong;
    LocalDate thoiGianNhan;
    LocalDate thoiGianTra;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_maPhong.setText("");
		lb_tenKH.setText("");
		lbSDT.setText("");
		lbCCCD.setText("");
		lbNgayNhan.setText("");
		dpTra.setValue(null);
		Phong_DAO dsP = new Phong_DAO();
		ArrayList<Phong> dsFull = dsP.getPhongTheoTrangThaiDanhSach(2);
		dsFull.addAll(dsP.getPhongTheoTrangThaiDanhSach(4));
		renderArrayPhong(dsFull);
		btnGiaHan.setOnAction(event ->{
			PhieuThuePhong PthueMoi = pthople[0];
			PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
			LocalDate thoiGianNhanMoi = dpTra.getValue();
			PthueMoi.setThoiHanGiaoPhong(thoiGianNhanMoi);
			dsPT.suaPhieuThue(PthueMoi);
			new Alert(Alert.AlertType.CONFIRMATION, "Gia hạn thành công!").showAndWait();
			renderArrayPhong(dsFull);
			lb_maPhong.setText("");
			lb_tenKH.setText("");
			lbSDT.setText("");
			lbCCCD.setText("");
			lbNgayNhan.setText("");
			dpTra.setValue(null);
		});
	}
	
	public Pane taoGiaoDienPhong(Phong phong) {
	    VBox roomItem = new VBox();
	    roomItem.setCursor(Cursor.HAND);
	    roomItem.setPrefHeight(250);
	    roomItem.setPrefWidth(250);
	    switch (phong.getTrangThai()) {
		case DANGTHUE:
			roomItem.setStyle("-fx-background-color: #ff3131; -fx-border-color: #000000; -fx-border-width: 1");
			break;
		case SAPCHECKOUT:
			roomItem.setStyle("-fx-background-color: #2972d3; -fx-border-color: #000000; -fx-border-width: 1");
			break;
		default:
			break;
		}
	    Label lblMaPhong = new Label(phong.getIdPhong());
	    lblMaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 700");
	    lblMaPhong.setPadding(new Insets(0, 0, 8, 0));
	    roomItem.getChildren().add(lblMaPhong);

	    Label lblLoaiPhong = new Label(phong.getLoaiPhong().toString());
	    lblLoaiPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
	    lblLoaiPhong.setPadding(new Insets(0, 0, 8, 0));
	    roomItem.getChildren().add(lblLoaiPhong);

	    String strBtnLeft = phong.getTrangThai() == TrangThaiPhong.SAPCHECKOUT ? "Chọn Phòng" :
	    	phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Chọn Phòng":"Chọn Phòng";

	    Button btnLeft = new Button(strBtnLeft);
	    btnLeft.setStyle("-fx-background-color: #ff3131; -fx-font-size: 16");
	    btnLeft.setOnAction((event) -> {
	    	String maPhong = phong.getIdPhong();
	    	
	    	PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
	    	
			pthople = new PhieuThuePhong[1];
			LocalDate GioHienTai = LocalDate.now();
			list = dsPT.layPhieuThueTheoMaPhong(maPhong);
			list.forEach(phieuThue -> {
				LocalDate thoiGianNhan = phieuThue.getThoiGianNhanPhong(); 
			    LocalDate thoiGianTra = phieuThue.getThoiHanGiaoPhong(); 
			    if (GioHienTai.isBefore(thoiGianTra)&& GioHienTai.isAfter(thoiGianNhan)) {
			    	pthople[0] = phieuThue;
			    } 
			});
			if (pthople[0] != null) {
				lb_maPhong.setText(maPhong);
		        String tenKH = pthople[0].getKhachHang().getTenKhachHang();
		        lb_tenKH.setText(tenKH);
		        String sdt = pthople[0].getKhachHang().getSoDienThoai();
		        lbSDT.setText(sdt);
		        String CCCD = pthople[0].getKhachHang().getCccd();
		        lbCCCD.setText(CCCD);
		        lbNgayNhan.setText(pthople[0].getThoiGianNhanPhong().toString());
		    } else {
		        System.out.println("Không có phiếu thuê hợp lệ nào.");
		    }
	    });

	    HBox hbox = new HBox(btnLeft);
	    hbox.setPadding(new Insets(0, 0, 8, 0));
	    hbox.setAlignment(Pos.CENTER);
	    hbox.setVisible(false);
	    roomItem.getChildren().add(hbox);

	    roomItem.setAlignment(Pos.CENTER);

	    roomItem.hoverProperty().addListener((obs, oldVal, newVal) -> {
	        if (newVal) {
	            hbox.setVisible(true);
	        } else {
	            hbox.setVisible(false);
	        }
	    });

	    return roomItem;
	}

	public void renderArrayPhong(ArrayList<Phong> dsPhong) {
	    // Kiểm tra xem scrollPane_GDDOi có phải là GridPane không
	    if (GridPane_GiaHan instanceof GridPane) {
	        GridPane grid = (GridPane) GridPane_GiaHan;
	        grid.getChildren().clear(); // Xóa hết các phần tử trước đó
	        grid.setHgap(20); // Khoảng cách giữa các cột
	        grid.setVgap(20); // Khoảng cách giữa các hàng

	        for (int i = 0; i < dsPhong.size(); i++) {
	            Phong phong = dsPhong.get(i);
	            Pane phongPane = taoGiaoDienPhong(phong);
	            grid.add(phongPane, i % 4, i / 4);
	        }
	    } else {
	        System.out.println("scrollPane_GDDOi không phải là GridPane.");
	    }
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
}
