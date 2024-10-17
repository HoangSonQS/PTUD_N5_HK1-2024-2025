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
import entity.NhanVien;

public class NhanVien_DAO {
	public ArrayList<NhanVien> getAllNhanVien() {
		ArrayList<NhanVien> dsNV = new ArrayList<NhanVien>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;

		try {
			stm = conN.createStatement();
			String sql = "select * from NhanVien";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idNhanVien = rs.getString("IDNhanVien");
				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				String cccd = rs.getString("CCCD");
				int chucVu = rs.getInt("ChucVu");
				ChucVu cv = null;
				
				if(chucVu == 1) {
					cv = ChucVu.NHANVIENLETAN;
				} else if (chucVu == 2) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
//				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
//					cv = ChucVu.NHANVIENLETAN;
//					
//				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
//					cv = ChucVu.NGUOIQUANLY;
//				}
				
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				dsNV.add(new NhanVien(idNhanVien, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, cccd, cv));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsNV;
	}
	
	public boolean themNhanVien(NhanVien nhanvien) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		try {
			String sql="INSERT INTO NhanVien ( IdNhanVien, TenNhanVien, SoDienThoai, NgaySinh, GioiTinh, CCCD, ChucVu) values(?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, nhanvien.getIdNhanVien());
			pstm.setString(2, nhanvien.getTenNhanVien());
			pstm.setString(3, nhanvien.getSoDienThoai());
			pstm.setDate(4, Date.valueOf(nhanvien.getNgaySinh()));
			pstm.setInt(5, nhanvien.isGioiTinh()? 1 : 0);
			pstm.setString(6, nhanvien.getCccd());

			System.out.println(1);
			int cv = 0;
			if(nhanvien.getChucVu().toString().equalsIgnoreCase("Nhân viên lễ tân")) {
				cv = 1;
			} else if (nhanvien.getChucVu().toString().equalsIgnoreCase("Người quản lý")) {
				cv = 2;
			} 
			pstm.setInt(7, cv);
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
	
	public NhanVien getNhanVienTheoMa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		NhanVien nv = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM NhanVien WHERE IDNhanVien = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, ma);
	        rs = stmt.executeQuery();
			while (rs.next()) {

				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				String cccd = rs.getString("CCCD");
//				String chucVu = rs.getString("ChucVu");
//				ChucVu cv = null;
//				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
//					cv = ChucVu.NHANVIENLETAN;
//					
//				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
//					cv = ChucVu.NGUOIQUANLY;
//				}
				int chucVu = rs.getInt("ChucVu");
				ChucVu cv = null;
				
				if(chucVu == 1) {
					cv = ChucVu.NHANVIENLETAN;
				} else if (chucVu == 2) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				nv = new NhanVien(ma, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, cccd, cv);
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
		 return nv;
	 }
	public boolean capNhatNhanVien(NhanVien nhanvien) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update NhanVien set TenNhanVien=?, SoDienThoai=?, NgaySinh=?, GioiTinh=?, CCCD=?, ChucVu=? where IDNhanVien=? ";
		try {
			pstm.setString(1, nhanvien.getIdNhanVien());
			pstm.setString(2, nhanvien.getTenNhanVien());
			pstm.setString(3, nhanvien.getSoDienThoai());
			pstm.setDate(4, Date.valueOf(nhanvien.getNgaySinh()));
			pstm.setInt(5, nhanvien.isGioiTinh()? 1 : 0);
			pstm.setString(6, nhanvien.getCccd());
			int cv = 0;
			if(nhanvien.getChucVu().toString().equalsIgnoreCase("Nhân viên lễ tân")) {
				cv = 1;
			} else if (nhanvien.getChucVu().toString().equalsIgnoreCase("Người quản lý")) {
				cv = 2;
			} 
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
	
	public boolean xoaTheoMaNhanVien(String maNV) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from NhanVien where IdNhanVien ='" + maNV + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
//	public static int demSLNhanVien(int ns) throws SQLException {
//        Connection conn = ConnectDB.getInstance().getConnection();
//        Statement stmt = null;
//
//        try {
//            stmt = conn.createStatement();
//            String sql = "SELECT YEAR(NgaySinh) AS NamSinh, COUNT(*) AS SoLuongNhanVien "
//                    + "FROM NhanVien "
//                    + "GROUP BY YEAR(NgaySinh)";
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                if (rs.getInt("NamSinh") == ns) {
//                    return rs.getInt("SoLuongNhanVien");
//                }
//            }
//            return 0;
//        } finally {
//            if (stmt != null) {
//                stmt.close();
//            }
//        }
//    }
}
