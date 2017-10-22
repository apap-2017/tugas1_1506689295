package com.example.tp1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tp1.model.KelurahanModel;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

@Mapper
public interface KelurahanMapper {
	
	 @Select("select id from kelurahan where nama_kelurahan = #{nama_kelurahan}")
	 String selectIdKelurahan (@Param("nama_kelurahan") String nama_kelurahan);
	 
	 @Select("select * from kelurahan where id = #{id}")
	 KelurahanModel getKelurahan (@Param("id") String id);
}
