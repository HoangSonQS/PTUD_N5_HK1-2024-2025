package entity;

import java.time.LocalDate;
import java.util.Objects;

import dao.Enum_ChucVu;

public class NhanVien {

	private String idNhanVien;
	private String tenNhanVien;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private boolean gioiTinh;
	private String cccd;
	private Enum_ChucVu chucVu;
	
	
	public NhanVien(String idNhanVien, String tenNhanVien, String soDienThoai, LocalDate ngaySinh, boolean gioiTinh,
			String cccd, Enum_ChucVu chucVu) {
		super();
		this.idNhanVien = idNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.cccd = cccd;
		this.chucVu = chucVu;
	}
	public NhanVien(String idnhanvien) {
		this.idNhanVien = idnhanvien;
	}
	public String getIdNhanVien() {
		return idNhanVien;
	}
	public void setIdNhanVien(String idNhanVien) {
		this.idNhanVien = idNhanVien;
	}
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
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
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public Enum_ChucVu getChucVu() {
		return chucVu;
	}
	public void setChucVu(Enum_ChucVu chucVu) {
		this.chucVu = chucVu;
	}
	@Override
	public String toString() {
		return "NhanVien [idNhanVien=" + idNhanVien + ", tenNhanVien=" + tenNhanVien + ", soDienThoai=" + soDienThoai
				+ ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", cccd=" + cccd + ", chucVu=" + chucVu + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idNhanVien);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(idNhanVien, other.idNhanVien);
	}

	
}