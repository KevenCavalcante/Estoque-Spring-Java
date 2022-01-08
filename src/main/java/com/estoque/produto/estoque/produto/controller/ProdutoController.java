package com.estoque.produto.estoque.produto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.produto.estoque.produto.model.Produto;
import com.estoque.produto.estoque.produto.repository.ProdutoRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ProdutoController {

  @Autowired
  ProdutoRepository produtoRepository;
  
  @RequestMapping("/hello")  
  public String hello() {   
   return "Hello, world"; 
   }
  
  @PostMapping("/produtos")
  public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
    try {
      Produto _produto = produtoRepository.save(new Produto(produto.getTipo(), produto.getDescricao(), false));
      return new ResponseEntity<>(_produto, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/produtos")
  public ResponseEntity<List<Produto>> getAllProduto(@RequestParam(required = false) String title) {
    try {
      List<Produto> produto = new ArrayList<Produto>();

      if (title == null)
        produtoRepository.findAll().forEach(produto::add);
      else
        produtoRepository.findByTipoContaining(title).forEach(produto::add);

      if (produto.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(produto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/produto/{id}")
  public ResponseEntity<Produto> getProdutoById(@PathVariable("id") String id) {
    Optional<Produto> produtoData = produtoRepository.findById(id);

    if (produtoData.isPresent()) {
      return new ResponseEntity<>(produtoData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/produto/published")
  public ResponseEntity<List<Produto>> findByPublished() {
    try {
      List<Produto> produto = produtoRepository.findByDisponivel(true);

      if (produto.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(produto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PutMapping("/produto/{id}")
  public ResponseEntity<Produto> updateProduto(@PathVariable("id") String id, @RequestBody Produto produto) {
    Optional<Produto> produtoData = produtoRepository.findById(id);

    if (produtoData.isPresent()) {
      Produto _produto = produtoData.get();
      _produto.setTipo(produto.getTipo());
      _produto.setDescricao(produto.getDescricao());
      _produto.setDisponivel(produto.isDisponivel());
      return new ResponseEntity<>(produtoRepository.save(_produto), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  @DeleteMapping("/produto/{id}")
  public ResponseEntity<HttpStatus> produtoTutorial(@PathVariable("id") String id) {
    try {
      produtoRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/produto")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      produtoRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
