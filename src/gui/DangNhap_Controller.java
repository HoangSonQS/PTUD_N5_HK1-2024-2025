package gui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

public class DangNhap_Controller {
	@FXML
	private TextField txt_ten;
	@FXML
	private TextField txt_mk;
	@FXML
	private Button btn_LogIn;

	// Event Listener on Button[#btn_LogIn].onAction
	@FXML
	public void LogIn(ActionEvent event) {
		String userName = txt_ten.getText().trim();
		String password = txt_mk.getText().trim();
		
		
	}
}
