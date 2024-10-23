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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class GD_SoDoPhong_Cotroller implements Initializable{
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public static String roomID;
	
	@FXML
    private ImageView avt;

    @FXML
    private ComboBox cbb;

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
    private Label lb_TimKiem;

    @FXML
    private Label lb_setCheckin;

    @FXML
    private Label lb_setCheckout1;

    @FXML
    private Label lb_soPhongDangO;

    @FXML
    private Label lb_soPhongTrong;
    @FXML
    private GridPane gridPane;




    @FXML
    void moGiaoDIenDoiPhong(MouseEvent event) {

    }

    @FXML
    void moGiaoDIenGiaHanPhong(MouseEvent event) {

    }

    @FXML
    void moGiaoDIenHuyPhong(MouseEvent event) {

    }

    @FXML
    void moGiaoDIenQuanLy(MouseEvent event) {

    }

    @FXML
    void moGiaoDIenThongKe(MouseEvent event) {

    }

    @FXML
    void moGiaoDIenTimKiem(MouseEvent event) {

    }

    @FXML
    void moGiaoDienSoDoPhong(MouseEvent event) {

    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	renderArrayPhong(Phong_DAO.getAllPhong());
		ObservableList<String> list = FXCollections.observableArrayList("Tất cả", "Phòng đơn", "Phòng đôi", "Phòng gia đình");
		cbb.setItems(list);
		cbb.setValue("Tất cả");
		
    }
    public void renderArrayPhong(ArrayList<Phong> dsPhong) {
        for (int i = 0; i < dsPhong.size(); i++) {
            Phong phong = dsPhong.get(i);
            gridPane.add(taoGiaoDienPhong(phong), i % 10, i / 10);
        }
    }
    public Pane taoGiaoDienPhong(Phong phong) {
    	VBox roomItem = new VBox();
        roomItem.setCursor(Cursor.HAND);
        roomItem.setPrefHeight(200);
        roomItem.setPrefWidth(100);

    	switch (phong.getTrangThai()) {
	        case TRONG:
	        	roomItem.setStyle("-fx-background-color: red;");
	            break;
	        case SAPCHECKIN:
	        	roomItem.setStyle("-fx-background-color: blue;");
	            break;
	        case SAPCHECKOUT:
	        	roomItem.setStyle("-fx-background-color: yellow;");
	        	break;
	        default:
	        	roomItem.setStyle("-fx-background-color: pink;");
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
        String strBtnLeft = phong.getTrangThai() == TrangThaiPhong.TRONG ? "Trả Phòng"
                : phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Hủy phòng" 
                		: phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Trả phòng" : "Trả phòng";
        String strBtnRight = phong.getTrangThai() == TrangThaiPhong.TRONG ? "Đặt phòng"
                : phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Nhận phòng"
                	: phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Thanh toán" : "Thanh toán";
        Button btnLeft = new Button(strBtnLeft);
        Button btnRight = new Button(strBtnRight);
        
        switch (phong.getTrangThai()) {
        case TRONG:
            btnLeft.setStyle("-fx-background-color: #FBFF16; -fx-font-size: 16");
            btnLeft.setDisable(true);
            btnRight.setOnAction((event) -> {
                roomID = phong.getIdPhong();
                moGDDatPhong();
            });
            break;
        case SAPCHECKIN:
            btnLeft.setStyle("-fx-background-color: #4078E3; -fx-text-fill: #fff; -fx-font-size: 16");
            btnLeft.setOnAction(((event) -> {
                roomID = phong.getIdPhong();
//                moGDDatDichVu();
            }));
            btnRight.setOnAction((event) -> {
                roomID = phong.getIdPhong();
//                moGDThanhToan();
            });
            break;
        case DANGTHUE:
        	btnLeft.setStyle("-fx-background-color: #4078E3; -fx-text-fill: #fff; -fx-font-size: 16");
            btnLeft.setOnAction(((event) -> {
                roomID = phong.getIdPhong();
//                moGDDatDichVu();
            }));
            btnRight.setOnAction((event) -> {
                roomID = phong.getIdPhong();
//                moGDThanhToan();
            });
            break;
        default:
            btnLeft.setStyle("-fx-background-color: #CF2A27; -fx-text-fill: #fff; -fx-font-size: 16");
            btnLeft.setOnAction((event) -> {
                roomID = phong.getIdPhong();
//                huyPhongCho();
            });
            btnRight.setOnAction((event) -> {
                roomID = phong.getIdPhong();
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
	private void moGDDatPhong(){
//		App.openModal("GD_DatPhongCho", 250, 350);
		
	}
    
}
