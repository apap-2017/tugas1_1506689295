package com.example.tp1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp1.dao.KeluargaMapper;
import com.example.tp1.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class KeluargaServiceDatabase implements KeluargaService
{
	@Autowired
    private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluarga(String nkk) 
	{
		log.info ("select keluarga with nkk {}", nkk);
		return keluargaMapper.selectKeluarga (nkk);
	}
	
	@Override
	public void addKeluarga(KeluargaModel keluarga)
	{
		log.info ("keluarga " + keluarga.getNomor_kk() + " added");
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public KeluargaModel selectKeluargaIni(String nkk) 
	{
		log.info ("keluarga dengan " + nkk + " selected");
		return keluargaMapper.selectKeluargaIni(nkk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		log.info ("keluarga " + keluarga.getNomor_kk() + " updated");
		keluargaMapper.updateKeluarga(keluarga);
	}

	@Override
	public KeluargaModel getKeluarga(String nkk) {
		log.info ("keluarga dengan " + nkk + " selected");
		return keluargaMapper.getKeluarga(nkk);
	}

	@Override
	public KeluargaModel getKeluargaId(long id) {
		log.info ("keluarga dengan id" + id + " selected");
		return keluargaMapper.getKeluargaId(id);
	}

	@Override
	public void updateStatus(String nkk) {
		log.info ("keluarga dengan " + nkk + " updated to not valid");
		keluargaMapper.updateStatus(nkk);
	}
	
}
