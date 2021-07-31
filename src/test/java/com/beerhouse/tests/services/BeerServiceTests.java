package com.beerhouse.tests.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.entities.Beer;
import com.beerhouse.repositories.BeerRepository;
import com.beerhouse.services.BeerService;
import com.beerhouse.services.exceptions.ResourceNotFoundException;
import com.beerhouse.tests.factory.BeerFactory;

@ExtendWith(SpringExtension.class)
public class BeerServiceTests {

	@InjectMocks
	private BeerService service;
	
	@Mock
	private BeerRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private Beer beer;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 10000L;
		beer = BeerFactory.createBeer();
		
		Mockito.when(repository.findAll()).thenReturn(List.of(beer));
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(beer);
		
		Mockito.when(repository.getOne(existingId)).thenReturn(beer);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(beer));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void findIdShouldReturnBeerDTOWhenIdExists() {

		BeerDTO result = service.findById(existingId);

		Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

	}
	
	@Test
	public void findAllShouldReturnListBeerDTOWhenSuccess() {
		List<BeerDTO> result = service.findAll();
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldReturnBeerDTOWhenIdExists() {

		BeerDTO dto = new BeerDTO();

		BeerDTO result = service.update(existingId, dto);

		Assertions.assertNotNull(result);
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		BeerDTO dto = new BeerDTO();

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, dto);
		});

	}
	
	@Test
	public void updateChangedsShouldReturnBeerDTOWhenIdExists() {

		BeerDTO dto = new BeerDTO();

		BeerDTO result = service.updateChangeds(existingId, dto);

		Assertions.assertNotNull(result);
	}

	@Test
	public void updateChangedsThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		BeerDTO dto = new BeerDTO();

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.updateChangeds(nonExistingId, dto);
		});

	}

}
