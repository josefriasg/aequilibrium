package com.aequilibrium.transformers.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BattleResponse {

	private Integer numBattles;
	
	private String winningTeam;
	
	private List<Transformer> survivers;
}
