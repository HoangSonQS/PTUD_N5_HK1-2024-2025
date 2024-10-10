package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.DatePicker;

public class GD_ThongKeDoanhThu_Controller implements Initializable{
	@FXML
	private Label lb_Ngay;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Label lb_HDNgay;
	@FXML
	private Label lb_SoHoaDon;
	@FXML
	private Label lb_HDNgay1;
	@FXML
	private Label lb_Tien;
	@FXML
	private BarChart<String, Double> chart_DTTN;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		XYChart.Series<String, Double> chart = new XYChart.Series();
		chart.getData().add(new XYChart.Data("10/10/2024", 11000));
		chart.getData().add(new XYChart.Data("11/10/2024", 10000));
		chart.getData().add(new XYChart.Data("12/10/2024", 20000));
		chart.getData().add(new XYChart.Data("13/10/2024", 9000));
		chart.getData().add(new XYChart.Data("14/10/2024", 8000));
		chart_DTTN.getData().addAll(chart);
	}

}
