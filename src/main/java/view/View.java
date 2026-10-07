package view;

import model.Penerbangan;
import model.Tiket;
import java.util.ArrayList;

public class View {

    public void tampilkanMenu() {
        System.out.println("");
        System.out.println("=== SISTEM PEMESANAN TIKET PESAWAT ===");
        System.out.println("1. Tambah Data Penerbangan");
        System.out.println("2. Lihat Jadwal Penerbangan");
        System.out.println("3. Pesan Tiket");
        System.out.println("4. Lihat Semua Tiket");
        System.out.println("5. Update Status Tiket");
        System.out.println("6. Batalkan Tiket");
        System.out.println("7. Keluar");
        System.out.print("Pilih menu: ");
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }

    public void tampilkanPesan(String label, String pesan) {
        System.out.println("[" + label + "] " + pesan);
    }

    public void tampilkanDaftarPenerbanganRingkas(ArrayList<Penerbangan> daftar) {
        for (int i = 0; i < daftar.size(); i++) {
            Penerbangan p = daftar.get(i);
            System.out.println((i + 1) + ". " + p.getKodePenerbangan() + " | " + p.getAsal() + " - " + p.getTujuan()
                    + " | " + p.getJamKeberangkatan() + " | Rp" + p.getHarga() + " | Sisa kursi: " + p.getKursiTersedia());
        }
    }

    public void tampilkanJadwalPenerbangan(ArrayList<Penerbangan> daftar) {
        if (daftar.size() == 0) {
            tampilkanPesan("Belum ada data penerbangan.");
        } else {
            for (int i = 0; i < daftar.size(); i++) {
                Penerbangan p = daftar.get(i);
                System.out.println("Penerbangan ke-" + (i + 1));
                System.out.println("Kode Penerbangan : " + p.getKodePenerbangan());
                System.out.println("Rute             : " + p.getAsal() + " - " + p.getTujuan());
                System.out.println("Jam Keberangkatan: " + p.getJamKeberangkatan());
                System.out.println("Harga Dasar      : Rp" + p.getHarga());
                System.out.println("Sisa Kursi       : " + p.getKursiTersedia());
                System.out.println("-----------------------------------");
            }
        }
    }

    public void tampilkanDaftarTiket(ArrayList<Tiket> daftar) {
        if (daftar.size() == 0) {
            tampilkanPesan("Belum ada data tiket.");
        } else {
            for (int i = 0; i < daftar.size(); i++) {
                System.out.println("Data ke-" + (i + 1));
                daftar.get(i).tampilkanInfo();
            }
        }
    }

    public void tampilkanDaftarTiketRingkas(ArrayList<Tiket> daftar) {
        if (daftar.size() == 0) {
            tampilkanPesan("Belum ada data tiket.");
        } else {
            for (int i = 0; i < daftar.size(); i++) {
                daftar.get(i).tampilkanInfo(true);
            }
        }
    }
}