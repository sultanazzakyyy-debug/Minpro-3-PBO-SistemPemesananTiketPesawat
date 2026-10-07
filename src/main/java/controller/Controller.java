package controller;

import model.Penerbangan;
import model.Penumpang;
import model.Tiket;
import model.TiketEkonomi;
import model.TiketBisnis;
import view.View;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    private final Scanner input = new Scanner(System.in);
    private final View view = new View();
    private final ArrayList<Penerbangan> daftarPenerbangan;
    private final ArrayList<Tiket> daftarTiket;

    public Controller() {
        daftarPenerbangan = new ArrayList<>();
        daftarTiket = new ArrayList<>();
    }

    public void jalankanAplikasi() {
        isiDataAwal();

        int pilihan;

        do {
            view.tampilkanMenu();
            String teksPilihan = input.nextLine();
            try {
                pilihan = Integer.parseInt(teksPilihan);
            } catch (NumberFormatException e) {
                pilihan = -1;
            }

            switch (pilihan) {
                case 1 -> tambahPenerbanganDariInput();
                case 2 -> {
                    System.out.println("=== JADWAL PENERBANGAN ===");
                    view.tampilkanJadwalPenerbangan(daftarPenerbangan);
                }
                case 3 -> pesanTiketDariInput();
                case 4 -> {
                    System.out.println("=== DAFTAR TIKET ===");
                    view.tampilkanDaftarTiket(daftarTiket);
                }
                case 5 -> updateStatusTiketDariInput();
                case 6 -> batalkanTiketDariInput();
                case 7 -> view.tampilkanPesan("Terima kasih telah menggunakan sistem ini!");
                default -> view.tampilkanPesan("Pilihan tidak tersedia!");
            }

        } while (pilihan != 7);
    }

    private String inputTeksWajib(String label) {
        String hasil;
        do {
            System.out.print(label);
            hasil = input.nextLine();
            if (hasil.equals("")) {
                System.out.println("Input tidak boleh kosong!");
            }
        } while (hasil.equals(""));
        return hasil;
    }

    private String inputHurufSaja(String label, String namaField) {
        String hasil;
        boolean valid;
        do {
            System.out.print(label);
            hasil = input.nextLine();
            if (hasil.equals("")) {
                System.out.println(namaField + " tidak boleh kosong!");
                valid = false;
            } else if (!hasil.matches("[a-zA-Z\\s]+")) {
                System.out.println(namaField + " harus berupa huruf, tidak boleh angka/simbol!");
                valid = false;
            } else {
                valid = true;
            }
        } while (!valid);
        return hasil;
    }

    private String inputNoKTP(String label) {
        String hasil;
        do {
            System.out.print(label + " (16 digit, contoh: 6472011503990001): ");
            hasil = input.nextLine();
            if (!hasil.matches("\\d{16}")) {
                System.out.println("No KTP harus 16 digit angka!");
            }
        } while (!hasil.matches("\\d{16}"));
        return hasil;
    }

    private String inputNoTelepon(String label) {
        String hasil;
        do {
            System.out.print(label + " (contoh: 081234567890): ");
            hasil = input.nextLine();
            if (!hasil.matches("\\d{10,13}")) {
                System.out.println("No Telepon harus berupa angka, 10-13 digit!");
            }
        } while (!hasil.matches("\\d{10,13}"));
        return hasil;
    }

    private double inputHargaValid(String label) {
        double hasil = 0;
        boolean valid = false;
        do {
            System.out.print(label + " (contoh: 1500000): ");
            String teks = input.nextLine();
            try {
                hasil = Double.parseDouble(teks);
                if (hasil > 0) {
                    valid = true;
                } else {
                    System.out.println("Harga harus lebih dari 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Harga harus berupa angka, contoh: 1500000.");
            }
        } while (!valid);
        return hasil;
    }

    private int inputAngkaPositif(String label) {
        int hasil = 0;
        boolean valid = false;
        do {
            System.out.print(label);
            String teks = input.nextLine();
            try {
                hasil = Integer.parseInt(teks);
                if (hasil > 0) {
                    valid = true;
                } else {
                    System.out.println("Nilai harus lebih dari 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid, bukan huruf/simbol.");
            }
        } while (!valid);
        return hasil;
    }

    private int inputPilihanAngka(String label, int min, int max) {
        int hasil = 0;
        boolean valid = false;
        do {
            System.out.print(label);
            String teks = input.nextLine();
            try {
                hasil = Integer.parseInt(teks);
                if (hasil >= min && hasil <= max) {
                    valid = true;
                } else {
                    System.out.println("Pilihan harus di antara " + min + " dan " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid, bukan huruf/simbol.");
            }
        } while (!valid);
        return hasil;
    }

    private String inputJamValid(String label) {
        String jam;
        do {
            System.out.print(label + " (format jam.menit, contoh: 08.00): ");
            jam = input.nextLine();
            if (!jam.matches("([01]\\d|2[0-3])\\.[0-5]\\d")) {
                System.out.println("Format jam salah. Gunakan format seperti 08.00 atau 14.30.");
            }
        } while (!jam.matches("([01]\\d|2[0-3])\\.[0-5]\\d"));
        return jam;
    }

    private boolean kodePenerbanganSudahAda(String kode) {
        for (Penerbangan p : daftarPenerbangan) {
            if (p.getKodePenerbangan().equalsIgnoreCase(kode)) {
                return true;
            }
        }
        return false;
    }

    private String inputStatusValid(String label) {
        String status;
        do {
            System.out.print(label);
            status = input.nextLine();
            if (!status.equals("Dipesan") && !status.equals("Lunas") && !status.equals("Dibatalkan")) {
                System.out.println("Status tidak dikenali! Gunakan Dipesan/Lunas/Dibatalkan.");
            }
        } while (!status.equals("Dipesan") && !status.equals("Lunas") && !status.equals("Dibatalkan"));
        return status;
    }

    private void tambahPenerbanganDariInput() {
        System.out.println("=== TAMBAH DATA PENERBANGAN ===");

        String kodePenerbangan;
        do {
            kodePenerbangan = inputTeksWajib("Kode Penerbangan (Contoh: GA401): ");
            if (kodePenerbanganSudahAda(kodePenerbangan)) {
                System.out.println("Kode penerbangan \"" + kodePenerbangan + "\" sudah terdaftar, gunakan kode lain!");
            }
        } while (kodePenerbanganSudahAda(kodePenerbangan));

        String asal = inputHurufSaja("Kota Asal: ", "Nama kota");
        String tujuan = inputHurufSaja("Kota Tujuan: ", "Nama kota");
        String jam = inputJamValid("Jam Keberangkatan");
        double harga = inputHargaValid("Harga Dasar");
        int kursi = inputAngkaPositif("Jumlah Kursi: ");

        tambahPenerbangan(kodePenerbangan, asal, tujuan, jam, harga, kursi);
        view.tampilkanPesan("Data penerbangan berhasil ditambahkan!");
    }

    private void pesanTiketDariInput() {
        System.out.println("=== PESAN TIKET ===");

        if (daftarPenerbangan.isEmpty()) {
            view.tampilkanPesan("Belum ada penerbangan yang bisa dipesan.");
            return;
        }

        System.out.println("Daftar penerbangan yang tersedia:");
        view.tampilkanDaftarPenerbanganRingkas(daftarPenerbangan);

        int nomor;
        do {
            nomor = inputAngkaPositif("Pilih nomor penerbangan: ");
            if (ambilPenerbangan(nomor) == null) {
                System.out.println("Nomor penerbangan tidak tersedia!");
            }
        } while (ambilPenerbangan(nomor) == null);

        Penerbangan penerbanganDipilih = ambilPenerbangan(nomor);

        if (penerbanganDipilih.getKursiTersedia() == 0) {
            view.tampilkanPesan("Maaf, kursi untuk penerbangan ini sudah habis.");
            return;
        }

        String nama = inputHurufSaja("Nama Penumpang: ", "Nama");
        String noKTP = inputNoKTP("No KTP");
        String noTelepon = inputNoTelepon("No Telepon");

        Penumpang penumpang = new Penumpang(nama, noKTP, noTelepon);

        System.out.println("Pilih kelas kursi:");
        System.out.println("1. Ekonomi");
        System.out.println("2. Bisnis");
        int pilihKelas = inputPilihanAngka("Pilihan: ", 1, 2);

        Tiket tiket = pesanTiket(penerbanganDipilih, penumpang, pilihKelas);

        view.tampilkanPesan("Tiket berhasil dipesan! ID Tiket: " + tiket.getIdTiket());
        System.out.println("Total harga: Rp" + tiket.hitungTotalHarga());
        System.out.println("Sisa kursi penerbangan " + penerbanganDipilih.getKodePenerbangan()
                + " sekarang: " + penerbanganDipilih.getKursiTersedia());
    }

    private void updateStatusTiketDariInput() {
        System.out.println("Daftar tiket saat ini:");
        view.tampilkanDaftarTiketRingkas(daftarTiket);

        System.out.print("Masukkan ID Tiket yang ingin diubah statusnya: ");
        String idUbah = input.nextLine();
        Tiket tiketUbah = cariTiket(idUbah);

        if (tiketUbah == null) {
            view.tampilkanPesan("Tiket tidak ditemukan.");
            return;
        }

        System.out.println("Status saat ini: " + tiketUbah.getStatusTiket());
        String statusBaru = inputStatusValid("Status Baru (Dipesan/Lunas/Dibatalkan): ");

        updateStatusTiket(idUbah, statusBaru);
        view.tampilkanPesan("Status tiket berhasil diubah!");
    }

    private void batalkanTiketDariInput() {
        System.out.println("Daftar tiket saat ini:");
        view.tampilkanDaftarTiketRingkas(daftarTiket);

        System.out.print("Masukkan ID Tiket yang ingin dibatalkan: ");
        String idHapus = input.nextLine();
        Tiket tiketHapus = cariTiket(idHapus);

        if (tiketHapus == null) {
            view.tampilkanPesan("Tiket tidak ditemukan.");
            return;
        }

        String kodeTerkait = tiketHapus.getPenerbangan().getKodePenerbangan();
        batalkanTiket(idHapus);

        view.tampilkanPesan("Tiket berhasil dibatalkan.");
        System.out.println("Sisa kursi penerbangan " + kodeTerkait
                + " sekarang: " + tiketHapus.getPenerbangan().getKursiTersedia());
    }

    public void isiDataAwal() {
        Penerbangan penerbanganAwal = new Penerbangan("GA401", "Balikpapan", "Jakarta", "08.00", 1500000, 10);
        daftarPenerbangan.add(penerbanganAwal);

        Penumpang penumpangAwal = new Penumpang("Sultan", "1234567890", "081234567890");
        Tiket tiketAwal = new TiketEkonomi(penumpangAwal, penerbanganAwal);
        daftarTiket.add(tiketAwal);
        penerbanganAwal.kurangiKursi();
    }

    public ArrayList<Penerbangan> getDaftarPenerbangan() {
        return daftarPenerbangan;
    }

    public ArrayList<Tiket> getDaftarTiket() {
        return daftarTiket;
    }

    public void tambahPenerbangan(String kode, String asal, String tujuan, String jam, double harga, int kursi) {
        Penerbangan penerbangan = new Penerbangan(kode, asal, tujuan, jam, harga, kursi);
        daftarPenerbangan.add(penerbangan);
    }

    public Penerbangan ambilPenerbangan(int nomor) {
        if (nomor < 1 || nomor > daftarPenerbangan.size()) {
            return null;
        }
        return daftarPenerbangan.get(nomor - 1);
    }

    public Tiket pesanTiket(Penerbangan penerbangan, Penumpang penumpang, int pilihKelas) {
        Tiket tiket;

        if (pilihKelas == 2) {
            tiket = new TiketBisnis(penumpang, penerbangan);
        } else {
            tiket = new TiketEkonomi(penumpang, penerbangan);
        }

        daftarTiket.add(tiket);
        penerbangan.kurangiKursi();
        return tiket;
    }

    public Tiket cariTiket(String idTiket) {
        for (Tiket t : daftarTiket) {
            if (t.getIdTiket().equals(idTiket)) {
                return t;
            }
        }
        return null;
    }

    public boolean updateStatusTiket(String idTiket, String statusBaru) {
        Tiket t = cariTiket(idTiket);
        if (t == null) {
            return false;
        }
        t.setStatusTiket(statusBaru);
        return true;
    }

    public boolean batalkanTiket(String idTiket) {
        Tiket t = cariTiket(idTiket);
        if (t == null) {
            return false;
        }
        t.getPenerbangan().tambahKursi();
        daftarTiket.remove(t);
        return true;
    }
}