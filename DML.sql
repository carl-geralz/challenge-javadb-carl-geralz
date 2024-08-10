use PenjualanWarungSamudra;
insert into Transaksi (Tanggal_Transaksi, No_Struk, Kode_Cabang, Tipe_Transaksi, Kode_Produk, Jumlah)
values
    ('2023-01-03', '0203-2023-0001', '0203', 'Eat In', '01-001', 1),
    ('2023-01-03', '0203-2023-0001', '0203', 'Eat In', '01-002', 1),
    ('2023-01-03', '0203-2023-0001', '0203', 'Eat In', '02-001', 1),
    ('2023-01-03', '0203-2023-0002', '0203', 'Take Away', '01-001', 1),
    ('2023-01-03', '0203-2023-0002', '0203', 'Take Away', '01-007', 1),
    ('2023-01-03', '0203-2023-0002', '0203', 'Take Away', '01-003', 3),
    ('2023-01-04', '0203-2023-0002', '0203', 'Online', '01-004', 1),
    ('2023-01-05', '0204-2023-0012', '0204', 'Take Away', '01-006', 1),
    ('2023-01-05', '0204-2023-0013', '0204', 'Eat In', '01-005', 1),
    ('2023-01-05', '0204-2023-0014', '0204', 'Eat In', '04-001', 1),
    ('2023-01-03', '0204-2023-0014', '0204', 'Online', '01-008', 1),
    ('2023-01-03', '0205-2023-1000', '0205', 'Online', '02-002', 1),
    ('2023-01-03', '0205-2023-1000', '0205', 'Online', '01-001', 3),
    ('2023-01-03', '0205-2023-1000', '0205', 'Online', '04-002', 2),
    ('2023-01-06', '0206-2023-0923', '0206', 'Take Away', '02-003', 4);
insert into Produk (Kode_Produk, Nama_Produk, Harga_Satuan)
values
    ('01-001', 'Nasi Putih', 5000),
    ('01-002', 'Aneka Tumisan', 2000),
    ('01-003', 'Aneka Gorengan', 1500),
    ('01-004', 'Nasi Goreng Biasa', 16000),
    ('01-005', 'Nasi Goreng Spesial', 19000),
    ('01-006', 'Mie Goreng Biasa', 17000),
    ('01-007', 'Aneka Sop', 7000),
    ('01-008', 'Ikan Gurame Bakar', 55000),
    ('02-001', 'Es Teh Tawar', 1000),
    ('02-002', 'Juice Strawberry', 23000),
    ('02-003', 'Juice Mangga', 20000),
    ('04-001', 'Telor Dadar', 5000),
    ('04-002', 'Sambal Mangga', 7000);
insert into Cabang (Kode_Cabang, Nama_Cabang)
values
    ('0203', 'Pancoran Barat'),
    ('0204', 'Kuningan Tengah'),
    ('0205', 'Pasar Minggu'),
    ('0206', 'Cilandak');
select
    Tanggal_Transaksi,
    No_Struk,
    Transaksi.Kode_Cabang as Kode_Cabang,
    Nama_Cabang,
    Tipe_Transaksi,
    Transaksi.Kode_Produk as Kode_Produk,
    Nama_Produk,
    Jumlah,
    Harga_Satuan,
    Jumlah * Harga_Satuan as Total_Penjualan
from Transaksi
inner join Cabang on Transaksi.Kode_Cabang = Cabang.Kode_Cabang
inner join Produk on Transaksi.Kode_Produk = Produk.Kode_Produk;
