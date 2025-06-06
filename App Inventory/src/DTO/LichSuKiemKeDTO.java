package DTO;

import java.util.Date;

public class LichSuKiemKeDTO {
    private String maSanPham;
    private String maKhuVuc;
    private int soLuongHeThong;
    private int soLuongThucTe;
    private int chenhLech;
    private Date ngayKiemKe;
    private String nguoiKiemKe;

    public LichSuKiemKeDTO() {
    }

    public LichSuKiemKeDTO(String maSanPham, String maKhuVuc, int soLuongHeThong, int soLuongThucTe, int chenhLech, Date ngayKiemKe, String nguoiKiemKe) {
        this.maSanPham = maSanPham;
        this.maKhuVuc = maKhuVuc;
        this.soLuongHeThong = soLuongHeThong;
        this.soLuongThucTe = soLuongThucTe;
        this.chenhLech = chenhLech;
        this.ngayKiemKe = ngayKiemKe;
        this.nguoiKiemKe = nguoiKiemKe;
    }

    // Getters và Setters
    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public int getSoLuongHeThong() {
        return soLuongHeThong;
    }

    public void setSoLuongHeThong(int soLuongHeThong) {
        this.soLuongHeThong = soLuongHeThong;
    }

    public int getSoLuongThucTe() {
        return soLuongThucTe;
    }

    public void setSoLuongThucTe(int soLuongThucTe) {
        this.soLuongThucTe = soLuongThucTe;
    }

    public int getChenhLech() {
        return chenhLech;
    }

    public void setChenhLech(int chenhLech) {
        this.chenhLech = chenhLech;
    }

    public Date getNgayKiemKe() {
        return ngayKiemKe;
    }

    public void setNgayKiemKe(Date ngayKiemKe) {
        this.ngayKiemKe = ngayKiemKe;
    }

    public String getNguoiKiemKe() {
        return nguoiKiemKe;
    }

    public void setNguoiKiemKe(String nguoiKiemKe) {
        this.nguoiKiemKe = nguoiKiemKe;
    }
}
