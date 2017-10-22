package com.example.tp1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KotaModel {
	private long id;
	private String kode_kota;
	private String nama_kota;
}
