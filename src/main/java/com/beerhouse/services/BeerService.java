package com.beerhouse.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.entities.Beer;
import com.beerhouse.repositories.BeerRepository;
import com.beerhouse.services.exceptions.ResourceNotFoundException;

@Service
public class BeerService {
	
	@Autowired
	private BeerRepository repository;

	@Transactional(readOnly = true)
	public List<BeerDTO> findAll() {
		List<Beer> list = repository.findAll();

		return list.stream().map(x -> new BeerDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public BeerDTO findById(Integer id) {
		Optional<Beer> obj = repository.findById(id);
		
		Beer entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		
		return new BeerDTO(entity);
	}
}
