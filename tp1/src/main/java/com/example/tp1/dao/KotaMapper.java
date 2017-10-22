package com.example.tp1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tp1.model.KotaModel;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

@Mapper
public interface KotaMapper {
	
	@Select("select kode_kota from kota where nama_kota = #{nama_kota}")
	 String selectKodeKota (@Param("nama_kota") String nama_kota);
	
	@Select("select * from kota where id = #{id}")
	KotaModel getKota (@Param("id") String id);
}
