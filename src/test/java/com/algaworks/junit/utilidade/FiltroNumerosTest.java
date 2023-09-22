package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class FiltroNumerosTest {

	@Test
	public void deveRetornarNumerosPares() {
		List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
		List<Integer> numerosParesEsperados = Arrays.asList(2, 4);
		List<Integer> numerosFiltrados = FiltroNumeros.numerosPares(numeros);
		assertIterableEquals(numerosParesEsperados, numerosFiltrados);
	}

}
