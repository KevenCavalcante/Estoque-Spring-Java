package com.estoque.produto.estoque.produto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estoque.produto.estoque.produto.model.Produto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
	  List<Produto> findByTipoContaining(String tipo);
	  List<Produto> findByDisponivel(boolean disponivel);
	}