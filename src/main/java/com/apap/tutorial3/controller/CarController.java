package com.apap.tutorial3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "brand", required = true) String brand, @RequestParam(value = "type", required = true) String type, @RequestParam(value = "price", required = true) Long price, @RequestParam(value = "amount", required = true) Integer amount) {
		CarModel car = new CarModel(id, brand, type, price, amount);
		mobilService.addCar(car);
		return "add";
	}
	
//	@RequestMapping("/car/view")
//	public String view(@RequestParam("id") String id, Model model) {
//		CarModel archive = mobilService.getCarDetail(id);
//		
//		model.addAttribute("car", archive);
//		return "view-car";
//	}
	
	@RequestMapping("/car/viewall")
	public String viewall(Model model) {
		List<CarModel> archive = mobilService.getCarList();
		
		model.addAttribute("listCar", archive);
		return "viewall-car";
	}
	
	@RequestMapping(value = {"/car/view","/car/view/{id}"})
	public String viewCar(@PathVariable Optional<String> id, Model model){
		if (id.isPresent()) {
			CarModel archive = mobilService.getCarDetail(id.get());
			if (archive!=null) {
				model.addAttribute("car", archive);
				return "view-car";
			}else {
				model.addAttribute("errortype", "ID Tidak Ditemukan!");
			}
		}else {
			model.addAttribute("errortype", "Nomor ID Kosong, Isi Dulu IDnya yaa!");
		}
		return "errorview";
	}
	
	@RequestMapping(value = {"/car/update","/car/update/{id}/amount/{amount}"})
	public String updateAmount(@PathVariable Optional<String> id, @PathVariable Optional<Integer> amount, Model model) {
		if (id.isPresent()) {
			model.addAttribute("info", null);
			CarModel carNow = mobilService.getCarDetail(id.get());
			if (carNow!=null) {
				carNow.setAmount(amount.get());
				model.addAttribute("info", "Data Berhasil Diupdate");
				model.addAttribute("car", carNow);
				return "update-amount";
			}else {
				model.addAttribute("errortype", "ID Tidak Ditemukan! Gagal Update");
			}
		}
		else{
			model.addAttribute("errortype", "Nomor ID Kosong, Isi Dulu IDnya yaa!");
		}
		return "errorview";
	}
	
	@RequestMapping(value = {"/car/delete","/car/delete/{id}"})
	public String deleteCar(@PathVariable Optional<String> id, Model model) {
		if (id.isPresent()) {
			String carNow = mobilService.deleteCar(id.get());
			if (carNow!=null) {
				model.addAttribute("info", "Data Berhasil Dihapus");
				model.addAttribute("listCar", mobilService.getCarList());
				return "delete-car";
			}else {
				model.addAttribute("errortype", "ID Tidak Ditemukan!");
			}
		}else {
			model.addAttribute("errortype", "Nomor ID Kosong, Isi Dulu IDnya yaa!");
		}
		return "errorview";
	}
}
