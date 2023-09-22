package com.algaworks.junit.utilidade;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SaudacaoUtilTest {

	@Test
	public void deveSaudar() {
		String saudacao = SaudacaoUtil.saudar(9);
		
		assertEquals("Bom dia", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveLancarUmaExcecao() {
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> saudar(-10));
		
		assertEquals("Hora inválida", illegalArgumentException.getMessage());
	}
	
	@Test
	public void naoDeveLancarUmaExcecao() {
		assertDoesNotThrow(() -> saudar(0));
	}

}
