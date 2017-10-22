package com.example.tp1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp1.dao.KecamatanMapper;
import com.example.tp1.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class KecamatanServiceDatabase implements KecamatanService
{
	@Autowired
    private KecamatanMapper kecamatanMapper;
	
	@Override
	public String selectKodeKecamatan(String nama_kecamatan) {
		log.info ("kecamatan " + nama_kecamatan + " selected");
		return kecamatanMapper.selectKodeKecamatan(nama_kecamatan);
	}

	@Override
	public String selectNamaKecamatan(String kode_kecamatan) {
		log.info ("kecamatan " + kode_kecamatan + " selected");
		return kecamatanMapper.selectNamaKecamatan(kode_kecamatan);
	}

	@Override
	public KecamatanModel getKecamatan(String id) {
		log.info ("kecamatan dengan id " + id + " selected");
		return kecamatanMapper.getKecamatan(id);
	}


}
