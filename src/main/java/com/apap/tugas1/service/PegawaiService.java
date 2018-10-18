package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

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
}
