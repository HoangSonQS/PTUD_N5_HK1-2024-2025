package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HoaDon_DAO {
	public boolean themHoaDon(HoaDon hoadon) {
	    int n = 0;
	    ConnectDB.getInstance();
	    Connection conN = ConnectDB.getInstance().getConnection();
	    PreparedStatement pstm = null;
	    String sql = "INSERT INTO HoaDon ( IDHoaDon, IDNhanVien, IDKhachHang, IDKhuyenMai, ThoiGianTao, ThoiGianCheckin) VALUES (?,?,?,?,?,?)";
	    try {
	    	String hd = hoadon.autoIdHoaDon();
	        pstm = conN.prepareStatement(sql);
	        if ((new HoaDon_DAO().layHoaDonTheoMaHoaDon(hd)) == null) {
		        pstm.setString(1, hd);
		        pstm.setString(2, hoadon.getNhanVienLap().getIdNhanVien());
		        pstm.setString(3, hoadon.getKhachHang().getIdKhachHang());
		        pstm.setString(4, hoadon.getKhuyenmai().getIdKhuyenMai());
		        pstm.setTimestamp(5, Timestamp.valueOf(hoadon.getThoiGianTao())); // ThoiGianTao là LocalDateTime
		        pstm.setTimestamp(6, Timestamp.valueOf(hoadon.getThoiGianCheckin()));
		        System.out.println("ok");// ThoiGianCheckin là LocalDateTime
	        }
	        // Chuyển LocalDateTime thành Timestamp

	        n = pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            pstm.close();
	        } catch (Exception e2) {
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
	            
	            // Lấy thời gian tạo hóa đơn
	            LocalDateTime thoigiantao = rs.getTimestamp("ThoiGianTao").toLocalDateTime();
	            
	            // Lấy thời gian check-in (chỉ ngày)
	            LocalDateTime thoigiancheckin = rs.getTimestamp("ThoiGianCheckin").toLocalDateTime();
	            
	            // Lấy thông tin nhân viên
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            
	            // Lấy thông tin khách hàng
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            
	            // Lấy thông tin khuyến mãi
	            KhuyenMai_DAO dskm = new KhuyenMai_DAO();
	            KhuyenMai km = dskm.layKhuyenMaiTheoMa(idkhuyenmai);
	            
	            // Tạo đối tượng HoaDon
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
	         // Lấy thời gian tạo hóa đơn
	            LocalDateTime thoigiantao = rs.getTimestamp("ThoiGianTao").toLocalDateTime();
	            
	            // Lấy thời gian check-in (chỉ ngày)
	            LocalDateTime thoigiancheckin = rs.getTimestamp("ThoiGianCheckin").toLocalDateTime();
	            
	            // Lấy thông tin nhân viên
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            
	            // Lấy thông tin khách hàng
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            KhachHang kh = dskh.getKhachHangTheoMa(idKhachHang);
	            
	            // Lấy thông tin khuyến mãi
	            KhuyenMai_DAO dskm = new KhuyenMai_DAO();
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
			String sql = "select * from HoaDon";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idHoaDon = rs.getString("IDHoaDon");
				String idnhanvien = rs.getString("IDNhanVien");
				String idKhachHang = rs.getString("IDKhachHang");
				String idKhuyenmai = rs.getString("IDKhuyenMai");
				LocalDateTime thoigiantao = rs.getTimestamp("ThoiGianTao").toLocalDateTime();
	            
	            // Lấy thời gian check-in (chỉ ngày)
	            LocalDateTime thoigiancheckin = rs.getTimestamp("ThoiGianCheckin").toLocalDateTime();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
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
	public ObservableList<HoaDon> getAllHoaDonOb(){
		ObservableList<HoaDon>dsHD = FXCollections.observableArrayList();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select * from HoaDon";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idHoaDon = rs.getString("IDHoaDon");
				String idnhanvien = rs.getString("IDNhanVien");
				String idKhachHang = rs.getString("IDKhachHang");
				String idKhuyenmai = rs.getString("IDKhuyenMai");
				LocalDateTime thoigiantao = rs.getTimestamp("ThoiGianTao").toLocalDateTime();
	            
	            // Lấy thời gian check-in (chỉ ngày)
	            LocalDateTime thoigiancheckin = rs.getTimestamp("ThoiGianCheckin").toLocalDateTime();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
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
	
	
	//Thong ke
	public ObservableList<LocalDateTime> TheoNgayob(LocalDate date){
		ObservableList<LocalDateTime> dsHD = FXCollections.observableArrayList(); 
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT ThoiGianTao AS Ngay"
                    + "FROM HoaDon WHERE YEAR(ThoiGianTao) = %d AND MONTH(ThoiGianTao) = %d "
                    + "GROUP BY ThoiGianTao", date.getYear(), date.getMonthValue());
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				LocalDateTime ngayLap = rs.getTimestamp("Ngay").toLocalDateTime();
				dsHD.add(ngayLap);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHD;
	}
	public ArrayList<LocalDate> TheoNgay(LocalDate date){
		ArrayList<LocalDate> dsHD = new ArrayList<LocalDate>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT CAST(ThoiGianTao AS DATE) AS Ngay FROM HoaDon "
					+ "WHERE YEAR(ThoiGianTao) = %d AND MONTH(ThoiGianTao) = %d "
					+ "GROUP BY CAST(ThoiGianTao AS DATE)", date.getYear(), date.getMonthValue());
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				LocalDate ngayLap = rs.getDate("Ngay").toLocalDate();
				dsHD.add(ngayLap);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHD;
	}
	public ArrayList<HoaDon> layTheoNgay(LocalDate date) {
		ArrayList<HoaDon>dsHD = new ArrayList<HoaDon>();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		try {
			String sql = "select * from HoaDon where CAST(ThoiGianTao AS DATE) = ?";
			stm = conN.prepareStatement(sql);
			stm.setDate(1, Date.valueOf(date));
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				String idHoaDon = rs.getString("IDHoaDon");
				String idnhanvien = rs.getString("IDNhanVien");
				String idKhachHang = rs.getString("IDKhachHang");
				String idKhuyenmai = rs.getString("IDKhuyenMai");
				LocalDateTime thoigiantao = rs.getTimestamp("ThoiGianTao").toLocalDateTime();
	       
	            // Lấy thời gian check-in (chỉ ngày)
	            LocalDateTime thoigiancheckin = rs.getTimestamp("ThoiGianCheckin").toLocalDateTime();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
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
