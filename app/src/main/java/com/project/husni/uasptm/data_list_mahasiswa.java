package com.project.husni.uasptm;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.husni.uasptm.R;
import com.project.husni.uasptm.api.ApiRequestBiodata;
import com.project.husni.uasptm.api.RetroServer;
import com.project.husni.uasptm.model.ModelData_marker;
import com.project.husni.uasptm.model.getdatamark;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class data_list_mahasiswa extends AppCompatActivity {

    ListView listView;
    Custom custom;
    private List<ModelData_marker> data = new ArrayList<>();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list_mahasiswa);

        pd = new ProgressDialog(this);
        pd.setMessage("Mendapatkan Data ....");
        pd.setCancelable(false);
        pd.show();

        listView = (ListView) findViewById(R.id.list);
        final ApiRequestBiodata apik = RetroServer.getClient().create(ApiRequestBiodata.class);
        Call<getdatamark> getmarker = apik.loadMarker();

        getmarker.enqueue(new Callback<getdatamark>() {
            @Override
            public void onResponse(Call<getdatamark> call, Response<getdatamark> response) {
                pd.dismiss();
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                if(kode.equals("1")){
                    data = response.body().getResult();
                    custom  = new Custom();
                    listView.setAdapter(custom);
                }else{
                    Toast.makeText(data_list_mahasiswa.this,pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getdatamark> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(data_list_mahasiswa.this,"Gagal Request Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class Custom extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom,null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            TextView text_nama = (TextView) convertView.findViewById(R.id.textView_nama);
            TextView text_nbi = (TextView) convertView.findViewById(R.id.textView_nbi);

            Picasso.with(data_list_mahasiswa.this).load(data.get(position).getFoto()).into(imageView);
            text_nama.setText(data.get(position).getNama());
            text_nbi.setText(data.get(position).getNbi());
            return convertView;
        }
    }
}
