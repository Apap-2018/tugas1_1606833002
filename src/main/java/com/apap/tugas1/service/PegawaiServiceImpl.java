package com.apap.tugas1.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	private InstansiService instansiService;
	
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
			double gajiBaru = (gajiPokok + (gajiPokok * persentase / 100));
			if (gaji < gajiBaru) {
				gaji = gajiBaru;
			}
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

	/**
	 * listAwal adalah list pegawai yang sudah difilter berdasarkan jabatannya
	 */
	@Override
	public List<PegawaiModel> filterPegawai(ProvinsiModel provinsi, InstansiModel instansi, List<PegawaiModel> listAwal) {
		List<PegawaiModel> hasilFilter = new ArrayList<PegawaiModel>();
		
		if (provinsi != null) {
			
			if (instansi != null) {
				// filter by provinsi + instansi
				// cukup dicari yang punya instansi yang sama
				// karena instansi to provinsi = many to one
				hasilFilter = filterByInstansi(instansi, listAwal);
			}
			else {
				// filter by provinsi
				List<InstansiModel> listInstansi = instansiService.getInstansiDetailByProvinsi(provinsi);
				for (InstansiModel instansiProv : listInstansi) {
					hasilFilter.addAll(filterByInstansi(instansiProv, listAwal));
					
				}
				
				
			}
			
		} else {
			
			if (instansi != null) {
				// filter by instansi
				hasilFilter = filterByInstansi(instansi, listAwal);
				
			}
			else {
				hasilFilter = listAwal;
			}
			
		}
		
		return hasilFilter;
	}
	
	private List<PegawaiModel> filterByInstansi (InstansiModel instansi, List<PegawaiModel> listPegawai){
		List<PegawaiModel> hasilFilter = new ArrayList<PegawaiModel>();
		for (PegawaiModel pegawai : listPegawai) {
			if (pegawai.getInstansi().getId() == instansi.getId())
				hasilFilter.add(pegawai);
		}
		return hasilFilter;
	}

	@Override
	public List<PegawaiModel> getAllPegawai() {
		return pegawaiDB.findAll();
	}
	
	
	
}
