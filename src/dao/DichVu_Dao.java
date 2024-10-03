package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import connectDB.ConnectDB;
import entity.DichVu;

public class DichVu_Dao {
	public static ArrayList<DichVu> getAllDichZVu(){
		ArrayList<DichVu>dsDV = new ArrayList<DichVu>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select*from DichVu";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idDichvu = rs.getString("idDichVu");
				String tenSP = rs.getString("tenSanPham");
				int sl = rs.getInt("soLuong");
				double dongia = rs.getDouble("donGia");
				DichVu dv = new DichVu(idDichvu, tenSP, sl, dongia);
				dsDV.add(dv);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsDV;
	}
	public boolean themDichVu(DichVu dichvu) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO DichVu ( idDichVu, tenSanPham, soLuong, donGia) VALUES (?,?,?,?)";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, dichvu.getIdDichVu());
			pstm.setString(2, dichvu.getTenSanPham());
			pstm.setInt(3, dichvu.getSoLuong());
			pstm.setDouble(4, dichvu.getDonGia());
			n = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return n > 0;
	}
}
