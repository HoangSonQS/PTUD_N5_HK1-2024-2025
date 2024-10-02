package java.entity;

public enum LoaiPhong {


	PHONGDON,
	PHONGDOI,
	PHONGGIADINH;

	private String loaiPhong;

	private LoaiPhong(String loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
	
	private LoaiPhong() {
		this("");
	}
	@Override
	public String toString() {
		return loaiPhong;
	}

}