package com.example.tp1.service;

import com.example.tp1.model.KotaModel;

public interface KotaService 
{
	String selectKodeKota(String nama_kota);
	
	KotaModel getKota(String id);
	
}	
