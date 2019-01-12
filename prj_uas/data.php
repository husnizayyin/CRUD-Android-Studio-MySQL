<?php 
	require 'koneksi.php';
	$response = array(); 
	$query = "SELECT * FROM mhs";
	$result = mysqli_query($koneksi, $query);
	$array = array();

	while($baris = mysqli_fetch_assoc($result)){
		$array[] = $baris;
	}

	if ($result) {
		$array[]= array("kode"=>1);
		echo json_encode($array);
	}else{
		echo json_encode(array("kode" =>0, "pesan"=>"Data Tidak Ditemukan" ));
	}
 ?>