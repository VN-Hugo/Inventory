package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class PhieuDTO {
    private int maphieu;
    private int manguoitao;
    private Timestamp thoigiantao;
    private int tongsanpham;

    public PhieuDTO() {
    }

    public PhieuDTO(int maphieu, int manguoitao, Timestamp thoigiantao, int tongsanpham) {
        this.maphieu = maphieu;
        this.manguoitao = manguoitao;
        this.thoigiantao = thoigiantao;
        this.tongsanpham = tongsanpham;
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


        @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.maphieu;
        hash = 59 * hash + this.manguoitao;
        hash = 59 * hash + Objects.hashCode(this.thoigiantao);
        hash = 59 * hash + (int) (this.tongsanpham ^ (this.tongsanpham >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhieuDTO other = (PhieuDTO) obj;
        if (this.maphieu != other.maphieu) {
            return false;
        }
        if (this.manguoitao != other.manguoitao) {
            return false;
        }
        if (this.tongsanpham != other.tongsanpham) {
            return false;
        }
        return Objects.equals(this.thoigiantao, other.thoigiantao);
    }

    @Override
    public String toString() {
        return "PhieuDTO{" + "maphieu=" + maphieu + ", manguoitao=" + manguoitao + ", thoigiantao=" + thoigiantao + ", tongsanpham=" + tongsanpham  + '}';
    }

    
}