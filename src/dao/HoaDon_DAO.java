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
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public boolean suaHoaDon(HoaDon hoadon) {
        int n = 0;
        Connection conN = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        String sql = "UPDATE HoaDon SET IDNhanVien = ?, IDKhachHang = ?, IDKhuyenMai = ?, ThoiGianTao = ?, ThoiGianCheckin = ? WHERE IDHoaDon = ?";
        try {
            pstm = conN.prepareStatement(sql);

            pstm.setString(1, hoadon.getNhanVienLap().getIdNhanVien());
            pstm.setString(2, hoadon.getKhachHang().getIdKhachHang());
            pstm.setString(3, hoadon.getKhuyenmai().getIdKhuyenMai());
            pstm.setTimestamp(4, Timestamp.valueOf(hoadon.getThoiGianTao()));
            pstm.setTimestamp(5, Timestamp.valueOf(hoadon.getThoiGianCheckin()));
            pstm.setString(6, hoadon.getIdHoaDon()); // Important: Set the ID to update

            n = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Important: Return false on error
        } finally {
            try {
                if (pstm != null) pstm.close(); // Close resources
            } catch (SQLException e) {
                e.printStackTrace();
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
	public ArrayList<Map<LocalDate, Double>> TheoNgayob(LocalDate dateA, LocalDate dateB){
		ArrayList<Map<LocalDate, Double>> dsHD = new ArrayList<Map<LocalDate, Double>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT CAST(ThoiGianTao AS DATE) AS Ngay FROM HoaDon "
					+ "WHERE CAST(ThoiGianTao AS DATE) BETWEEN '%s' AND '%s' "
					+ "GROUP BY CAST(ThoiGianTao AS DATE)", 
					dateA.format(DateTimeFormatter.ISO_DATE), 
					dateB.format(DateTimeFormatter.ISO_DATE));
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				double tongTien = 0;
				LocalDate ngayLap = rs.getDate("Ngay").toLocalDate();
				ArrayList<HoaDon> ds = layTheoNgay(ngayLap);
				for(HoaDon hd : ds) {
					tongTien += hd.tongTien();
				}
				Map<LocalDate, Double> map = new HashMap<LocalDate, Double>();
				map.put(ngayLap, tongTien);
				System.out.println(map);
				dsHD.add(map);
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
	
	
	public ArrayList<Map<Integer, Double>> theoThang(int month, int year){
		ArrayList<Map<Integer, Double>> dsHD = new ArrayList<Map<Integer, Double>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT MONTH(ThoiGianTao) AS Month "
					+ "FROM HoaDon WHERE YEAR(ThoiGianTao) = %d "
					+ "GROUP BY MONTH(ThoiGianTao)", year);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				double tongTien = 0;
				int thang = rs.getInt("Month");
				ArrayList<HoaDon> ds = layTheoThang(thang);
				for(HoaDon hd : ds) {
					tongTien += hd.tongTien();
				}
				Map<Integer, Double> map = new HashMap<Integer, Double>();
				map.put(thang, tongTien);
				System.out.println(map);
				dsHD.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHD;
	}
	public ArrayList<HoaDon> layTheoThang(int date) {
		ArrayList<HoaDon>dsHD = new ArrayList<HoaDon>();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		try {
			String sql = "select * from HoaDon where MONTH(ThoiGianTao) = ?";
			stm = conN.prepareStatement(sql);
			stm.setInt(1, date);
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
	
	public ArrayList<Map<Integer, Double>> theoNam(int year){
		ArrayList<Map<Integer, Double>> dsHD = new ArrayList<Map<Integer, Double>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT Year(ThoiGianTao) AS Year FROM HoaDon WHERE Year(ThoiGianTao) BETWEEN %d AND %d GROUP BY Year(ThoiGianTao)",year - 2, year + 2);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				double tongTien = 0;
				int nam = rs.getInt("Year");
				ArrayList<HoaDon> ds = layTheoNam(nam);
				for(HoaDon hd : ds) {
					tongTien += hd.tongTien();
				}
				Map<Integer, Double> map = new HashMap<Integer, Double>();
				map.put(nam, tongTien);
				System.out.println(map);
				dsHD.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHD;
	}
	public ArrayList<HoaDon> layTheoNam(int date) {
		ArrayList<HoaDon>dsHD = new ArrayList<HoaDon>();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		try {
			String sql = "select * from HoaDon where Year(ThoiGianTao) = ?";
			stm = conN.prepareStatement(sql);
			stm.setInt(1, date);
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
	public int demHDAtoB(LocalDate dateA, LocalDate dateB) {
		int dem = 0;
		Connection connection = null; // Assuming you have a method to get the connection
		try {
			Connection conN = ConnectDB.getInstance().getConnection();
			String sql = "SELECT COUNT(*) AS SoHoaDon FROM HoaDon " +
						"WHERE CAST(ThoiGianTao AS DATE) BETWEEN ? AND ?";
			PreparedStatement pstmt = conN.prepareStatement(sql);
			pstmt.setString(1, dateA.format(DateTimeFormatter.ISO_DATE));
			pstmt.setString(2, dateB.format(DateTimeFormatter.ISO_DATE));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				dem = rs.getInt("SoHoaDon");
			}

		} catch (SQLException e) {
			System.err.println("Error executing query: " + e.getMessage());
			//  Consider logging the exception or displaying an error message to the user
            return -1; // Or throw an exception, depending on your error handling strategy
		}
		return dem;
	}
	public double tongDTAtoB(LocalDate dateA, LocalDate dateB) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		double tongTien = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT * FROM HoaDon "
					+ "WHERE CAST(ThoiGianTao AS DATE) BETWEEN '%s' AND '%s' ",
					dateA.format(DateTimeFormatter.ISO_DATE), 
					dateB.format(DateTimeFormatter.ISO_DATE));
			System.out.println(sql);
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
	            tongTien += hd.tongTien();
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tongTien;
	}
	
	public int demHDTheoThang(int month, int year){
		int dem = 0;
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT COUNT(*) AS TongSoHoaDon "
					+ "FROM HoaDon "
					+ "WHERE YEAR(ThoiGianTao) = %d AND MONTH(ThoiGianTao) = %d", year, month) ;
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				dem = rs.getInt("TongSoHoaDon");
			}

		} catch (SQLException e) {
			System.err.println("Error executing query: " + e.getMessage());
			//  Consider logging the exception or displaying an error message to the user
            return -1; // Or throw an exception, depending on your error handling strategy
		}
		return dem;
	}
	public double tongDTThang(int month, int year) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		double tongTien = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT * FROM HoaDon "
					+ "WHERE YEAR(ThoiGianTao) = %d AND MONTH(ThoiGianTao) = %d",
					year, 
					month);
			System.out.println(sql);
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
	            tongTien += hd.tongTien();
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tongTien;
	}
	public int demHDTheoNam(int year){
		int dem = 0;
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT COUNT(*) AS TongSoHoaDon "
					+ "FROM HoaDon "
					+ "WHERE YEAR(ThoiGianTao) = %d", year) ;
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				dem = rs.getInt("TongSoHoaDon");
			}

		} catch (SQLException e) {
			System.err.println("Error executing query: " + e.getMessage());
			//  Consider logging the exception or displaying an error message to the user
            return -1; // Or throw an exception, depending on your error handling strategy
		}
		return dem;
	}
	public double tongDTTNam(int year) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		double tongTien = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT * FROM HoaDon "
					+ "WHERE YEAR(ThoiGianTao) = %d",
					year);
			System.out.println(sql);
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
	            tongTien += hd.tongTien();
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tongTien;
	}
}
