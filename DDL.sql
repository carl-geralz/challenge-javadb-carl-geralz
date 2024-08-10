create database PenjualanWarungSamudra;
use PenjualanWarungSamudra;
create table Transaksi (
    Tanggal_Transaksi DATE not null,
    No_Struk VARCHAR(14) not null,
    Kode_Cabang VARCHAR(4) not null,
    Tipe_Transaksi VARCHAR(10) not null,
    Kode_Produk VARCHAR(6) not null,
    Jumlah SMALLINT not null
);
create table Produk (
    Kode_Produk VARCHAR(6) not null,
    Nama_Produk VARCHAR(50) not null,
    Harga_Satuan INT(10) not null
);
create table Cabang (
    Kode_Cabang VARCHAR(4) not null,
    Nama_Cabang VARCHAR(25) not null
);

