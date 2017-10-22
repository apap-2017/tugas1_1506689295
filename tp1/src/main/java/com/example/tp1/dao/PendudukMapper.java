package com.example.tp1.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tp1.model.PendudukModel;
import com.example.tp1.model.KeluargaModel;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

@Mapper
public interface PendudukMapper 
{
	@Select("select nik, id_keluarga, nama, tempat_lahir, tanggal_lahir, golongan_darah, agama, status_perkawinan, "
			+ "pekerjaan, is_wni, is_wafat from penduduk where nik = #{nik}")
	@Results(value = {
    		@Result(property="nik", column="nik"),
    		@Result(property="nama", column="nama"),
    		@Result(property="tempat_lahir", column="tempat_lahir"),
    		@Result(property="golongan_darah", column="golongan_darah"),
    		@Result(property="agama", column="agama"),
    		@Result(property="status_perkawinan", column="status_perkawinan"),
    		@Result(property="pekerjaan", column="pekerjaan"),
    		@Result(property="is_wni", column="is_wni"),
    		@Result(property="is_wafat", column="is_wafat"),
    		@Result(property="keluarga", column="id_keluarga",
    				javaType = List.class,
    				many=@Many(select="selectThisFamily"))
    })
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select kl.id, kl.nomor_kk, kl.alamat, kl.rt, kl.rw, ku.nama_kelurahan, "
			+ "kc.nama_kecamatan, ko.nama_kota "
			+ "from penduduk p, keluarga kl, kelurahan ku, kecamatan kc, kota ko " 
			+ "where p.id_keluarga = #{id_keluarga} and p.id_keluarga = kl.id "
			+ "and kl.id_kelurahan = ku.id and ku.id_kecamatan = kc.id and kc.id_kota = ko.id")
	List<KeluargaModel> selectThisFamily (@Param("id_keluarga") String id_keluarga);
	
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, "
			+ "is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, "
			+ "golongan_darah, is_wafat) VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, "
			+ "#{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, "
			+ "#{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void addPenduduk(PendudukModel penduduk);
	
	@Update("update penduduk set nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = "
			+ "#{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, "
			+ "agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah} where id = #{id}")
    void updatePenduduk(PendudukModel penduduk);
	
	@Update("update penduduk set is_wafat = 1, id_keluarga = 0 where nik = #{nik}")
    void setPendudukDeath(PendudukModel penduduk);
	
	@Select("select * from penduduk where nik like (concat(#{nik},'%')) order by id desc limit 1")
    PendudukModel getNik (@Param("nik") String nik);
	
	@Select("select * from penduduk where nik = #{nik}")
    PendudukModel getPenduduk (@Param("nik") String nik);
}


