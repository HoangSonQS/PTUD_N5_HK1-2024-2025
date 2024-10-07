package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;


public class GD_Chinh_Controller implements Initializable{
	@FXML
	private TreeView<String> treeView;
	
	public void selectItem() {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		TreeItem<String> rootItem = new TreeItem<>("");
//		
//		
//		TreeItem<String> branchItem1 = new TreeItem<>("Quản lý phòng");
//		TreeItem<String> leafItem1 = new TreeItem<>("Sơ đồ phòng");
//		TreeItem<String> leafItem2 = new TreeItem<>("Đổi phòng");
//		TreeItem<String> leafItem3 = new TreeItem<>("Hủy phòng");
//		TreeItem<String> leafItem4 = new TreeItem<>("Gia hạn phòng");
//		branchItem1.getChildren().addAll(leafItem1, leafItem2, leafItem3, leafItem4);
//		rootItem.getChildren().add(branchItem1);
//		branchItem1.setExpanded(true);
//		TreeItem<String> branchItem2 = new TreeItem<>("Danh sách");
//		TreeItem<String> leafItem2_1 = new TreeItem<>("Phòng");
//		TreeItem<String> leafItem2_2 = new TreeItem<>("Nhân viên");
//		TreeItem<String> leafItem2_3 = new TreeItem<>("Khách hàng");
//		TreeItem<String> leafItem2_4 = new TreeItem<>("Hóa đơn");
//		TreeItem<String> leafItem2_5 = new TreeItem<>("Dịch vụ");
//		TreeItem<String> leafItem2_6 = new TreeItem<>("Tài khoản");
//		TreeItem<String> leafItem2_7 = new TreeItem<>("Ưu đãi");
//		branchItem2.getChildren().addAll(leafItem2_1, leafItem2_2, leafItem2_3, leafItem2_4,leafItem2_5, leafItem2_6, leafItem2_7);
//		rootItem.getChildren().add(branchItem2);
//		
//		TreeItem<String> branchItem3 = new TreeItem<>("Thống kê");
//		TreeItem<String> leafItem3_1 = new TreeItem<>("Doanh thu");
//		TreeItem<String> leafItem3_2 = new TreeItem<>("Khách hàng");
//		TreeItem<String> leafItem3_3 = new TreeItem<>("Sản phẩm");
//		branchItem3.getChildren().addAll(leafItem3_1, leafItem3_2, leafItem3_3);
//		rootItem.getChildren().add(branchItem3);
//		
//		treeView.setShowRoot(false);
//		treeView.setRoot(rootItem);
	
		
	}
}
