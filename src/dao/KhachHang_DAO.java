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
			String sql = "select*from KhachHang";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idKhachHang = rs.getString("IDKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				
				//KhachHang nv = new KhachHang(idKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				dsKH.add(new KhachHang(idKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dsKH);
		return dsKH;
	}
	
	public boolean themKhachHang(KhachHang khachhang) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		try {
			String sql="INSERT INTO KhachHang ( IdKhachHang, TenKhachHang, SoDienThoai, NgaySinh, CCCD, TichDiem) values(?,?,?,?,?,?,?)";
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
		ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE IdKhachHang = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(5, cccd);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String idKhachHang = rs.getString("IdKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(idKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				dsKH.add(new KhachHang(idKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem));
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
		ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE IdKhachHang = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(3, sdt);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String idKhachHang = rs.getString("IdKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(idKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				dsKH.add(new KhachHang(idKhachHang, tenKhachHang, sdt, ngaySinh, cccd, tichDiem));
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
		String sql = "update KhachHang set IdKhachHang=?, TenKhachHang=?, NgaySinh=?, GioiTinh=?, CCCD=?, ChucVu=? where SoDienThoai=? ";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, khachhang.getTenKhachHang());
			pstm.setString(2, khachhang.getSoDienThoai());
			pstm.setDate(3, Date.valueOf(khachhang.getNgaySinh()));
			pstm.setString(5, khachhang.getCccd());
			pstm.setInt(6, khachhang.getTichDiem());
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
