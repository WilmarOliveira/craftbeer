package com.beerhouse.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.entities.Beer;
import com.beerhouse.repositories.BeerRepository;
import com.beerhouse.services.exceptions.DatabaseException;
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
	
	@Transactional
	public BeerDTO update(Integer id, BeerDTO dto) {
		
		try {
			Beer entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity.setIngredients(dto.getIngredients());
			entity.setAlcoholContent(dto.getAlcoholContent());
			entity.setPrice(dto.getPrice());
			entity.setCategory(dto.getCategory());
			entity = repository.save(entity);
			
			return new BeerDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " Not Found ");
		}
	}
	
	@Transactional
	public BeerDTO insert(BeerDTO dto) {
		Beer entity = new Beer();
		entity.setName(dto.getName());
		entity.setIngredients(dto.getIngredients());
		entity.setAlcoholContent(dto.getAlcoholContent());
		entity.setPrice(dto.getPrice());
		entity.setCategory(dto.getCategory());
		entity= repository.save(entity);
		
		return new BeerDTO(entity);
	}
	
	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " Not Found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
	}
}
