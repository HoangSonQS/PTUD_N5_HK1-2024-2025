package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.KhachHang_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.KhachHang;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.App;

public class GD_HuyPhong_Controller implements Initializable{
	@FXML
    private ImageView avt;

    @FXML
    private Button btnTim;

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
    private Label lbCCCD;

    @FXML
    private Label lbSDT;

    @FXML
    private Label lb_QuanLy;

    @FXML
    private Label lb_ThongKe;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_tenKH;

    @FXML
    private GridPane scrollPane_GDHuy;

    @FXML
    private TextField txt_CCCD;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private String cccd;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    lb_tenKH.setText("");
	    lbSDT.setText("");
	    lbCCCD.setText("");
	    
	    // Load rooms with status "sắp checkin" (status 3) on initialization
	    Phong_DAO dsP = new Phong_DAO();
	    ArrayList<Phong> availableRooms = dsP.getPhongTheoTrangThaiDanhSach(3);
	    renderArrayPhong(availableRooms);

	    btnTim.setOnAction(even -> {
	        cccd = txt_CCCD.getText();
	        KhachHang_DAO khdao = new KhachHang_DAO();
	        KhachHang kh = khdao.getKhachHangTheoCCCD(cccd);
	        
	        if (cccd.isEmpty()) {
	            renderArrayPhong(availableRooms);
	            return;
	        }

	        if (kh != null) {
	            PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
	            ArrayList<PhieuThuePhong> phieuThueList = dsPT.layPhieuThueTheoMaKH(kh.getIdKhachHang());

//	            System.out.println("Tìm kiếm với CCCD: " + cccd);
//	            System.out.println("Số phiếu thuê tìm thấy: " + phieuThueList.size());

	            // Filter the rooms that can be canceled before check-in
	            ArrayList<Phong> filteredRooms = new ArrayList<>();
	            LocalDate currentDate = LocalDate.now();

	            for (PhieuThuePhong phieuThue : phieuThueList) {
//	                System.out.println("Phiếu thuê: " + phieuThue.getIdPhieuThue());
//	                System.out.println("Ngày hiện tại: " + currentDate);
//	                System.out.println("Ngày nhận phòng: " + phieuThue.getThoiGianNhanPhong());

	                // Điều kiện để hủy phòng:
	                // 1. Ngày hiện tại phải trước ngày check-in
	                // 2. Phòng phải ở trạng thái sắp check-in
	                if (currentDate.isBefore(phieuThue.getThoiGianNhanPhong())) {
	                    Phong phong = phieuThue.getPhong();
	                    
	                    // Kiểm tra trạng thái phòng (tuỳ thuộc vào enum của bạn)
	                    // Ví dụ: if (phong.getTrangThai() == TrangThaiPhong.SAPCHECKINHOACDAT) 
	                    filteredRooms.add(phong);
	                    
//	                    System.out.println("Thêm phòng có thể hủy: " + phong.getIdPhong());
	                }
	            }

//	            System.out.println("Số phòng có thể hủy: " + filteredRooms.size());

	            // Hiển thị kết quả
	            if (!filteredRooms.isEmpty()) {
	                // Hiển thị thông tin khách hàng
	                lb_tenKH.setText(kh.getTenKhachHang());
	                lbSDT.setText(kh.getSoDienThoai());
	                lbCCCD.setText(kh.getCccd());

	                renderArrayPhong(filteredRooms);
	            } else {
	                // Xóa thông tin khách hàng
	                lb_tenKH.setText("");
	                lbSDT.setText("");
	                lbCCCD.setText("");

	                new Alert(Alert.AlertType.INFORMATION, "Không có phòng nào có thể hủy.").showAndWait();
	            }
	        } else {
	            // Xóa thông tin khách hàng
	            lb_tenKH.setText("");
	            lbSDT.setText("");
	            lbCCCD.setText("");

	            new Alert(Alert.AlertType.WARNING, "Không tìm thấy khách hàng với CCCD này.").showAndWait();
	        }
	    });
	}
	
	public void renderArrayPhong(ArrayList<Phong> dsPhong) {
        if (scrollPane_GDHuy instanceof GridPane) {
            GridPane grid = (GridPane) scrollPane_GDHuy;
            grid.getChildren().clear();
            
            // Thiết lập khoảng cách giữa các ô
            grid.setHgap(15);  
            grid.setVgap(15); 
            grid.setPadding(new Insets(15));
            
            // Lấy kích thước của ScrollPane
            double availableWidth = scrollPane_GDHuy.getWidth() - 60;
            double columnWidth = (availableWidth - 40) / 3;
            
            // Thêm ColumnConstraints để đặt khoảng cách giữa các cột
            grid.getColumnConstraints().clear();
            for (int i = 0; i < 3; i++) {
                ColumnConstraints column = new ColumnConstraints();
                column.setHgrow(Priority.SOMETIMES);
                column.setMinWidth(columnWidth);
                column.setPrefWidth(columnWidth);
                grid.getColumnConstraints().add(column);
            }
            
            // Số cột tối đa trong grid
            int maxColumns = 3;
            
         // Render từng phòng
	        for (int i = 0; i < dsPhong.size(); i++) {
	            Phong phong = dsPhong.get(i);
	            Pane phongPane = taoGiaoDienPhong(phong);
	            
	            // Tính toán vị trí của phòng trong grid
	            int column = i % maxColumns;
	            int row = i / maxColumns;
	            
	            // Thêm phòng vào grid tại vị trí tính toán
	            grid.add(phongPane, column, row);
	        }
        }
    }
	
	private VBox selectedRoomItem = null;

    public Pane taoGiaoDienPhong(Phong phong) {
        // Tạo VBox chứa thông tin phòng với kích thước cố định
        VBox roomItem = new VBox(10);
        // Tìm phiếu thuê gần đây nhất của phòng này
        PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
        ArrayList<PhieuThuePhong> dsPhieuThue = phieuThueDAO.layPhieuThueTheoMaPhong(phong.getIdPhong());
        
        roomItem.setPrefWidth(Region.USE_COMPUTED_SIZE);
        roomItem.setPrefHeight(230);
        roomItem.setMinHeight(230);
        roomItem.setMaxHeight(230);
        
        roomItem.setStyle("-fx-background-color: #edbf6d; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
        roomItem.setAlignment(Pos.CENTER);

        // Mã phòng
        Label lblMaPhong = new Label(phong.getIdPhong());
        lblMaPhong.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: white;");
        lblMaPhong.setWrapText(true);
        lblMaPhong.setAlignment(Pos.CENTER);

        // Nếu có phiếu thuê
        if (!dsPhieuThue.isEmpty()) {
            // Lấy phiếu thuê gần đây nhất
            PhieuThuePhong phieuThue = dsPhieuThue.get(0);
            
            // Ngày check-in
            Label lblNgayCheckIn = new Label("Check-in: " + 
                phieuThue.getThoiGianNhanPhong().format(formatter));
            lblNgayCheckIn.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: white;");
            
            // Ngày check-out 
            Label lblNgayCheckOut = new Label("Check-out: " + 
                phieuThue.getThoiHanGiaoPhong().format(formatter));
            lblNgayCheckOut.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: white;");
            
            roomItem.getChildren().addAll(lblMaPhong, lblNgayCheckIn, lblNgayCheckOut);
        } else {
            // Nếu không có phiếu thuê
            Label lblTrangThai = new Label("Phòng trống");
            roomItem.getChildren().addAll(lblMaPhong, lblTrangThai);
        }

     // Nút hủy phòng
        Button btnHuy = new Button("Hủy phòng");
        btnHuy.setPrefWidth(120);
        btnHuy.setPrefHeight(35);
        btnHuy.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand;");
        
        // Sự kiện cho nút hủy phòng
        btnHuy.setOnAction(event -> {
            // Kiểm tra xem đã chọn khách hàng chưa
//            if (lb_tenKH.getText().isEmpty()) {
//                new Alert(Alert.AlertType.WARNING, "Vui lòng chọn khách hàng trước khi hủy phòng.").showAndWait();
//                return;
//            }

            // Hiển thị hộp thoại xác nhận
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, 
                "Bạn có chắc chắn muốn hủy phòng " + phong.getIdPhong() + "?");
            confirmDialog.setHeaderText("Xác nhận hủy phòng");
            
            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Kết nối DAO để thực hiện các thao tác
                    PhieuThuePhong_DAO ptdao = new PhieuThuePhong_DAO();
                    Phong_DAO phongDAO = new Phong_DAO();
                    KhachHang_DAO khachHangDAO = new KhachHang_DAO();

                    // 1. Lấy phiếu thuê gần nhất của phòng
                    ArrayList<PhieuThuePhong> dsPhieuThuee = ptdao.layPhieuThueTheoMaPhong(phong.getIdPhong());
                    
                    if (!dsPhieuThuee.isEmpty()) {
                        PhieuThuePhong phieuThue = dsPhieuThuee.get(0);
                        
                        // 2. Cập nhật trạng thái phiếu thuê thành false (hủy)
                        phieuThue.setHieuLuc(false);
                        ptdao.suaPhieuThue(phieuThue);

                        // 3. Cập nhật trạng thái phòng về trạng thái trống (2)
                        phong.setTrangThai(TrangThaiPhong.TRONG);
                        phongDAO.capNhatTrangThaiPhong(phong);

                     // 4. Làm mới danh sách phòng
                        String cccd = txt_CCCD.getText().trim();
                        if (cccd.isEmpty()) {
                            // Nếu không có CCCD, load lại danh sách phòng ban đầu
                            Phong_DAO dsP = new Phong_DAO();
                            ArrayList<Phong> availableRooms = dsP.getPhongTheoTrangThaiDanhSach(3);
                            renderArrayPhong(availableRooms);
                        } else {
                            // Nếu có CCCD, load lại danh sách phòng của khách hàng
                            KhachHang kh = khachHangDAO.getKhachHangTheoCCCD(cccd);
                            PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
                            ArrayList<PhieuThuePhong> phieuThueList = dsPT.layPhieuThueTheoMaKH(kh.getIdKhachHang());

                            ArrayList<Phong> filteredRooms = new ArrayList<>();
                            LocalDate currentDate = LocalDate.now();

                            for (PhieuThuePhong pt : phieuThueList) {
                                if (currentDate.isBefore(pt.getThoiGianNhanPhong())) {
                                    filteredRooms.add(pt.getPhong());
                                }
                            }
                            Phong_DAO dsP = new Phong_DAO();
                            ArrayList<Phong> availableRooms = dsP.getPhongTheoTrangThaiDanhSach(3);
                            renderArrayPhong(availableRooms);
                        }

                        txt_CCCD.clear(); 

                        // Thông báo thành công
                        new Alert(Alert.AlertType.INFORMATION, "Hủy phòng thành công.").showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Có lỗi xảy ra: " + e.getMessage()).showAndWait();
                }
            }
        });
            roomItem.getChildren().add(btnHuy);
     // Sự kiện khi nhấn vào phòng
        roomItem.setOnMouseClicked(e -> {
            if (selectedRoomItem != null) {
                selectedRoomItem.setStyle("-fx-background-color: #edbf6d; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
            }
            selectedRoomItem = roomItem;
            roomItem.setStyle("-fx-background-color: #d6a95c; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
        });

        // Thêm hiệu ứng hover
        roomItem.setOnMouseEntered(e -> {
            roomItem.setStyle("-fx-background-color: #d6a95c; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
        });

        roomItem.setOnMouseExited(e -> {
            if (roomItem != selectedRoomItem) {
                roomItem.setStyle("-fx-background-color: #edbf6d; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
            }
        });

        return roomItem;
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
