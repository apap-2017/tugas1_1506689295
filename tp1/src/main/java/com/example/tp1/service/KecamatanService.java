package com.example.tp1.service;

import com.example.tp1.model.KecamatanModel;

public interface KecamatanService 
{
	String selectKodeKecamatan(String nama_kecamatan);
	
	String selectNamaKecamatan(String kode_kecamatan);
	
	KecamatanModel getKecamatan(String id);
	
	
}	
