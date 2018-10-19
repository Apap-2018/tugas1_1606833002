package com.apap.tugas1.service;

import java.util.List;

import org.springframework.lang.Nullable;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

/**
 * 
 * PegawaiService
 * @author ivanabdurrahman
 *
 */
public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip (String nip);
	
	void addPegawai(PegawaiModel pegawai);
	
	int countGaji(PegawaiModel pegawai);
	
	void createNIP(PegawaiModel pegawai);
	
	PegawaiModel getPegawaiTuaInstansi (InstansiModel instansi);
	
	PegawaiModel getPegawaiMudaInstansi (InstansiModel instansi);
	
	List<PegawaiModel> filterPegawai(@Nullable ProvinsiModel provinsi, @Nullable InstansiModel instansi, List<PegawaiModel> listAwal);
	
	List<PegawaiModel> getAllPegawai();
}
