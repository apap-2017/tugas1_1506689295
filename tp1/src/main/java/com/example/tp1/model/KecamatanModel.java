package com.example.tp1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KecamatanModel
{
	private long id;
	private String kode_kecamatan;
	private String id_kota;
	private String nama_kecamatan;
}
