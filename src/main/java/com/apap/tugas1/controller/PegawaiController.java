package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("listSemuaJabatan", jabatanService.getAll());
		model.addAttribute("listInstansi", instansiService.getAll());
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPilot (@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		model.addAttribute("nip", nip);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", pegawai.getJabatanList());
		model.addAttribute("gaji", pegawaiService.countGaji(pegawai));
		return "view-pegawai";
	}
	
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add (Model model) {
		JabatanModel jabatan = new JabatanModel();
		PegawaiModel pegawai = new PegawaiModel();
		ArrayList<JabatanModel> listJabatan = new ArrayList<JabatanModel>();
		listJabatan.add(jabatan);
		pegawai.setJabatanList(listJabatan);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listSemuaJabatan", jabatanService.getAll());
		model.addAttribute("listInstansi", instansiService.getAll());
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String update (@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listSemuaJabatan", jabatanService.getAll());
		model.addAttribute("listInstansi", instansiService.getAll());
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"submit"})
	private String updatePegawaiSubmit (@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.createNIP(pegawai);
		
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("NIP", pegawai.getNip());
		return "update";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addRowJabatan"})
	private String addRowJabatan (@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult , Model model) {
		if(pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList<JabatanModel>());
		}
		
		pegawai.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listSemuaJabatan", jabatanService.getAll());
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"addRowJabatan"})
	private String updateAddRowJabatan (@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult , Model model) {
		if(pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList<JabatanModel>());
		}
		
		JabatanModel jabatan = new JabatanModel();
		jabatan.setNama("pilih Jabatan");
		jabatan.setDeskripsi("");
		jabatan.setGajiPokok((double)0);
		jabatan.setPegawaiList(new ArrayList<PegawaiModel>());
		jabatan.setId(999999);
		
		
		pegawai.getJabatanList().add(jabatan);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listSemuaJabatan", jabatanService.getAll());
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="provinsiId") Long provinsiId) {
		ProvinsiModel provinsi = provinsiService.getProvinsiDetailbyId(provinsiId);
		return instansiService.getInstansiDetailByProvinsi(provinsi);
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
	private String addPegawaiSubmit (@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.createNIP(pegawai);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("NIP", pegawai.getNip());
		return "add";
	}
	
	
	@RequestMapping(value = "/pegawai/cari")
	private String cari(@RequestParam(value = "idProvinsi", required=false) String idProvinsi,
			@RequestParam(value="idInstansi",  required=false) String id_instansi,
			@RequestParam(value="idJabatan", required=false) String id_jabatan,
			Model model) {
		
		
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listJabatan", jabatanService.getAll());
		model.addAttribute("listInstansi", instansiService.getAll());
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua")
	private String termudaTertua(@RequestParam(value = "idInstansi") Long id_instansi, Model model){
		InstansiModel instansi = instansiService.getInstansiDetailById(id_instansi);
		PegawaiModel tua = pegawaiService.getPegawaiTuaInstansi(instansi);
		PegawaiModel muda = pegawaiService.getPegawaiMudaInstansi(instansi);
		
		model.addAttribute("tua", tua);
		model.addAttribute("muda", muda);
		return "tua-muda-pegawai";
	}
	
	
}
