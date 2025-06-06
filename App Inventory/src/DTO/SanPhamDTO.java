package DTO;

import java.util.Objects;

public class SanPhamDTO {

    private int masp;
    private String tensp;
    private String xuatxu;
    private String thuonghieu;

    public SanPhamDTO() {
    }

    public SanPhamDTO(int masp, String tensp, String xuatxu, String thuonghieu) {
        this.masp = masp;
        this.tensp = tensp;
        this.xuatxu = xuatxu;
        this.thuonghieu = thuonghieu;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }



    public String getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        this.xuatxu = xuatxu;
    }



    public String getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }


    @Override
    public int hashCode() {
        return Objects.hash(masp, tensp, xuatxu, thuonghieu);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SanPhamDTO other = (SanPhamDTO) obj;
        return masp == other.masp &&
               xuatxu == other.xuatxu &&
               thuonghieu == other.thuonghieu &&
               Objects.equals(tensp, other.tensp);

    }

    @Override
    public String toString() {
        return "SanPham{" +
                "masp=" + masp +
                ", tensp='" + tensp + '\'' +
                ", xuatxu=" + xuatxu +
                ", thuonghieu=" + thuonghieu +
                '}';
    }
}
