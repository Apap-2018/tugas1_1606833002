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

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addRowJabatan"})
	private String addRowJabatan (@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult , Model model) {
		if(pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList<JabatanModel>());
		}
		
		pegawai.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listSemuaJabatan", jabatanService.getAll());
		return "add-Pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
	private String addPegawaiSubmit (@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.createNIP(pegawai);
		pegawaiService.addPegawai(pegawai);
		return "add";
	}
	
	
	@RequestMapping(value = "/pegawai/cari")
	private String cari(@RequestParam(value = "idProvinsi", required=false) String idProvinsi,
			@RequestParam(value="idInstansi",  required=false) String id_instansi,
			@RequestParam(value="idJabatan", required=false) String id_jabatan,
			Model model) {
		
		return "cari-Pegawai";
	}
	
	
	
	
}
