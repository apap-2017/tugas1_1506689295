package com.example.tp1.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PendudukModel
{
    private long id;
    private long id_keluarga;
    private long id_keluarga_former;
    private String nik;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    private int jenis_kelamin;
    private String golongan_darah;
    private String agama;
    private String alamat;
    private String rt;
    private String rw;
    private String nama_kelurahan;
    private String nama_kecamatan;
    private String nama_kota;
    private String status_perkawinan;
    private String pekerjaan;
    public int is_wni;
    public int is_wafat;
    private String status_dalam_keluarga;

}