package com.example.tp1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KeluargaModel 
{
	private long id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
	private String id_kelurahan;
	private int is_tidak_berlaku;
	private List<PendudukModel> anggota_keluarga;
}
