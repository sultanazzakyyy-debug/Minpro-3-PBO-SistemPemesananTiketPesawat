package model;

public class Penerbangan {

    private final String kodePenerbangan;
    private String asal;
    private String tujuan;
    private String jamKeberangkatan;
    private double harga;
    private int kursiTersedia;

    public Penerbangan(String kodePenerbangan, String asal, String tujuan, String jamKeberangkatan, double harga, int kursiTersedia) {
        this.kodePenerbangan = kodePenerbangan;
        setAsal(asal);
        setTujuan(tujuan);
        setJamKeberangkatan(jamKeberangkatan);
        setHarga(harga);
        this.kursiTersedia = kursiTersedia;
    }

    public String getKodePenerbangan() {
        return kodePenerbangan;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getJamKeberangkatan() {
        return jamKeberangkatan;
    }

    public void setJamKeberangkatan(String jamKeberangkatan) {
        this.jamKeberangkatan = jamKeberangkatan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga > 0) {
            this.harga = harga;
        }
    }

    public int getKursiTersedia() {
        return kursiTersedia;
    }

    public void kurangiKursi() {
        kursiTersedia = kursiTersedia - 1;
    }

    public void tambahKursi() {
        kursiTersedia = kursiTersedia + 1;
    }
}