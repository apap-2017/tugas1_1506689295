package com.example.tp1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp1.dao.KotaMapper;
import com.example.tp1.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class KotaServiceDatabase implements KotaService
{
	@Autowired
    private KotaMapper kotaMapper;

	@Override
	public String selectKodeKota(String nama_kota) {
		log.info ("Kota " + nama_kota + " selected");
		return kotaMapper.selectKodeKota(nama_kota);
	}

	@Override
	public KotaModel getKota(String id) {
		log.info ("Kota dengan id" + id + " selected");
		return kotaMapper.getKota(id);
	}
}
