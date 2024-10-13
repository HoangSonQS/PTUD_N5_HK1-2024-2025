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
			String sql = "select*from NhanVien";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idNhanVien = rs.getString("IDNhanVien");
				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				String cccd = rs.getString("CCCD");
				String chucVu = rs.getString("ChucVu");
				ChucVu cv = null;
				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
					cv = ChucVu.NHANVIENLETAN;
					
				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				dsNV.add(new NhanVien(idNhanVien, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, cccd, cv));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dsNV);
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
			pstm.setString(7, nhanvien.getChucVu().toString());
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
		ArrayList<NhanVien> dsNV = new ArrayList<NhanVien>();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		NhanVien nv = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM NhanVien WHERE IdNhanVien = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, ma);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				String cccd = rs.getString("CCCD");
				String chucVu = rs.getString("ChucVu");
				ChucVu cv = null;
				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
					cv = ChucVu.NHANVIENLETAN;
					
				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				dsNV.add(new NhanVien(ma, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, cccd, cv));
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
}
