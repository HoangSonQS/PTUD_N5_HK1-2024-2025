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

import entity.KhachHang;

import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;


public class PhieuThuePhong_DAO {
	public boolean themPhieuThue(PhieuThuePhong phieuthue) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO PhieuThue ( IDPhieuThue, IDKhachHang, IDPhong, IDNhanVien, ThoiGianNhanPhong, ThoiHanGiaoPhong) VALUES (?,?,?,?,?,?)";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, phieuthue.getIdPhieuThue());
			pstm.setString(2, phieuthue.getKhachHang().getIdKhachHang());
			pstm.setString(3, phieuthue.getPhong().getIdPhong());
			pstm.setString(4, phieuthue.getNhanVienLap().getIdNhanVien());
			pstm.setDate(5, Date.valueOf(phieuthue.getThoiGianNhanPhong()));
			pstm.setDate(6, Date.valueOf(phieuthue.getThoiHanGiaoPhong()));
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
	public boolean suaPhieuThue(PhieuThuePhong phieuthue) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update PhieuThue set IDKhachHang=?, IDPhong=?, IDNhanVien=?, ThoiGianNhanPhong=?, ThoiHanGiaoPhong=? where IDPhieuThue=? ";
		try {
			
			pstm = conN.prepareStatement(sql);
			
			pstm.setString(1, phieuthue.getKhachHang().getIdKhachHang());
			pstm.setString(2, phieuthue.getPhong().getIdPhong());
			pstm.setString(3, phieuthue.getNhanVienLap().getIdNhanVien());
			pstm.setDate(4, Date.valueOf(phieuthue.getThoiGianNhanPhong()));
			pstm.setDate(5, Date.valueOf(phieuthue.getThoiHanGiaoPhong()));
			pstm.setString(6, phieuthue.getIdPhieuThue());
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
	public boolean xoaPhieuThue(String idPhieuThue) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from PhieuThuePhong where IDPhieuThue ='" + idPhieuThue + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public PhieuThuePhong layPhieuThueTheoMa(String idPhieuThue) {
	    PhieuThuePhong pt = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhieuThue = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idPhieuThue);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);
	            pt = new PhieuThuePhong(idPhieuThue, kh, p, nv, thoigiancheckin, thoigiancheckout);
	            
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
	    return pt;
	}
	public ArrayList<PhieuThuePhong> getAllPhieuThue(){
		ArrayList<PhieuThuePhong>dsPT = new ArrayList<PhieuThuePhong>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select*from PhieuThuePhong";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThu");
				String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout);
	            dsPT.add(pt);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
}
