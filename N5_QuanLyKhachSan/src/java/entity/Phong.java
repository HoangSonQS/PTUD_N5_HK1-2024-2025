package java.entity;

import java.util.Objects;

public class Phong {

	private String idPhong;
	private LoaiPhong loaiPhong;
	private double donGia;
	private int trangThai;
	public Phong(String idPhong, LoaiPhong loaiPhong, double donGia, int trangThai) {
		super();
		this.idPhong = idPhong;
		this.loaiPhong = loaiPhong;
		this.donGia = donGia;
		this.trangThai = trangThai;
	}
	public String getIdPhong() {
		return idPhong;
	}
	public void setIdPhong(String idPhong) {
		this.idPhong = idPhong;
	}
	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}
	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public String toString() {
		return "Phong [idPhong=" + idPhong + ", loaiPhong=" + loaiPhong + ", donGia=" + donGia + ", trangThai="
				+ trangThai + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idPhong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phong other = (Phong) obj;
		return Objects.equals(idPhong, other.idPhong);
	}

	
	
}