package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDB jabatanDB;

	@Override
	public JabatanModel getJabatanDetailById(Long id) {
		return jabatanDB.getOne(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDB.save(jabatan);
		
	}

	@Override
	public List<JabatanModel> getAll() {
		return jabatanDB.findAll();
	}

	@Override
	public void deleteJabatanById(Long id) {
		jabatanDB.deleteById(id);
		
	}

}
