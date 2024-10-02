package java.entity;

public enum TrangThaiPhong {

	DANGTHUE,
	TRONG,
	SAPCHECKIN,
	SAPCHECKOUT;

	private String trangThaiPhong;

	private TrangThaiPhong(String trangThaiPhong) {
		this.trangThaiPhong = trangThaiPhong;
	}
	private TrangThaiPhong() {
		this("");
	}
	public String getTrangThaiPhong() {
		return trangThaiPhong;
	}
	
	@Override
	public String toString() {
		return trangThaiPhong;
	}
	
}