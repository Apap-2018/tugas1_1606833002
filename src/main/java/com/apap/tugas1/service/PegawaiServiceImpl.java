package com.apap.tugas1.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDB pegawaiDB;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDB.findByNip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDB.save(pegawai);
	}

	@Override
	public int countGaji(PegawaiModel pegawai) {
		double gaji = 0;
		
		InstansiModel instansi = pegawai.getInstansi();
		ProvinsiModel provinsi = instansi.getProvinsi();
		double persentase = provinsi.getPresentaseTunjangan();
		
		for(JabatanModel jabatan : pegawai.getJabatanList()) {
			double gajiPokok = jabatan.getGajiPokok();
			gaji += (gajiPokok + (gajiPokok * persentase / 100));
		}
		
		return (int)gaji;
	}

	public String codeNIP(PegawaiModel pegawai) {
		String nip = "";
		nip += pegawai.getInstansi().getId();
		
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		String kodeDate = format.format(pegawai.getTanggalLahir());
		nip += kodeDate;
		
		nip += pegawai.getTahunMasuk();
		
		
		int urutan = 1;
		ArrayList<PegawaiModel> listPegawai = pegawaiDB.findByInstansi(pegawai.getInstansi());
		for (PegawaiModel oPegawai : listPegawai) {
			if (pegawai.getTanggalLahir().equals(oPegawai.getTanggalLahir()))
				urutan++;
		}
		
		DecimalFormat formatter = new DecimalFormat("00");
		String kodeUrut = formatter.format(urutan);
		nip += kodeUrut;
		return nip;
	}

	@Override
	public void createNIP(PegawaiModel pegawai) {
		pegawai.setNip(codeNIP(pegawai));
	}

	@Override
	public PegawaiModel getPegawaiTuaInstansi(InstansiModel instansi) {
		return pegawaiDB.findFirstByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public PegawaiModel getPegawaiMudaInstansi(InstansiModel instansi) {
		return pegawaiDB.findFirstByInstansiOrderByTanggalLahirDesc(instansi);
	}
	
	
	
}
