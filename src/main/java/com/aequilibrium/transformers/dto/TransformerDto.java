package com.aequilibrium.transformers.dto;

import com.aequilibrium.transformers.model.enums.TransformerType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransformerDto {

private Integer idTransformer;
	
	private String name;
	
	private TransformerType type;
	
	private Integer strength;
	
	private Integer intelligence;
	
	private Integer speed;
	
	private Integer endurance;
	
	private Integer rank;
	
	private Integer courage;
	
	private Integer firepower;
	
	private Integer skill;
}
