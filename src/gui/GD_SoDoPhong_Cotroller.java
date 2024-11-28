package gui;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TrangThaiPhong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class GD_SoDoPhong_Cotroller implements Initializable {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	Phong_DAO dsp = new Phong_DAO();

	public static String roomID;

	@FXML
	private ImageView avt;

	@FXML
	private ComboBox<String> cbb;

	@FXML
	private ImageView icon_QuanLy;

	@FXML
	private ImageView icon_ThongKe;

	@FXML
	private ImageView icon_TimKiem;

	@FXML
	private Label l;

	@FXML
	private Label lb_QuanLy;

	@FXML
	private Label lb_ThongKe;

	 @FXML
	private Button btn_DangO;

	    @FXML
	    private Button btn_PhongTrong;

	    @FXML
	    private Button btn_SapCheckIn;

	    @FXML
	    private Button btn_SapCheckOut;

	    @FXML
	    private Button btn_TatCa;
	@FXML
	private GridPane gridPane;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadsoPhong();
		ObservableList<String> list = FXCollections.observableArrayList("Tất cả", "Phòng đơn", "Phòng đôi",
				"Phòng gia đình");
    	renderArrayPhong(new Phong_DAO().getAllPhong());
		cbb.setItems(list);
		cbb.setValue("Tất cả");
		loadLoaiPhong();
		loadTrangThaiPhong();
	}
	
	public void loadLoaiPhong() {
		renderArrayPhong(new Phong_DAO().getAllPhong());
		cbb.setOnAction(event -> {
			gridPane.getChildren().clear(); // Xóa các thành phần trong GridPane trước khi thêm mới
		    ArrayList<Phong> dsP = kiemTraLoaiPhong(); // Khởi tạo danh sách phòng
		    renderArrayPhong(dsP);
		});
	}
	public void loadsoPhong() {
		Phong_DAO dsp1 = new Phong_DAO();
	    btn_PhongTrong.setText("Phòng trống ("+String.valueOf(dsp1.getPhongTheoTrangThai(1))+")");
	    btn_DangO.setText("Đang ở ("+String.valueOf(dsp1.getPhongTheoTrangThai(2))+")");
	    btn_SapCheckIn.setText("Sắp Check-in ("+String.valueOf(dsp1.getPhongTheoTrangThai(4))+")");
	    btn_SapCheckOut.setText("Sắp Check-out ("+String.valueOf(dsp1.getPhongTheoTrangThai(3))+")");
	    btn_TatCa.setText("Tất cả ("+String.valueOf(new Phong_DAO().getAllPhong().size())+")");
	}
	
	public ArrayList<Phong> kiemTraLoaiPhong() {
		String selectedItem = (String) cbb.getSelectionModel().getSelectedItem();

	    ArrayList<Phong> dsP = new ArrayList<Phong>(); // Khởi tạo danh sách phòng

	    if (selectedItem.equals("Phòng đơn")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(2);
	    } else if (selectedItem.equals("Phòng đôi")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(1);
	    } else if (selectedItem.equals("Phòng gia đình")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(3);
	    } else if (selectedItem.equals("Tất cả")) {
	        dsP = new Phong_DAO().getAllPhong(); // Lấy tất cả các phòng
	    }
	    return dsP;
	}
	public void loadTrangThaiPhong() {
		btn_PhongTrong.setOnAction(event ->{
			gridPane.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			ArrayList<Phong> dsKiemTraTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(1);
			ArrayList<Phong> dsHoanCHinh = new ArrayList<Phong>();
			for (Phong phongTT : dsPKiemTra) {
			    // Kiểm tra nếu dsPKiemTra chứa phần tử này
			    if (dsKiemTraTT.contains(phongTT)) {
			        // Thêm phần tử vào dsHoanCHinh
			        dsHoanCHinh.add(phongTT);
			    }
			}
			renderArrayPhong(dsHoanCHinh);
		});
		btn_DangO.setOnAction(event ->{
			gridPane.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			ArrayList<Phong> dsKiemTraTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(2);
			ArrayList<Phong> dsHoanCHinh = new ArrayList<Phong>();
			for (Phong phongTT : dsKiemTraTT) {
			    // Kiểm tra nếu dsPKiemTra chứa phần tử này
			    if (dsPKiemTra.contains(phongTT)) {
			        // Thêm phần tử vào dsHoanCHinh
			        dsHoanCHinh.add(phongTT);
			    }
			}
			renderArrayPhong(dsHoanCHinh);
		});
		btn_SapCheckIn.setOnAction(event ->{
			gridPane.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			ArrayList<Phong> dsKiemTraTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(3);
			ArrayList<Phong> dsHoanCHinh = new ArrayList<Phong>();
			for (Phong phongTT : dsKiemTraTT) {
			    // Kiểm tra nếu dsPKiemTra chứa phần tử này
			    if (dsPKiemTra.contains(phongTT)) {
			        // Thêm phần tử vào dsHoanCHinh
			        dsHoanCHinh.add(phongTT);
			    }
			}
			renderArrayPhong(dsHoanCHinh);
		});
		btn_SapCheckOut.setOnAction(event ->{
			gridPane.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			ArrayList<Phong> dsKiemTraTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(4);
			ArrayList<Phong> dsHoanCHinh = new ArrayList<Phong>();
			for (Phong phongTT : dsKiemTraTT) {
			    // Kiểm tra nếu dsPKiemTra chứa phần tử này
			    if (dsPKiemTra.contains(phongTT)) {
			        // Thêm phần tử vào dsHoanCHinh
			        dsHoanCHinh.add(phongTT);
			    }
			}
			renderArrayPhong(dsHoanCHinh);
		});
		btn_TatCa.setOnAction(event->{
			gridPane.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			renderArrayPhong(dsPKiemTra);
		});
	}
	
	public void renderArrayPhong(ArrayList<Phong> dsPhong) {
		for (int i = 0; i < dsPhong.size(); i++) {
			Phong phong = dsPhong.get(i);
			gridPane.setHgap(10); // Khoảng cách giữa các cột
	        gridPane.setVgap(10); // Khoảng cách giữa các hàng
			gridPane.add(taoGiaoDienPhong(phong), i % 5, i / 5);
		}
	}

	public Pane taoGiaoDienPhong(Phong phong) {
		VBox roomItem = new VBox();
		roomItem.setCursor(Cursor.HAND);
		roomItem.setPrefHeight(250);
		roomItem.setPrefWidth(250);

		switch (phong.getTrangThai()) {
		case TRONG:
			roomItem.setStyle("-fx-background-color: #31c57e");
			break;
		case SAPCHECKIN:
			roomItem.setStyle("-fx-background-color: #edbf6d");
			break;
		case SAPCHECKOUT:
			roomItem.setStyle("-fx-background-color: #ff3131");
			break;
		default:
			roomItem.setStyle("-fx-background-color: #2972d3");
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
		
		String dongia = String.valueOf(phong.getDonGia());
		Label lbGiaPhong = new Label(dongia + " VND");
		lbGiaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 700");
		lbGiaPhong.setPadding(new Insets(0, 0, 8, 0));
		roomItem.getChildren().add(lbGiaPhong);

		if (phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN) {
			try {
				PhieuThuePhong phieu = new PhieuThuePhong_DAO().layPhieuThueTheoMa(phong.getIdPhong());
				if (phieu != null) {
					Label lblGioNhan = new Label("Giờ nhận: " + dtf.format(phieu.getThoiGianNhanPhong()));
					lblGioNhan.setStyle("-fx-font-size: 16; -fx-font-weight: 600");
					lblGioNhan.setPadding(new Insets(0, 0, 8, 0));
					roomItem.getChildren().add(lblGioNhan);
				}
			} catch (Exception ex) {
				Logger.getLogger(GD_SoDoPhong_Cotroller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		String strBtnLeft = phong.getTrangThai() == TrangThaiPhong.TRONG ? "Chọn phòng"
				: phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Hủy phòng"
						: phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Đổi phòng" : "Gia Hạn Phòng";
		
		String strBtnRight = phong.getTrangThai() == TrangThaiPhong.TRONG ? "Đặt phòng"
				: phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Nhận phòng"
						: phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Thanh toán" : "Thanh toán";
		Button btnLeft = new Button(strBtnLeft);
		Button btnRight = new Button(strBtnRight);

		switch (phong.getTrangThai()) {
		case TRONG:
			btnLeft.setStyle("-fx-background-color: #edbf6d; -fx-text-fill: #fff; -fx-font-size: 16");
			btnLeft.setOnAction(((event) -> {
				try {
					String maChon = phong.getIdPhong();
					GD_DatPhongChoController.dsMaPhong.add(maChon);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}));
			btnRight.setOnAction((event) -> {
				try {
					String maChon = phong.getIdPhong();
					if(GD_DatPhongChoController.dsMaPhong.isEmpty()) {
						GD_DatPhongChoController.dsMaPhong.add(maChon);
					}else {
						if (!GD_DatPhongChoController.dsMaPhong.contains(maChon)) {
						    GD_DatPhongChoController.dsMaPhong.add(maChon);
						}
					}
					moGDDatPhong();
					renderArrayPhong(new Phong_DAO().getAllPhong());
					loadsoPhong();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			break;
		case SAPCHECKIN:
			btnLeft.setStyle("-fx-background-color: #edbf6d; -fx-text-fill: #fff; -fx-font-size: 16");
			btnLeft.setOnAction(((event) -> {
				try {
					phong.setTrangThai(TrangThaiPhong.TRONG);
					dsp.capNhatTrangThaiPhong(phong);
					renderArrayPhong(new Phong_DAO().getAllPhong());
					loadsoPhong();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}));
			btnRight.setOnAction((event) -> {
				try {
					phong.setTrangThai(TrangThaiPhong.DANGTHUE);
					dsp.capNhatTrangThaiPhong(phong);
					renderArrayPhong(new Phong_DAO().getAllPhong());
					loadsoPhong();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			});
			break;
		case DANGTHUE:
			btnLeft.setStyle("-fx-background-color: #2972d3; -fx-text-fill: #fff; -fx-font-size: 16");
			btnLeft.setOnAction(((event) -> {
                try {
					moGDDoiPhong();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
			btnRight.setOnAction((event) -> {
				try {
					phong.setTrangThai(TrangThaiPhong.TRONG);
					dsp.capNhatTrangThaiPhong(phong);
					renderArrayPhong(new Phong_DAO().getAllPhong());
					loadsoPhong();
				} catch (Exception e) {
					// TODO: handle exception
				}
			});
			break;
		default:
			btnLeft.setStyle("-fx-background-color: #ff3131; -fx-text-fill: #fff; -fx-font-size: 16");
			btnLeft.setOnAction((event) -> {

			});
			btnRight.setOnAction((event) -> {
//                moGDNhanPhongCho();
			});
//             {
//                try {
//                    if (PhieuDatPhong.getBookingTicketOfRoom(phong.getMaPhong()) == null) {
//                        btnRight.setDisable(true);
//                    }
//                } catch (Exception ex) {
//                    Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
			break;

		}
		btnRight.setStyle("-fx-background-color: #379F10; -fx-text-fill: #fff; -fx-font-size: 16");
		HBox hbox = new HBox(btnLeft, btnRight);
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(0, 0, 8, 0));
		hbox.setAlignment(Pos.CENTER);
		hbox.setVisible(false);
		roomItem.getChildren().add(hbox);

		roomItem.setAlignment(Pos.CENTER);
		((Pane) roomItem).hoverProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				hbox.setVisible(true);
			} else {
				hbox.setVisible(false);
			}
		});

		return roomItem;
		
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
