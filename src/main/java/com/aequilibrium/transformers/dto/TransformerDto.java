package com.aequilibrium.transformers.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.aequilibrium.transformers.model.enums.TransformerType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransformerDto {

private Integer idTransformer;
	
	@NotNull
	private String name;
	
	@Pattern(regexp = "[AD]")
	private String type;
	
	@Min(1)
	@Max(10)
	private Integer strength;
	

	@Min(1)
	@Max(10)
	private Integer intelligence;
	

	@Min(1)
	@Max(10)
	private Integer speed;
	

	@Min(1)
	@Max(10)
	private Integer endurance;
	

	@Min(1)
	@Max(10)
	private Integer rank;
	

	@Min(1)
	@Max(10)
	private Integer courage;
	

	@Min(1)
	@Max(10)
	private Integer firepower;
	

	@Min(1)
	@Max(10)
	private Integer skill;
}
