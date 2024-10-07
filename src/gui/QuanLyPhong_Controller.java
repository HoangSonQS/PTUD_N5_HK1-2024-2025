package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class QuanLyPhong_Controller implements Initializable{
    @FXML
    private ComboBox cbb;
    @FXML
    private Label lb_setCheckout;

    @FXML
    private Label lb_soPhongDangO;

    @FXML
    private Label lb_soPhongTrong;

    @FXML
    private Label lb_SoPhongBan;

    @FXML
    private Label lb_PhongDangSua;

    @FXML
    private Label lb_soPhongSach;
    @FXML
    void setSoPhongDangO(MouseEvent event) {

    }

    @FXML
    void setSoPhongSapCheckOut(MouseEvent event) {

    }

    @FXML
    void setSoPhongTrong(MouseEvent event) {

    }
    
    @FXML
    void setSoPhongDangSua(MouseEvent event) {

    }

    @FXML
    void setSoPhongSach(MouseEvent event) {

    }
    
    @FXML
    void setSoPhongBan(MouseEvent event) {

    }
    @FXML
    void selectCbb(ActionEvent event) {
    	String s = cbb.getSelectionModel().getSelectedItem().toString();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Tất cả", "Phòng đơn", "Phòng đôi", "Phòng gia đình");
		cbb.setItems(list);
		cbb.setValue("Tất cả");
		
	}
}
