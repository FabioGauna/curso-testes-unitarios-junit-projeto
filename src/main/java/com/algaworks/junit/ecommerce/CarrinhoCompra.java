package com.algaworks.junit.ecommerce;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CarrinhoCompra {

	private final Cliente cliente;
	private final List<ItemCarrinhoCompra> itens;

	public CarrinhoCompra(Cliente cliente) {
		this(cliente, new ArrayList<>());
	}

	public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
		requireNonNull(cliente);
		requireNonNull(itens);
		this.cliente = cliente;
		this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
	}

	public List<ItemCarrinhoCompra> getItens() {
		return new ArrayList<ItemCarrinhoCompra>(itens);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void adicionarProduto(Produto produto, int quantidade) {
		requireNonNull(produto);
		validarQuantidade(quantidade);
		buscarItem(produto).ifPresentOrElse(i -> i.adicionarQuantidade(quantidade), () -> adicionarNovoItem(produto, quantidade));
	}

	public void removerProduto(Produto produto) {
		requireNonNull(produto);
		ItemCarrinhoCompra item = buscarItem(produto).orElseThrow(RuntimeException::new);
		this.itens.remove(item);
	}

	public void aumentarQuantidadeProduto(Produto produto) {
		requireNonNull(produto);
		ItemCarrinhoCompra item = buscarItem(produto).orElseThrow(RuntimeException::new);
		item.adicionarQuantidade(1);
	}

    public void diminuirQuantidadeProduto(Produto produto) {
    	requireNonNull(produto);
    	ItemCarrinhoCompra item = buscarItem(produto).orElseThrow(RuntimeException::new);
    	if(item.getQuantidade() == 1) {
    		this.itens.remove(item);
    	} else {
    		item.subtrairQuantidade(1);
    	}
	}

    public BigDecimal getValorTotal() {
    	return this.itens.stream()
	    	.map(i -> i.getValorTotal())
	    	.reduce(BigDecimal.ZERO, BigDecimal::add);
    }

	public int getQuantidadeTotalDeProdutos() {
		return this.itens.stream()
				.map(i -> i.getQuantidade())
				.reduce(0, Integer::sum);
	}

	public void esvaziar() {
		this.itens.clear();
	}
	
	private void validarQuantidade(int quantidade) {
		if(quantidade <= 0) {
			throw new IllegalArgumentException();
		}
	}
	
	private void adicionarNovoItem(Produto produto, int quantidade) {
		this.itens.add(new ItemCarrinhoCompra(produto, quantidade));
	}
	
	private Optional<ItemCarrinhoCompra> buscarItem(Produto produto){
		return this.itens.stream()
				.filter(i -> i.getProduto().equals(produto))
				.findFirst();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CarrinhoCompra that = (CarrinhoCompra) o;
		return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itens, cliente);
	}
}