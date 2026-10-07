# Minpro 3 PBO - Sistem Pemesanan Tiket Pesawat

Nama : Muhammad Nadhir Sultan Azzaky

NIM : 2509116080

Kelas : Sistem Informasi 25'B

## 1. Deskripsi Singkat Program

Program ini adalah lanjutan dari Mini Project 2, masih berupa aplikasi berbasis *console* memakai Java untuk mengelola pemesanan tiket pesawat. Data yang dikelola tetap tiga: data penumpang, data penerbangan, dan data tiket, dengan tiap objek `Tiket` menyimpan langsung referensi ke `Penumpang` dan `Penerbangan` di dalamnya.

Program menyediakan fitur tambah data penerbangan, lihat jadwal penerbangan, pesan tiket (dengan pilihan kelas kursi), lihat seluruh tiket, ubah status tiket, dan batalkan tiket. Di Mini Project 3 ini, selain tetap mempertahankan *encapsulation* dan *inheritance* dari Mini Project 2, program ditambah dengan *abstraction* (class `Tiket` jadi *abstract class*), *polymorphism* lewat *overloading* (selain *overriding* yang sudah ada), tetap disusun dengan struktur MVC (Model-View-Controller), dan ditambah satu *interface* (`Cetak`) sebagai nilai tambah.

Program ini juga memperbaiki beberapa catatan dari asisten lab di Mini Project 2 — penjelasannya ada di bagian akhir README ini.

## 2. Penjelasan Struktur Package

| Class / Interface | Package | Peran |
|---|---|---|
| `Penumpang` | `model` | Menyimpan data diri penumpang |
| `Penerbangan` | `model` | Menyimpan data jadwal, harga, dan kursi penerbangan |
| `Tiket` | `model` | **Abstract class**, superclass yang menggabungkan data penumpang dan penerbangan jadi satu tiket, sekaligus meng-*implements* interface `Cetak` |
| `TiketEkonomi` | `model` | Subclass dari `Tiket` untuk tiket kelas Ekonomi |
| `TiketBisnis` | `model` | Subclass dari `Tiket` untuk tiket kelas Bisnis |
| `Cetak` | `model` | Interface nilai tambah, berisi satu method `cetak()` |
| `View` | `view` | Menampilkan menu dan pesan ke layar |
| `Controller` | `controller` | Mengatur alur menu dan proses pengolahan data |
| `SistemPemesananTiketPesawat` | `main` | Titik masuk aplikasi |

Pembagian MVC-nya sama seperti Mini Project 2: **Model** (`model/`) menyimpan data dan aturan bisnis, **View** (`view/`) menampilkan menu dan pesan ke layar, **Controller** (`controller/`) mengatur seluruh alur program dan menghubungkan Model dengan View.

## 3. Penjelasan Alur Program

Program dimulai dari `SistemPemesananTiketPesawat.java` (package `main`). Berbeda dari Mini Project 2, class ini sekarang hanya memanggil satu method, `new Controller().jalankanAplikasi()` — seluruh alur menu, termasuk pembuatan `Scanner` dan `View`, sekarang dikelola di dalam `Controller`.

Begitu `jalankanAplikasi()` berjalan, program memanggil `isiDataAwal()` untuk menyiapkan `ArrayList` dan mengisi satu data dummy (satu penerbangan dan satu tiket) sebagai data awal. Program lalu menampilkan menu utama dengan 7 pilihan, sama seperti sebelumnya.

*(Screenshot: tampilan menu utama)*

1. **Tambah Data Penerbangan**

   User memasukkan kode penerbangan, kota asal, kota tujuan, jam keberangkatan, harga dasar, dan jumlah kursi. Validasinya diperkuat dibanding Mini Project 2:
   - Kode penerbangan ditolak kalau sudah pernah dipakai sebelumnya.
   - Kota asal dan kota tujuan harus berupa huruf (ditolak kalau diisi angka/simbol).
   - Jam keberangkatan harus mengikuti format jam.menit, dengan contoh `08.00` langsung di prompt-nya.
   - Harga dasar dan jumlah kursi memakai *try-catch*, jadi tidak *crash* kalau user memasukkan huruf.

   *(Screenshot: proses tambah penerbangan, termasuk saat validasi menolak input salah)*

