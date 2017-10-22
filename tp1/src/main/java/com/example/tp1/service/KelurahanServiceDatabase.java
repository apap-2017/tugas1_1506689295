package com.example.tp1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp1.dao.KelurahanMapper;
import com.example.tp1.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class KelurahanServiceDatabase implements KelurahanService
{
	@Autowired
    private KelurahanMapper kelurahanMapper;

	@Override
	public String selectIdKelurahan(String nama_kelurahan) 
	{
		log.info ("kelurahan " + nama_kelurahan + " selected");
		return kelurahanMapper.selectIdKelurahan(nama_kelurahan);
		
	}

	@Override
	public KelurahanModel getKelurahan(String id) {
		log.info ("kelurahan " + id + " selected");
		return kelurahanMapper.getKelurahan(id);
	}

	
	

}
