package model;

public class Penumpang {

    private String nama;
    private String noKTP;
    private String noTelepon;

    public Penumpang(String nama, String noKTP, String noTelepon) {
        setNama(nama);
        setNoKTP(noKTP);
        setNoTelepon(noTelepon);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoKTP() {
        return noKTP;
    }

    public void setNoKTP(String noKTP) {
        this.noKTP = noKTP;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
}