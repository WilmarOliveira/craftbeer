package com.beerhouse.dto;

import com.beerhouse.entities.Beer;

public class BeerDTO {

	private Integer id;
	private String name;
	private String ingredients;
	private String alcoholContent;
	private Integer price;
	private String category;
	
	public BeerDTO() {
		
	}

	public BeerDTO(Integer id, String name, String ingredients, String alcoholContent, Integer price, String category) {
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
		this.alcoholContent = alcoholContent;
		this.price = price;
		this.category = category;
	}
	
	public BeerDTO(Beer entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.ingredients = entity.getIngredients();
		this.alcoholContent = entity.getAlcoholContent();
		this.price = entity.getPrice();
		this.category = entity.getCategory();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getAlcoholContent() {
		return alcoholContent;
	}

	public void setAlcoholContent(String alcoholContent) {
		this.alcoholContent = alcoholContent;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
