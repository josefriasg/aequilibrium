package com.aequilibrium.transformers.service;

import java.util.List;
import java.util.Optional;

import com.aequilibrium.transformers.model.BattleResponse;
import com.aequilibrium.transformers.model.Transformer;

public interface ITransformerService {
	public Transformer createTransformer (Transformer transformer);
	public Transformer updateTransformer (Transformer transformer);
	public List<Transformer> getAllTransformer();
	public Optional<Transformer> findById(Integer idTransformer);
	public boolean deleteTransformer(int idTransformer);
	public BattleResponse battle(List<Integer> transformerIDs);
}
