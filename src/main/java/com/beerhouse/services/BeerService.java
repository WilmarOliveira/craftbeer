package com.beerhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.entities.Beer;
import com.beerhouse.repositories.BeerRepository;

@Service
public class BeerService {

	@Autowired
	private BeerRepository repository;
	
	public List<Beer> findAll() {
		return repository.findAll();
	}
}
