package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FiltroNumerosTest {

	@Test
	public void deveRetornarNumerosPares() {
		List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
		List<Integer> numerosParesEsperados = Arrays.asList(2, 4);
		
		List<Integer> numerosFiltrados = FiltroNumeros.numerosPares(numeros);
		
		assertIterableEquals(numerosParesEsperados, numerosFiltrados);
	}
	
	@Test
	public void deveRetornarNumerosImpares() {
		List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
		List<Integer> numerosImparesEsperados = Arrays.asList(1, 3);
		
		List<Integer> numerosFiltrados = FiltroNumeros.numerosImpares(numeros);
		
		assertIterableEquals(numerosImparesEsperados, numerosFiltrados);
	}
	
	@Test
	void deveVerificarSeUmNumeroEPositivo() {
		int numero = 1;
		
		boolean isPositivo = FiltroNumeros.isPositivo(numero);
		
		Assertions.assertTrue(isPositivo);
	}
	
	@Test
	void deveVerificarSeUmNumeroNaoPositivo() {
		int numero = -1;
		
		boolean isPositivo = FiltroNumeros.isPositivo(numero);
		
		Assertions.assertFalse(isPositivo);
	}

}
