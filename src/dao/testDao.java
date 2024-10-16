package dao;

import connectDB.ConnectDB;
import entity.DichVu;
import entity.LoaiPhong;
import entity.Phong;
import entity.TrangThaiPhong;

public class testDao {
	
	public static void main(String[] args) {
		connect();
		testPhong();
	}
	
	//done
	public static void testPhong() {
		Phong_DAO pdao = new Phong_DAO();
//		System.out.println(pdao.getAllPhong());
//		System.out.println(pdao.getPhongTheoMa("T01P01"));
//		Phong p = new Phong("T02P01", LoaiPhong.PHONGDOI, 200000, TrangThaiPhong.SAPCHECKIN);
//		pdao.themPhong(p);
//		System.out.println(pdao.getPhongTheoLoai("Phòng đơn"));
	}
	
	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
}
