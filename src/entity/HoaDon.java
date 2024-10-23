package entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import dao.HoaDon_DAO;

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
	

	public HoaDon(String idHoaDon) {
		super();
		this.idHoaDon = idHoaDon;
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
	
	public static String autoIdHoaDon() {
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO(); // Đối tượng DAO để truy xuất dữ liệu từ database
        ArrayList<HoaDon> hoaDonList = null;

        try {
            hoaDonList = hoaDonDAO.getAllHoaDon(); // Lấy danh sách hóa đơn từ database
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Hoặc trả về giá trị mặc định nếu lỗi xảy ra
        }


        if (hoaDonList == null || hoaDonList.isEmpty()) {
            return "HD24100101"; // Hoặc giá trị mặc định khác nếu không có hóa đơn nào
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateString = currentDate.format(formatter);


        int max = 0;
        for (HoaDon hd : hoaDonList) {
            String idHD = hd.getIdHoaDon();
            if (idHD.startsWith("HD" + dateString)) {
                String suffix = idHD.substring(idHD.length() - 2); // Lấy 2 ký tự cuối
                try {
                    int suffixInt = Integer.parseInt(suffix);
                    if (suffixInt > max) {
                        max = suffixInt;
                    }
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp suffix không phải là số nguyên
                    System.err.println("Lỗi định dạng ID hóa đơn: " + suffix);
                    // Xử lý lỗi phù hợp. Ví dụ, trả về null hoặc giá trị mặc định
                    return null;
                }
            }
        }

        int nextId = max + 1;
        String formattedNextId = new DecimalFormat("00").format(nextId); // Định dạng 4 số
        return "HD" + dateString + formattedNextId;
    }

}