package com.example.tp1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tp1.model.KecamatanModel;
import com.example.tp1.model.KelurahanModel;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

@Mapper
public interface KecamatanMapper {
	
	@Select("select kode_kecamatan from kecamatan where nama_kecamatan = #{nama_kecamatan}")
	 String selectKodeKecamatan (@Param("nama_kecamatan") String nama_kecamatan);
	
	@Select("select nama_kecamatan from kecamatan where kode_kecamatan = #{kode_kecamatan}")
	 String selectNamaKecamatan (@Param("kode_kecamatan") String kode_kecamatan);
	
	
	@Select("select * from kecamatan where id = #{id}")
	 KecamatanModel getKecamatan (@Param("id") String id);
	

}
