package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class PhieuNhapDTO extends PhieuDTO {
    private int manhacungcap;

    public PhieuNhapDTO() {
        super();
    }

    public PhieuNhapDTO(int manhacungcap) {
        this.manhacungcap = manhacungcap;
    }

    public PhieuNhapDTO(int manhacungcap, int maphieu, int manguoitao, Timestamp thoigiantao, int tongsanpham, int trangthai) {
        super(maphieu, manguoitao, thoigiantao, tongsanpham, trangthai);
        this.manhacungcap = manhacungcap;
    }

    public int getManhacungcap() {
        return manhacungcap;
    }

    public void setManhacungcap(int manhacungcap) {
        this.manhacungcap = manhacungcap;
    }

    @Override
    public int hashCode() {
        return 67 * super.hashCode() + this.manhacungcap;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final PhieuNhapDTO other = (PhieuNhapDTO) obj;
        return this.manhacungcap == other.manhacungcap;
    }

    @Override
    public String toString() {
        return "PhieuNhapDTO{" +
               "manhacungcap=" + manhacungcap +
               "} " + super.toString();
    }
}
