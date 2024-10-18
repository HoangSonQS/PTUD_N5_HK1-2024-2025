package dao;

import java.time.LocalDate;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.DichVu;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;

public class testDao {
	
	public static void main(String[] args) {
		connect();
//		testTaiKhoan();
//		testNhanVien();
		testHoaDon();

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
	
//	done
	public static void testNhanVien() {
		LocalDate ngaySinh = LocalDate.of(2000, 1, 1);
		NhanVien_DAO nv = new NhanVien_DAO();
		
//		System.out.println(nv.getAllNhanVien());
		
//		System.out.println(nv.getNhanVienTheoMa("NV24100301"));
		
//		nv.themNhanVien(new NhanVien("NV001", "test", "0000000001", ngaySinh, false, "11111", ChucVu.NHANVIENLETAN));
		
//		nv.capNhatNhanVien(new NhanVien("NV001", "test", "0000000002", ngaySinh, false, "11111", ChucVu.NGUOIQUANLY));
		
		nv.xoaTheoMaNhanVien("NV001");
	}
	
	public static void testHoaDon() {
		HoaDon_DAO hddao = new HoaDon_DAO();
		System.out.println(hddao.getAllHoaDon());
	}

	
	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
}
