package com.aequilibrium.transformers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.aequilibrium.transformers.model.enums.TransformerType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Transformer implements Comparable<Transformer>{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idTransformer;
	
	@Column
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column
	private TransformerType type;
	
	@Column
	private Integer strength;
	
	@Column
	private Integer intelligence;
	
	@Column
	private Integer speed;
	
	@Column
	private Integer endurance;
	
	@Column
	private Integer rank;
	
	@Column
	private Integer courage;
	
	@Column
	private Integer firepower;
	
	@Column
	private Integer skill;

	@Override
	public boolean equals(Object obj) {
		if (obj == this) { 
            return true; 
        } 
  
        
        if (!(obj instanceof Transformer)) { 
            return false; 
        } 
        
        Transformer transformer = (Transformer)obj;
        return ((transformer.getIdTransformer() == null && this.getIdTransformer() == null) || (transformer.getIdTransformer().equals(this.getIdTransformer()))
        		&& ((transformer.getName() == null && this.getName()==null) || (transformer.getName().equals(this.getName())))
        		&& (transformer.getType().equals(this.getType()))
        		&& ((transformer.getStrength() == null && this.getStrength()==null) || (transformer.getStrength().equals(this.getStrength())))
        		&& ((transformer.getIntelligence() == null && this.getIntelligence()==null) || (transformer.getIntelligence().equals(this.getIntelligence())))
        		&& ((transformer.getSpeed() == null && this.getSpeed()==null) || (transformer.getSpeed().equals(this.getSpeed())))
        		&& ((transformer.getEndurance() == null && this.getEndurance()==null) || (transformer.getEndurance().equals(this.getEndurance())))
        		&& ((transformer.getRank() == null && this.getRank()==null) || (transformer.getRank().equals(this.getRank())))
        		&& ((transformer.getCourage() == null && this.getCourage()==null) || (transformer.getCourage().equals(this.getCourage())))
        		&& ((transformer.getFirepower() == null && this.getFirepower()==null) || (transformer.getFirepower().equals(this.getFirepower())))
        		&& ((transformer.getSkill() == null && this.getSkill()==null) || (transformer.getSkill().equals(this.getStrength())))
        		);
	}

	@Override
	public int compareTo(Transformer o) {
		return this.getRank().intValue() == o.getRank().intValue()
				? this.getIdTransformer().compareTo(o.getIdTransformer())
						:this.getRank().compareTo(o.getRank());
	}
	
	
	
	
	public int getOverallRating() {
		return this.getStrength()+this.getIntelligence()+this.getSpeed()+this.getEndurance()+this.getFirepower();
	}

	@Override
	public String toString() {
		return "ID:"+this.getIdTransformer()+
				" - Name:"+this.getName()+
				" - Type:"+this.getType()+
				" - Strength:"+this.getStrength()+
				" - Intelligence:"+this.getIntelligence()+
				" - Speed:"+this.getSpeed()+
				" - Endurance:"+this.getEndurance()+
				" - Rank:"+this.getRank()+
				" - Courage:"+this.getCourage()+
				" - Firepower:"+this.getFirepower()+
				" - Skill"+this.getSkill();
	}
	

}
