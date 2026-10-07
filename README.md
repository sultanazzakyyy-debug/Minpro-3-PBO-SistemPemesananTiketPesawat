# Minpro 3 PBO - Sistem Pemesanan Tiket Pesawat

Nama : Muhammad Nadhir Sultan Azzaky

NIM : 2509116080

Kelas : Sistem Informasi 25'B

## 1. Deskripsi Singkat Program

Program ini adalah lanjutan dari Mini Project 2, berupa aplikasi berbasis *console* memakai Java untuk mengelola pemesanan tiket pesawat. Data yang dikelola ada tiga: data penumpang, data penerbangan, dan data tiket.

Setiap objek `Tiket` menyimpan langsung referensi ke objek `Penumpang` dan `Penerbangan` di dalamnya, jadi satu tiket selalu jelas terhubung ke penumpang dan penerbangan yang mana.

Program menyediakan fitur tambah data penerbangan, lihat jadwal penerbangan, pesan tiket (dengan pilihan kelas kursi), lihat seluruh tiket, ubah status tiket, dan batalkan tiket.

Pada Mini Project 3 ini, program dikembangkan dengan menerapkan:

- **Polymorphism** (*overriding* dan *overloading*)
- **Abstraction** (`abstract class` dan `abstract method`)
- **Struktur MVC** (Model, View, Controller)
- **Interface** (nilai tambah)

Selain itu konsep *encapsulation* dan *inheritance* dari Mini Project 2 tetap dipakai.

## 2. Penjelasan Struktur Package

Program disusun memakai struktur MVC dan dibagi menjadi empat package:

```text
src
├── main
│   └── SistemPemesananTiketPesawat.java
├── controller
│   └── Controller.java
├── model
│   ├── Cetak.java            (interface)
│   ├── Penumpang.java
│   ├── Penerbangan.java
│   ├── Tiket.java            (abstract class)
│   ├── TiketEkonomi.java
│   └── TiketBisnis.java
└── view
    └── View.java
```

| Package | Class | Peran |
|---|---|---|
| `main` | `SistemPemesananTiketPesawat` | Titik awal program, hanya membuat `Controller` dan menjalankannya |
| `model` | `Cetak` | Interface yang mewajibkan method `cetak()` |
| `model` | `Penumpang` | Menyimpan data diri penumpang (nama, no KTP, no telepon) |
| `model` | `Penerbangan` | Menyimpan data kode, rute, jam, harga dasar, dan sisa kursi |
| `model` | `Tiket` | *Abstract class* induk yang menggabungkan penumpang dan penerbangan |
| `model` | `TiketEkonomi` | Subclass `Tiket` untuk kelas Ekonomi |
| `model` | `TiketBisnis` | Subclass `Tiket` untuk kelas Bisnis |
| `view` | `View` | Menampilkan menu, pesan, dan daftar data ke layar |
| `controller` | `Controller` | Mengatur alur program, membaca dan memvalidasi input, serta mengolah data |

- **Model** berisi class yang berhubungan dengan data program.
- **View** hanya bertugas menampilkan output ke pengguna.
- **Controller** menjadi penghubung: membaca input user, memvalidasi, memanggil method di Model, lalu meminta View menampilkan hasilnya.

## 3. Penjelasan Alur Program

Program dimulai dari `SistemPemesananTiketPesawat.java` (package `main`) yang membuat objek `Controller` lalu memanggil `jalankanAplikasi()`. Method ini memanggil `isiDataAwal()` untuk mengisi data dummy (satu penerbangan dan satu tiket), kemudian menampilkan menu utama lewat `View.tampilkanMenu()` dengan 7 pilihan, dan terus berulang (`do-while`) sampai user memilih menu 7.

1. **Menu Awal**

<img width="223" height="109" alt="menu awal" src="https://github.com/user-attachments/assets/1f8f2f8b-fd29-4d7b-8193-001c396dd434" />

   Begitu program dijalankan, menu utama tampil dengan 7 pilihan. User mengetik angka pada baris `Pilih menu:`. Jika yang dimasukkan bukan angka atau tidak ada di menu, program menampilkan "Pilihan tidak tersedia!" dan menu ditampilkan lagi.

2. **Tambah Data Penerbangan**

<img width="299" height="110" alt="menu 1" src="https://github.com/user-attachments/assets/b62c50b6-2f68-43bb-ba51-34a33b01e22b" />

   User memasukkan kode penerbangan, kota asal, kota tujuan, jam keberangkatan, harga dasar, dan jumlah kursi. Setiap input divalidasi (kode tidak boleh kosong dan tidak boleh kembar, kota hanya huruf, jam berformat `HH.mm`, harga dan kursi harus lebih dari 0). Setelah valid, data dikirim ke `Controller.tambahPenerbangan()` dan dimasukkan ke `ArrayList<Penerbangan>`.