2. **Lihat Jadwal Penerbangan**

   Program mengambil seluruh isi `ArrayList<Penerbangan>` dan menampilkannya lewat `View`, lengkap dengan kode, rute, jam, harga dasar, dan sisa kursi tiap penerbangan.

   *(Screenshot: daftar jadwal penerbangan)*

3. **Pesan Tiket**

   User memilih nomor penerbangan dari daftar ringkas yang ditampilkan. Program mengecek dulu apakah kursi penerbangan itu masih tersedia — kalau sudah habis, program menolak dan tidak lanjut ke input tiket.

   Kalau masih ada kursi, user mengisi data penumpang (nama harus huruf, No KTP harus 16 digit angka, No Telepon harus 10-13 digit angka, semuanya dengan contoh format di prompt), lalu memilih kelas kursi (1 = Ekonomi, 2 = Bisnis). Berbeda dari Mini Project 2, **ID tiket sekarang dibuat otomatis oleh sistem** (format `TKT-1001`, dst), tidak lagi diminta manual dari user.

   Berdasarkan kelas yang dipilih, `Controller.pesanTiket()` membuat objek `TiketEkonomi` atau `TiketBisnis`, memasukkannya ke `ArrayList<Tiket>`, dan mengurangi kursi penerbangan terkait lewat `kurangiKursi()`. Total harga tiket bisnis otomatis lebih besar (dikali 1.5) dibanding tiket ekonomi.

   *(Screenshot: proses pesan tiket, termasuk hasil e-tiket dengan ID otomatis)*

4. **Lihat Semua Tiket**

   Program menampilkan detail seluruh tiket yang ada di `ArrayList<Tiket>`, termasuk data penumpang, data penerbangan, kelas kursi, total harga, dan status tiket. Tiket ekonomi dan bisnis tampil dengan label kategori serta info bagasi yang berbeda.

   *(Screenshot: daftar semua tiket)*

5. **Update Status Tiket**

   User memasukkan ID tiket yang ingin diubah statusnya. Program mencarinya lewat `Controller.cariTiket()`. Jika ditemukan, user memasukkan status baru (harus salah satu dari "Dipesan", "Lunas", "Dibatalkan"), lalu status tiket diperbarui.

   *(Screenshot: proses update status tiket)*

6. **Batalkan Tiket**

   User memasukkan ID tiket yang ingin dibatalkan. Jika ditemukan, tiket dihapus dari `ArrayList<Tiket>` lewat `Controller.batalkanTiket()`, dan kursi penerbangan yang terkait dikembalikan lewat `tambahKursi()`.

   *(Screenshot: proses batalkan tiket dan sisa kursi yang bertambah kembali)*

7. **Keluar**

   User memilih menu 7, program mencetak pesan penutup "Terima kasih telah menggunakan sistem ini!", perulangan menu dihentikan, dan program selesai dijalankan.

Selama proses input di semua menu, program melakukan validasi untuk memastikan data yang dimasukkan sesuai ketentuan. Jika input tidak sesuai, pengguna diminta memasukkan kembali data tersebut lewat perulangan `do-while`, sama seperti pola di Mini Project 2 — hanya saja sekarang logikanya dikumpulkan jadi beberapa method validasi privat di `Controller` (`inputTeksWajib`, `inputHurufSaja`, `inputHargaValid`, `inputAngkaPositif`, `inputPilihanAngka`, `inputJamValid`, `inputStatusValid`, `inputNoKTP`, `inputNoTelepon`) supaya tidak ada kode validasi yang ditulis ulang di banyak tempat.

### Cara Menjalankan

1. Buka project di NetBeans (atau IDE Java lain yang mendukung Maven).
2. Pastikan **Main Class** pada properti project mengarah ke `main.SistemPemesananTiketPesawat`.
3. Jalankan project (Run Project / F6), lalu ikuti menu di konsol.

## 4. Penjelasan Penerapan Encapsulation dan Inheritance

### Encapsulation

Sama seperti Mini Project 2, encapsulation diterapkan dengan menjadikan atribut di dalam class bertipe `private` (khusus `Tiket` pakai `protected` supaya bisa langsung diwariskan ke subclass-nya). Atribut tidak bisa diakses langsung dari luar class, harus lewat getter dan setter. Khusus `Penerbangan.setHarga()`, ada validasi tambahan supaya harga yang tersimpan tidak pernah 0 atau negatif:

