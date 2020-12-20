package com.aequilibrium.transformers.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aequilibrium.transformers.dao.ITransformersDAO;
import com.aequilibrium.transformers.model.BattleResponse;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.service.ITransformerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TransformerServiceImpl implements ITransformerService {

	@Autowired
	private ITransformersDAO transformerDAO;
	
	
	@Override
	public Transformer createTransformer(Transformer transformer) {
		log.info("Create transformer");
		if (transformer.getIdTransformer() == null || !this.findById(transformer.getIdTransformer()).isPresent()){
			log.info("Didn't found transformer, good to create");
			return this.saveTransformer(transformer);
		}else {
			return null;
		}
		
	}
	

	@Override
	public Transformer updateTransformer(Transformer transformer) {
		log.info("Update transformer");
		if (transformer.getIdTransformer() != null && this.findById(transformer.getIdTransformer()).isPresent()){
			return this.saveTransformer(transformer);	
		}else {
			return null;
		}
		
	}
	
	private Transformer saveTransformer(Transformer transformer) {
		return transformerDAO.save(transformer);
	}

	@Override
	public List<Transformer> getAllTransformer() {
		log.info("Get all transformers");
		Iterable<Transformer> iterable = transformerDAO.findAll();
		
		return StreamSupport.stream(iterable.spliterator(), false) 
	            .collect(Collectors.toList()); 
	}

	@Override
	public boolean deleteTransformer(int idTransformer) {
		log.info("Delete transformer");
		if (idTransformer>=0 && this.findById(idTransformer).isPresent()){
			transformerDAO.deleteById(idTransformer);
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public BattleResponse battle(List<Integer> transformerIDs) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<Transformer> findById(Integer idTransformer) {
		log.info("Find transformer by Id");
		return transformerDAO.findById(idTransformer);
	}

}
