package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import dao.KhachHang_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import dao.TaiKhoan_DAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Button btn_KiemTra;

    @FXML
    private Button btn_TimKiem;

    @FXML
    private ComboBox<String> cbb_LoaiPhong;

    @FXML
    private ComboBox<String> cbb_TrangThai;

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
    private Button btn_Lọc;
    
    @FXML
    private Label lb_ThongKe;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label maNV;

    @FXML
    private Pane p_DSPT;

    @FXML
    private Label tenNV;

    @FXML
    private TextField txt_HoTen;
    
    @FXML
    private TextField txt_PhongDuocChon;
    
    @FXML
    private DatePicker txt_NgayNhan;

    @FXML
    private DatePicker txt_NgayTra;

    @FXML
    private TextField txt_PhongThue;

    @FXML
    private TextField txt_SDT;

    
    public ArrayList<PhieuThuePhong> list;
    public PhieuThuePhong[] pthople;
    public String maphong;
    LocalDate thoiGianNhan;
    LocalDate thoiGianTra;
    private PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
	private ArrayList<String> dsMaPhong = new ArrayList<String>();
	private ArrayList<String> dsMPhongDuocChon = new ArrayList<String>();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list_LoaiPhong = FXCollections.observableArrayList("Tất cả", "Phòng đơn", "Phòng đôi","Phòng gia đình");
		cbb_LoaiPhong.setItems(list_LoaiPhong);
		cbb_LoaiPhong.setValue("Tất cả");
		ObservableList<String> list_TrangThai = FXCollections.observableArrayList("Tất cả", "Đang ở","Sắp Check-out");
		cbb_TrangThai.setItems(list_TrangThai);
		cbb_TrangThai.setValue("Tất cả");
		addUserLogin();
	
		ArrayList<Phong> dsPChon = new Phong_DAO().getPhongTheoTrangThaiDanhSach(1);
		dsPChon.addAll(new Phong_DAO().getPhongTheoTrangThaiDanhSach(4));
		renderArrayPhong(dsPChon);
		loadLoaiPhong();
		suKienNutTK();
		suKienNutKiemTra();
		suKienNutLoc();
	}
	
	public void suKienNutTK() {
		btn_TimKiem.setOnAction((event ->{
			String sdt = txt_SDT.getText();
			if(txt_SDT.getText() == "") {
				new Alert(Alert.AlertType.ERROR, "Không được để trống ô tìm kiếm!").showAndWait();
			}else {
				KhachHang_DAO dsKH = new KhachHang_DAO();
				KhachHang kh = dsKH.getKhachHangTheoSDT(sdt);
				if(kh == null) {
					new Alert(Alert.AlertType.ERROR, "Khách hàng không tồn tại!").showAndWait();
				}else {
					txt_HoTen.setText(kh.getTenKhachHang());
					ArrayList<PhieuThuePhong> dsPT = new PhieuThuePhong_DAO().layPhieuThueTheoHieuLuc(true);
					ArrayList<Phong> dsP = new ArrayList<Phong>();
					for (PhieuThuePhong pt : dsPT) {
						if(pt.getKhachHang().getSoDienThoai().equalsIgnoreCase(sdt)) {
							String maPhong = pt.getPhong().getIdPhong();
							dsMaPhong.add(maPhong);
							Phong p = new Phong_DAO().getPhongTheoMa(pt.getPhong().getIdPhong());
							dsP.add(p);
						}
					}
					for(String maPhongDuocChon : dsMaPhong) {
						txt_PhongThue.appendText(maPhongDuocChon +" ");
					}
					renderArrayPhong(dsP);
					dsMaPhong.removeAll(dsMaPhong);
				}
			}
			
		}));
	}
	public void suKienNutKiemTra() {
		btn_KiemTra.setOnAction((event -> {
		    LocalDate NgayNhan = txt_NgayNhan.getValue();
		    LocalDate NgayTra = txt_NgayTra.getValue();
		    
		    if (NgayNhan == null || NgayTra == null) {
		        new Alert(Alert.AlertType.ERROR, "Vui lòng chọn ngày nhận và ngày trả!").showAndWait();
		        return;
		    }

		    ArrayList<String> maPhongDaCoNguoiDat = new ArrayList<>();
		    for (String ma : dsMPhongDuocChon) {
		        ArrayList<PhieuThuePhong> dsPhieuThue = new PhieuThuePhong_DAO().layPhieuThueTheoMaPhong(ma);
		        for (PhieuThuePhong pt : dsPhieuThue) {
		        	if ((NgayNhan.isAfter(pt.getThoiGianNhanPhong())&& NgayNhan.isBefore(pt.getThoiHanGiaoPhong()))||
	                		(NgayTra.isAfter(pt.getThoiGianNhanPhong())&& NgayTra.isBefore(pt.getThoiHanGiaoPhong()))) {
	                	maPhongDaCoNguoiDat.add(ma);
	                    break;
	                }
		        }
		        dsPhieuThue.removeAll(dsPhieuThue);
		    }

		    // Hiển thị kết quả kiểm tra
		    if (maPhongDaCoNguoiDat.isEmpty()) {
		        new Alert(Alert.AlertType.INFORMATION, "Các phòng có thể gia hạn được!").showAndWait();
		    } else {
		        new Alert(Alert.AlertType.ERROR, "Phòng " + maPhongDaCoNguoiDat + " đã có người đặt! Vui lòng chọn phòng khác!").showAndWait();
		    }

		    // Cập nhật danh sách: Loại bỏ phòng đã có người đặt
		    dsMPhongDuocChon.removeAll(maPhongDaCoNguoiDat);
		    for(String maPhong: dsMPhongDuocChon) {
		    	txt_PhongDuocChon.appendText(maPhong + " ");
		    }
		    
		}));


	}
	
	public void suKienNutLoc() {
		btn_Lọc.setOnAction((event ->{
			 LocalDate NgayNhan = txt_NgayNhan.getValue();
			 LocalDate NgayTra = txt_NgayTra.getValue();
			 if (NgayNhan == null || NgayTra == null) {
			        new Alert(Alert.AlertType.ERROR, "Vui lòng chọn ngày nhận và ngày trả!").showAndWait();
			        return;
			 }
			 ArrayList<Phong> dsP = new ArrayList<Phong>();
			 dsP= new Phong_DAO().getAllPhong();
			 ArrayList<String> dsMPhongTrong = new ArrayList<String>();
			 for(Phong p : dsP) {
				 dsMPhongTrong.add(p.getIdPhong());
			 }
			 ArrayList<String> maPhongDaCoNguoiDat = new ArrayList<>();
			 for(String ma: dsMPhongTrong) {
				 ArrayList<PhieuThuePhong> dsPhieuThue = new PhieuThuePhong_DAO().layPhieuThueTheoMaPhong(ma);
			        for (PhieuThuePhong pt : dsPhieuThue) {
			        	if (NgayNhan.equals(pt.getThoiGianNhanPhong())&& NgayTra.equals(pt.getThoiHanGiaoPhong())) {
		                	maPhongDaCoNguoiDat.add(ma);
		                    break;
		                }
			        }
			 }
			 dsMPhongTrong.removeAll(maPhongDaCoNguoiDat);
			 ArrayList<Phong> dsPhongHoanChinh = new ArrayList<Phong>();
			 for (String ma : dsMPhongTrong) {
				Phong p = new Phong_DAO().getPhongTheoMa(ma);
				dsPhongHoanChinh.add(p);
			 }
			 renderArrayPhong2(dsPhongHoanChinh);
		}));
	}
	
	public Pane taoGiaoDienPhong(Phong phong) {
	    VBox roomItem = new VBox();
	    roomItem.setCursor(Cursor.HAND);
	    roomItem.setPrefHeight(250);
	    roomItem.setPrefWidth(250);
	    
	    switch (phong.getTrangThai()) {
		case DANGTHUE:
			roomItem.setStyle("-fx-background-color: #2972d3; -fx-border-color: #000000; -fx-border-width: 1");
			break;
		case SAPCHECKOUT:
			roomItem.setStyle("-fx-background-color: #ff3131; -fx-border-color: #000000; -fx-border-width: 1");
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

	    ArrayList<PhieuThuePhong> dsPT_TheoHieuLuc = new PhieuThuePhong_DAO().layPhieuThueTheoHieuLuc(true);
	    for (PhieuThuePhong pt : dsPT_TheoHieuLuc) {
	    	if(phong.getIdPhong().equals(pt.getPhong().getIdPhong())) {
		    	Label lblNgayNhan = new Label("Ngày nhận: " +pt.getThoiGianNhanPhong().toString());
			    lblNgayNhan.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
			    lblNgayNhan.setPadding(new Insets(0, 0, 8, 0));
			    roomItem.getChildren().add(lblNgayNhan);
			    
			    Label lblNgayTra = new Label("Ngày trả: " +pt.getThoiHanGiaoPhong().toString());
			    lblNgayTra.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
			    lblNgayTra.setPadding(new Insets(0, 0, 8, 0));
			    roomItem.getChildren().add(lblNgayTra);
			    break;
	    	}
	    }

	    
	    String strBtnLeft = phong.getTrangThai() == TrangThaiPhong.SAPCHECKOUT ? "Chọn Phòng" :"Chọn Phòng";
	    String strBtnRight = phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Gia hạn phòng" : "Gia hạn phòng";
	    Button btnLeft = new Button(strBtnLeft);
	    Button btnRight = new Button(strBtnRight);
	    btnLeft.setStyle("-fx-background-color: #31c57e; -fx-font-size: 15");
	    btnRight.setStyle("-fx-background-color: #31c57e; -fx-font-size: 15");
	    
		btnLeft.setOnAction(((event) -> {
			dsMPhongDuocChon.add(phong.getIdPhong());
			txt_PhongDuocChon.appendText(phong.getIdPhong()+" ");
		}));
		btnRight.setOnAction((event) ->{
			String sdt = txt_SDT.getText();
			KhachHang_DAO dsKH = new KhachHang_DAO();
			KhachHang kh = dsKH.getKhachHangTheoSDT(sdt);
			LocalDate NgayNhan = txt_NgayNhan.getValue();
		    LocalDate NgayTra = txt_NgayTra.getValue();
			
		    for (String MaP : dsMPhongDuocChon) {
		    	Phong_DAO dsP = new Phong_DAO();
		    	Phong p = dsP.getPhongTheoMa(MaP);
		    	NhanVien nv = new NhanVien("NV24100301");
		    	String idPT = PhieuThuePhong.autoIdPhieuThue();
		    	PhieuThuePhong pt = new PhieuThuePhong(idPT, kh, p, nv, NgayNhan, NgayTra, true);
		    	Boolean them = dsPT.themPhieuThue(pt);
		    	if (them) {
                    System.out.println("Thêm phiếu thuê thành công.");
                    dsP.capNhatTrangThaiPhong(p); // Cập nhật trạng thái phòng vào hệ thống
                } else {
                    System.out.println("Lỗi khi thêm phiếu thuê cho phòng " + MaP);
                }
		    }
		});
	    HBox hbox = new HBox(btnLeft,btnRight);
	    hbox.setPadding(new Insets(0, 0, 8, 0));
	    hbox.setSpacing(10);
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
	        grid.setHgap(10); // Khoảng cách giữa các cột
	        grid.setVgap(10); // Khoảng cách giữa các hàng

	        for (int i = 0; i < dsPhong.size(); i++) {
	            Phong phong = dsPhong.get(i);
	            Pane phongPane = taoGiaoDienPhong(phong);
	            grid.add(phongPane, i % 5, i / 5);
	        }
	    } else {
	        System.out.println("scrollPane_GDDOi không phải là GridPane.");
	    }
	}
	
	public void renderArrayPhong2(ArrayList<Phong> dsPhong) {
	    // Kiểm tra xem scrollPane_GDDOi có phải là GridPane không
	    if (GridPane_GiaHan instanceof GridPane) {
	        GridPane grid = (GridPane) GridPane_GiaHan;
	        grid.getChildren().clear(); // Xóa hết các phần tử trước đó
	        grid.setHgap(10); // Khoảng cách giữa các cột
	        grid.setVgap(10); // Khoảng cách giữa các hàng

	        for (int i = 0; i < dsPhong.size(); i++) {
	            Phong phong = dsPhong.get(i);
	            Pane phongPane = taoGiaoDienPhong2(phong);
	            grid.add(phongPane, i % 5, i / 5);
	        }
	    } else {
	        System.out.println("scrollPane_GDDOi không phải là GridPane.");
	    }
	}
	
	public Pane taoGiaoDienPhong2(Phong phong) {
	    VBox roomItem = new VBox();
	    roomItem.setCursor(Cursor.HAND);
	    roomItem.setPrefHeight(250);
	    roomItem.setPrefWidth(250);
	    
	    switch (phong.getTrangThai()) {
	    case TRONG:
	    	roomItem.setStyle("-fx-background-color: #2972d3; -fx-border-color: #000000; -fx-border-width: 1");
	    	break;
		case DANGTHUE:
			roomItem.setStyle("-fx-background-color: #2972d3; -fx-border-color: #000000; -fx-border-width: 1");
			break;
		case SAPCHECKOUT:
			roomItem.setStyle("-fx-background-color: #2972d3; -fx-border-color: #000000; -fx-border-width: 1");
			break;
		case SAPCHECKIN:
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
	    
	    String strBtnLeft = phong.getTrangThai() == TrangThaiPhong.SAPCHECKOUT ? "Chọn phòng" :
	    	phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Chọn phòng":phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Chọn phòng":"Chọn phòng";
	    String strBtnRight = phong.getTrangThai() == TrangThaiPhong.SAPCHECKOUT ? "Đặt Phòng" :
	    	phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Đặt Phòng":phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Đặt phòng":"Đặt phòng";
	    Button btnLeft = new Button(strBtnLeft);
	    Button btnRight = new Button(strBtnRight);
	    btnLeft.setStyle("-fx-background-color: #31c57e; -fx-font-size: 15");
	    btnRight.setStyle("-fx-background-color: #31c57e; -fx-font-size: 15");
	    
	    btnLeft.setOnAction((event->{
	    	try {
				String maChon = phong.getIdPhong();
				if (!GD_DatPhongChoController.dsMaPhong.contains(maChon)) {
				    GD_DatPhongChoController.dsMaPhong.add(maChon);
				}
				txt_PhongDuocChon.appendText(maChon + " ");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    	
	    }));
	    btnRight.setOnAction((event ->{
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }));
	    HBox hbox = new HBox(btnLeft,btnRight);
	    hbox.setPadding(new Insets(0, 0, 8, 0));
	    hbox.setSpacing(10);
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
	
	public void loadLoaiPhong() {
		cbb_LoaiPhong.setOnAction(event -> {
			GridPane_GiaHan.getChildren().clear(); // Xóa các thành phần trong GridPane trước khi thêm mới
		    ArrayList<Phong> dsP = kiemTraLoaiPhong();
		    cbb_TrangThai.setValue("Tất cả");// Khởi tạo danh sách phòng
		    renderArrayPhong(dsP);
		});
	}
	
	public ArrayList<Phong> kiemTraLoaiPhong() {
		String selectedItem = (String) cbb_LoaiPhong.getSelectionModel().getSelectedItem();
		
	    ArrayList<Phong> dsP = new ArrayList<Phong>(); // Khởi tạo danh sách phòng
	    ArrayList<Phong> dsP_gop = new ArrayList<>();
	    if (selectedItem.equals("Phòng đơn")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(2);
	        ArrayList<Phong> dsP_chonTheoTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(1);
	        dsP_chonTheoTT.addAll(new Phong_DAO().getPhongTheoTrangThaiDanhSach(4)) ;
	        for(Phong p: dsP_chonTheoTT) {
	        	if(dsP.contains(p)) {
	        		dsP_gop.add(p);
	        	}
	        }
	    } else if (selectedItem.equals("Phòng đôi")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(1);
	        ArrayList<Phong> dsP_chonTheoTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(1);
	        dsP_chonTheoTT.addAll(new Phong_DAO().getPhongTheoTrangThaiDanhSach(4)) ;
	        for(Phong p: dsP_chonTheoTT) {
	        	if(dsP.contains(p)) {
	        		dsP_gop.add(p);
	        	}
	        }
	    } else if (selectedItem.equals("Phòng gia đình")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(3);
	        ArrayList<Phong> dsP_chonTheoTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(1);
	        dsP_chonTheoTT.addAll(new Phong_DAO().getPhongTheoTrangThaiDanhSach(4)) ;
	        for(Phong p: dsP_chonTheoTT) {
	        	if(dsP.contains(p)) {
	        		dsP_gop.add(p);
	        	}
	        }
	    } else if (selectedItem.equals("Tất cả")) {
	        dsP_gop = new Phong_DAO().getPhongTheoTrangThaiDanhSach(1);// Lấy tất cả các phòng đang ở và sắp check out
	        dsP_gop.addAll(new Phong_DAO().getPhongTheoTrangThaiDanhSach(4));
	    }
	    return dsP_gop;
	}
	public ArrayList<Phong> LocPhong2TieuChi(int maso ){
		GridPane_GiaHan.getChildren().clear();
		ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
		ArrayList<Phong> dsKiemTraTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(maso);
		ArrayList<Phong> dsHoanCHinh = new ArrayList<Phong>();
		for (Phong phongTT : dsPKiemTra) {
		    // Kiểm tra nếu dsPKiemTra chứa phần tử này
		    if (dsKiemTraTT.contains(phongTT)) {
		        // Thêm phần tử vào dsHoanCHinh
		        dsHoanCHinh.add(phongTT);
		    }
		}
		return dsHoanCHinh;
	}
	public void loadTrangThaiPhong_CBB() {
		String selectedItemTT = (String) cbb_TrangThai.getSelectionModel().getSelectedItem();	    
	    if (selectedItemTT.equals("Phòng trống")) {
//	    	renderArrayPhong(LocPhong2TieuChi(2));
	    } else if (selectedItemTT.equals("Đang ở")) {
	    	
	    	renderArrayPhong(LocPhong2TieuChi(1));
	    } else if (selectedItemTT.equals("Sắp Check-out")) {
	    	
	    	renderArrayPhong(LocPhong2TieuChi(4));
	    }else if(selectedItemTT.equals("Tất cả")) {
	    	GridPane_GiaHan.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			renderArrayPhong(dsPKiemTra);
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
	
	@FXML
	private void moGDTKe() throws IOException {
//		App.openModal("GD_ThongKe", 800, 684);
	}
	private void addUserLogin() {
		TaiKhoan tk = App.tk;
		maNV.setText(String.valueOf(tk.getNhanVien().getIdNhanVien()));
		tenNV.setText(String.valueOf(tk.getNhanVien().getTenNhanVien()));
	}
}
