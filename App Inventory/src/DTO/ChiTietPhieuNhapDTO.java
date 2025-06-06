package DTO;

public class ChiTietPhieuNhapDTO {
    private int maPhieuNhap;
    private int maSanPham;
    private int soLuong;
    private int makho;
    public ChiTietPhieuNhapDTO() {
    }

    public ChiTietPhieuNhapDTO(int maPhieuNhap, int maSanPham, int soLuong, int makho) {
        this.maPhieuNhap = maPhieuNhap;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.makho= makho;
    }

    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public int getMaKho() {
        return makho;
    }

    public void setMaKho(int makho) {
        this.makho = makho;
    }
    @Override
    public String toString() {
        return "ChiTietPhieuNhapDTO{" +
                "maPhieuNhap=" + maPhieuNhap +
                ", maSanPham=" + maSanPham +
                ", soLuong=" + soLuong +
                ", makho=" + makho +
                '}';
    }
}
