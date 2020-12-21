package com.aequilibrium.transformers.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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
import com.aequilibrium.transformers.model.enums.TransformerType;
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
		if (transformerIDs !=null && !transformerIDs.isEmpty()) {
			
			BattleResponse response = new BattleResponse();
			
			//get Transformers from DB
			Iterable<Transformer> transformersToBattle = transformerDAO.findAllById(transformerIDs);
			
			List<Transformer> decepticonsToBattle = new ArrayList<Transformer>();
			List<Transformer> autobotsToBattle = new ArrayList<Transformer>();
			
			//divide transformers into autobots and decepticons
			Iterator<Transformer> itTransformers = transformersToBattle.iterator();
			
			boolean isOptimus = false;
			boolean isPredaking = false;
			while (itTransformers.hasNext()) {
				Transformer transformer = itTransformers.next();
				if (ITransformerService.OPTIMUS_PRIME.equalsIgnoreCase(transformer.getName())){
					log.info("Optimus is fighting");
					isOptimus=true;
				}
				if (ITransformerService.PREDAKING.equalsIgnoreCase(transformer.getName())){
					log.info("Predaking is fighting");
					isPredaking=true;
				}
				if (transformer.getType().equals(TransformerType.A)) {
					autobotsToBattle.add(transformer);
				}else {
					decepticonsToBattle.add(transformer);
				}
			}
			
			//if optimus and predaking in fight, battle is over
			if (isOptimus && isPredaking) {
				log.info("Optimus and Predaking in battle, fight is over");
				response.setNumBattles(0);
				response.setSurvivors(new ArrayList<Transformer>());
				response.setWinningTeam(ITransformerService.WINNING_TEAM_NONE);
				return response;
			}
			
			log.info("Decepticons to battle:"+decepticonsToBattle.size());
			log.info("Autobots to battle:"+autobotsToBattle.size());
			
			//order by rank and id
			List<Transformer> decepticonsOrdered = decepticonsToBattle.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
			List<Transformer> autobotsOrdered = autobotsToBattle.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
			
			//determine the team with less fighters
			int numberOfFights = Math.min(decepticonsToBattle.size(), autobotsToBattle.size());
			log.info("Number of fights:"+numberOfFights);
			
			int winsForAutobots = 0;
			int winsForDecepticons = 0;
			
			//fight each transformer in the order specified
			for (int i=0; i<numberOfFights; i++) {
				Transformer decepticon = decepticonsOrdered.get(i);
				Transformer autobot = autobotsOrdered.get(i);
				log.info(decepticon.toString() + " VS "+autobot.toString());
				
				Transformer winner = this.determineWinner(decepticon, autobot);
				
				//if winner is null, it was a tie
				if (winner == null) {
					log.info("Fight was a tie");
					decepticonsToBattle.remove(decepticon);
					autobotsToBattle.remove(autobot);
				}else if (winner.getType().equals(TransformerType.A)) {
					log.info("Fight won by autobot");
					decepticonsToBattle.remove(decepticon);
					winsForAutobots++;
				}else {
					log.info("Fight won by decepticon");
					autobotsToBattle.remove(autobot);
					winsForDecepticons++;
				}
			}
			
			response.setNumBattles(numberOfFights);
			
			if (winsForAutobots > winsForDecepticons) {
				log.info("Autobots won the battle");
				response.setWinningTeam(ITransformerService.WINNING_TEAM_AUTOBOTS);
				response.setSurvivors(decepticonsToBattle);
			}else if (winsForAutobots < winsForDecepticons) {
				log.info("Decepticons won the battle");
				response.setWinningTeam(ITransformerService.WINNING_TEAM_DECEPTICONS);
				response.setSurvivors(autobotsToBattle);
			}else {
				log.info("No winner in the battle");
				response.setWinningTeam(ITransformerService.WINNING_TEAM_NONE);
			}
			
			return response;
		}else {
			log.info("No transformer IDs provided");
			return null;
		}
	}




	private Transformer determineWinner(Transformer transformer1, Transformer transformer2) {
		
		//if any of the fighters is optimus or predaking, it wins automatically. it can't be both because fight would be over before this point
		if (ITransformerService.OPTIMUS_PRIME.equalsIgnoreCase(transformer1.getName()) || ITransformerService.PREDAKING.equalsIgnoreCase(transformer1.getName())) {
			log.info("Transformer 1 is optimus or predaking");
			return transformer1;
		}else if (ITransformerService.OPTIMUS_PRIME.equalsIgnoreCase(transformer2.getName()) || ITransformerService.PREDAKING.equalsIgnoreCase(transformer2.getName())) {
			log.info("Transformer 2 is optimus or predaking");
			return transformer2;
		}
		
		//if a fighter is down 4 or more points of courage and 3 or more points of strengh, it loses
		if (transformer1.getCourage() >= transformer2.getCourage()+4 && transformer1.getStrength() >= transformer2.getStrength()+3) {
			log.info("Transformer 2 ran away");
			return transformer1;
		} else if (transformer2.getCourage() >= transformer1.getCourage()+4 && transformer2.getStrength() >= transformer1.getStrength()+3) {
			log.info("Transformer 1 ran away");
			return transformer2;
		}else if (transformer1.getSkill() >= transformer2.getSkill()+3) {//if a fighter is 3 or more skill above their opponent, it wins
			log.info("Transformer 2 unskilled");
			return transformer1;
		}else if (transformer2.getSkill() >= transformer1.getSkill()+3) {
			log.info("Transformer 1 unskilled");
			return transformer2;
		}
		
		//if any of the above conditions was not meet, overall rating determines the winner. returns null if tied
		if (transformer1.getOverallRating() > transformer2.getOverallRating()) {
			log.info("Transformer 1 wins overall rating");
			return transformer1;
		}else if (transformer2.getOverallRating() > transformer1.getOverallRating()) {
			log.info("Transformer 2 wins overall rating");
			return transformer2;
		}else {
			log.info("Overall rating is the same");
			return null;
		}
	}


	@Override
	public Optional<Transformer> findById(Integer idTransformer) {
		log.info("Find transformer by Id");
		return transformerDAO.findById(idTransformer);
	}

}
