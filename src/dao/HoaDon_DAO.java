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
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;

public class HoaDon_DAO {
	public boolean themKhuyenMai(HoaDon hoadon) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO HoaDon ( IDHoaDon, IDNhanVien, IDKhachHang, IDKhuyenMai, ThoiGianTao, ThoiGianCheckin) VALUES (?,?,?,?,?,?)";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, hoadon.getIdHoaDon());
			pstm.setString(2, hoadon.getNhanVienLap().getIdNhanVien());
			pstm.setString(3, hoadon.getKhachHang().getIdKhachHang());
			pstm.setString(4, hoadon.getKhuyenmai().getIdKhuyenMai());
			pstm.setDate(5, Date.valueOf(hoadon.getThoiGianTao()));
			pstm.setDate(6, Date.valueOf(hoadon.getThoiGianCheckin()));
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
	public HoaDon layHoaDonTheoMaHoaDon(String idHoaDon) {
	    HoaDon hd = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM HoaDon WHERE IDHoaDon = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idHoaDon);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            String idnhanvien = rs.getString("IDNhanVien");
	            String idkhachhang = rs.getString("IDKhachHang");
	            String idkhuyenmai = rs.getString("IDKhuyenMai");
	            LocalDate thoigiantao = rs.getDate("ThoiGianTao").toLocalDate();
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianCheckin").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            KhuyenMai_DAO dskm = new KhuyenMai_DAO();
	            dskm.getAllKhuyenMai();
	            KhuyenMai km = dskm.layKhuyenMaiTheoMa(idkhuyenmai);
	            hd = new HoaDon(idHoaDon, nv, kh, km, thoigiantao, thoigiancheckin);
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
	    return hd;
	}
	public ArrayList<HoaDon> layHoaDonTheoMaKhachHang(String idKhachHang) {
		ArrayList<HoaDon>dsHD = new ArrayList<HoaDon>();
	    HoaDon hd = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM HoaDon WHERE IDKhachHang = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idKhachHang);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idhoadon = rs.getString("IDHoaDon");
	            String idnhanvien = rs.getString("IDNhanVien");
	            String idkhuyenmai = rs.getString("IDKhuyenMai");
	            LocalDate thoigiantao = rs.getDate("ThoiGianTao").toLocalDate();
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianCheckin").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idKhachHang);
	            KhuyenMai_DAO dskm = new KhuyenMai_DAO();
	            dskm.getAllKhuyenMai();
	            KhuyenMai km = dskm.layKhuyenMaiTheoMa(idkhuyenmai);
	            hd = new HoaDon(idhoadon, nv, kh, km, thoigiantao, thoigiancheckin);
	            dsHD.add(hd);
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
	    return dsHD;
	}
	public ArrayList<HoaDon> getAllHoaDon(){
		ArrayList<HoaDon>dsHD = new ArrayList<HoaDon>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select*from HoaDon";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idHoaDon = rs.getString("IDHoaDon");
				String idnhanvien = rs.getString("IDNhanVien");
				String idKhachHang = rs.getString("IDKhachHang");
				String idKhuyenmai = rs.getString("IDKhuyenMai");
				LocalDate thoigiantao = rs.getDate("ThoiGianTao").toLocalDate();
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianCheckin").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idKhachHang);
	            KhuyenMai_DAO dskm = new KhuyenMai_DAO();
	            dskm.getAllKhuyenMai();
	            KhuyenMai km = dskm.layKhuyenMaiTheoMa(idKhuyenmai);
	            HoaDon hd = new HoaDon(idHoaDon, nv, kh, km, thoigiantao, thoigiancheckin);
				dsHD.add(hd);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHD;
	}
	
}
