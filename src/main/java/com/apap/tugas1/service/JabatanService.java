package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanDetailById(Long id);
	
	void addJabatan(JabatanModel jabatan);
	
	List<JabatanModel> getAll();
	
	void deleteJabatanById(Long id);

}
