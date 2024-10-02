package entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhachHang {

	private String idKhachHang;
	private String tenKhachHang;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private String cccd;
	private int tichDiem;
	public KhachHang(String idKhachHang, String tenKhachHang, String soDienThoai, LocalDate ngaySinh, String cccd,
			int tichDiem) {
		super();
		this.idKhachHang = idKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.cccd = cccd;
		this.tichDiem = tichDiem;
	}
	public String getIdKhachHang() {
		return idKhachHang;
	}
	public void setIdKhachHang(String idKhachHang) {
		this.idKhachHang = idKhachHang;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public int getTichDiem() {
		return tichDiem;
	}
	public void setTichDiem(int tichDiem) {
		this.tichDiem = tichDiem;
	}
	@Override
	public String toString() {
		return "KhachHang [idKhachHang=" + idKhachHang + ", tenKhachHang=" + tenKhachHang + ", soDienThoai="
				+ soDienThoai + ", ngaySinh=" + ngaySinh + ", cccd=" + cccd + ", tichDiem=" + tichDiem + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idKhachHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(idKhachHang, other.idKhachHang);
	}

	


}