package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;

class CalculadoraGanhosTest {
	
	static CalculadoraGanhos calculadora;
	static Editor autor;
	static Post post;
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Antes de todos os testes e apenas uma vez");
		
		calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
		autor = new Editor(1L, "Fabio", "fabio_gauna@hotmail.com", new BigDecimal(5), true);
		post = new Post(1L, "Ecossistema Java", "O ecossistema do Java é muito maduro", autor, "ecossistema-java-abc123", null, false, false);
	}
	
	@BeforeEach
	void beforeEach() {
		System.out.println("Antes de cada método");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("Depois de todos os testes e apenas uma vez");
	}
	
	@AfterEach
	void afterEach() {
		System.out.println("Depois de cada método");
	}

	@Test
	void deveCalcularGanhosComPremium() {
		
		Ganhos ganhos = calculadora.calcular(post);
		
		assertEquals(new BigDecimal("45"), ganhos.getTotalGanho());
		assertEquals(7, ganhos.getQuantidadePalavras());
		assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
	}

	@Test
	void deveCalcularGanhosSemPremium() {
		autor.setPremium(false);
		
		Ganhos ganhos = calculadora.calcular(post);
		
		assertEquals(new BigDecimal("35"), ganhos.getTotalGanho());
		assertEquals(7, ganhos.getQuantidadePalavras());
		assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
	}
}
