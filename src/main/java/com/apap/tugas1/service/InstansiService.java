package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	List<InstansiModel> getAll();
	
	List<InstansiModel> getInstansiDetailByProvinsi(ProvinsiModel provinsi);

}