3. **Lihat Jadwal Penerbangan**

<img width="238" height="192" alt="menu 2" src="https://github.com/user-attachments/assets/1d6b1e16-870c-4ed6-b26b-28dbfb2a65ea" />

   Program mengirim seluruh isi `ArrayList<Penerbangan>` ke `View.tampilkanJadwalPenerbangan()`, yang menampilkan kode, rute, jam, harga dasar, dan sisa kursi tiap penerbangan.

4. **Pesan Tiket**

<img width="362" height="192" alt="menu 3 bisnis" src="https://github.com/user-attachments/assets/4b88c771-232d-4010-bcc6-e7f2922f1535" />
<img width="357" height="192" alt="menu 3 ekonomi" src="https://github.com/user-attachments/assets/66d2acd2-1af4-4f8f-9d53-32bcc54b2123" />

   User memilih nomor penerbangan dari daftar ringkas. Program mengecek dulu apakah kursi masih tersedia; jika habis, pemesanan ditolak. Jika masih ada, user mengisi nama, no KTP (16 digit), dan no telepon (10-13 digit), lalu memilih kelas kursi (1 = Ekonomi, 2 = Bisnis). `Controller.pesanTiket()` membuat objek `TiketEkonomi` atau `TiketBisnis`, memasukkannya ke `ArrayList<Tiket>`, dan mengurangi kursi lewat `kurangiKursi()`. ID tiket dibuat otomatis oleh program (format `TKT-1001`, `TKT-1002`, dst.). Total harga tiket Bisnis adalah harga dasar dikali 1.5.

5. **Lihat Semua Tiket**

<img width="360" height="412" alt="menu 4" src="https://github.com/user-attachments/assets/5dba8120-6c52-4694-b29b-655f940ced76" />

   Program memanggil `tampilkanInfo()` pada setiap objek di `ArrayList<Tiket>`. Tiket Ekonomi dan Bisnis tampil dengan label kategori dan info bagasi yang berbeda, lengkap dengan data penumpang, penerbangan, total harga, dan status.

6. **Update Status Tiket**

<img width="286" height="110" alt="menu 5" src="https://github.com/user-attachments/assets/174c2722-bd08-47ad-8a08-b557d63e7c62" />

   Program menampilkan daftar tiket ringkas, lalu user memasukkan ID tiket. Program mencarinya lewat `Controller.cariTiket()`. Jika ditemukan, user memasukkan status baru (harus `Dipesan`, `Lunas`, atau `Dibatalkan`), lalu status diperbarui lewat `updateStatusTiket()`.

7. **Batalkan Tiket**

<img width="251" height="96" alt="menu 6" src="https://github.com/user-attachments/assets/fd14f2e4-e101-4f91-9c4d-6b9fade260b0" />

   User memasukkan ID tiket yang ingin dibatalkan. Jika ditemukan, `Controller.batalkanTiket()` mengembalikan kursi lewat `tambahKursi()` dan menghapus tiket dari `ArrayList<Tiket>`. Sisa kursi penerbangan terkait bertambah 1.

8. **Keluar**

<img width="371" height="99" alt="menu 7" src="https://github.com/user-attachments/assets/84051e20-2a38-4398-83fa-65dc52f4a2eb" />

   Program mencetak "Terima kasih telah menggunakan sistem ini!", perulangan menu berhenti, dan program selesai.

Selama proses input di semua menu, program melakukan validasi memakai perulangan `do-while` di `Controller` (misalnya `inputHurufSaja()`, `inputNoKTP()`, `inputNoTelepon()`, `inputHargaValid()`, `inputAngkaPositif()`, `inputPilihanAngka()`, `inputJamValid()`, dan `inputStatusValid()`). Jika input tidak sesuai, pengguna diminta mengulang.

## 4. Penjelasan Penerapan Encapsulation dan Inheritance

### Encapsulation

Encapsulation diterapkan dengan menjadikan atribut di dalam class bertipe `private`, sehingga tidak bisa diakses langsung dari luar dan harus lewat getter/setter. Khusus `Tiket`, atribut `penumpang`, `penerbangan`, dan `statusTiket` memakai `protected` agar bisa dipakai subclass, sedangkan `idTiket` tetap `private final`.

Contoh pada `model/Penerbangan.java`:

<img width="275" height="109" alt="image" src="https://github.com/user-attachments/assets/bff7f650-3f6a-46db-bc52-52a5d667d9a9" />

Pada `setHarga()` ada validasi tambahan supaya harga yang tersimpan tidak pernah 0 atau negatif:

