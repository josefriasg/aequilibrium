package com.aequilibrium.transformers.dto;

import java.util.List;

import com.aequilibrium.transformers.model.Transformer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BattleResponseDto {
	
private Integer numBattles;
	
	private String winningTeam;
	
	private List<Transformer> survivors;

}
