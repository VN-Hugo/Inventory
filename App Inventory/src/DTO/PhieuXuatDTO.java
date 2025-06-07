package DTO;

import java.sql.Timestamp;

public class PhieuXuatDTO extends PhieuDTO {
    private int makh;

    public PhieuXuatDTO() {
        super();
    }

    public PhieuXuatDTO(int makh) {
        this.makh = makh;
    }

    public PhieuXuatDTO(int makh, int maphieu, int manguoitao, Timestamp thoigiantao, int tongsanpham, int trangthai) {
        super(maphieu, manguoitao, thoigiantao, tongsanpham, trangthai);
        this.makh = makh;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    @Override
    public int hashCode() {
        return 23 * super.hashCode() + this.makh;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final PhieuXuatDTO other = (PhieuXuatDTO) obj;
        return this.makh == other.makh;
    }

    @Override
    public String toString() {
        return "PhieuXuatDTO{" +
               "makh=" + makh +
               "} " + super.toString();
    }
}
