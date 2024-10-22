package gui;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Phong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GD_QLPhong_Controller implements Initializable{

	 	@FXML
	    private ImageView avt;

	    @FXML
	    private Button btnSua1;

	    @FXML
	    private Button btnSua2;

	    @FXML
	    private Button btnThem;

	    @FXML
	    private Button btnXoa1;

	    @FXML
	    private Button btnXoa2;

	    @FXML
	    private Button btn_TimPhong;

	    @FXML
	    private ComboBox cbb;

	    @FXML
	    private TableView<Phong> tablePhong;
	    
	    @FXML
	    private TableColumn<Phong, String> clGiaTien;

	    @FXML
	    private TableColumn<Phong, Phong> clLoaiPhong;

	    @FXML
	    private TableColumn<Phong, Phong> clMaPhong;

	    @FXML
	    private TableColumn<Phong, Phong> clSTT;

	    @FXML
	    private TableColumn<Phong, Phong> clTrangThai;
	    @FXML
	    void nhanNutSua(ActionEvent event) {

	    }
	    
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			ObservableList<String> list = FXCollections.observableArrayList("Tất cả", "Phòng đơn", "Phòng đôi", "Phòng gia đình");
			cbb.setItems(list);
			cbb.setValue("Tất cả");
			
			
	    }
}
