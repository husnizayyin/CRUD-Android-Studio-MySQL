package com.project.husni.uasptm.model;

import java.util.List;

public class getdatamark {
    String kode,pesan;
    List<ModelData_marker> result;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<ModelData_marker> getResult() {
        return result;
    }

    public void setResult(List<ModelData_marker> result) {
        this.result = result;
    }
}
