<?php 
	require 'koneksi.php';
	
	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		$part = "./gambar/";
		$filename = "img".rand(9,9999).".jpg";

		$nbi = $_POST['nbi']; 
		$nama = $_POST['nama']; 
		$alamat = $_POST['alamat']; 
		$tanggal_lahir = $_POST["tanggal_lahir"]; 
		$fakultas = $_POST["fakultas"];
		$jurusan = $_POST['jurusan'];
		$jenis_kelamin = $_POST["jenis_kelamin"];
		$hobi = $_POST["hobi"];
		$kewarganegaraan = $_POST["kewarganegaraan"]; 
		$latitude = $_POST['latitude']; 
		$longitude = $_POST['longitude'];
		$tanggal_lulus=$_POST['tanggal_lulus'];
		$jumlah_semester=$_POST['jumlah_semester'];
		$dosen_wali=$_POST['dosen_wali'];
		$lokasi = $_POST['lokasi'];

		$cek = mysqli_query($koneksi,"SELECT * FROM mhs WHERE nbi = '$nbi' ");
		$ada = mysqli_num_rows($cek);
		
		$lokasin = mysqli_query($koneksi,"SELECT lokasi,foto FROM mhs WHERE nbi = '$nbi' ");
		while($baris = mysqli_fetch_assoc($lokasin)){
			$newlok = $baris['lokasi'];
			$newfoto = $baris['foto'];
		}
		
		
		

		if ($ada > 0) {
			if($lokasi == $newlok){
				$query = "UPDATE mhs SET nbi ='$nbi', nama = '$nama', alamat = '$alamat', tanggal_lahir = STR_TO_DATE('$tanggal_lahir', '%Y-%m-%d'), fakultas = '$fakultas',jurusan = '$jurusan', jenis_kelamin = '$jenis_kelamin', hobi = '$hobi', kewarganegaraan = '$kewarganegaraan',foto='$newfoto', latitude = '$latitude', longitude = '$longitude',tanggal_lulus='$tanggal_lulus',jumlah_semester='$jumlah_semester',dosen_wali='$dosen_wali',lokasi='$lokasi' WHERE nbi = '$nbi' ";
				$result = mysqli_query($koneksi, $query);
				if ($result) {
					echo json_encode(array('kode'=>1,'pesan'=> 'Berhasil Edit Data new Lokasi'));
				}else{
					echo json_encode(array('kode'=>2,'pesan'=>'Gagal Edit Data new Lokasi'));
				}
			}else{
			if ($_FILES['imageupload']) {
				$sqlHapus = mysqli_query($koneksi,"SELECT lokasi,foto FROM mhs WHERE nbi = '$nbi' ");
				$hasil = mysqli_fetch_array($sqlHapus, MYSQLI_ASSOC);
				$file = str_replace($ip_fix, "", $hasil['foto']);
				unlink($file);

				$foto = $ip.$filename;
				$query = "UPDATE mhs SET nbi ='$nbi', nama = '$nama', alamat = '$alamat', tanggal_lahir = STR_TO_DATE('$tanggal_lahir', '%Y-%m-%d'), fakultas = '$fakultas',jurusan = '$jurusan', jenis_kelamin = '$jenis_kelamin', hobi = '$hobi', kewarganegaraan = '$kewarganegaraan', foto = '$foto', latitude = '$latitude', longitude = '$longitude',tanggal_lulus='$tanggal_lulus',jumlah_semester='$jumlah_semester',dosen_wali='$dosen_wali',lokasi='$lokasi' WHERE nbi = '$nbi' ";
				$result = mysqli_query($koneksi, $query);
				if ($result) {
					$destinationfile = $part.$filename; 
					move_uploaded_file($_FILES['imageupload']['tmp_name'], $destinationfile);
					echo json_encode(array('kode'=>1,'pesan'=> 'Berhasil Edit Data just images'));
				}else{
					echo json_encode(array('kode'=>2,'pesan'=>'Gagal Edit Data just images'));
				}
			}
			}
		}else{
			echo json_encode(array('kode'=>3,'pesan'=>'Data Tidak Ditemukan, Gagal Edit Data'));
		}
	}else{
		echo json_encode(array('kode'=>4,'pesan'=>'Gagal Request Data gagal total'));
	}
 ?>