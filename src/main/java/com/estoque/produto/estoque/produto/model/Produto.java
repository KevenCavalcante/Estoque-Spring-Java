package com.estoque.produto.estoque.produto.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "produtos")
public class Produto {
  @Id
  private String id;

  private String tipo;
  private String descricao;
  private boolean disponivel;

  public Produto() {

  }

  public Produto(String tipo, String descricao, boolean disponivel) {
    this.tipo = tipo;
    this.descricao = descricao;
    this.disponivel = disponivel;
  }

  public String getId() {
    return id;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public boolean isDisponivel() {
    return disponivel;
  }

  public void setDisponivel(boolean isDisponivel) {
    this.disponivel = isDisponivel;
  }

  @Override
  public String toString() {
    return "Produto [id=" + id + ", tipo=" + tipo + ", desc=" + descricao + ", disponivel=" + disponivel + "]";
  }
}