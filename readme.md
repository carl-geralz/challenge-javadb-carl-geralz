# Aplikasi Manajemen Transaksi Penjualan Warung Samudra Menggunakan JDBC

Aplikasi konsol sederhana ini menyediakan fungsionalitas dasar untuk mengelola data transaksi menggunakan JDBC (Java Database Connectivity) dan HikariCP untuk connection pooling.

## Fitur

- **Insert Data Transaksi:** Memungkinkan pengguna memasukkan data transaksi baru ke dalam database, termasuk validasi input.
- **Update Data Transaksi:** Memungkinkan pengguna mengubah data transaksi yang sudah ada, dengan menampilkan data saat ini dan meminta input data baru.
- **Tampilkan Data Transaksi:** Menampilkan daftar semua transaksi dari database, termasuk informasi detail dan perhitungan total penjualan berdasarkan tipe transaksi.
- **Hapus Data Transaksi:** Memungkinkan pengguna menghapus transaksi berdasarkan nomor struk, dengan konfirmasi sebelum penghapusan.


## Prasyarat

- **Java Development Kit (JDK):** Pastikan Anda telah menginstal JDK versi 22.
- **MySQL:** Anda memerlukan server MySQL yang berjalan dan database yang telah disiapkan dengan tabel `Transaksi`, `Cabang`, dan `Produk` (cek "Penggunaan" no 1. di bawah).
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
     - 1. `Insert Data Transaksi`
     - 2. `Update Data Transaksi`
     - 3. `Tampilkan Data Transaksi`
     - 4. `Hapus Data Transaksi`
     - 5. `Keluar`

3. **Ikuti Petunjuk:**
   - **Eksekusi DDL dan DML:** Eksekusi DDL.sql lalu DML.sql untuk memasukkan data.
   - **Kompilasi:** Kompilasi kode Java menggunakan perintah javac App.java.
   - **Eksekusi:** Jalankan aplikasi menggunakan perintah java App.
   - **Ikuti petunjuk:** Aplikasi akan menampilkan menu. Pilih opsi yang diinginkan dan ikuti petunjuk untuk memasukkan atau mengubah data.