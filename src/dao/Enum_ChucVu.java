package dao;

public enum Enum_ChucVu {
	NHANVIENLETAN,
	NGUOIQUANLY;
	
	public String getChucVu() {
        switch (this) {
            case NHANVIENLETAN:
                return "Nhân viên quản lý";
            case NGUOIQUANLY:
                return "Người quản lý";
            default:
                return null;
        }
    }
}
