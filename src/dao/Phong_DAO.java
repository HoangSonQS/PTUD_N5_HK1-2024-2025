package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiPhong;
import entity.Phong;
import entity.Phong;
import entity.TrangThaiPhong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Phong_DAO {
	public ArrayList<Phong> getAllPhong() {
		ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		Connection con = ConnectDB.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "SELECT * FROM Phong";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ma = rs.getString("IDPhong");
				int loaiPhong = rs.getInt("LoaiPhong");
				LoaiPhong lp = null;
				if(loaiPhong == 1) {
					lp = LoaiPhong.PHONGDOI;
					
				} else if(loaiPhong == 2) {
					lp = LoaiPhong.PHONGDON;
					
				} else if(loaiPhong == 3) {
					lp = LoaiPhong.PHONGGIADINH;
				}
				double donGia = rs.getDouble("DonGia");
				int trangThai = rs.getInt("TrangThai");
				TrangThaiPhong tt = null;
				if(trangThai == 1) {
					tt = TrangThaiPhong.TRONG;
				} else if(trangThai == 2) {
					tt = TrangThaiPhong.DANGTHUE;
				} else if(trangThai == 3) {
					tt = TrangThaiPhong.SAPCHECKIN;
				} else {
					tt = TrangThaiPhong.SAPCHECKOUT;
				}
				Phong phong = new Phong(ma, lp, donGia, tt);
				dsPhong.add(phong);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dsPhong;
		
	}
	
	public boolean themPhong(Phong phong) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		con = ConnectDB.getInstance().getConnection();
		String sql = "INSERT INTO Phong (IDPhong, LoaiPhong, DonGia, TrangThai)values(?,?,?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phong.getIdPhong());
			int lp = 0;
			if(phong.getLoaiPhong().toString().equalsIgnoreCase("Phòng đôi")) {
				lp = 1;
			} else if(phong.getLoaiPhong().toString().equalsIgnoreCase("Phòng đơn")) {
				lp = 2;
			} else {
				lp = 3;
			}
			pstm.setInt(2, lp);
			pstm.setDouble(3, phong.getDonGia());
			int tt = 0;
			if(phong.getTrangThai().toString().equalsIgnoreCase("Trống")) {
				tt = 1;
			} else if(phong.getTrangThai().toString().equalsIgnoreCase("Đang thuê")) {
				tt = 2;
			} else if(phong.getTrangThai().toString().equalsIgnoreCase("Sắp checkin")) {
				tt = 3;
			} else {
				tt = 4;
			}
			pstm.setInt(4, tt);
			n = pstm.executeUpdate();
			System.out.println("Thêm thành công");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}
	 public Phong getPhongTheoMa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		Phong phong = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM Phong WHERE IDPhong = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				int loaiPhong = rs.getInt("LoaiPhong");
				LoaiPhong lp = null;
				if(loaiPhong == 1) {
					lp = LoaiPhong.PHONGDOI;
				} else if(loaiPhong == 2) {
					lp = LoaiPhong.PHONGDON;
				} else{
					lp = LoaiPhong.PHONGGIADINH;
				}
				Double donGia = rs.getDouble("DonGia");
				int trangThai = rs.getInt("TrangThai");
				TrangThaiPhong tt = null;
				if(trangThai == 1) {
					tt = TrangThaiPhong.TRONG;
				} else if(trangThai == 1) {
					tt = TrangThaiPhong.DANGTHUE;
				} else if(trangThai == 1) {
					tt = TrangThaiPhong.SAPCHECKIN;
				} else {
					tt = TrangThaiPhong.SAPCHECKOUT;
				}
				phong = new Phong(ma, lp, donGia, tt);
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
	 public ArrayList<Phong> getPhongTheoLoai(String loai) {
		 ArrayList<Phong> dsPhong = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			Statement stmt = null;
			
			try {
				stmt = con.createStatement();
				String sql = "SELECT * FROM Phong";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String IDPhong = rs.getString("IDPhong");
					int loaiPhong = rs.getInt("LoaiPhong");
					LoaiPhong lphong = null;
					if(loaiPhong == 1) {
						lphong = LoaiPhong.PHONGDOI;
					} else if(loaiPhong == 2) {
						lphong = LoaiPhong.PHONGDON;
					} else{
						lphong = LoaiPhong.PHONGGIADINH;
					}
					Double donGia = rs.getDouble("DonGia");
					int trangThai = rs.getInt("TrangThai");
					TrangThaiPhong tt = null;
					if(trangThai == 1) {
						tt = TrangThaiPhong.TRONG;
					} else if(trangThai == 1) {
						tt = TrangThaiPhong.DANGTHUE;
					} else if(trangThai == 1) {
						tt = TrangThaiPhong.SAPCHECKIN;
					} else {
						tt = TrangThaiPhong.SAPCHECKOUT;
					}
					Phong phong = new Phong(IDPhong, lphong, donGia, tt);
					System.out.println(phong);
					dsPhong.add(phong);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dsPhong;
		 }
	 public boolean capNhatPhong(Phong phong) {
			int n = 0;
			ConnectDB.getInstance();
			Connection conN = ConnectDB.getInstance().getConnection();
			PreparedStatement pstm = null;
			String sql = "update Phong set LoaiPhong=?, DonGia=?, TrangThai=? where IDPhong=? ";
			try {
				pstm = conN.prepareStatement(sql);
				//pstm.setString(1, phong.getIdPhong());
//				pstm.setString(2, phong.getLoaiPhong());
				pstm.setString(1, phong.getIdPhong());
				int lp = 0;
				if(phong.getLoaiPhong().toString().equalsIgnoreCase("Phòng đôi")) {
					lp = 1;
				} else if(phong.getLoaiPhong().toString().equalsIgnoreCase("Phòng đơn")) {
					lp = 2;
				} else {
					lp = 3;
				}
				pstm.setInt(2, lp);
				pstm.setDouble(3, phong.getDonGia());
				int tt = 0;
				if(phong.getTrangThai().toString().equalsIgnoreCase("Trống")) {
					tt = 1;
				} else if(phong.getTrangThai().toString().equalsIgnoreCase("Đang thuê")) {
					tt = 2;
				} else if(phong.getTrangThai().toString().equalsIgnoreCase("Sắp checkin")) {
					tt = 3;
				} else {
					tt = 4;
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
	 public ObservableList<Phong> getAllPhongOb() {
	        ObservableList<Phong> phongList = FXCollections.observableArrayList();
	        Connection con = ConnectDB.getInstance().getConnection();
	        Statement stmt = null;

	        try {
	            stmt = con.createStatement();
	            String sql = "SELECT * FROM Phong";
	            ResultSet rs = stmt.executeQuery(sql);

	            while (rs.next()) {
	                String idPhong = rs.getString("IDPhong");
	                int loaiPhong = rs.getInt("LoaiPhong");
	                int trangThai = rs.getInt("TrangThai");
	                double donGia = rs.getDouble("DonGia");

	                LoaiPhong loaiPhongEnum = getLoaiPhongEnum(loaiPhong);
	                TrangThaiPhong trangThaiEnum = getTrangThaiEnum(trangThai);

	                if(loaiPhongEnum != null && trangThaiEnum != null){
	                    Phong phong = new Phong(idPhong, loaiPhongEnum, donGia, trangThaiEnum);
	                    phongList.add(phong);
	                }

	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) stmt.close();
	                // Important: Close the connection too!
					if (con != null) con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return phongList;
	    }
	 private LoaiPhong getLoaiPhongEnum(int loaiPhong) {
	        switch (loaiPhong) {
	            case 1: return LoaiPhong.PHONGDOI;
	            case 2: return LoaiPhong.PHONGDON;
	            case 3: return LoaiPhong.PHONGGIADINH;
				default: return null; // Handle invalid values
	        }
	    }
	    private TrangThaiPhong getTrangThaiEnum(int trangThai) {
	        switch (trangThai) {
	            case 1: return TrangThaiPhong.TRONG;
	            case 2: return TrangThaiPhong.DANGTHUE;
	            case 3: return TrangThaiPhong.SAPCHECKIN;
	            case 4: return TrangThaiPhong.SAPCHECKOUT;
				default: return null;  // Handle invalid values
	        }
	    }
	    public boolean xoaPhong(String idPhong) {
	        Connection con = null;
	        PreparedStatement pstm = null;
	        try {
	            con = ConnectDB.getInstance().getConnection();
	            String sql = "DELETE FROM Phong WHERE IDPhong = ?";
	            pstm = con.prepareStatement(sql);
	            pstm.setString(1, idPhong);
	            int n = pstm.executeUpdate();
	            return n > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            try {
	                if (pstm != null) {
	                    pstm.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
