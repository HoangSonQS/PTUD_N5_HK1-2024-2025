package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.KhuyenMai;

public class KhuyenMai_Dao {
	public boolean themKhuyenMai(KhuyenMai khuyenmai) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO KhuyenMai ( idKhuyenMai, tenKhuyenMai, chietKhau) VALUES (?,?,?)";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, khuyenmai.getIdKhuyenMai());
			pstm.setString(2, khuyenmai.getTenKhuyenMai());
			pstm.setDouble(3, khuyenmai.getChietKhau());
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
	public boolean suaDichVu(KhuyenMai khuyenmai) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update DichVu set idKhuyenMai=?, tenKhuyenMai=?, chietkhau=? where idKhuyenMai=? ";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, khuyenmai.getIdKhuyenMai());
			pstm.setString(2, khuyenmai.getTenKhuyenMai());
			pstm.setDouble(3, khuyenmai.getChietKhau());
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
	public boolean xoaKhuyenMai(String idKhuyenMai) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from KhuyenMai where idKhuyenMai ='" + idKhuyenMai + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public static KhuyenMai layKhuyenMai(String idKhuyenMai) {
	    KhuyenMai km = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM KhuyenMai WHERE idKhuyenMai = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idKhuyenMai);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            String tenKhuyenMai = rs.getString("TenKhuyenMai");
	            double chietkhau = rs.getDouble("ChietKhau");
	            km = new KhuyenMai(idKhuyenMai, tenKhuyenMai, chietkhau);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return km;
	}
}