```java
public void setHarga(double harga) {
    if (harga > 0) {
        this.harga = harga;
    }
}
```

Jumlah kursi juga sengaja tidak punya setter bebas — perubahannya hanya boleh lewat method `kurangiKursi()` dan `tambahKursi()`.

### Inheritance

Inheritance tetap diterapkan dengan `Tiket` sebagai superclass, dan `TiketEkonomi` beserta `TiketBisnis` sebagai subclass-nya:

```text
Tiket  (abstract)
├── TiketEkonomi
└── TiketBisnis
```

Kedua subclass memakai `extends` dan memanggil `super(...)` di constructor-nya untuk mengisi bagian data yang diwarisi dari `Tiket` (`idTiket`, `penumpang`, `penerbangan`, `statusTiket`), lalu menambahkan atribut sendiri berupa `bagasiKg` (20 kg untuk Ekonomi, 30 kg untuk Bisnis).

## 5. Penjelasan Penerapan Polymorphism dan Abstraction

### Abstraction

Yang baru di Mini Project 3: `Tiket` sekarang jadi **abstract class** dengan dua **abstract method**, `getNamaKelas()` dan `hitungTotalHarga()`. Karena abstract, `Tiket` tidak bisa diinstansiasi langsung — harus lewat `TiketEkonomi` atau `TiketBisnis`, dan kedua subclass itu wajib mengisi sendiri implementasi dua method tersebut.

```java
public abstract class Tiket implements Cetak {
    ...
    public abstract String getNamaKelas();
    public abstract double hitungTotalHarga();
}
```

### Polymorphism - Method Overriding

Sama seperti Mini Project 2, `TiketEkonomi` dan `TiketBisnis` meng-*override* `tampilkanInfo()` untuk menampilkan label kategori dan info bagasi yang berbeda. Sekarang keduanya juga wajib meng-*override* `getNamaKelas()` dan `hitungTotalHarga()` karena method itu abstrak di `Tiket`. `TiketBisnis` mengalikan harga dasar 1.5, sedangkan `TiketEkonomi` memakai harga dasar apa adanya.

### Polymorphism - Method Overloading

Ada dua contoh *overloading* yang dipertahankan dari Mini Project 2:

- `View.tampilkanPesan(String pesan)` versus `View.tampilkanPesan(String label, String pesan)` untuk format pesan biasa atau pesan dengan label seperti `[VALIDASI]`.
- `Tiket.tampilkanInfo()` versus `Tiket.tampilkanInfo(boolean ringkas)` untuk memilih tampilan lengkap atau ringkas.

## 6. Penjelasan Letak Penerapan Nilai Tambah (Interface)

Nilai tambah yang diterapkan di Mini Project 3 ini adalah **interface**, lewat `model/Cetak.java`:

```java
public interface Cetak {
    void cetak();
}
```

Class `Tiket` meng-*implements* interface ini, dengan method `cetak()` yang memanggil `tampilkanInfo()`:

```java
public abstract class Tiket implements Cetak {
    ...
    @Override
    public void cetak() {
        tampilkanInfo();
    }
}
```

Karena `TiketEkonomi` dan `TiketBisnis` adalah subclass dari `Tiket`, keduanya otomatis ikut mewarisi implementasi interface `Cetak` ini tanpa perlu menulis ulang kode apa pun.

## Perbaikan dari Koreksi Mini Project 2

Beberapa catatan dari asisten lab di Mini Project 2 yang diperbaiki di Mini Project 3 ini:

- Nama class diubah ke PascalCase: `Tiketekonomi` → `TiketEkonomi`, `Tiketbisnis` → `TiketBisnis`.
- Validasi input yang tadinya berulang-ulang di `main`, sekarang dirapikan jadi method privat di `Controller` (lihat daftar method-nya di bagian Alur Program).
- `Main` sekarang hanya memanggil satu method untuk menjalankan seluruh program: `new Controller().jalankanAplikasi()`.
- Validasi diperkuat: input harga, jumlah kursi, dan pilihan menu memakai *try-catch* supaya tidak *error*/*crash* kalau user memasukkan huruf.
- Input jam keberangkatan sekarang diberi contoh format (`08.00`) langsung di prompt-nya.
- ID tiket dibuat otomatis oleh sistem, tidak lagi diminta manual dari user.
