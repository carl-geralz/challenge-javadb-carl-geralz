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

        do {
            tampilkanMenu();
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (pilihan) {
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
                    System.out.println("Keluar dari aplikasi...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 4);

        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Insert Data Transaksi");
        System.out.println("2. Update Data Transaksi");
        System.out.println("3. Tampilkan Data Transaksi");
        System.out.println("4. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static void insertDataTransaksi(Scanner scanner) {
        System.out.print("Tanggal Transaksi (YYYY-MM-DD): ");
        String tanggalTransaksi = scanner.nextLine();
        System.out.print("No Struk (KodeCabang-Tahun-NomorOrder: ");
        String noStruk = scanner.nextLine();
        System.out.print("Kode Cabang: ");
        String kodeCabang = scanner.nextLine();
        System.out.print("Tipe Transaksi: ");
        String tipeTransaksi = scanner.nextLine();
        System.out.print("Kode Produk: ");
        String kodeProduk = scanner.nextLine();
        System.out.print("Jumlah: ");
        int jumlah = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer

        String sql = "INSERT INTO Transaksi (Tanggal_Transaksi, No_Struk, Kode_Cabang, Tipe_Transaksi, Kode_Produk, Jumlah) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1,
                    tanggalTransaksi);
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
                System.out.println("Tanggal Transaksi: " + resultSet.getDate("Tanggal Transaksi"));
                System.out.println("Kode Cabang: " + resultSet.getString("Kode Cabang"));
                System.out.println("Tipe Transaksi: " + resultSet.getString("Tipe Transaksi"));
                System.out.println("Kode Produk: " + resultSet.getString("Kode Produk"));
                System.out.println("Jumlah: " + resultSet.getInt("Jumlah"));

                // Minta input data baru dari pengguna
                System.out.print("Tanggal Transaksi baru (YYYY-MM-DD, kosongkan jika tidak berubah): ");
                String tanggalTransaksiBaru = scanner.nextLine();
                System.out.print("Kode Cabang baru (kosongkan jika tidak berubah): ");
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

            // Menampilkan data
            while (resultSet.next()) {
                String tanggalTransaksi = resultSet.getString("Tanggal Transaksi");
                String noStruk = resultSet.getString("No Struk");
                String kodeCabang = resultSet.getString("Kode Cabang");
                String namaCabang = resultSet.getString("Nama Cabang");
                String tipeTransaksi = resultSet.getString("Tipe Transaksi");
                String kodeProduk = resultSet.getString("Kode Produk");
                String namaProduk = resultSet.getString("Nama Produk");
                int jumlah = resultSet.getInt("Jumlah");
                int hargaSatuan = resultSet.getInt("Harga Satuan");
                int totalPenjualan = resultSet.getInt("Total Penjualan");

                // Format output sesuai gambar
                System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20d | %-20d | %-20d |\n",
                        tanggalTransaksi, noStruk, kodeCabang, namaCabang, tipeTransaksi, kodeProduk, namaProduk, jumlah, hargaSatuan, totalPenjualan);
            }

            // Footer tabel
            System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }