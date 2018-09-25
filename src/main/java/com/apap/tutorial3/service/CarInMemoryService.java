package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tutorial3.model.CarModel;

@Service
public class CarInMemoryService implements CarService{
	private List<CarModel> archiveCar;
	
	public CarInMemoryService() {
		archiveCar = new ArrayList<>();
	}

	@Override
	public void addCar(CarModel car) {
		archiveCar.add(car);
		
	}

	@Override
	public List<CarModel> getCarList() {
		return archiveCar;
	}

	@Override
	public CarModel getCarDetail(String id) {
		for(int idx=0;idx<archiveCar.size();idx++) {
			CarModel carNow = archiveCar.get(idx);
			if (carNow.getId().equals(id)) {
				return carNow;
			}
		}
		return null;
	}
	
	public String deleteCar(String id) {
		int idxFound = 0;
		for(int idx=0;idx<archiveCar.size();idx++) {
			CarModel carNow = archiveCar.get(idx);
			if (carNow.getId().equals(id)) {
				idxFound = idx;
				archiveCar.remove(idxFound);
				return id;
			}
		}
		return null;
	}
	
	
}
