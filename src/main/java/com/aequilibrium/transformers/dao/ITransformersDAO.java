package com.aequilibrium.transformers.dao;

import org.springframework.data.repository.CrudRepository;

import com.aequilibrium.transformers.model.Transformer;

public interface ITransformersDAO extends CrudRepository<Transformer, Integer> {

}
