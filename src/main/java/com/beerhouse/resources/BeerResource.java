package com.beerhouse.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.services.BeerService;

@RestController
@RequestMapping(value = "/beers")
public class BeerResource {
	
	@Autowired
	private BeerService service;

	@GetMapping
	public ResponseEntity<List<BeerDTO>> findAll() {
		List<BeerDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<BeerDTO> findById(@PathVariable Integer id) {
		BeerDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
}