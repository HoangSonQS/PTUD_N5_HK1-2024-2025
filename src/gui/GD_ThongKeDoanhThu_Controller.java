package gui;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import dao.HoaDon_DAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class GD_ThongKeDoanhThu_Controller implements Initializable{
	@FXML
    private ImageView avt;
    @FXML
    private ComboBox<String> cbbLoai;
    @FXML
    private ComboBox<String> cbbNam;
    @FXML
    private ComboBox<String> cbbThang;
    @FXML
    private BarChart<String, Double> chart_DTTN;
    @FXML
    private Button btnThongKe;
    @FXML
    private Label chon;
    @FXML
    private Label chon1;
    @FXML
    private Label chon2;
    @FXML
    private Label chon21;
    @FXML
    private Label chon11;
    @FXML
    private Label chon111;
    @FXML
    private DatePicker datePickerNgaybd;
    @FXML
    private DatePicker datePickerNgaykt;
    @FXML
    private ImageView icon_TimKiem;
    @FXML
    private Label lb_DT;
    @FXML
    private Label lb_DTSS;
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
		ThongKeTheoNgay(LocalDate.of(2024,10,12));
	}
    @FXML
    void thongKe(MouseEvent event) {

    }
	public void createCBB() {
		ObservableList<String> list = FXCollections.observableArrayList("Theo ngày", "Theo tháng", "Theo năm");
		cbbLoai.setItems(list);
		cbbLoai.setValue("Theo ngày");
		ObservableList<String> thang = FXCollections.observableArrayList("Tháng 1","Tháng 2","Tháng 3"
				,"Tháng 4","Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12");
		cbbThang.setItems(thang);
		ObservableList<String> nam = FXCollections.observableArrayList();
		LocalDate now = LocalDate.now();
		for (int i = now.getYear(); i > now.getYear() - 10; i--) {
			nam.add(String.valueOf(i));
		}
		cbbNam.setItems(nam);
	}
	
	public void ThongKeTheoNgay(LocalDate date) {
        XYChart.Series<String, Double> chart1 = new XYChart.Series<>();
		for (int i = 1; i <= 31; i++) {
			chart1.getData().add(new XYChart.Data<>(String.valueOf(i), 0.0));
		}
        ArrayList<Map<LocalDate, Double>> dsHD = new HoaDon_DAO().TheoNgayob(date);
		if (dsHD == null || dsHD.isEmpty()) {
            System.out.println("Không có dữ liệu để thống kê.");
            return;
        }
		
        for (Map<LocalDate, Double> map : dsHD) {
            for (Map.Entry<LocalDate, Double> entry : map.entrySet()) {
                LocalDate ngay = entry.getKey();
                double tongTien = entry.getValue();
                String ngayString = String.valueOf(ngay.getDayOfMonth());
                boolean found = false;
                for (XYChart.Data<String, Double> data : chart1.getData()) {
                    if (data.getXValue().equals(ngayString)) {
                        data.setYValue(tongTien);
						found = true;
                        break;
                    }
//                    if (data.equals(String.valueOf(date.getDayOfMonth()))) {
//                    	data.getNode().setStyle("-fx-background-color: #FFC0CB; -fx-text-fill: black;");
//                    }
                }
                if(!found){
                    System.err.println("Ngày " + ngayString + " không tìm thấy trong danh sách.");
                }
            }
        }
		chart_DTTN.getData().clear();
		chart_DTTN.setLegendVisible(false);
		chart_DTTN.getData().add(chart1);
		
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
