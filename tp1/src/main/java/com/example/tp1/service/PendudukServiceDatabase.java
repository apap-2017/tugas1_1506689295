package com.example.tp1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp1.dao.PendudukMapper;
import com.example.tp1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class PendudukServiceDatabase implements PendudukService
{
	@Autowired
    private PendudukMapper pendudukMapper;
	
	@Override
    public PendudukModel selectPenduduk (String nik)
    {
        log.info ("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk (nik);
    }

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info ("penduduk " + penduduk.getNik() + " added");
		pendudukMapper.addPenduduk(penduduk);
	}
	
	@Override
	public PendudukModel getNik(String nik) {
		log.info ("penduduk " + nik + " selected");
		return pendudukMapper.getNik(nik);
	}

	@Override
	public PendudukModel getPenduduk(String nik) {
		log.info ("penduduk " + nik + " selected");
		return pendudukMapper.getPenduduk(nik);
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		log.info("penduduk " + penduduk.getNik() + " updated");
		pendudukMapper.updatePenduduk(penduduk);
	}

	@Override
	public void setPendudukDeath(PendudukModel penduduk) {
		log.info("penduduk " + penduduk.getNik()  + " updated to death");
		pendudukMapper.setPendudukDeath(penduduk);
		
	}
}
