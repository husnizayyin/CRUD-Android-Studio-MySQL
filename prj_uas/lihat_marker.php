<?php 
	require 'koneksi.php';
	$response = array(); 
	$query = "SELECT nama,nbi,tanggal_lahir,jurusan,foto,latitude,longitude FROM mhs";
	$result = mysqli_query($koneksi, $query);
	$ada = mysqli_num_rows($result);
	$array = array();

	if($ada > 0){
		while($baris = mysqli_fetch_assoc($result)){
			$array[]=$baris;
		}
		echo json_encode(array("kode" =>1, "result"=>$array));
	}else{
		echo json_encode(array("kode" =>2, "pesan"=>"Data Tidak Ditemukan" ));
	}
 ?>