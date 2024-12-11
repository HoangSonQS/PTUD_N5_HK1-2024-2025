package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.NhanVien_DAO;
import dao.Phong_DAO;
import dao.TaiKhoan_DAO;
import entity.ChucVu;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.App;

public class GD_TKNhanVien_Controller implements Initializable{

    @FXML
    private TableColumn<NhanVien, String> clGioiTinh;

    @FXML
    private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private Button btnTraCuu;

    @FXML
    private Button btnXoa2;

    @FXML
    private ComboBox<String> cbbGiaoDien;

    @FXML
    private TableColumn<NhanVien, String> clCCCD;

    @FXML
    private TableColumn<NhanVien, String> clChucVu;

    @FXML
    private TableColumn<NhanVien, String> clNgaySinh;

    @FXML
    private TableColumn<NhanVien, String> clSDT;

    @FXML
    private TableColumn<NhanVien, String> clSTT;

    @FXML
    private TableColumn<NhanVien, String> clmaNv;

    @FXML
    private TableColumn<NhanVien, String> cltenNv;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label lb_MaNV;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_cccd;

    @FXML
    private Label lb_chucVu;

    @FXML
    private Label lb_gioiTinh;

    @FXML
    private Label lb_nsNV;

    @FXML
    private Label lb_sdtNV;

    @FXML
    private Label lb_tenNV;

    @FXML
    private TableView<NhanVien> tableNhanVien;

    @FXML
    private TextField txt_maNV;
    
    @FXML
    private Button btnQLNV;

    @FXML
    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLPhong");
    }

    @FXML
    void moGiaoDienThongKe(MouseEvent event) throws IOException {
    	App.setRoot("GD_ThongKeDoanhThu");
    }

    @FXML
    void moGiaoDienThuePhong(MouseEvent event) throws IOException {
    	App.setRoot("GD_SoDoPhong");
    }

    @FXML
    void moGiaoDienTimKiem(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKPhong");
    }
    
    @FXML
    void denGDQLNhanVien(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLNhanVien");
    }

	@FXML
    void timKiem(MouseEvent event) {
   
    	String maNhanVien = txt_maNV.getText();
    	App.ma = maNhanVien;
    	NhanVien nv = new NhanVien_DAO().getNhanVienTheoMa(maNhanVien);
		lb_MaNV.setText(nv.getIdNhanVien());
		lb_tenNV.setText(nv.getTenNhanVien());
		lb_sdtNV.setText(nv.getSoDienThoai());
		lb_cccd.setText(nv.getCccd());
		lb_gioiTinh.setText(nv.isGioiTinh() ? "Nam" : "Nữ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lb_nsNV.setText(nv.getNgaySinh().format(formatter));
		lb_chucVu.setText(nv.getChucVuString());

		highlightMatchingRow(maNhanVien);
    }


	private void highlightMatchingRow(String maNhanVien) {
        for (int i = 0; i < tableNhanVien.getItems().size(); i++) {
            NhanVien nhanvien = tableNhanVien.getItems().get(i);
            if (nhanvien.getIdNhanVien().equals(maNhanVien)) {
                // Select the row (important)
                tableNhanVien.getSelectionModel().select(i);
                // Set focus to the row
                tableNhanVien.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
                tableNhanVien.getFocusModel().focus(i);
        		tableNhanVien.getSelectionModel().focus(i);
                break;
            }
        }
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_MaNV.setText("");
		lb_tenNV.setText("");
		lb_sdtNV.setText("");
		lb_nsNV.setText("");
		lb_gioiTinh.setText("");
		lb_cccd.setText("");
		lb_chucVu.setText("");
		ObservableList<String> list = FXCollections.observableArrayList("Nhân viên", "Hoá đơn", "Phòng", "Khách hàng",
				"Dịch vụ", "Tài khoản", "Ưu đãi");
		cbbGiaoDien.setItems(list);
		cbbGiaoDien.setValue("Nhân viên");
		 // Xử lý sự kiện chọn ComboBox để chuyển giao diện
        cbbGiaoDien.setOnAction(event -> {
            String selectedValue = cbbGiaoDien.getValue();
            switch (selectedValue) {
            	case "Nhân viên":
            	try {
            		App.setRoot("GD_TKNhanVien");
            	} catch (IOException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
                break;
                case "Tài khoản":
				try {
					App.setRoot("GD_TKTaiKhoan");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Hoá đơn":
				try {
					App.setRoot("GD_TKHoaDon");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Phòng":
				try {
					App.setRoot("GD_TKPhong");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Khách hàng":
				try {
					App.setRoot("GD_TKKhachHang");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Dịch vụ":
				try {
					App.setRoot("GD_TKDichVu");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Ưu đãi":
				try {
					App.setRoot("GD_TKUuDai");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                default:
                    System.out.println("Không tìm thấy giao diện phù hợp!");
                    break;
            }
        });
		clSTT.setCellFactory(col -> {
            return new TableCell<NhanVien, String>() {
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
        
        
        clmaNv.setCellValueFactory(new PropertyValueFactory<>("idNhanVien"));
        clChucVu.setCellValueFactory(cellData -> {
        	ChucVu cv = cellData.getValue().getChucVu();
        	return new ReadOnlyStringWrapper(cv.toString());
        });
        cltenNv.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        clSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        clNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));

        clNgaySinh.setCellValueFactory(cellData -> {
            LocalDate ngaySinh = cellData.getValue().getNgaySinh();
            if (ngaySinh != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String ngaySinhFormatted = ngaySinh.format(formatter);
                return new ReadOnlyStringWrapper(ngaySinhFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        clGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));

        clGioiTinh.setCellValueFactory(cellData -> {
            boolean gioiTinh = cellData.getValue().isGioiTinh();
            return new ReadOnlyStringWrapper(gioiTinh ? "Nam" : "Nữ");
        });


        clCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));


        
        loadTableData();
        tableNhanVien.setOnMouseClicked(event -> {
        	NhanVien selectedNhanVien = tableNhanVien.getSelectionModel().getSelectedItem();
        	if (selectedNhanVien != null) {
        		lb_MaNV.setText(selectedNhanVien.getIdNhanVien());
        		lb_tenNV.setText(selectedNhanVien.getTenNhanVien());
        		lb_sdtNV.setText(selectedNhanVien.getSoDienThoai());
        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        		lb_nsNV.setText(selectedNhanVien.getNgaySinh().format(formatter));
        		lb_gioiTinh.setText(selectedNhanVien.isGioiTinh() ? "Nam" : "Nữ");
        		lb_cccd.setText(selectedNhanVien.getCccd());
        		lb_chucVu.setText(selectedNhanVien.getChucVuString());
        	}
        });
        
        addUserLogin();
	}
    private void loadTableData() {
        try {
            NhanVien_DAO NVdao = new NhanVien_DAO();
            ArrayList<NhanVien> dsnv = NVdao.getAllNhanVien();

            ObservableList<NhanVien> observableList = FXCollections.observableArrayList(dsnv);
            tableNhanVien.setItems(observableList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void moHuongDan(MouseEvent event) {
		String initial = "data\\TaiLieu\\5_7_ApplicationDevelopment_UserManual-trang.html";
		Path initialDirectory = Paths.get(initial).toAbsolutePath();
		File file = new File(initial);

        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private void addUserLogin() {
		TaiKhoan tk = App.tk;
		maNV.setText(String.valueOf(tk.getNhanVien().getIdNhanVien()));
		tenNV.setText(String.valueOf(tk.getNhanVien().getTenNhanVien()));
	}
}
