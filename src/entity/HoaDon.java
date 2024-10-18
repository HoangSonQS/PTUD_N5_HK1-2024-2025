package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class HoaDon {

	private String idHoaDon;
	private NhanVien nhanVienLap;
	private KhachHang khachHang;
	private KhuyenMai khuyenmai;
	private LocalDateTime thoiGianTao;
	private LocalDateTime thoiGianCheckin;

	

	public HoaDon(String idHoaDon, NhanVien nhanVienLap, KhachHang khachHang, KhuyenMai khuyenmai,
			LocalDateTime thoiGianTao, LocalDateTime thoiGianCheckin) {
		super();
		this.idHoaDon = idHoaDon;
		this.nhanVienLap = nhanVienLap;
		this.khachHang = khachHang;
		this.khuyenmai = khuyenmai;
		this.thoiGianTao = thoiGianTao;
		this.thoiGianCheckin = thoiGianCheckin;
	}

	public String getIdHoaDon() {
		return idHoaDon;
	}

	public void setIdHoaDon(String idHoaDon) {
		this.idHoaDon = idHoaDon;
	}

	public NhanVien getNhanVienLap() {
		return nhanVienLap;
	}

	public void setNhanVienLap(NhanVien nhanVienLap) {
		this.nhanVienLap = nhanVienLap;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	
	public LocalDateTime getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao(LocalDateTime thoiGianTao) {
		this.thoiGianTao = thoiGianTao;
	}

	public LocalDateTime getThoiGianCheckin() {
		return thoiGianCheckin;
	}

	public void setThoiGianCheckin(LocalDateTime thoiGianCheckin) {
		this.thoiGianCheckin = thoiGianCheckin;
	}

	public KhuyenMai getKhuyenmai() {
		return khuyenmai;
	}

	public void setKhuyenmai(KhuyenMai khuyenmai) {
		this.khuyenmai = khuyenmai;
	}

	@Override
	public String toString() {
		return "\nHoaDon [idHoaDon=" + idHoaDon + ", nhanVienLap=" + nhanVienLap + ", khachHang=" + khachHang
				+ ", khuyenmai=" + khuyenmai + ", thoiGianTao=" + thoiGianTao + ", thoiGianCheckin=" + thoiGianCheckin
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idHoaDon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(idHoaDon, other.idHoaDon);
	}

	public void chietKhau() {
		// TODO - implement HoaDon.chietKhau
		throw new UnsupportedOperationException();
	}

	public void tongTien() {
		// TODO - implement HoaDon.tongTien
		throw new UnsupportedOperationException();
	}

	public void tinhThue() {
		// TODO - implement HoaDon.tinhThue
		throw new UnsupportedOperationException();
	}

	public void thanhTien() {
		// TODO - implement HoaDon.thanhTien
		throw new UnsupportedOperationException();
	}

}