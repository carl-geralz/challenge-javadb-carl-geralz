package chvrches.jdbc.exercise.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        // Tampilkan nilai default dan opsi untuk mengubah
        System.out.println("Konfigurasi Koneksi Database saat ini:");
        System.out.println("JDBC URL: " + ConnectionUtil.getJdbcUrl());
        System.out.println("Username: " + ConnectionUtil.getUsername());
        System.out.println("Password: " + ConnectionUtil.getPassword());
        System.out.print("\nApakah Anda ingin mengubah konfigurasi koneksi? (y/n): ");

        String pilihanUbah = scanner.nextLine();
        if (pilihanUbah.equalsIgnoreCase("y")) {
            ubahKonfigurasiKoneksi(scanner);
        }

        int pilihanMenu;

        do {
            tampilkanMenu();
            pilihanMenu = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (pilihanMenu) {
                case 1:
                    insertDataTransaksi(scanner);
                    break;
                case 2:
                    updateDataTransaksi(scanner);
                    break;
                case 3:
                    tampilkanDataTransaksi();
                    break;
                case 4:
                    hapusDataTransaksi(scanner);
                    break;
                case 5:
                    System.out.println("Keluar dari aplikasi...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihanMenu != 5);

        scanner.close();
    }
    
    private static void ubahKonfigurasiKoneksi(Scanner scanner) {
        System.out.print("Masukkan JDBC URL baru: ");
        String jdbcUrlBaru = scanner.nextLine();
        System.out.print("Masukkan username baru: ");
        String usernameBaru = scanner.nextLine();
        System.out.print("Masukkan password baru: ");
        String passwordBaru = scanner.nextLine();

        ConnectionUtil.setJdbcUrl(jdbcUrlBaru);
        ConnectionUtil.setUsername(usernameBaru);
        ConnectionUtil.setPassword(passwordBaru);

        System.out.println("Konfigurasi koneksi berhasil diubah.");
    }

    private static void tampilkanMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Insert Data Transaksi");
        System.out.println("2. Update Data Transaksi");
        System.out.println("3. Tampilkan Data Transaksi");
        System.out.println("4. Hapus Data Transaksi");
        System.out.println("5. Keluar");
        System.out.printf("%nPilih menu: ");
    }

    private static void insertDataTransaksi(Scanner scanner) {
        String tanggalTransaksi, noStruk, kodeCabang, tipeTransaksi, kodeProduk;
        int jumlah;

        // Input Tanggal Transaksi dengan validasi
        do {
            System.out.print("Tanggal Transaksi (YYYY-MM-DD): ");
            tanggalTransaksi = scanner.nextLine();
            if (tanggalTransaksi.isEmpty()) {
                System.out.println("Tanggal Transaksi tidak boleh kosong. Silakan coba lagi.");
            }
        } while (tanggalTransaksi.isEmpty());
        
        // Input No Struk dengan validasi
        do {
            System.out.print("No Struk (Kode_Cabang-Tahun-Nomor_Order): ");
            noStruk = scanner.nextLine();
            if (noStruk.isEmpty()) {
                System.out.println("No Struk tidak boleh kosong. Silakan coba lagi.");
            }
        } while (noStruk.isEmpty());

        // Menampilkan daftar cabang sebelum meminta input Kode_Cabang
        tampilkanDaftarCabang();
        
        // Input Kode Cabang dengan validasi
        do {
            System.out.print("Kode Cabang: ");
            kodeCabang = scanner.nextLine();
            if (kodeCabang.isEmpty()) {
                System.out.println("Kode Cabang tidak boleh kosong. Silakan coba lagi.");
            }
        } while (kodeCabang.isEmpty());

        // Input Tipe Transaksi dengan validasi
        do {
            System.out.print("Tipe Transaksi: ");
            tipeTransaksi = scanner.nextLine();
            if (tipeTransaksi.isEmpty()) {
                System.out.println("Tipe Transaksi tidak boleh kosong. Silakan coba lagi.");
            }
        } while (tipeTransaksi.isEmpty());

        // Menampilkan daftar produk
        tampilkanDaftarProduk();

        // Input Kode Produk dengan validasi
        do {
            System.out.print("Kode Produk: ");
            kodeProduk = scanner.nextLine();
            if (kodeProduk.isEmpty()) {
                System.out.println("Kode Produk tidak boleh kosong. Silakan coba lagi.");
            }
        } while (kodeProduk.isEmpty());

        // Input Jumlah dengan validasi
        do {
            System.out.print("Jumlah: ");
            if (scanner.hasNextInt()) {
                jumlah = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih besar dari 0. Silakan coba lagi.");
                }
            } else {
                System.out.println("Input Jumlah tidak valid. Silakan masukkan angka.");
                scanner.nextLine(); // Membersihkan buffer
                jumlah = 0; // Menetapkan nilai awal agar loop berjalan lagi
            }
        } while (jumlah <= 0);

        String sql = "INSERT INTO Transaksi (Tanggal_Transaksi, No_Struk, Kode_Cabang, Tipe_Transaksi, Kode_Produk, Jumlah) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tanggalTransaksi);
            preparedStatement.setString(2, noStruk);
            preparedStatement.setString(3, kodeCabang);
            preparedStatement.setString(4, tipeTransaksi);
            preparedStatement.setString(5, kodeProduk);
            preparedStatement.setInt(6, jumlah);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " baris berhasil ditambahkan.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void tampilkanDaftarCabang() {
        String sql = "SELECT Kode_Cabang, Nama_Cabang FROM Cabang";

        try (Connection connection = ConnectionUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {

            System.out.println("\nDaftar Cabang:");
            System.out.println("+----------------------+----------------------+");
            System.out.println("| Kode_Cabang          | Nama_Cabang          |");
            System.out.println("+----------------------+----------------------+");

            while (resultSet.next()) {
                String kodeCabang = resultSet.getString("Kode_Cabang");
                String namaCabang = resultSet.getString("Nama_Cabang");
                System.out.printf("| %-20s | %-20s |\n", kodeCabang, namaCabang);
            }

            System.out.println("+----------------------+----------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void tampilkanDaftarProduk() {
        String sql = "SELECT Kode_Produk, Nama_Produk, Harga_Satuan FROM Produk";

        try (Connection connection = ConnectionUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {

            System.out.println("\nDaftar Produk:");
            System.out.println("+----------------------+----------------------+----------------------+");
            System.out.println("| Kode_Produk          | Nama_Produk          | Harga_Satuan         |");
            System.out.println("+----------------------+----------------------+----------------------+");

            while (resultSet.next()) {
                String kodeProduk = resultSet.getString("Kode_Produk");
                String namaProduk = resultSet.getString("Nama_Produk");
                int hargaSatuan = resultSet.getInt("Harga_Satuan");
                System.out.printf("| %-20s | %-20s | %-20d |\n", kodeProduk, namaProduk, hargaSatuan);
            }

            System.out.println("+----------------------+----------------------+----------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateDataTransaksi(Scanner scanner) {
        System.out.print("No Struk transaksi yang akan diupdate: ");
        String noStruk = scanner.nextLine();

        // Tampilkan data transaksi saat ini berdasarkan No_Struk
        String selectSql = "SELECT * FROM Transaksi WHERE No_Struk = ?";
        try (Connection connection = ConnectionUtil.getDataSource().getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {

            selectStatement.setString(1, noStruk);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Tampilkan data transaksi saat ini
                System.out.println("Data transaksi saat ini:");
                System.out.println("Tanggal Transaksi: " + resultSet.getDate("Tanggal_Transaksi"));
                System.out.println("Kode Cabang: " + resultSet.getString("Kode_Cabang"));
                System.out.println("Tipe Transaksi: " + resultSet.getString("Tipe_Transaksi"));
                System.out.println("Kode Produk: " + resultSet.getString("Kode_Produk"));
                System.out.println("Jumlah: " + resultSet.getInt("Jumlah"));

                // Minta input data baru dari pengguna
                System.out.print("Tanggal Transaksi baru (YYYY-MM-DD, kosongkan jika tidak berubah): ");
                String tanggalTransaksiBaru = scanner.nextLine();
                tampilkanDaftarProduk();
                System.out.print("Kode Produk baru (kosongkan jika tidak berubah): ");
                tampilkanDaftarCabang();
                String kodeCabangBaru = scanner.nextLine();
                System.out.print("Tipe Transaksi baru (kosongkan jika tidak berubah): ");
                String tipeTransaksiBaru = scanner.nextLine();
                System.out.print("Kode Produk baru (kosongkan jika tidak berubah): ");
                String kodeProdukBaru = scanner.nextLine();
                System.out.print("Jumlah baru (0 jika tidak berubah): ");
                int jumlahBaru = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer

                // Bangun query UPDATE berdasarkan input pengguna
                String updateSql = "UPDATE Transaksi SET ";
                boolean perluKoma = false;

                if (!tanggalTransaksiBaru.isEmpty()) {
                    updateSql += "Tanggal_Transaksi = ?";
                    perluKoma = true;
                }
                if (!kodeCabangBaru.isEmpty()) {
                    updateSql += (perluKoma ? ", " : "") + "Kode Cabang = ?";
                    perluKoma = true;
                }
                if (!tipeTransaksiBaru.isEmpty()) {
                    updateSql += (perluKoma ? ", " : "") + "Tipe Transaksi = ?";
                    perluKoma = true;
                }
                if (!kodeProdukBaru.isEmpty()) {
                    updateSql += (perluKoma ? ", " : "") + "Kode Produk = ?";
                    perluKoma = true;
                }
                if (jumlahBaru != 0) {
                    updateSql += (perluKoma ? ", " : "") + "Jumlah = ?";
                }

                updateSql += " WHERE No_Struk = ?";

                // Eksekusi query UPDATE
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    int parameterIndex = 1;

                    if (!tanggalTransaksiBaru.isEmpty()) {
                        updateStatement.setString(parameterIndex++, tanggalTransaksiBaru);
                    }
                    if (!kodeCabangBaru.isEmpty()) {
                        updateStatement.setString(parameterIndex++, kodeCabangBaru);
                    }
                    if (!tipeTransaksiBaru.isEmpty()) {
                        updateStatement.setString(parameterIndex++, tipeTransaksiBaru);
                    }
                    if (!kodeProdukBaru.isEmpty()) {
                        updateStatement.setString(parameterIndex++, kodeProdukBaru);
                    }
                    if (jumlahBaru != 0) {
                        updateStatement.setInt(parameterIndex++, jumlahBaru);
                    }

                    updateStatement.setString(parameterIndex, noStruk);

                    int rowsAffected = updateStatement.executeUpdate();
                    System.out.println(rowsAffected + " baris berhasil diupdate.");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Transaksi dengan No Struk " + noStruk + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void tampilkanDataTransaksi() {
        String sql = "select Tanggal_Transaksi, No_Struk, Transaksi.Kode_Cabang as Kode_Cabang, Nama_Cabang, Tipe_Transaksi, Transaksi.Kode_Produk as Kode_Produk, Nama_Produk, Jumlah, Harga_Satuan, Jumlah * Harga_Satuan as Total_Penjualan from Transaksi\n" +
                "    inner join Cabang on Transaksi.Kode_Cabang = Cabang.Kode_Cabang\n" +
                "    inner join Produk on Transaksi.Kode_Produk = Produk.Kode_Produk;";

        try (Connection connection = ConnectionUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {

            // Header tabel
            System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+");
            System.out.println("| Tanggal Transaksi    | No Struk             | Kode Cabang          | Nama Cabang          | Tipe Transaksi       | Kode Produk          | Nama Produk          | Jumlah               | Harga Satuan         | Total_Penjualan      |");
            System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+");

            // Variabel untuk menyimpan total penjualan berdasarkan tipe transaksi
            int totalEatIn = 0;
            int totalTakeAway = 0;
            int totalOnline = 0;

            // Menampilkan data
            while (resultSet.next()) {
                String tanggalTransaksi = resultSet.getString("Tanggal_Transaksi");
                String noStruk = resultSet.getString("No_Struk");
                String kodeCabang = resultSet.getString("Kode_Cabang");
                String namaCabang = resultSet.getString("Nama_Cabang");
                String tipeTransaksi = resultSet.getString("Tipe_Transaksi");
                String kodeProduk = resultSet.getString("Kode_Produk");
                String namaProduk = resultSet.getString("Nama_Produk");
                int jumlah = resultSet.getInt("Jumlah");
                int hargaSatuan = resultSet.getInt("Harga_Satuan");
                int totalPenjualan = resultSet.getInt("Total_Penjualan");

                // Menghitung total penjualan berdasarkan tipe transaksi
                switch (tipeTransaksi) {
                    case "Eat In":
                        totalEatIn += totalPenjualan;
                        break;
                    case "Take Away":
                        totalTakeAway += totalPenjualan;
                        break;
                    case "Online":
                        totalOnline += totalPenjualan;
                        break;
                }

                // Format output sesuai gambar
                System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20d | %-20d | %-20d |\n",
                        tanggalTransaksi, noStruk, kodeCabang, namaCabang, tipeTransaksi, kodeProduk, namaProduk, jumlah, hargaSatuan, totalPenjualan);
            }

            // Footer tabel
            System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+");

            // Menampilkan total penjualan berdasarkan tipe transaksi
            System.out.printf("%n%n");
            System.out.println("Eat In: " + totalEatIn);
            System.out.println("Take Away: " + totalTakeAway);
            System.out.println("Online: " + totalOnline);
            System.out.printf("%n%n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void hapusDataTransaksi(Scanner scanner) {

        tampilkanDataTransaksi();

        System.out.print("Masukkan No Struk transaksi yang akan dihapus: ");
        String noStruk = scanner.nextLine();

        // Tampilkan detail transaksi yang akan dihapus
        String selectSql = "SELECT * FROM Transaksi WHERE No_Struk = ?";
        try (Connection connection = ConnectionUtil.getDataSource().getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {

            selectStatement.setString(1, noStruk);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Tampilkan detail transaksi
                System.out.println("\nDetail transaksi yang akan dihapus:");
                System.out.println("Tanggal Transaksi: " + resultSet.getDate("Tanggal_Transaksi"));
                System.out.println("No Struk: " + resultSet.getString("No_Struk"));
                System.out.println("Kode Cabang: " + resultSet.getString("Kode_Cabang"));
                System.out.println("Tipe Transaksi: " + resultSet.getString("Tipe_Transaksi"));
                System.out.println("Kode Produk: " + resultSet.getString("Kode_Produk"));
                System.out.println("Jumlah: " + resultSet.getInt("Jumlah"));

                // Konfirmasi penghapusan
                System.out.print("\nApakah Anda yakin ingin menghapus transaksi ini? (y/n): ");
                String konfirmasi = scanner.nextLine();

                if (konfirmasi.equalsIgnoreCase("y")) {
                    // Eksekusi penghapusan jika dikonfirmasi
                    String deleteSql = "DELETE FROM Transaksi WHERE No_Struk = ?";
                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                        deleteStatement.setString(1, noStruk);
                        int rowsAffected = deleteStatement.executeUpdate();
                        System.out.println(rowsAffected + " baris berhasil dihapus.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Penghapusan dibatalkan.");
                }

            } else {
                System.out.println("Transaksi dengan No Struk " + noStruk + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}