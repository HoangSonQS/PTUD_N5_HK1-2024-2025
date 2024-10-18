package dao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import connectDB.ConnectDB;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;

public class testDao {
	
	public static void main(String[] args) {
		connect();
//		testTaiKhoan();
//		testNhanVien();
//		testPhieuThue();
//		testKH();
		testHoaDon();
	}
	
	//done
	public static void testPhong() {
//		Phong_DAO pdao = new Phong_DAO();
//		System.out.println(pdao.getAllPhong());
//		System.out.println(pdao.getPhongTheoMa("T01P01"));
//		Phong p = new Phong("T02P01", LoaiPhong.PHONGDOI, 200000, TrangThaiPhong.SAPCHECKIN);
//		pdao.themPhong(p);
//		System.out.println(pdao.getPhongTheoLoai("Phòng đơn"));
	}
//done
	public static void testTaiKhoan() {
//		TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
//		NhanVien_DAO nvdao = new NhanVien_DAO();
//		NhanVien nvVien = nvdao.getNhanVienTheoMa("NV24100301");
//		TaiKhoan tk1 = new TaiKhoan("NV0303", "12", "nghi viec",nvVien);
//		tkdao.capNhatTaiKhoan(tk1);
//		
	}
	
	public static void testNhanVien() {
		NhanVien_DAO nv = new NhanVien_DAO();
		System.out.println(nv.getNhanVienTheoMa("NV24100301"));
	}
	public static void testPhieuThue() {
//		PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
//		
//		KhachHang_DAO dsKH = new KhachHang_DAO();
//		KhachHang kh1 = dsKH.getKhachHangTheoMa("KH24100301");
//		
//		Phong_DAO dsP = new Phong_DAO();
//		Phong p1 = dsP.getPhongTheoMa("T0P04");
//		
//		NhanVien_DAO nvdao = new NhanVien_DAO();
//		NhanVien nvVien = nvdao.getNhanVienTheoMa("NV24100301");
//		
//		PhieuThuePhong pt1 = new PhieuThuePhong("PT241003002", kh1, p1, nvVien, LocalDate.of(2019, 6, 24) , LocalDate.of(2024, 6, 24));
////		dsPT.themPhieuThue(pt1);
//		
//		System.out.println(dsPT.getAllPhieuThue());
		
	}
//done
	public static void testKH() {
//		KhachHang_DAO dsKH = new KhachHang_DAO();
//		
//		KhachHang kh1 = new KhachHang("KH24100305", "Nguyen B", "012345678", LocalDate.of(2019, 6, 24), "1123123", 0);
//		dsKH.themKhachHang(kh1);
//		dsKH.capNhatKhachHangTheoSDT(kh1);
//		System.out.println(dsKH.getAllKhachHang());
	}
	
	//done
	public static void testHoaDon() {
	    HoaDon_DAO hddao = new HoaDon_DAO();
//	    LocalDateTime ngayGio = LocalDateTime.now(); // Thời gian hiện tại
//	    LocalDateTime ngayGio2 = LocalDateTime.of(2024, 10, 20, 14, 30); // Ngày 20/10/2024 lúc 14:30
//
//	    NhanVien nv = new NhanVien("NV24100301");
//	    KhachHang kh = new KhachHang("KH24100301");
//	    KhuyenMai km = new KhuyenMai("KM241001");
//
//	    // Tạo đối tượng HoaDon
//	    HoaDon hd = new HoaDon("HD002", nv, kh, km, ngayGio, ngayGio2);
//
//	    // Thêm hóa đơn vào cơ sở dữ liệu
//	    hddao.themHoaDon(hd);
	    
//	    System.out.println(hddao.layHoaDonTheoMaHoaDon("HD001"));
//	    System.out.println(hddao.layHoaDonTheoMaKhachHang("KH24100301"));
	    System.out.println(hddao.getAllHoaDon());

	}

	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
}