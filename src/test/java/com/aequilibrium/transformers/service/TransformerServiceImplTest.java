package com.aequilibrium.transformers.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.aequilibrium.transformers.dao.ITransformersDAO;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.model.enums.TransformerType;
import com.aequilibrium.transformers.service.impl.TransformerServiceImpl;


@ExtendWith(MockitoExtension.class)
public class TransformerServiceImplTest {
	@Mock
	private ITransformersDAO transformerDAO;
	
	@InjectMocks
    private TransformerServiceImpl transformerService;

	@Test
	public void createTransformer_ValidTransformerWithId_NotNull() {
		Transformer bumblebee = new Transformer(1, "Bumblebee", TransformerType.A, 7,5,8,6,2,9,7,4);
		
		when(transformerDAO.findById(1)).thenReturn(Optional.empty());
		when(transformerDAO.save(Mockito.any())).thenReturn(bumblebee);
		
		Transformer savedTransformer = transformerService.createTransformer(new Transformer(1, "Bumblebee", TransformerType.A, 7,5,8,6,2,9,7,4));
		
		assertThat(savedTransformer).isNotNull();
		
	}
	
	@Test
	public void createTransformer_ValidTransformerWithNullId_NotNull() {
		Transformer bumblebee = new Transformer(1, "Bumblebee", TransformerType.A, 7,5,8,6,2,9,7,4);
		
		when(transformerDAO.save(Mockito.any())).thenReturn(bumblebee);
		
		Transformer savedTransformer = transformerService.createTransformer(new Transformer(null, "Bumblebee", TransformerType.A, 2,3,8,3,4,3,6,7));
		
		assertThat(savedTransformer).isNotNull();
	}
	
	@Test
	public void createTransformer_TransformerAlreadyExists_Null() {
		Transformer bumblebee = new Transformer(1, "Bumblebee", TransformerType.A, 7,5,8,6,2,9,7,4);
		
		
		when(transformerDAO.findById(1)).thenReturn(Optional.of(bumblebee));
		
		Transformer savedTransformer = transformerService.createTransformer(new Transformer(1, "Bumblebee", TransformerType.A, 7,5,8,6,2,9,7,4));
		
		assertThat(savedTransformer).isNull();
	}

	@Test
	public void updateTransformer_IdExists_NotNull() {
		Transformer optimus = new Transformer(0, "Optimus Prime", TransformerType.A, 10,10,10,10,1,10,10,10);
		
		
		when(transformerDAO.findById(0)).thenReturn(Optional.of(optimus));
		when(transformerDAO.save(Mockito.any())).thenReturn(optimus);
		
		Transformer response = transformerService.updateTransformer(new Transformer(0, "Optimus Prime", TransformerType.A, 10,10,10,10,1,10,10,10));
		
		assertThat(response).isNotNull();
	}
	
	@Test
	public void updateTransformer_IdNotExists_Null() {
		when(transformerDAO.findById(-1)).thenReturn(Optional.empty());
		
		Transformer jazz = new Transformer(-1, "Jazz", TransformerType.A, 10,10,10,10,1,10,10,10);
		Transformer response = transformerService.updateTransformer(jazz);
		
		assertThat(response).isNull();
	}
	
	@Test
	public void updateTransformer_IdNull_Null() {
		Transformer jazz = new Transformer(null, "Jazz", TransformerType.A, 10,10,10,10,1,10,10,10);
		Transformer response = transformerService.updateTransformer(jazz);
		
		assertThat(response).isNull();
	}

	@Test
	public void getAllTransformer_Valid_NotEmptyList() {
		Transformer optimus = new Transformer(0, "Optimus Prime", TransformerType.A, 10,10,10,10,1,10,10,10);
		Transformer bumblebee = new Transformer(1, "Bumblebee", TransformerType.A, 7,5,8,6,2,9,7,4);
		Transformer megatron = new Transformer(2, "Megatron", TransformerType.D, 10,10,10,10,1,10,10,10);
		Transformer starscream = new Transformer(3, "Starscream", TransformerType.D, 3,4,9,6,2,3,7,9);
		
    	List<Transformer> list = new ArrayList<Transformer>();
    	list.add(optimus);
    	list.add(bumblebee);
    	list.add(megatron);
    	list.add(starscream);
    	
    	when(transformerDAO.findAll()).thenReturn(list);
    	
		List<Transformer> transformers = transformerService.getAllTransformer();
		
		assertThat(transformers).isNotEmpty();
	}

	@Test
	public void deleteTransformer_IdExists_True() {
		Transformer optimus = new Transformer(0, "Optimus Prime", TransformerType.A, 10,10,10,10,1,10,10,10);
		
		when(transformerDAO.findById(0)).thenReturn(Optional.of(optimus));
		boolean result = transformerService.deleteTransformer(0);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void deleteTransformer_IdNotExists_False() {
		
		when(transformerDAO.findById(1)).thenReturn(Optional.empty());
		
		boolean result = transformerService.deleteTransformer(1);
		
		assertThat(result).isFalse();
	}
	

	@Test
	public void findById_IdExists_isPresentTrue() {
		Transformer optimus = new Transformer(0, "Optimus Prime", TransformerType.A, 10,10,10,10,1,10,10,10);
		
		when(transformerDAO.findById(0)).thenReturn(Optional.of(optimus));
		
		Optional<Transformer> result = transformerService.findById(0);
		
		assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	public void findById_IdNotExists_isPresentFalse() {
		
		when(transformerDAO.findById(2)).thenReturn(Optional.empty());
		
		
		Optional<Transformer> result = transformerService.findById(2);
		
		assertThat(result.isPresent()).isFalse();
	}
	
	@Test
	public void battle_ValidList_True() {
		//TODO implement
	}
	
	@Test
	public void battle_EmptyList_False() {
		//TODO implement
	}
	
	@Test
	public void battle_NullParameter_False() {
		//TODO implement
	}
	
	@Test
	public void battle_ListAllAutobots_True() {
		//TODO implement
	}
}