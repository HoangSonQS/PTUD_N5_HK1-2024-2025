package entity;

public enum ChucVu {
	

	NHANVIENLETAN,
	NGUOIQUANLY;

	private String chucVu;

	private ChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	private ChucVu() {
		this("");
	}
	public String getchucVu() {
		return chucVu;
	}
	
	@Override
	public String toString() {
		return chucVu;
	}
}