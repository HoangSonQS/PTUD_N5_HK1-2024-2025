package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiPhong;
import entity.Phong;

public class Phong_DAO {
	public ArrayList<Phong> getAllSanPham() {
		ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select*from Phong";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idPhong = rs.getString("IDPhong");
				String loaiPhong = rs.getString("LoaiPhong");
				LoaiPhong lp = null;
				if(loaiPhong.equalsIgnoreCase(LoaiPhong.PHONGDOI.toString())) {
					lp = LoaiPhong.PHONGDOI;
					
				} else if(loaiPhong.equalsIgnoreCase(LoaiPhong.PHONGDON.toString())) {
					lp = LoaiPhong.PHONGDON;
					
				} else if(loaiPhong.equalsIgnoreCase(LoaiPhong.PHONGGIADINH.toString())) {
					lp = LoaiPhong.PHONGGIADINH;
					
				}
				
				Double donGia = rs.getDouble("DonGia");
				Integer trangThai = rs.getInt("TrangThai");
				dsPhong.add(new Phong(idPhong, lp, donGia, trangThai));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsPhong;
	}
	
	public boolean themPhong(Phong phong) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		try {
			String sql = "INSERT INTO Phong (IDPhong, LoaiPhong, DonGia, TrangThai)values(?,?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phong.getIdPhong());
			pstm.setString(2, phong.getLoaiPhong().toString());
			pstm.setDouble(3, phong.getDonGia());
			pstm.setInt(4, phong.getTrangThai());
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
	 public Phong getPhongTheoMa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		Statement stmt = null;
		Phong phong = null;
		try {
			stmt = con.createStatement();
			String sql = String
					.format("SELECT * FROM Phong"+ "WHERE Phong.IDPhong = '%s'", ma);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String loaiPhong = rs.getString("LoaiPhong");
				LoaiPhong lp = null;
				if(loaiPhong.equalsIgnoreCase(LoaiPhong.PHONGDOI.toString())) {
					lp = LoaiPhong.PHONGDOI;
					
				} else if(loaiPhong.equalsIgnoreCase(LoaiPhong.PHONGDON.toString())) {
					lp = LoaiPhong.PHONGDON;
					
				} else if(loaiPhong.equalsIgnoreCase(LoaiPhong.PHONGGIADINH.toString())) {
					lp = LoaiPhong.PHONGGIADINH;
					
				}
				
				Double donGia = rs.getDouble("DonGia");
				Integer trangThai = rs.getInt("TrangThai");
				phong = new Phong(ma, lp, donGia, trangThai);
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
		 return phong;
	 
	 }
	 public Phong getPhongTheoLoai(String loai) {
			Connection con = ConnectDB.getInstance().getConnection();
			Statement stmt = null;
			Phong phong = null;
			try {
				stmt = con.createStatement();
				String sql = String
						.format("SELECT * FROM Phong"+ "WHERE Phong.LoaiPhong = '%s'", loai);
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String idPhong = rs.getString("IDPhong");
					LoaiPhong lp = null;
					if(loai.equalsIgnoreCase(LoaiPhong.PHONGDOI.toString())) {
						lp = LoaiPhong.PHONGDOI;
						
					} else if(loai.equalsIgnoreCase(LoaiPhong.PHONGDON.toString())) {
						lp = LoaiPhong.PHONGDON;
						
					} else if(loai.equalsIgnoreCase(LoaiPhong.PHONGGIADINH.toString())) {
						lp = LoaiPhong.PHONGGIADINH;
						
					}
					
					Double donGia = rs.getDouble("DonGia");
					Integer trangThai = rs.getInt("TrangThai");
					phong = new Phong(idPhong, lp, donGia, trangThai);
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
			 return phong;
		 
		 }
}
