package com.beerhouse.tests.factory;

import java.math.BigDecimal;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.entities.Beer;

public class BeerFactory {
	
	public static Beer createBeer() {
		BigDecimal value = new BigDecimal(15);
		return new Beer(
				1L, 
				"Bohemia", 
				"New ingredient1, new ingredient2", 
				"5%", 
				value, 
				"Malte");
	}

	public static BeerDTO createBeerDTO() {
		return new BeerDTO(createBeer());
	}
}
