package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.TrangThaiPhong;

public class TaiKhoan_DAO {
	public boolean themTaiKhoan(TaiKhoan taikhoan) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		try {
			String sql="INSERT INTO TaiKhoan ( IdTaiKhoan, MatKhau, TrangThai, IDNhanVien) values(?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, taikhoan.getIdTaiKhoan());
			pstm.setString(2, taikhoan.getMatKhau());
			pstm.setString(3, taikhoan.getTrangThai());
			pstm.setString(4, taikhoan.getNhanVien().getIdNhanVien());
			n = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstm.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n>0;
	}
	public boolean capNhatTaiKhoan(TaiKhoan taikhoan) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update TaiKhoan set MatKhau=?, TrangThai=?, IDNhanVien=? where IdTaiKhoan=? ";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, taikhoan.getMatKhau());
			pstm.setString(2, taikhoan.getTrangThai());
			pstm.setString(3, taikhoan.getNhanVien().getIdNhanVien());
			pstm.setString(4, taikhoan.getIdTaiKhoan());
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
	public boolean xoaTheoMaTaiKhoan(String maTK) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from NhanVien where IdTaiKhoan ='" + maTK + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public ArrayList<TaiKhoan> getAllTaiKhoan() {
		ArrayList<TaiKhoan> dsTK = new ArrayList<TaiKhoan>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		System.out.println(1);
		try {
			stm = conN.createStatement();
			String sql = "select * from TaiKhoan";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idtaikhoan = rs.getString("IDTaiKhoan");
				String matkhau = rs.getString("MatKhau");
				String trangthai = rs.getString("TrangThai");
				String idnhanvien = rs.getString("IDNhanVien");
				NhanVien nv = new NhanVien_DAO().getNhanVienTheoMa(idnhanvien);
				System.out.println(nv);
				TaiKhoan tk = new TaiKhoan(idtaikhoan, matkhau, trangthai, nv);
				dsTK.add(tk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsTK;
	}
}
