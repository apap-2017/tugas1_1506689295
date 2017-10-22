package com.example.tp1.service;

import com.example.tp1.model.KeluargaModel;


public interface KeluargaService 
{
	KeluargaModel selectKeluarga (String nkk);
	
	KeluargaModel getKeluarga (String nkk);
	
	KeluargaModel getKeluargaId (long id);
	
	KeluargaModel selectKeluargaIni (String nkk);
	
	void addKeluarga(KeluargaModel keluarga);
	
	void updateKeluarga(KeluargaModel keluarga);
	
	void updateStatus(String nkk);
	
}
