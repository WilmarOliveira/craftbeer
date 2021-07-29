package com.beerhouse.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.entities.Beer;
import com.beerhouse.services.BeerService;

@RestController
@RequestMapping(value = "/beers")
public class BeerResource {
	
	@Autowired
	private BeerService service;

	@GetMapping
	public ResponseEntity<List<Beer>> findAll() {

		List<Beer> list = service.findAll();
		return ResponseEntity.ok().body(list);

	}
}
