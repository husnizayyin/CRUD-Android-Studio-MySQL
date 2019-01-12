package com.project.husni.uasptm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.husni.uasptm.R;
import com.project.husni.uasptm.api.ApiRequestBiodata;
import com.project.husni.uasptm.api.RetroServer;
import com.project.husni.uasptm.model.ModelData_load;
import com.project.husni.uasptm.model.ModelData_marker;
import com.project.husni.uasptm.model.ResponsModel;
import com.project.husni.uasptm.model.getdatamark;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.project.husni.uasptm.fragment.WorkaroundMapFragment;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.squareup.picasso.Picasso;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText edNbi, edNama, edAlamat, edTglLahir, edLatitude, edLongitude,tgl_lulus,jml_smester,dsn_wali;
    private Spinner spJurusan, spFakultas;
    private ListView lvNegara;
    private TextView tvNegara;
    private ImageView imgFoto;
    private ScrollView mScrollView;
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath = "";
    private Button save, load, reset, edit, hapus, loadall;
    private RadioButton r1, r2;
    private RadioGroup rgKelamin;
    private CheckBox cbBola, cbRenang, cbBasket, cbLari;
    private List<ModelData_marker> data = new ArrayList<>();

    ProgressDialog pd;


    String[] negara = {
            "Indonesia", "Malaysia", "Filipina", "Vietnam",
            "Kamboja", "India", "Jepang", "Thailand",
            "Singapore"};

    private GoogleMap mMap;
    private Marker marker;


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean permission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        if (mMap == null) {
            SupportMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    mScrollView = findViewById(R.id.scrllview); //
                    ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                            .setListener(new WorkaroundMapFragment.OnTouchListener() {
                                @Override
                                public void onTouch() {
                                    mScrollView.requestDisallowInterceptTouchEvent(true);
                                }
                            });
                }
            });
        }
        getLocationPermission();

        toolbar = (Toolbar) findViewById(R.id.tolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        edNbi = (EditText) findViewById(R.id.edNBI);
        edNama = (EditText) findViewById(R.id.edNama);
        edAlamat = (EditText) findViewById(R.id.edAlamat);
        edTglLahir = (EditText) findViewById(R.id.edTglLahir);
        edLatitude = (EditText) findViewById(R.id.edLatitude);
        edLongitude = (EditText) findViewById(R.id.edLongitude);
        tgl_lulus = (EditText) findViewById(R.id.edTglLulus);
        jml_smester = (EditText) findViewById(R.id.edjml_smester);
        dsn_wali = (EditText) findViewById(R.id.edds_wali);

        spJurusan = (Spinner) findViewById(R.id.spJurusan);
        spFakultas = (Spinner) findViewById(R.id.spFakultas);
        lvNegara = (ListView) findViewById(R.id.lvNegara);
        tvNegara = (TextView) findViewById(R.id.tvNegara);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        ImageButton btnImage = (ImageButton) findViewById(R.id.imgBtn);

        save = (Button) findViewById(R.id.btnSave);
        load = (Button) findViewById(R.id.btnLoad);
        reset = (Button) findViewById(R.id.btnReset);
        edit = (Button) findViewById(R.id.btnEdit);
        hapus = (Button) findViewById(R.id.btnDelete);
        loadall = (Button) findViewById(R.id.btnLoadall);

        rgKelamin = (RadioGroup) findViewById(R.id.rgKelamin);

        r1 = (RadioButton) findViewById(R.id.radioButton4);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        cbBola = (CheckBox) findViewById(R.id.cbBola);
        cbBasket = (CheckBox) findViewById(R.id.cbBasket);
        cbRenang = (CheckBox) findViewById(R.id.cbRenang);
        cbLari = (CheckBox) findViewById(R.id.cbLari);
        pd = new ProgressDialog(this);


        ArrayAdapter adapterJurusan = ArrayAdapter.createFromResource(this, R.array.jurusan, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapterFakultas = ArrayAdapter.createFromResource(this, R.array.fakultas, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterNegara = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, negara);


        lvNegara.setAdapter(adapterNegara);
        spJurusan.setAdapter(adapterJurusan);
        spFakultas.setAdapter(adapterFakultas);

        dateFormatter = new SimpleDateFormat("YYYY-MM-dd");

        edTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
                edTglLahir.setFocusable(false);
                edTglLahir.setFocusableInTouchMode(true);
            }
        });
        tgl_lulus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate2Dialog();
                tgl_lulus.setFocusable(false);
                tgl_lulus.setFocusableInTouchMode(true);
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickIntent, RESULT_LOAD_IMAGE);
            }
        });

        lvNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvNegara.setText(negara[position]);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = "";
                if (cbBola.isChecked()) {
                    txt = txt.concat("Bola,");
                }
                if (cbBasket.isChecked()) {
                    txt = txt.concat("Basket,");
                }
                if (cbRenang.isChecked()) {
                    txt = txt.concat("Renang,");
                }
                if (cbLari.isChecked()) {
                    txt = txt.concat("Lari,");
                }
                String nbi = edNbi.getText().toString();
                String nama = edNama.getText().toString();
                String alamat = edAlamat.getText().toString();
                final String tanggal_lahir = edTglLahir.getText().toString();
                String fakultas = spFakultas.getSelectedItem().toString();
                String jurusan = spJurusan.getSelectedItem().toString();
                String jenis_kelamin = ((RadioButton) findViewById(rgKelamin.getCheckedRadioButtonId())).getText().toString();
                String kewarganegaraan = tvNegara.getText().toString();
                String hobi = txt;
                String foto = picturePath;
                String latitude = edLatitude.getText().toString();
                String longitude = edLongitude.getText().toString();
                final String tanggal_lulus = tgl_lulus.getText().toString();
                String jumlah_semester = jml_smester.getText().toString();
                String dosen_wali = dsn_wali.getText().toString();
                String lokasi = picturePath;


                if (cekinput(txt, nbi, nama, alamat, tanggal_lahir, fakultas, jurusan, jenis_kelamin, kewarganegaraan, latitude, longitude)) {
                    if (foto == "") {
                        Toast.makeText(MapActivity.this, "Masukan Gambar Kembali !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.setMessage("Mengirim Data .... ");
                        pd.setCancelable(false);
                        pd.show();

                        final ApiRequestBiodata apik = RetroServer.getClient().create(ApiRequestBiodata.class);

                        File img = new File(picturePath);
                        RequestBody regbody = RequestBody.create(MediaType.parse("image/*"), img);
                        MultipartBody.Part partimg = MultipartBody.Part.createFormData("imageupload", "image.jpg", regbody);

                        final MultipartBody.Part nbipart = MultipartBody.Part.createFormData("nbi", nbi);
                        final MultipartBody.Part namapart = MultipartBody.Part.createFormData("nama", nama);
                        final MultipartBody.Part alamatpart = MultipartBody.Part.createFormData("alamat", alamat);
                        final MultipartBody.Part tgllahirpart = MultipartBody.Part.createFormData("tanggal_lahir", tanggal_lahir);
                        final MultipartBody.Part fakultaspart = MultipartBody.Part.createFormData("fakultas", fakultas);
                        final MultipartBody.Part jurusanpart = MultipartBody.Part.createFormData("jurusan", jurusan);
                        final MultipartBody.Part jniskelpart = MultipartBody.Part.createFormData("jenis_kelamin", jenis_kelamin);
                        final MultipartBody.Part hobipart = MultipartBody.Part.createFormData("hobi", hobi);
                        final MultipartBody.Part kewarganegaraanpart = MultipartBody.Part.createFormData("kewarganegaraan", kewarganegaraan);
                        final MultipartBody.Part latitudepart = MultipartBody.Part.createFormData("latitude", latitude);
                        final MultipartBody.Part longitudepart = MultipartBody.Part.createFormData("longitude", longitude);
                        final MultipartBody.Part tanggal_luluspart = MultipartBody.Part.createFormData("tanggal_lulus", tanggal_lulus);
                        final MultipartBody.Part jumlah_semesterpart = MultipartBody.Part.createFormData("jumlah_semester", jumlah_semester);
                        final MultipartBody.Part dosen_walipart = MultipartBody.Part.createFormData("dosen_wali", dosen_wali);
                        final MultipartBody.Part lokasipart = MultipartBody.Part.createFormData("lokasi", lokasi);

                        Call<ResponsModel> sendBio = apik.sendBiodata(nbipart, namapart, alamatpart, tgllahirpart,
                                fakultaspart, jurusanpart, jniskelpart, hobipart, kewarganegaraanpart, partimg, latitudepart, longitudepart,tanggal_luluspart,jumlah_semesterpart,dosen_walipart,lokasipart);

                        sendBio.enqueue(new Callback<ResponsModel>() {
                            @Override
                            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                                pd.dismiss();
                                Log.d("RETRO", response.body().toString());
                                String kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                if (kode.equals("1")) {
                                    Log.d("RETRO", pesan);
                                    Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("RETRO", pesan);
                                    Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponsModel> call, Throwable t) {
                                pd.dismiss();
                                Toast.makeText(MapActivity.this, "Gagal Request Data "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                    builder.setTitle("Peringatan !!!");
                    builder.setMessage("Isi semua form diatas !!!");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", null);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nbi = edNbi.getText().toString();

                pd.setMessage("Memuat Data .... ");
                pd.setCancelable(false);
                pd.show();

                final ApiRequestBiodata apik = RetroServer.getClient().create(ApiRequestBiodata.class);
                Call<ModelData_load> load = apik.sendNbi(nbi);
                load.enqueue(new Callback<ModelData_load>() {
                    @Override
                    public void onResponse(Call<ModelData_load> call, Response<ModelData_load> response) {
                        pd.dismiss();
                        String kode = response.body().getKode();
                        String pesan = response.body().getPesan();
                        if (kode.equals("1")) {
                            String newnbi = response.body().getNbi();
                            String newnama = response.body().getNama();
                            String newalamat = response.body().getAlamat();
                            String newtanggal = response.body().getTanggal_lahir();
                            String newfakultas = response.body().getFakultas();
                            String newjurusan = response.body().getJurusan();
                            String newjenis_kel = response.body().getJenis_kelamin();
                            String newhobi = response.body().getHobi();
                            String newkewarga = response.body().getKewarganegaraan();
                            String newfoto = response.body().getFoto();
                            String newlat = response.body().getLatitude();
                            String newlang = response.body().getLongitude();
                            String newtanggal_lulus = response.body().getTanggal_lulus();
                            String newjumlah_semester = response.body().getJumlah_semester();
                            String newdosen_wali = response.body().getDosen_wali();
                            String newlokasi = response.body().getLokasi();

                            reset();
                            edNbi.setText(newnbi);
                            edNama.setText(newnama);
                            edAlamat.setText(newalamat);
                            edTglLahir.setText(newtanggal);
                            tgl_lulus.setText(newtanggal_lulus);
                            jml_smester.setText(newjumlah_semester);
                            dsn_wali.setText(newdosen_wali);
                            picturePath=newlokasi;

                            if (newfakultas.equals("Teknik")) {
                                spFakultas.setSelection(0);
                            } else if (newfakultas.equals("Hukum")) {
                                spFakultas.setSelection(1);
                            } else if (newfakultas.equals("Psikologi")) {
                                spFakultas.setSelection(2);
                            } else if (newfakultas.equals("Sastra")) {
                                spFakultas.setSelection(3);
                            } else if (newfakultas.equals("Ekonomi")) {
                                spFakultas.setSelection(4);
                            } else {
                                spFakultas.setSelection(5);
                            }

                            if (newjurusan.equals("Informatika")) {
                                spJurusan.setSelection(0);
                            } else if (newjurusan.equals("Sipil")) {
                                spJurusan.setSelection(1);
                            } else if (newjurusan.equals("Arsitektur")) {
                                spJurusan.setSelection(2);
                            } else if (newjurusan.equals("Mesin")) {
                                spJurusan.setSelection(3);
                            } else if (newjurusan.equals("Industri")) {
                                spJurusan.setSelection(4);
                            } else {
                                spJurusan.setSelection(5);
                            }

                            if (newjenis_kel.equals("Pria")) {
                                rgKelamin.check(r1.getId());
                            } else {
                                rgKelamin.check(r2.getId());
                            }

                            String[] hob = newhobi.split(",");
                            for (int i = 0; i < hob.length; i++) {
                                if (hob[i].equals("Bola")) {
                                    cbBola.setChecked(true);
                                } else if (hob[i].equals("Basket")) {
                                    cbBasket.setChecked(true);
                                } else if (hob[i].equals("Lari")) {
                                    cbLari.setChecked(true);
                                } else if (hob[i].equals("Renang")) {
                                    cbRenang.setChecked(true);
                                }
                            }

                            Picasso.with(MapActivity.this).load(newfoto).placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher)
                                    .into(imgFoto, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(MapActivity.this, "Gambar OK", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError() {
                                            Toast.makeText(MapActivity.this, "Gambar Failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                            tvNegara.setText(newkewarga);
                            edLatitude.setText(newlat);
                            edLongitude.setText(newlang);
                            buatmarkerall(newnbi, newnama, newlat, newlang,newjurusan,newtanggal);
                            Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelData_load> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(MapActivity.this, "Gagal Request Data", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                Toast.makeText(MapActivity.this,"Berhasil Reset", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt = "";
                if (cbBola.isChecked()) {
                    txt = txt.concat("Bola,");
                }
                if (cbBasket.isChecked()) {
                    txt = txt.concat("Basket,");
                }
                if (cbRenang.isChecked()) {
                    txt = txt.concat("Renang,");
                }
                if (cbLari.isChecked()) {
                    txt = txt.concat("Lari,");
                }
                String nbi = edNbi.getText().toString();
                String nama = edNama.getText().toString();
                String alamat = edAlamat.getText().toString();
                final String tanggal_lahir = edTglLahir.getText().toString();
                String fakultas = spFakultas.getSelectedItem().toString();
                String jurusan = spJurusan.getSelectedItem().toString();
                String jenis_kelamin = ((RadioButton) findViewById(rgKelamin.getCheckedRadioButtonId())).getText().toString();
                String kewarganegaraan = tvNegara.getText().toString();
                String hobi = txt;
                String foto = picturePath;
                String latitude = edLatitude.getText().toString();
                String longitude = edLongitude.getText().toString();
                final String tanggal_lulus = tgl_lulus.getText().toString();
                String jumlah_semester = jml_smester.getText().toString();
                String dosen_wali = dsn_wali.getText().toString();
                String lokasi = picturePath;


                if (cekinput(txt, nbi, nama, alamat, tanggal_lahir, fakultas, jurusan, jenis_kelamin, kewarganegaraan, latitude, longitude)) {
                    if (foto == "") {
                        Toast.makeText(MapActivity.this, "Masukan Gambar Kembali !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.setMessage("Update Data .... ");
                        pd.setCancelable(false);
                        pd.show();

                        final ApiRequestBiodata apik = RetroServer.getClient().create(ApiRequestBiodata.class);

                        File img = new File(picturePath);
                        RequestBody regbody = RequestBody.create(MediaType.parse("image/*"), img);
                        MultipartBody.Part partimg = MultipartBody.Part.createFormData("imageupload", "image.jpg", regbody);

                        final MultipartBody.Part nbipart = MultipartBody.Part.createFormData("nbi", nbi);
                        final MultipartBody.Part namapart = MultipartBody.Part.createFormData("nama", nama);
                        final MultipartBody.Part alamatpart = MultipartBody.Part.createFormData("alamat", alamat);
                        final MultipartBody.Part tgllahirpart = MultipartBody.Part.createFormData("tanggal_lahir", tanggal_lahir);
                        final MultipartBody.Part fakultaspart = MultipartBody.Part.createFormData("fakultas", fakultas);
                        final MultipartBody.Part jurusanpart = MultipartBody.Part.createFormData("jurusan", jurusan);
                        final MultipartBody.Part jniskelpart = MultipartBody.Part.createFormData("jenis_kelamin", jenis_kelamin);
                        final MultipartBody.Part hobipart = MultipartBody.Part.createFormData("hobi", hobi);
                        final MultipartBody.Part kewarganegaraanpart = MultipartBody.Part.createFormData("kewarganegaraan", kewarganegaraan);
                        final MultipartBody.Part latitudepart = MultipartBody.Part.createFormData("latitude", latitude);
                        final MultipartBody.Part longitudepart = MultipartBody.Part.createFormData("longitude", longitude);
                        final MultipartBody.Part tanggal_luluspart = MultipartBody.Part.createFormData("tanggal_lulus", tanggal_lulus);
                        final MultipartBody.Part jumlah_semesterpart = MultipartBody.Part.createFormData("jumlah_semester", jumlah_semester);
                        final MultipartBody.Part dosen_walipart = MultipartBody.Part.createFormData("dosen_wali", dosen_wali);
                        final MultipartBody.Part lokasipart = MultipartBody.Part.createFormData("lokasi", lokasi);
                        Call<ResponsModel> editBio = apik.editBiodata(nbipart, namapart, alamatpart, tgllahirpart,
                                fakultaspart, jurusanpart, jniskelpart, hobipart, kewarganegaraanpart, partimg, latitudepart, longitudepart,tanggal_luluspart,jumlah_semesterpart,dosen_walipart,lokasipart);

                        editBio.enqueue(new Callback<ResponsModel>() {
                            @Override
                            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                                pd.dismiss();
                                Log.d("RETRO", response.body().toString());
                                String kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                if (kode.equals("1")) {
                                    Log.d("RETRO", pesan);
                                    Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("RETRO", pesan);
                                    Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponsModel> call, Throwable t) {
                                pd.dismiss();
                                Toast.makeText(MapActivity.this, "Gagal Request Data "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                    builder.setTitle("Peringatan !!!");
                    builder.setMessage("Isi semua form diatas !!!");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", null);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Menghapus Data ....");
                pd.setCancelable(false);
                pd.show();

                String nbi = edNbi.getText().toString();
                final ApiRequestBiodata apik = RetroServer.getClient().create(ApiRequestBiodata.class);
                Call<ResponsModel> hapusBio = apik.hapusBiodata(nbi);
                hapusBio.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        pd.dismiss();
                        String kode = response.body().getKode();
                        String pesan = response.body().getPesan();
                        if(kode.equals("1")) {
                            Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(MapActivity.this, "Gagal Request Data "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loadall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Loading Marker ....");
                pd.setCancelable(false);
                pd.show();

                final ApiRequestBiodata apik = RetroServer.getClient().create(ApiRequestBiodata.class);
                Call<getdatamark> getmarker = apik.loadMarker();

                getmarker.enqueue(new Callback<getdatamark>() {
                    @Override
                    public void onResponse(Call<getdatamark> call, Response<getdatamark> response) {
                        pd.dismiss();
                        String kode = response.body().getKode();
                        if (kode.equals("1")) {
                            reset();
                            data = response.body().getResult();
                            if (marker != null) {
                                marker.remove();
                                mMap.clear();
                            }
                            for (int i = 0; i < data.size(); i++) {
                                String nbi = data.get(i).getNbi();
                                String nama = data.get(i).getNama();
                                String lat = data.get(i).getLatitude();
                                String lang = data.get(i).getLongitude();
                                String tgl_lahir = data.get(i).getTanggal_lahir();
                                String jurusan = data.get(i).getJurusan();
                                buatmarkerall(nbi, nama, lat, lang,jurusan,tgl_lahir);
                            }
                        } else {
                            reset();
                            String pesan = response.body().getPesan();
                            Toast.makeText(MapActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<getdatamark> call, Throwable t) {
                        reset();
                        pd.dismiss();
                        Toast.makeText(MapActivity.this, "Gagal Request Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void buatmarkerall(String nbi, String nama, String lat, String lang,String jurusan,String tgl_lahir) {
        double nlat = Double.parseDouble(lat);
        double nlang = Double.parseDouble(lang);
        LatLng latLng = new LatLng(nlat, nlang);
        marker = mMap.addMarker(new MarkerOptions().position(latLng));
        marker.setTitle(nama + "(" + nbi + "," + tgl_lahir +"," + jurusan+")");
    }

    public void reset() {
        edNbi.setText("");
        edNama.setText("");
        edAlamat.setText("");
        edTglLahir.setText("");
        tgl_lulus.setText("");
        jml_smester.setText("");
        dsn_wali.setText("");
        spFakultas.setSelection(0);
        spJurusan.setSelection(0);
        rgKelamin.check(r1.getId());
        cbBola.setChecked(false);
        cbRenang.setChecked(false);
        cbBasket.setChecked(false);
        cbLari.setChecked(false);
        tvNegara.setText("Belum Memilih");
        getlokasi();
        Drawable draw = getResources().getDrawable(R.drawable.abingkai);
        imgFoto.setImageDrawable(draw);
        picturePath = "";
        clearmarker();
    }

    public boolean cekinput(String txt, String nbi, String nama, String alamat, String tanggal_lahir, String fakultas,
                            String jurusan, String jenis, String kewarga, String latitude, String longitude) {

        if (txt == "" || nbi.equals("") || nama.equals("") || alamat.equals("") || tanggal_lahir.equals("") || fakultas == "" ||
                jurusan == "" || jenis == "" || kewarga == "" || latitude == "" || longitude == "") {

            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ImageView ii = (ImageView) findViewById(R.id.imgFoto);
                ii.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearmarker() {
        if (marker != null) {
            marker.remove();
            mMap.clear();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map Sudah Siap", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
//      mMap.setBuildingsEnabled(true);
//      mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        getlokasi();

    }

    public void getlokasi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates("gps",2000,0,locationListener);

        // Membuat sebuah criteria object untuk mengambil provider
        Criteria criteria = new Criteria();

        // Mendapatkan nama dari provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Mendapatkan Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location != null) {
            final double latitude = location.getLatitude();

            // Mendapatkan longitude dari current location
            final double longitude = location.getLongitude();

            edLatitude.setText(Double.toString(latitude));
            edLongitude.setText(Double.toString(longitude));

            // Membuat sebuah LatLng object untuk current location
            LatLng latLng = new LatLng(latitude, longitude);

            // Menampilkan current location di Google Map
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(8)
                    .tilt(0) // Memiringkan sudut kamera
                    .build(); // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }



    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }
    public class JurusanOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void showDateDialog() {

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                edTglLahir.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    private void showDate2Dialog() {

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tgl_lulus.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_dat:
                datamhs();
                return true;
            case R.id.it_exit:
                exit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void datamhs(){
        Intent intent = new Intent(this,data_list_mahasiswa.class);
        startActivity(intent);
    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Kamu Benar-Benar ingin Keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MapActivity.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void getLocationPermission() {

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permission = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1234);
            }
        }
        else {
            ActivityCompat.requestPermissions(this, permissions, 1234);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permission = false;
        switch (requestCode){
            case 1234:{
                if (grantResults.length > 0){
                    for (int i=0;i< grantResults.length;i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            permission=false;
                            return;
                        }
                    }
                    permission =true;
                    initMap();
                }
            }
        }
    }
}
