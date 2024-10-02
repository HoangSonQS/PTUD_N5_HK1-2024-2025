package java.entity;

import java.time.LocalDate;
import java.util.Objects;

public class PhieuThuePhong {

	private String idPhieuThue;
	private KhachHang khachHang;
	private Phong phong;
	private NhanVien nhanVienLap;
	private LocalDate thoiGianNhanPhong;
	private LocalDate thoiHanGiaoPhong;
	public PhieuThuePhong(String idPhieuThue, KhachHang khachHang, Phong phong, NhanVien nhanVienLap,
			LocalDate thoiGianNhanPhong, LocalDate thoiHanGiaoPhong) {
		super();
		this.idPhieuThue = idPhieuThue;
		this.khachHang = khachHang;
		this.phong = phong;
		this.nhanVienLap = nhanVienLap;
		this.thoiGianNhanPhong = thoiGianNhanPhong;
		this.thoiHanGiaoPhong = thoiHanGiaoPhong;
	}
	public String getIdPhieuThue() {
		return idPhieuThue;
	}
	public void setIdPhieuThue(String idPhieuThue) {
		this.idPhieuThue = idPhieuThue;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public Phong getPhong() {
		return phong;
	}
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	public NhanVien getNhanVienLap() {
		return nhanVienLap;
	}
	public void setNhanVienLap(NhanVien nhanVienLap) {
		this.nhanVienLap = nhanVienLap;
	}
	public LocalDate getThoiGianNhanPhong() {
		return thoiGianNhanPhong;
	}
	public void setThoiGianNhanPhong(LocalDate thoiGianNhanPhong) {
		this.thoiGianNhanPhong = thoiGianNhanPhong;
	}
	public LocalDate getThoiHanGiaoPhong() {
		return thoiHanGiaoPhong;
	}
	public void setThoiHanGiaoPhong(LocalDate thoiHanGiaoPhong) {
		this.thoiHanGiaoPhong = thoiHanGiaoPhong;
	}
	@Override
	public String toString() {
		return "PhieuThuePhong [idPhieuThue=" + idPhieuThue + ", khachHang=" + khachHang + ", phong=" + phong
				+ ", nhanVienLap=" + nhanVienLap + ", thoiGianNhanPhong=" + thoiGianNhanPhong + ", thoiHanGiaoPhong="
				+ thoiHanGiaoPhong + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idPhieuThue);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuThuePhong other = (PhieuThuePhong) obj;
		return Objects.equals(idPhieuThue, other.idPhieuThue);
	}

	
}