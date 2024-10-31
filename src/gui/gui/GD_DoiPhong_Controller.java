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
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;


public class GD_DoiPhong_Controller implements Initializable{
	@FXML
    private ImageView avt;

    @FXML
    private Button btnDoi;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label kb_UuDai;

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
    private Label lb_NgayNhan;

    @FXML
    private Label lb_Ngaytra;

    @FXML
    private Label lb_NhanVien;

    @FXML
    private Label lb_Phong;

    @FXML
    private Label lb_SDT;

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
    private GridPane scrollPane_GDDOi;
    @FXML
    private Button btnTim;
    @FXML
    private TextField txt_MaPhong;
    public String maPhongDoi;
    public ArrayList<PhieuThuePhong> list;
    public PhieuThuePhong[] pthople;
    public String maphong;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_maPhong.setText("");
		lb_tenKH.setText("");
		lb_SDT.setText("");
		lb_NgayNhan.setText("");
		lb_Ngaytra.setText("");
		Phong_DAO dsP = new Phong_DAO();
		renderArrayPhong(dsP.getPhongTheoTrangThaiDanhSach(1));
		btnTim.setOnAction(even ->{
			maphong = txt_MaPhong.getText();
			
			PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
			pthople = new PhieuThuePhong[1];
			LocalDate GioHienTai = LocalDate.now();
			list = dsPT.layPhieuThueTheoMaPhong(maphong);
			list.forEach(phieuThue -> {
			    LocalDate thoiGianNhan = phieuThue.getThoiGianNhanPhong(); 
			    LocalDate thoiGianTra = phieuThue.getThoiHanGiaoPhong(); 
			    if (GioHienTai.isBefore(thoiGianTra)&& GioHienTai.isAfter(thoiGianNhan)) {
			    	pthople[0] = phieuThue;
			    } 
			});
			if (pthople[0] != null) {
		        lb_maPhong.setText(pthople[0].getPhong().getIdPhong());
		        String tenKH = pthople[0].getKhachHang().getTenKhachHang();
		        lb_tenKH.setText(tenKH);
		        String sdt = pthople[0].getKhachHang().getSoDienThoai();
		        lb_SDT.setText(sdt);
		        lb_NgayNhan.setText(pthople[0].getThoiGianNhanPhong().toString());
		        lb_Ngaytra.setText(pthople[0].getThoiHanGiaoPhong().toString());
		    } else {
		        System.out.println("Không có phiếu thuê hợp lệ nào.");
		    }
		});
		btnDoi.setOnAction(event -> {
		    if (pthople[0] != null && maPhongDoi != null) {
		        PhieuThuePhong pthuemoi = pthople[0];
		        Phong pmoi = dsP.getPhongTheoMa(maPhongDoi);

		        if (pmoi != null && pmoi.getTrangThai() == TrangThaiPhong.TRONG) {
		            // Cập nhật phiếu thuê với phòng mới
		            pthuemoi.setPhong(pmoi);
		            PhieuThuePhong_DAO dsPTMoi = new PhieuThuePhong_DAO();
		            dsPTMoi.suaPhieuThue(pthuemoi);

		            // Cập nhật trạng thái phòng mới
		            pmoi.setTrangThai(TrangThaiPhong.DANGTHUE);
		            dsP.capNhatTrangThaiPhong(pmoi);

		            // Cập nhật trạng thái phòng cũ
		            Phong pCu = dsP.getPhongTheoMa(lb_maPhong.getText());
		            pCu.setTrangThai(TrangThaiPhong.TRONG);
		            dsP.capNhatTrangThaiPhong(pCu);

		            new Alert(Alert.AlertType.CONFIRMATION, "Đổi phòng thành công!").showAndWait();
		            renderArrayPhong(dsP.getPhongTheoTrangThaiDanhSach(1));
		        } else {
		            new Alert(Alert.AlertType.WARNING, "Phòng được chọn không khả dụng để đổi.").showAndWait();
		        }
		    } else {
		        new Alert(Alert.AlertType.WARNING, "Không có phiếu thuê hợp lệ hoặc phòng đổi không được chọn.").showAndWait();
		    }
		    txt_MaPhong.setText("");
		    lb_maPhong.setText("");
			lb_tenKH.setText("");
			lb_SDT.setText("");
			lb_NgayNhan.setText("");
			lb_Ngaytra.setText("");
		});
	}
	public Pane taoGiaoDienPhong(Phong phong) {
	    VBox roomItem = new VBox();
	    roomItem.setCursor(Cursor.HAND);
	    roomItem.setPrefHeight(200);
	    roomItem.setPrefWidth(200);
	    roomItem.setStyle("-fx-background-color: #31c57e; -fx-border-color: #000000; -fx-border-width: 1"); // Viền đen để dễ nhận diện

	    Label lblMaPhong = new Label(phong.getIdPhong());
	    lblMaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 700");
	    lblMaPhong.setPadding(new Insets(0, 0, 8, 0));
	    roomItem.getChildren().add(lblMaPhong);

	    Label lblLoaiPhong = new Label(phong.getLoaiPhong().toString());
	    lblLoaiPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
	    lblLoaiPhong.setPadding(new Insets(0, 0, 8, 0));
	    roomItem.getChildren().add(lblLoaiPhong);

	    String strBtnLeft = phong.getTrangThai() == TrangThaiPhong.TRONG ? "Chọn Phòng" : "Chọn phòng";

	    Button btnLeft = new Button(strBtnLeft);
	    btnLeft.setStyle("-fx-background-color: #2972d3; -fx-font-size: 16");
	    btnLeft.setOnAction((event) -> {
	    	maPhongDoi = phong.getIdPhong();
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
	    if (scrollPane_GDDOi instanceof GridPane) {
	        GridPane grid = (GridPane) scrollPane_GDDOi;
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
