package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;


public class PhieuThuePhong_DAO {
	public boolean themPhieuThue(PhieuThuePhong phieuthue) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		System.out.println(0);
		
		String sql = "INSERT INTO PhieuThuePhong ( IDPhieuThue, IDKhachHang, IDPhong, IDNhanVien, ThoiGianNhanPhong, ThoiHanGiaoPhong, HieuLuc) VALUES (?,?,?,?,?,?,?)";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, phieuthue.getIdPhieuThue());
			pstm.setString(2, phieuthue.getKhachHang().getIdKhachHang());
			pstm.setString(3, phieuthue.getPhong().getIdPhong());
			pstm.setString(4, phieuthue.getNhanVienLap().getIdNhanVien());
			pstm.setDate(5, Date.valueOf(phieuthue.getThoiGianNhanPhong()));
			pstm.setDate(6, Date.valueOf(phieuthue.getThoiHanGiaoPhong()));
			pstm.setBoolean(7, Boolean.TRUE);
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
		String sql = "update PhieuThuePhong set IDKhachHang=?, IDPhong=?, IDNhanVien=?, ThoiGianNhanPhong=?, ThoiHanGiaoPhong=?, HieuLuc=? where IDPhieuThue=? ";
		try {
			
			pstm = conN.prepareStatement(sql);
			
			pstm.setString(1, phieuthue.getKhachHang().getIdKhachHang());
			pstm.setString(2, phieuthue.getPhong().getIdPhong());
			pstm.setString(3, phieuthue.getNhanVienLap().getIdNhanVien());
			pstm.setDate(4, Date.valueOf(phieuthue.getThoiGianNhanPhong()));
			pstm.setDate(5, Date.valueOf(phieuthue.getThoiHanGiaoPhong()));
			pstm.setBoolean(6, phieuthue.getHieuLuc());
			pstm.setString(7, phieuthue.getIdPhieuThue());
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
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            pt = new PhieuThuePhong(idPhieuThue, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            
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
	
	public PhieuThuePhong layPhieuThueTheoMaPhong_1Phong(String idPhong) {
		PhieuThuePhong pt = new PhieuThuePhong();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhong = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idPhong);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idphieuthue = rs.getString("IDPhieuThue");
	        	String idkhachhang = rs.getString("IDKhachHang");
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
	            Phong p = dsp.getPhongTheoMa(idPhong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            pt = new PhieuThuePhong(idphieuthue, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
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
	
	public ArrayList<PhieuThuePhong> layPhieuThueTheoMaPhong(String idPhong) {
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhong = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idPhong);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idphieuthue = rs.getString("IDPhieuThue");
	        	String idkhachhang = rs.getString("IDKhachHang");
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
	            Phong p = dsp.getPhongTheoMa(idPhong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthue, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            dsPT.add(pt);
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
	    return dsPT;
	}
	public ArrayList<PhieuThuePhong> getAllPhieuThue(){
		ArrayList<PhieuThuePhong>dsPT = new ArrayList<PhieuThuePhong>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select * from PhieuThuePhong";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
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
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            dsPT.add(pt);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	
	
	
	// lọc hiệu lực
	public ArrayList<PhieuThuePhong> layPhieuThueTheoMaKH(String maKH){
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			String sql = "select * from PhieuThuePhong where IDKhachHang = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
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
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            if (hieuLuc == true) {
		            dsPT.add(pt);
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	public ArrayList<PhieuThuePhong> layPhieuThueTheoMaHD(String maHD){
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			String sql = "select * from PhieuThuePhong where IDHoaDon = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
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
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            dsPT.add(pt);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	
	//lay Phieu thue theo hieu luc 
	public ArrayList<PhieuThuePhong> layPhieuThueTheoHieuLuc(boolean a){
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			String sql = "select * from PhieuThuePhong where HieuLuc = ?";
			stmt = con.prepareStatement(sql);
			stmt.setBoolean(1, a);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
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
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, a);
	            dsPT.add(pt);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	
	//thongke
	
	public ArrayList<Map<LocalDate, Integer>> thongKeTheoNgay(LocalDate dateA, LocalDate dateB){
		ArrayList<Map<LocalDate, Integer>> kq = new ArrayList<Map<LocalDate, Integer>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;

		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT CAST(ThoiGianNhanPhong AS DATE) AS Ngay "
					+ "FROM PhieuThuePhong "
					+ "WHERE CAST(ThoiGianNhanPhong AS DATE) BETWEEN '%s' AND '%s' and IDHoaDon IS NOT NULL "
					+ "GROUP BY CAST(ThoiGianNhanPhong AS DATE)",
					dateA.format(DateTimeFormatter.ISO_DATE), 
					dateB.format(DateTimeFormatter.ISO_DATE));
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int dem = 0;
				Map<LocalDate, Integer> map = new HashMap<LocalDate, Integer>();
				LocalDate ngayLap = rs.getDate("Ngay").toLocalDate();
				dem += layPhieuThueTheoNgay(ngayLap);
				map.put(ngayLap, dem);
				kq.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kq;
	}
	public int layPhieuThueTheoNgay(LocalDate date){
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		int dem = 0;
		try {
			String sql = "select IDPhong from PhieuThuePhong where CAST(ThoiGianNhanPhong AS DATE) = ? and IDHoaDon IS NOT NULL";
			stm = conN.prepareStatement(sql);
			stm.setDate(1, Date.valueOf(date));
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public ArrayList<Map<Integer, Integer>> thongKeTheoThang(int month, int year){
		ArrayList<Map<Integer, Integer>> kq = new ArrayList<Map<Integer, Integer>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;

		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT MONTH(ThoiGianNhanPhong) AS Month "
					+ "FROM PhieuThuePhong "
					+ "WHERE YEAR(ThoiGianNhanPhong) = '%d' and IDHoaDon IS NOT NULL "
					+ "GROUP BY MONTH(ThoiGianNhanPhong)", year);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int dem = 0;
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				int thang = rs.getInt("Month");
				dem += layPhieuThueTheoThang(thang);
				map.put(thang, dem);
				kq.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kq;
	}
	public int layPhieuThueTheoThang(int thang){
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		int dem = 0;
		try {
			String sql = "select IDPhong from PhieuThuePhong where MONTH(ThoiGianNhanPhong) = ? and IDHoaDon IS NOT NULL";
			stm = conN.prepareStatement(sql);
			stm.setInt(1, thang);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public ArrayList<Map<Integer, Integer>> thongKeTheoNam(int year){
		ArrayList<Map<Integer, Integer>> kq = new ArrayList<Map<Integer, Integer>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT Year(ThoiGianNhanPhong) AS Year FROM PhieuThuePhong WHERE Year(ThoiGianNhanPhong) BETWEEN %d AND %d GROUP BY Year(ThoiGianNhanPhong)",year - 2, year + 2);
			
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int dem = 0;
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				int nam = rs.getInt("Year");
				dem += layPhieuThueTheoNam(nam);
				map.put(nam, dem);
				System.out.println(map);
				kq.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kq;
	}
	public int layPhieuThueTheoNam(int nam){
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		int dem = 0;
		try {
			String sql = "select IDPhong from PhieuThuePhong where Year(ThoiGianNhanPhong) = ? and IDHoaDon IS NOT NULL";
			stm = conN.prepareStatement(sql);
			stm.setInt(1, nam);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public int tongKHAtoB(LocalDate dateA, LocalDate dateB) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		int dem = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT IDPhong FROM PhieuThuePhong "
					+ "WHERE CAST(ThoiGianNhanPhong AS DATE) BETWEEN '%s' AND '%s' and IDHoaDon IS NOT NULL",
					dateA.format(DateTimeFormatter.ISO_DATE), 
					dateB.format(DateTimeFormatter.ISO_DATE));
			
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public int tongKHThang(int month, int year) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		int dem = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT IDPhong FROM PhieuThuePhong "
					+ "WHERE YEAR(ThoiGianNhanPhong) = %d AND MONTH(ThoiGianNhanPhong) = %d and IDHoaDon IS NOT NULL",
					year, 
					month);
			
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public int tongKHTNam(int year) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		int dem = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT IDPhong FROM PhieuThuePhong "
					+ "WHERE YEAR(ThoiGianNhanPhong) = %d and IDHoaDon IS NOT NULL",
					year);
			
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public LocalDateTime getThoiGianNhanPhong(String maPhieuThue) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		LocalDateTime time = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT ThoiGianNhanPhong FROM PhieuThuePhong "
					+ "WHERE IDPhieuThue = '%s'",
					maPhieuThue);
			
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				time = rs.getTimestamp("ThoiGianNhanPhong").toLocalDateTime();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return time;
	}
	public LocalDateTime getThoiGianTraPhong(String maPhieuThue) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		LocalDateTime time = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT ThoiHanGiaoPhong FROM PhieuThuePhong "
					+ "WHERE IDPhieuThue = '%s'",
					maPhieuThue);
			
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				time = rs.getTimestamp("ThoiHanGiaoPhong").toLocalDateTime();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return time;
	}
}
