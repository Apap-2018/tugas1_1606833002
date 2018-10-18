package com.apap.tugas1.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

/**
 * 
 * PegawaiDB
 * @author ivanabdurrahman
 *
 */
@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findByNip(String nip);
	
	ArrayList<PegawaiModel> findByInstansi (InstansiModel instansi);
	
	PegawaiModel findFirstByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	
	PegawaiModel findFirstByInstansiOrderByTanggalLahirDesc(InstansiModel instansi);
}
