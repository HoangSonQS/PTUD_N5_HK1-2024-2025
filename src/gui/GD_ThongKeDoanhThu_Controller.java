package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.App;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class GD_ThongKeDoanhThu_Controller implements Initializable{
	@FXML
    private ImageView avt;

    @FXML
    private ComboBox<String> cbb;
    @FXML
    private BarChart<String, Double> chart_DTTN;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ImageView icon_TimKiem;
    @FXML
    private Label lb_DT;
    @FXML
    private Label lb_DTSS;
    @FXML
    private Label lb_Ngay;
    @FXML
    private Label lb_SoHD;
    @FXML
    private Label lb_SoHoaDon;
    @FXML
    private Label lb_Tien;
    @FXML
    private Label lb_Tienss;
    @FXML
    private Label lb_TimKiem;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createCBB();
	}
	
	public void createCBB() {
		ObservableList<String> list = FXCollections.observableArrayList("Theo ngày", "Theo tháng", "Theo năm");
		cbb.setItems(list);
		cbb.setValue("Theo ngày");
	}
	    
	    @FXML
	    void moGDQL(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLPhong");
	    }

	    @FXML
	    void moGDQLTP(MouseEvent event) throws IOException {
	    	App.setRoot("GD_SoDoPhong");
	    }

	    @FXML
	    void moGDTK(MouseEvent event) throws IOException {
			 App.setRoot("GD_TKPhong");
	    }

	    @FXML
	    void moGDTKDT(MouseEvent event) throws IOException {
			 App.setRoot("GD_ThongKeDoanhThu");
	    }

	    @FXML
	    void moGDTKKH(MouseEvent event) throws IOException {
	    	App.setRoot("GD_ThongKeKhachHang");
	    }

	    @FXML
	    void moGDTKSP(MouseEvent event) throws IOException {
			 App.setRoot("GD_ThongKeSanPham");
	    }

	    @FXML
	    void moGDTKe(MouseEvent event) throws IOException {
			 App.setRoot("GD_ThongKeDoanhThu");
	    }
}
