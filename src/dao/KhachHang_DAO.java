package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.KhachHang;
import entity.KhachHang;
import entity.KhachHang;
import entity.KhachHang;

public class KhachHang_DAO {
	public ArrayList<KhachHang> getAllKhachHang() {
		ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select * from KhachHang";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String IDKhachHang = rs.getString("IDKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				dsKH.add(new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsKH;
	}
	
	public boolean themKhachHang(KhachHang khachhang) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		try {
			String sql="INSERT INTO KhachHang ( IDKhachHang, TenKhachHang, SoDienThoai, NgaySinh, CCCD, TichDiem) values(?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, khachhang.getIdKhachHang());
			pstm.setString(2, khachhang.getTenKhachHang());
			pstm.setString(3, khachhang.getSoDienThoai());
			pstm.setDate(4, Date.valueOf(khachhang.getNgaySinh()));
			pstm.setString(5, khachhang.getCccd());
			pstm.setInt(6, khachhang.getTichDiem());
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
	
	public KhachHang getKhachHangTheoCCCD(String cccd) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE CCCD = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, cccd);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String IDKhachHang = rs.getString("IDKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				kh = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return kh;
	 }
	
	public KhachHang getKhachHangTheoSDT(String sdt) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE SoDienThoai = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, sdt);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String IDKhachHang = rs.getString("IDKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				kh = new KhachHang(IDKhachHang, tenKhachHang, sdt, ngaySinh, cccd, tichDiem);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return kh;
	 }
	public KhachHang getKhachHangTheoMa(String IDKhachHang) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE IDKhachHang = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, IDKhachHang);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				kh = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return kh;
	 }
	
	public boolean capNhatKhachHangTheoSDT(KhachHang khachhang) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update KhachHang set TenKhachHang=?, NgaySinh=?, CCCD=?, TichDiem=? where SoDienThoai=? ";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, khachhang.getTenKhachHang());
			pstm.setDate(2, Date.valueOf(khachhang.getNgaySinh()));
			pstm.setString(3, khachhang.getCccd());
			pstm.setInt(4, khachhang.getTichDiem());
			pstm.setString(5, khachhang.getSoDienThoai());
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