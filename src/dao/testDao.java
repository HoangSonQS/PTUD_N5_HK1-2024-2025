package dao;

import connectDB.ConnectDB;
import entity.DichVu;
import entity.LoaiPhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;

public class testDao {
	
	public static void main(String[] args) {
		connect();
//		testTaiKhoan();
		testNhanVien();
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
	
	public static void testTaiKhoan() {
		TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
		System.out.println(tkdao.getAllTaiKhoan());
	}
	
	public static void testNhanVien() {
		NhanVien_DAO nv = new NhanVien_DAO();
		System.out.println(nv.getNhanVienTheoMa("NV24100301"));
	}
	
	
	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
}