<img width="233" height="72" alt="image" src="https://github.com/user-attachments/assets/f2770bd0-6a75-4272-a6bb-aeb34d8ac623" />

Untuk jumlah kursi, program sengaja tidak menyediakan setter bebas. Perubahannya hanya boleh lewat `kurangiKursi()` dan `tambahKursi()`. Kode penerbangan dan ID tiket juga `final`, jadi tidak bisa diubah setelah objek dibuat.

### Inheritance

`Tiket` menjadi superclass, dan `TiketEkonomi` serta `TiketBisnis` menjadi subclass-nya memakai `extends`:

```text
Tiket (abstract)
├── TiketEkonomi
└── TiketBisnis
```

Constructor subclass memanggil `super(...)` untuk mengisi data yang diwarisi dari `Tiket`:

<img width="433" height="98" alt="image" src="https://github.com/user-attachments/assets/cbac093b-b74e-4f2a-b920-acd010622612" />

Kedua subclass mewarisi atribut (`idTiket`, `penumpang`, `penerbangan`, `statusTiket`) dan method (`getIdTiket()`, `getStatusTiket()`, `setStatusTiket()`, dll.) dari `Tiket`, lalu menambahkan atribut sendiri berupa `bagasiKg` (20 kg untuk Ekonomi, 30 kg untuk Bisnis).

## 5. Penjelasan Penerapan Polymorphism dan Abstraction

### Abstraction

Abstraction diterapkan lewat `abstract class Tiket` yang memiliki dua *abstract method*:

<img width="283" height="11" alt="image" src="https://github.com/user-attachments/assets/3057d8b9-8f4c-4674-afb2-b4ba2c56864d" />
<img width="281" height="47" alt="image" src="https://github.com/user-attachments/assets/1f8848d0-073c-418d-8fa3-3db0edcad5b7" />

`Tiket` tidak bisa dibuat objeknya secara langsung (`new Tiket(...)` akan error), karena "tiket" itu sendiri masih konsep umum. Yang bisa dibuat hanya jenis tiket yang konkret, yaitu `TiketEkonomi` atau `TiketBisnis`. Kedua subclass **wajib** mengimplementasikan `getNamaKelas()` dan `hitungTotalHarga()` dengan caranya masing-masing:

| Method | `TiketEkonomi` | `TiketBisnis` |
|---|---|---|
| `getNamaKelas()` | `"Ekonomi"` | `"Bisnis"` |
| `hitungTotalHarga()` | harga dasar | harga dasar × 1.5 |

Method biasa seperti `tampilkanInfo()` tetap ditulis lengkap di `Tiket` supaya bisa dipakai ulang oleh subclass.

### Polymorphism - Method Overriding

Method `tampilkanInfo()` di `Tiket` di-*override* oleh `TiketEkonomi` dan `TiketBisnis`, masing-masing menambahkan label kategori dan info bagasi sendiri, lalu memanggil `super.tampilkanInfo()` untuk bagian data umum.

Contoh pada `model/TiketBisnis.java`:

<img width="426" height="123" alt="image" src="https://github.com/user-attachments/assets/abeda43a-cafc-4c48-a4cf-c7c39f394529" />

Method `getNamaKelas()` dan `hitungTotalHarga()` juga di-*override* karena berasal dari abstract method.

Polymorphism terlihat jelas di `View.tampilkanDaftarTiket()`. Daftar bertipe `ArrayList<Tiket>` berisi campuran objek Ekonomi dan Bisnis, tetapi cukup memanggil `daftar.get(i).tampilkanInfo()`. Java otomatis memilih versi method sesuai jenis objek aslinya (*dynamic method dispatch*), sehingga View tidak perlu mengecek jenis tiket satu per satu.

Begitu juga di `Controller.pesanTiket()`, variabel bertipe `Tiket` dapat diisi `TiketBisnis` maupun `TiketEkonomi`.

### Polymorphism - Method Overloading

Ada dua contoh *overloading* pada program ini:

1. `View.tampilkanPesan(String pesan)` dan `View.tampilkanPesan(String label, String pesan)` — nama sama, parameter berbeda. Versi kedua menampilkan pesan dengan label, contohnya `[Label] pesan`.

<img width="345" height="94" alt="image" src="https://github.com/user-attachments/assets/202f4103-6177-458e-84a0-15652653039d" />

2. `Tiket.tampilkanInfo()` dan `Tiket.tampilkanInfo(boolean ringkas)` — versi tanpa parameter menampilkan info lengkap, sedangkan versi dengan parameter `true` menampilkan satu baris ringkas (dipakai di `View.tampilkanDaftarTiketRingkas()` saat memilih tiket untuk diubah atau dibatalkan).

