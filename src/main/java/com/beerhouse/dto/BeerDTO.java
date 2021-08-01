package com.beerhouse.dto;

import java.math.BigDecimal;

import com.beerhouse.entities.Beer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BeerDTO {

	private Long id;
	private String name;
	private String ingredients;
	private String alcoholContent;
	private BigDecimal price;
	private String category;
	
	public BeerDTO(Beer entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.ingredients = entity.getIngredients();
		this.alcoholContent = entity.getAlcoholContent();
		this.price = entity.getPrice();
		this.category = entity.getCategory();
	}

}
