package DTO;

public class ChiTietPhieuXuatDTO {
    private int maPhieuXuat;
    private int maSanPham;
    private int soLuong;
    private int makho;
    public ChiTietPhieuXuatDTO() {
    }

    public ChiTietPhieuXuatDTO(int maPhieuXuat, int maSanPham, int soLuong,int makho) {
        this.maPhieuXuat = maPhieuXuat;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.makho = makho;
    }

    public int getMaPhieuXuat() {
        return maPhieuXuat;
    }

    public void setMaPhieuXuat(int maPhieuXuat) {
        this.maPhieuXuat = maPhieuXuat;
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
        return "ChiTietPhieuXuatDTO{" +
                "maPhieuXuat=" + maPhieuXuat +
                ", maSanPham=" + maSanPham +
                ", soLuong=" + soLuong +
                ", makho=" + makho +
                '}';
    }
}
