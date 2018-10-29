package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "add";
	}
	
	@RequestMapping(value = "/jabatan/view")
	private String view(@RequestParam(value="idJabatan") long id_jabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id_jabatan);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String ubah(@RequestParam(value="idJabatan") long id_jabatan, Model model) {
		model.addAttribute("jabatan", jabatanService.getJabatanDetailById(id_jabatan));
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah/", method = RequestMethod.POST)
	private String ubahSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "update";
	}

	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String hapus(@RequestParam(value="idJabatan") long id_jabatan, Model model) {
		jabatanService.deleteJabatanById(id_jabatan);
		return "delete";
	}
	
	@RequestMapping(value="/jabatan/viewall")
	private String viewall(Model model) {
		model.addAttribute("listJabatan", jabatanService.getAll());
		return "viewall-jabatan";
	}
}