<img width="1380" height="191" alt="image" src="https://github.com/user-attachments/assets/8bc6a7ff-9bf1-48b1-9f10-c03a89b40e3f" />

## 6. Penjelasan Letak Penerapan Nilai Tambah

### Interface

Interface diterapkan lewat `model/Cetak.java`:

<img width="157" height="38" alt="image" src="https://github.com/user-attachments/assets/b6c7fe29-53d0-4fbb-a87b-bdc451033909" />

Class `Tiket` meng-*implements* interface ini, sehingga wajib menyediakan method `cetak()`:

<img width="171" height="67" alt="image" src="https://github.com/user-attachments/assets/9f3ee961-2481-4f73-a8f5-8f2362b9d8c8" />

Karena `TiketEkonomi` dan `TiketBisnis` mewarisi `Tiket`, keduanya otomatis juga bertipe `Cetak`. Pemanggilan `cetak()` pada tiket Ekonomi atau Bisnis akan menjalankan `tampilkanInfo()` versi masing-masing (hasil *overriding*). Dengan interface, kemampuan "bisa dicetak" dipisahkan dari struktur pewarisan, jadi class lain di luar hierarki `Tiket` pun bisa memakainya di masa depan.

### Struktur MVC

Penjelasan lengkap ada di bagian **Penjelasan Struktur Package**. Pemisahan tugasnya:

<img width="227" height="201" alt="MVC" src="https://github.com/user-attachments/assets/a907f830-92ee-4675-90ae-026b91b596f2" />

- `Model` hanya menyimpan data dan aturan data (misalnya hitung harga, kurangi kursi).
- `View` hanya menampilkan output.
- `Controller` mengatur input, validasi, dan alur.

### Validasi Input dan Dummy Data

- **Validasi input** memakai `do-while` dan *regex* pada `Controller`, misalnya jam harus `HH.mm`, KTP 16 digit, telepon 10-13 digit, nama hanya huruf, dan kode penerbangan tidak boleh kembar.

<img width="304" height="48" alt="error handling kode penerbangan" src="https://github.com/user-attachments/assets/72edc3fb-5300-4d1c-95be-4fe80ef2a303" />

<img width="282" height="50" alt="error handling kota asal" src="https://github.com/user-attachments/assets/cc4fc239-3fbc-4dc6-a927-e237cded9d98" />

<img width="284" height="47" alt="error handling kota tujuan" src="https://github.com/user-attachments/assets/b099973e-3e18-468c-962d-656e4eefe5c9" />

<img width="295" height="51" alt="error handling jam keberangkatan" src="https://github.com/user-attachments/assets/c1167984-6c83-420e-9cf0-944c308e0744" />

<img width="213" height="23" alt="error handling harga dasar" src="https://github.com/user-attachments/assets/1b5a829b-ce24-487d-9383-0f53a59f2bc0" />

<img width="235" height="49" alt="error handling jumlah kursi" src="https://github.com/user-attachments/assets/4afcfa4b-0869-45c5-8224-d6d3fa961130" />

<img width="238" height="47" alt="error handling no ktp" src="https://github.com/user-attachments/assets/73cee71c-1c61-4e6b-a95d-231d6f86441e" />

<img width="225" height="45" alt="error handling no telepon" src="https://github.com/user-attachments/assets/05e13fd2-3259-4fe8-9cec-dad0603bd43f" />

<img width="240" height="85" alt="error handling pilih kelas kursi" src="https://github.com/user-attachments/assets/26e0f66a-6423-4b1c-b516-7b5154f136de" />

<img width="261" height="47" alt="error handling nama penumpang" src="https://github.com/user-attachments/assets/082e2f7a-58a9-4892-8481-8764d7157528" />

- **Dummy data** diisi lewat `isiDataAwal()` pada `Controller`: satu penerbangan `GA401` (Balikpapan - Jakarta, 08.00, Rp1.500.000, 10 kursi) dan satu tiket Ekonomi atas nama Sultan, sehingga menu lihat data, update, dan batal bisa langsung dicoba.

<img width="650" height="126" alt="image" src="https://github.com/user-attachments/assets/6f0a6fc8-93c3-41a7-ac08-15cb78f451b2" />

### Siklus Status Tiket

Status tiket dapat diubah antara `"Dipesan"`, `"Lunas"`, dan `"Dibatalkan"` lewat menu 5, dengan validasi agar hanya tiga nilai tersebut yang diterima.

<img width="289" height="23" alt="menu 5 status" src="https://github.com/user-attachments/assets/2998f959-550b-4ae0-8ee9-d9e29452f18d" />
