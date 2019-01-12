<?php 
	require 'koneksi.php';

	if ($_SERVER["REQUEST_METHOD"] == "POST") {
		$nbi = $_POST['nbi'];
		$query = "SELECT * FROM mhs WHERE nbi='$nbi' ";
		$result = mysqli_query($koneksi, $query);
		$jum = mysqli_num_rows($result);
		$array = array();
		while($baris = mysqli_fetch_assoc($result)){
			$array['nbi'] = $baris['nbi'];
			$array['nama'] = $baris['nama'];
			$array['alamat'] = $baris['alamat'];
			$array['tanggal_lahir'] = $baris['tanggal_lahir'];
			$array['fakultas'] = $baris['fakultas'];
			$array['jurusan'] = $baris['jurusan'];
			$array['jenis_kelamin'] = $baris['jenis_kelamin'];
			$array['hobi'] = $baris['hobi'];
			$array['kewarganegaraan'] = $baris['kewarganegaraan'];
			$array['foto'] = $baris['foto'];
			$array['latitude'] = $baris['latitude'];
			$array['longitude'] = $baris['longitude'];
			$array['tanggal_lulus']=$baris['tanggal_lulus'];
			$array['jumlah_semester']=$baris['jumlah_semester'];
			$array['dosen_wali']=$baris['dosen_wali'];
			$array['lokasi']=$baris['lokasi'];
			$array['kode'] = 1;
			$array['pesan'] = "Data Berhasil Dimuat";
		}
		if ($jum > 0) {
			echo json_encode($array);
		}else{
			echo json_encode(array("kode" =>2, "pesan"=>"Data Tidak Ditemukan" ));
		}
	}else{
		echo json_encode(array("kode" =>0, "pesan"=>"Gagal meRequest Data"));
	}
 ?>}
