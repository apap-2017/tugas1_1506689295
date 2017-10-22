package com.example.tp1.service;

import com.example.tp1.model.PendudukModel;

public interface PendudukService 
{
	PendudukModel selectPenduduk (String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	void updatePenduduk(PendudukModel penduduk);
	
	void setPendudukDeath(PendudukModel penduduk);
	
	PendudukModel getNik(String nik);
	
	PendudukModel getPenduduk(String nik);
	
}	
