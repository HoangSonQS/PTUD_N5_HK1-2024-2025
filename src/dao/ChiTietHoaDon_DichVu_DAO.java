package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietHD_DichVu;
import entity.DichVu;
import entity.HoaDon;


public class ChiTietHoaDon_DichVu_DAO {
	public boolean themChiTietHoaDon(ChiTietHD_DichVu chiTietHoaDon) {
        int n = 0;
        ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO ChiTietHD_DichVu (IDHoaDon, IDDichVu, SoLuong) VALUES (?, ?, ?)";
        try {
            preparedStatement = conN.prepareStatement(sql);
            preparedStatement.setString(1, chiTietHoaDon.getHoaDon().getIdHoaDon());
            preparedStatement.setString(2, chiTietHoaDon.getDichVu().getIdDichVu());
            preparedStatement.setInt(3, chiTietHoaDon.getSoLuong());
            n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }
	public List<ChiTietHD_DichVu>  layChiTietHoaDonTheoMaHoaDon(String maHoaDon) {
        List<ChiTietHD_DichVu> danhSachChiTietHoaDon = new ArrayList<>();
        Connection connection = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM ChiTietHD_DichVu WHERE MaHD = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maHoaDon);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String IdDichVu = resultSet.getString("IDDichVu");
                int soLuongSP = resultSet.getInt("SoLuong");
                // Lấy thông tin của sản phẩm từ cơ sở dữ liệu
                DichVu dichvu = null;
                HoaDon_DAO dshd = new HoaDon_DAO();
                dshd.getAllHoaDon();
                HoaDon hd = dshd.layHoaDonTheoMaHoaDon(maHoaDon);
				try {
					DichVu_DAO dsDV = new DichVu_DAO();
					dichvu = dsDV.layDichVuTheoMa(IdDichVu);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                // Tạo đối tượng ChiTietHoaDon
                ChiTietHD_DichVu chiTietHoaDon = new ChiTietHD_DichVu(hd, dichvu, soLuongSP);
                danhSachChiTietHoaDon.add(chiTietHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachChiTietHoaDon;
    }
}
