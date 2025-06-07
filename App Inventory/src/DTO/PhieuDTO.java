package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class PhieuDTO {
    private int maphieu;
    private int manguoitao;
    private Timestamp thoigiantao;
    private int tongsanpham;
    private int trangthai; // 0 = chưa xác nhận, 1 = đã xác nhận

    public PhieuDTO() {
    }

    public PhieuDTO(int maphieu, int manguoitao, Timestamp thoigiantao, int tongsanpham, int trangthai) {
        this.maphieu = maphieu;
        this.manguoitao = manguoitao;
        this.thoigiantao = thoigiantao;
        this.tongsanpham = tongsanpham;
        this.trangthai = trangthai;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public int getManguoitao() {
        return manguoitao;
    }

    public void setManguoitao(int manguoitao) {
        this.manguoitao = manguoitao;
    }

    public Timestamp getThoigiantao() {
        return thoigiantao;
    }

    public void setThoigiantao(Timestamp thoigiantao) {
        this.thoigiantao = thoigiantao;
    }

    public int getTongSP() {
        return tongsanpham;
    }

    public void setTongSP(int tongsanpham) {
        this.tongsanpham = tongsanpham;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.maphieu;
        hash = 59 * hash + this.manguoitao;
        hash = 59 * hash + Objects.hashCode(this.thoigiantao);
        hash = 59 * hash + this.tongsanpham;
        hash = 59 * hash + this.trangthai;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final PhieuDTO other = (PhieuDTO) obj;
        return this.maphieu == other.maphieu &&
               this.manguoitao == other.manguoitao &&
               this.tongsanpham == other.tongsanpham &&
               this.trangthai == other.trangthai &&
               Objects.equals(this.thoigiantao, other.thoigiantao);
    }

    @Override
    public String toString() {
        return "PhieuDTO{" +
               "maphieu=" + maphieu +
               ", manguoitao=" + manguoitao +
               ", thoigiantao=" + thoigiantao +
               ", tongsanpham=" + tongsanpham +
               ", trangthai=" + (trangthai == 1 ? "Đã xác nhận" : "Chưa xác nhận") +
               '}';
    }
}
