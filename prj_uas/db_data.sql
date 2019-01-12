-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 11 Jan 2019 pada 16.19
-- Versi Server: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_data`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `mhs`
--

CREATE TABLE `mhs` (
  `nbi` int(11) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `fakultas` varchar(20) NOT NULL,
  `jurusan` varchar(20) NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `hobi` varchar(50) NOT NULL,
  `kewarganegaraan` varchar(20) NOT NULL,
  `foto` varchar(50) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `tanggal_lulus` date NOT NULL,
  `jumlah_semester` int(5) NOT NULL,
  `dosen_wali` varchar(100) NOT NULL,
  `lokasi` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `mhs`
--

INSERT INTO `mhs` (`nbi`, `nama`, `alamat`, `tanggal_lahir`, `fakultas`, `jurusan`, `jenis_kelamin`, `hobi`, `kewarganegaraan`, `foto`, `latitude`, `longitude`, `tanggal_lulus`, `jumlah_semester`, `dosen_wali`, `lokasi`) VALUES
(5, 'faizal', 'surabaya', '2019-01-11', 'Teknik', 'Informatika', 'Pria', 'Bola,Renang,', 'Indonesia', 'http://192.168.43.79/prj_uas/gambar/img7325.jpg', -7.29841931, 112.76640326, '2019-01-11', 2, 'test', '/storage/emulated/0/DCIM/Screenshots/Screenshot_2019-01-11-21-00-14-666_com.google.android.googlequi'),
(1461505251, 'Bimo Satriyo NP', 'Surabaya', '1999-12-31', 'Teknik', 'Informatika', 'Pria', 'Bola,Basket,', 'Indonesia', 'http://192.168.137.1/prj_uas/gambar/img3020.jpg', -7.29031891, 112.76112822, '0000-00-00', 0, '', ''),
(1461505300, 'Fajar Dwi Yunanto', 'Tulungagung', '1997-02-22', 'Teknik', 'Informatika', 'Pria', 'Bola,Basket,Renang,', 'Vietnam', 'http://192.168.137.1/prj_uas/gambar/img1059.jpg', -8.14029072, 111.86716682, '0000-00-00', 0, '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mhs`
--
ALTER TABLE `mhs`
  ADD PRIMARY KEY (`nbi`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mhs`
--
ALTER TABLE `mhs`
  MODIFY `nbi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1461505302;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
