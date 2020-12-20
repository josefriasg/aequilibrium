package com.aequilibrium.transformers.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformers.dto.BattleResponseDto;
import com.aequilibrium.transformers.dto.TransformerDto;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.service.ITransformerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/transformers")
public class TransformerController {
	
	@Autowired
	private ITransformerService transformerService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@GetMapping(value = "/list")
	public ResponseEntity<List<TransformerDto>> getAll(){
		log.info("List transformers");
		List<Transformer> transformers =  transformerService.getAllTransformer();
		return ResponseEntity.ok(transformers.stream().map(x->modelMapper.map(x, TransformerDto.class)).collect(Collectors.toList()));
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity createTransformer(@RequestBody TransformerDto transformer) {
		log.info("Create transformer");
		
		Transformer transformerResponse = transformerService.createTransformer(modelMapper.map(transformer, Transformer.class));
	
		if (transformerResponse != null) {
			return ResponseEntity.ok(modelMapper.map(transformerResponse, TransformerDto.class));	
		}else {
			log.error("Transformer was not created");
            return ResponseEntity.badRequest().build();
		}
		
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity updateTransformer(@PathVariable("id") Integer idTransformer, @RequestBody TransformerDto transformer) {
		log.info("Update transformer");
	
		if (idTransformer != null && idTransformer >= 0) {
			Transformer transformerModel = modelMapper.map(transformer, Transformer.class);
			transformerModel.setIdTransformer(idTransformer);
			Transformer transformerResponse = transformerService.updateTransformer(transformerModel);
			
			if (transformerResponse != null) {
				return ResponseEntity.ok(modelMapper.map(transformerResponse, TransformerDto.class));	
			}else {
				log.error("Transformer with id " + idTransformer + " could not be updated");
	            return ResponseEntity.badRequest().build();
			}	
		}else {
			log.error("Wrong Id provided");
            return ResponseEntity.badRequest().build();
		}
		
		
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity deleteTransformer(@PathVariable("id") Integer idTransformer) {
		log.info("Delete transformer");
		if (transformerService.findById(idTransformer).isPresent()) {
			transformerService.deleteTransformer(idTransformer);
			
			return ResponseEntity.ok().build();
				
		}else {
			log.error("Transformer with id " + idTransformer + " does not exist");
            return ResponseEntity.badRequest().build();
		}
		
	}
	
	@RequestMapping(value = "/battle")
	public BattleResponseDto battle(@RequestBody List<Integer> transformerIDs) {
		log.info("Transformer battle!");
		return modelMapper.map(transformerService.battle(transformerIDs), BattleResponseDto.class);
		
	}

}
