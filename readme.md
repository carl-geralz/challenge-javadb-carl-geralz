# Aplikasi Transaksi JDBC

Aplikasi konsol sederhana ini menyediakan fungsionalitas dasar untuk mengelola data transaksi menggunakan JDBC (Java Database Connectivity) dan HikariCP untuk connection pooling.

## Fitur

- **Insert Data Transaksi:** Memungkinkan pengguna memasukkan data transaksi baru ke dalam database.
- **Update Data Transaksi:** Memungkinkan pengguna memperbarui data transaksi yang sudah ada.
- **Tampilkan Data Transaksi:** Menampilkan daftar transaksi dengan format tabel yang rapi.

## Prasyarat

- **Java Development Kit (JDK):** Pastikan Anda telah menginstal JDK versi yang sesuai (misalnya, JDK 11 atau lebih baru).
- **MySQL:** Anda memerlukan server MySQL yang berjalan dan database yang telah disiapkan dengan tabel `Transaksi`, `Cabang`, dan `Produk`.
- **MySQL Connector/J:** Sertakan driver MySQL Connector/J dalam proyek Anda (lihat `pom.xml`).
- **HikariCP:** Sertakan library HikariCP untuk connection pooling (lihat `pom.xml`).

## Konfigurasi

1. **Database:**
   - Sesuaikan koneksi database di `ConnectionUtil.java` dengan URL, username, dan password yang sesuai dengan konfigurasi MySQL Anda.
   - Pastikan tabel `Transaksi`, `Cabang`, dan `Produk` ada di database Anda dan memiliki struktur yang sesuai dengan query SQL yang digunakan dalam aplikasi.

2. **Build:**
   - Gunakan Maven untuk membangun proyek: `mvn clean install`

## Penggunaan

1. **Jalankan Aplikasi:**
   - Setelah membangun proyek, jalankan aplikasi dari baris perintah: `java -jar target/jdbc-exercise-1.0-SNAPSHOT.jar`

2. **Menu:**
   - Aplikasi akan menampilkan menu dengan pilihan operasi:
     - `1. Insert Data Transaksi`
     - `2. Update Data Transaksi`
     - `3. Tampilkan Data Transaksi`
     - `4. Keluar`

3. **Ikuti Petunjuk:**
   - Pilih operasi yang diinginkan dan ikuti petunjuk di layar untuk memasukkan atau memperbarui data, atau menampilkan daftar transaksi.

## Struktur Proyek

- `src/main/java/chvrches/jdbc/exercise/database/`: Berisi kelas `ConnectionUtil` untuk mengelola koneksi database menggunakan HikariCP.
- `src/main/java/TransaksiApp.java`: Kelas utama aplikasi yang berisi logika menu dan operasi transaksi.
- `pom.xml`: File konfigurasi Maven yang mendefinisikan dependensi dan plugin build.