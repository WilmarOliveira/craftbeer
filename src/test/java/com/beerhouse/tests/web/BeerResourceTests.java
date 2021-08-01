package com.beerhouse.tests.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.services.BeerService;
import com.beerhouse.services.exceptions.ResourceNotFoundException;
import com.beerhouse.tests.factory.BeerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BeerResourceTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BeerService service;
	
	@Autowired
	private ObjectMapper objMapper;
	
	private Long existingId;
	private Long nonExistingId;
	private BeerDTO newBeerDTO;
	private BeerDTO existingBeerDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		
		newBeerDTO = BeerFactory.createBeerDTO(null);
		existingBeerDTO = BeerFactory.createBeerDTO(existingId);
		
		Mockito.when(service.findAll()).thenReturn(List.of(existingBeerDTO));
		
		Mockito.when(service.findById(existingId)).thenReturn(existingBeerDTO);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
		Mockito.when(service.insert(ArgumentMatchers.any())).thenReturn(existingBeerDTO);
		
		Mockito.when(service.update(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(existingBeerDTO);
		Mockito.when(service.update(ArgumentMatchers.eq(nonExistingId), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);
		
		Mockito.when(service.updateChangeds(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(existingBeerDTO);
		Mockito.when(service.updateChangeds(ArgumentMatchers.eq(nonExistingId), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);
		
		Mockito.doNothing().when(service).delete(existingId);
		Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.delete("/beers/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isNoContent());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.delete("/beers/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnCreatedBeer() throws Exception {
		String jsonBody = objMapper.writeValueAsString(newBeerDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.post("/beers")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isCreated());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}
	
	@Test
	public void updateShouldReturnBeerDTOWhenIdExists() throws Exception {
		
		String jsonBody = objMapper.writeValueAsString(newBeerDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.put("/beers/{id}", existingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingId));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		String jsonBody = objMapper.writeValueAsString(newBeerDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.put("/beers/{id}", nonExistingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnBeerWhenIdExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/beers/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingId));
		
	}
	
	@Test
	public void updateChangedsShouldReturnBeerDTOWhenIdExists() throws Exception {
		
		String jsonBody = objMapper.writeValueAsString(newBeerDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.patch("/beers/{id}", existingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingId));
	}
	
	
	
	@Test
	public void updateChangedsShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		String jsonBody = objMapper.writeValueAsString(newBeerDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.patch("/beers/{id}", nonExistingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/beers/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findAllShouldReturnAllBeers() throws Exception {
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/beers")
						.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isOk());
	}

}
