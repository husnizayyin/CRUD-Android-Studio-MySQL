<?php 
	require 'koneksi.php';

	if ($_SERVER["REQUEST_METHOD"] == "POST") {

		$part = "./gambar/";
		$filename = "img".rand(9,9999).".jpg";
		$nbi = $_POST['nbi']; 
		$nama = $_POST['nama']; 
		$alamat = $_POST['alamat']; 
		$tanggal_lahir = $_POST['tanggal_lahir']; 
		$fakultas = $_POST['fakultas'];
		$jurusan = $_POST['jurusan'];
		$jenis_kelamin = $_POST['jenis_kelamin'];
		$hobi = $_POST['hobi'];
		$kewarganegaraan = $_POST['kewarganegaraan']; 
		$latitude = $_POST['latitude']; 
		$longitude = $_POST['longitude'];
		$tanggal_lulus=$_POST['tanggal_lulus'];
		$jumlah_semester=$_POST['jumlah_semester'];
		$dosen_wali=$_POST['dosen_wali'];
		$lokasi=$_POST['lokasi'];


		if ($_FILES['imageupload']) {
			$foto = $ip.$filename;
			$query = "INSERT INTO mhs (nbi, nama, alamat, tanggal_lahir, fakultas, jurusan, jenis_kelamin, hobi, kewarganegaraan,foto, latitude, longitude, tanggal_lulus,jumlah_semester,dosen_wali,lokasi) VALUES ('$nbi', '$nama', '$alamat', '$tanggal_lahir', '$fakultas', '$jurusan', '$jenis_kelamin', '$hobi', '$kewarganegaraan', '$foto', '$latitude', '$longitude','$tanggal_lulus','$jumlah_semester','$dosen_wali','$lokasi')";
			$result = mysqli_query($koneksi, $query); 
			if ($result){
				$destinationfile = $part.$filename; 
				move_uploaded_file($_FILES['imageupload']['tmp_name'], $destinationfile);
				echo json_encode(array('kode'=>1,'pesan'=> 'Berhasil Tambah Data'));
			}else{
				echo json_encode(array('kode'=>2,'pesan'=>'Gagal Tambah Data'));
			}
		}
	}else{
		echo json_encode(array('kode'=>3,'pesan'=>'Gagal Request Data'));
	}
 ?>