package com.example.tp1.service;

import com.example.tp1.model.KelurahanModel;

public interface KelurahanService 
{
	String selectIdKelurahan(String nama_kelurahan);
	
	KelurahanModel getKelurahan(String id);
	
}	
