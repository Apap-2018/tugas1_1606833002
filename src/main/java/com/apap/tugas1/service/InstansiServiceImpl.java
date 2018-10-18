package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDB instansiDB;

	@Override
	public List<InstansiModel> getAll() {
		return instansiDB.findAll();
	}

	@Override
	public List<InstansiModel> getInstansiDetailByProvinsi(ProvinsiModel provinsi) {
		return instansiDB.findByProvinsi(provinsi);
	}

	@Override
	public InstansiModel getInstansiDetailById(Long id) {
		return instansiDB.findById(id).get();
	}

}
