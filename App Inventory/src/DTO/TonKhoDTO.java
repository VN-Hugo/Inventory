package DTO;

public class TonKhoDTO {

    private String maSanPham;
    private String maKhuVuc;
    private int soLuongTon;

    // Constructor không tham số
    public TonKhoDTO() {
    }

    // Constructor đầy đủ
    public TonKhoDTO(String maSanPham, String maKhuVuc, int soLuongTon) {
        this.maSanPham = maSanPham;
        this.maKhuVuc = maKhuVuc;
        this.soLuongTon = soLuongTon;
    }

    // Getter và Setter
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

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    // toString
    @Override
    public String toString() {
        return "TonKhoDTO{" +
                "maSanPham='" + maSanPham + '\'' +
                ", maKhuVuc='" + maKhuVuc + '\'' +
                ", soLuongTon=" + soLuongTon +
                '}';
    }
}
