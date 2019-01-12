<?php 
	require 'koneksi.php';

	if ($_SERVER["REQUEST_METHOD"] == "POST") {
		$nbi = $_POST['nbi']; 
		$cek = mysqli_query($koneksi,"SELECT * FROM mhs WHERE nbi = '$nbi'");
		$ada = mysqli_num_rows($cek);
		$hasil = mysqli_fetch_array($cek, MYSQLI_ASSOC);
		$file = str_replace($ip_fix, "", $hasil['foto']);
		unlink($file);
		if ($ada) {
			$query = "DELETE FROM mhs WHERE nbi ='$nbi'";
			$result = mysqli_query($koneksi, $query);
			echo json_encode(array('kode'=>1,'pesan'=>'Berhasil Hapus Data'));
		}else{
			echo json_encode(array('kode'=>3,'pesan'=>'Data Tidak Ditemukan'));
		}
	}else{
		echo json_encode(array('kode'=>4,'pesan'=>'Gagal Request Data'));
	}

	/*$nbi = "5";
	$cek = mysqli_query($koneksi,"SELECT * FROM mhs WHERE nbi = '$nbi'");
	$ada = mysqli_num_rows($cek);
	$hasil = mysqli_fetch_array($cek, MYSQLI_ASSOC);
	// unlink($hasil['foto']);
	echo $file = str_replace($ip_fix, "", $hasil['foto']);
	// echo $hasil['foto'];*/
 ?>