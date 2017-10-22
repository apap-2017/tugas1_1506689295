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
public interface KeluargaMapper 
{
	@Select("select kl.id, kl.nomor_kk, kl.alamat, kl.rt, kl.rw, ku.nama_kelurahan, kc.nama_kecamatan, ko.nama_kota, "
			+ "kl.id_kelurahan, kl.is_tidak_berlaku "
			+ "from keluarga kl, kelurahan ku, kecamatan kc, kota ko "
			+ "where nomor_kk = #{nkk} "
			+ "and kl.id_kelurahan = ku.id and ku.id_kecamatan = kc.id and kc.id_kota = ko.id ")
	@Results(value = {
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="rt"),
    		@Result(property="rw", column="rw"),
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="nama_kecamatan", column="nama_kecamatan"),
    		@Result(property="nama_kota", column="nama_kota"),
    		@Result(property="id_kelurahan", column="id_kelurahan"),
    		@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
    		@Result(property="anggota_keluarga", column="id",
    				javaType = List.class,
    				many=@Many(select="selectFamilyMembers"))
    })
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("select * from keluarga where nomor_kk like (concat(#{nkk},'%')) order by id desc limit 1")
	KeluargaModel selectKeluargaIni(@Param("nkk") String nkk);
	
	@Select("select p.nama, p.nik, p.jenis_kelamin, p.tempat_lahir, p.tanggal_lahir, p.agama, p.pekerjaan, "
			+ "p.status_perkawinan, p.status_dalam_keluarga, p.is_wni "
			+ "from penduduk p, keluarga kl "
			+ "where p.id_keluarga = #{id} and p.id_keluarga = kl.id ")
	List<PendudukModel> selectFamilyMembers (@Param("id") String id_keluarga);
	
	@Select("select * from keluarga where nomor_kk = #{nkk}")
	KeluargaModel getKeluarga(@Param("nkk") String nkk);
	
	@Select("select * from keluarga where id = #{id}")
	KeluargaModel getKeluargaId(@Param("id") long id);
	
	@Insert("INSERT INTO keluarga (nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) "
			+ "VALUES (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga(KeluargaModel keluarga);
	
	@Update("update keluarga set nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = "
			+ "#{rw}, id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} "
			+ "where id = #{id}")
    void updateKeluarga(KeluargaModel keluarga);
	
	@Update("update keluarga set is_tidak_berlaku = 1 where nomor_kk = #{nomor_kk}")
    void updateStatus(@Param("nomor_kk") String nomor_kk);
}


