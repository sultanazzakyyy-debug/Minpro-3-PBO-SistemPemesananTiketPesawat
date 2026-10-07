package model;

public abstract class Tiket implements Cetak {

    private static int counter = 1000;

    private final String idTiket;
    protected Penumpang penumpang;
    protected Penerbangan penerbangan;
    protected String statusTiket;

    public Tiket(Penumpang penumpang, Penerbangan penerbangan) {
        counter++;
        this.idTiket = "TKT-" + counter;
        this.penumpang = penumpang;
        setPenerbangan(penerbangan);
        this.statusTiket = "Dipesan";
    }

    public String getIdTiket() {
        return idTiket;
    }

    public Penerbangan getPenerbangan() {
        return penerbangan;
    }

    public void setPenerbangan(Penerbangan penerbangan) {
        this.penerbangan = penerbangan;
    }

    public String getStatusTiket() {
        return statusTiket;
    }

    public void setStatusTiket(String statusTiket) {
        this.statusTiket = statusTiket;
    }

    public abstract String getNamaKelas();

    public abstract double hitungTotalHarga();

    public void tampilkanInfo() {
        System.out.println("ID Tiket         : " + idTiket);
        System.out.println("Nama Penumpang   : " + penumpang.getNama());
        System.out.println("No KTP           : " + penumpang.getNoKTP());
        System.out.println("No Telepon       : " + penumpang.getNoTelepon());
        System.out.println("Kode Penerbangan : " + penerbangan.getKodePenerbangan());
        System.out.println("Asal             : " + penerbangan.getAsal());
        System.out.println("Tujuan           : " + penerbangan.getTujuan());
        System.out.println("Jam Keberangkatan: " + penerbangan.getJamKeberangkatan());
        System.out.println("Kelas Kursi      : " + getNamaKelas());
        System.out.println("Total Harga      : Rp" + hitungTotalHarga());
        System.out.println("Status Tiket     : " + statusTiket);
    }

    public void tampilkanInfo(boolean ringkas) {
        if (ringkas) {
            System.out.println(idTiket + " - " + penumpang.getNama() + " (" + getNamaKelas() + ") - " + statusTiket);
        } else {
            tampilkanInfo();
        }
    }

    @Override
    public void cetak() {
        tampilkanInfo();
    }
}