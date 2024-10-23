package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Phong_DAO;
import entity.LoaiPhong;
import entity.Phong;
import entity.TrangThaiPhong;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.App;

public class GD_TKPhong_Controller implements Initializable{

    @FXML
    private ImageView avt;

    @FXML
    private Button btnQLP;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView iconFind;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_donGia;

    @FXML
    private Label lb_loaiPhong;

    @FXML
    private Label lb_maPhong;

    @FXML
    private Label lb_trangThai;

    @FXML
    private TableView<Phong> tablePhong;
    

    @FXML
    private TableColumn<Phong, String> clDG;
    @FXML
    private TableColumn<Phong, String> clLP;

    @FXML
    private TableColumn<Phong, String> clMaP;

    @FXML
    private TableColumn<Phong, String> clSTT;

    @FXML
    private TableColumn<Phong, String> clTT;

    @FXML
    private TextField txt_MaPhong;

    @FXML
    void moGiaoDienDichVu(MouseEvent event) {

    }

    @FXML
    void moGiaoDienHoaDon(MouseEvent event) {

    }

    @FXML
    void moGiaoDienKhachHang(MouseEvent event) {

    }

    @FXML
    void moGiaoDienPhong(MouseEvent event) {

    }

    @FXML
    void moGiaoDienQuanLy(MouseEvent event) {

    }

    @FXML
    void moGiaoDienTaiKhoan(MouseEvent event) {

    }

    @FXML
    void moGiaoDienThongKe(MouseEvent event) {

    }

    @FXML
    void moGiaoDienThuePhong(MouseEvent event) {

    }

    @FXML
    void moGiaoDienTimKiem(MouseEvent event) {

    }

    @FXML
    void moGiaoDienUuDai(MouseEvent event) {

    }
    
    @FXML
    void denGDQLPhong(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLPhong");
    }
    

    @FXML
    void timKiem(MouseEvent event) {
    	String maPhong = txt_MaPhong.getText();
    	Phong p = new Phong_DAO().getPhongTheoMa(maPhong);
		lb_maPhong.setText(p.getIdPhong());
		lb_loaiPhong.setText(p.getLoaiPhongString());
		lb_donGia.setText(String.valueOf(p.getDonGia()));
		lb_trangThai.setText(p.getTrangThaiString());
		highlightMatchingRow(maPhong);
    }
    private void highlightMatchingRow(String maPhong) {
        for (int i = 0; i < tablePhong.getItems().size(); i++) {
            Phong phong = tablePhong.getItems().get(i);
            if (phong.getIdPhong().equals(maPhong)) {
                // Select the row (important)
                tablePhong.getSelectionModel().select(i);
                // Set focus to the row
                tablePhong.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
                tablePhong.getFocusModel().focus(i);
        		tablePhong.getSelectionModel().focus(i);
                break;
            }
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clSTT.setCellFactory(col -> {
            return new TableCell<Phong, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        // Số thứ tự = index + 1
                        setText(String.valueOf(getIndex() + 1));
                    }
                }
            };
        });
        
        
        clMaP.setCellValueFactory(new PropertyValueFactory<>("idPhong"));
        clLP.setCellValueFactory(cellData -> {
        	LoaiPhong lp = cellData.getValue().getLoaiPhong();
        	return new ReadOnlyStringWrapper(lp.toString());
        });
        clDG.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        clTT.setCellValueFactory(cellData -> {
        	TrangThaiPhong tt = cellData.getValue().getTrangThai();
        	return new ReadOnlyStringWrapper(tt.toString());
        });
        loadTableData();
        tablePhong.setOnMouseClicked(event -> {
        	Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();
        	if (selectedPhong != null) {
        		lb_maPhong.setText(selectedPhong.getIdPhong());
        		lb_loaiPhong.setText(selectedPhong.getLoaiPhongString());
        		lb_donGia.setText(String.valueOf(selectedPhong.getDonGia()));
        		lb_trangThai.setText(selectedPhong.getTrangThaiString());
        	}
        });
	}
    private void loadTableData() {
        try {
            Phong_DAO pdao = new Phong_DAO();
            ArrayList<Phong> dsp = pdao.getAllPhong();

            ObservableList<Phong> observableList = FXCollections.observableArrayList(dsp);
            tablePhong.setItems(observableList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
