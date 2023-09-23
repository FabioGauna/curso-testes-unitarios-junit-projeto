package com.algaworks.junit.utilidade;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SaudacaoUtilTest {

	@Test
	public void deveSaudarBomDia() {
		String saudacao = SaudacaoUtil.saudar(9);
		
		assertEquals("Bom dia", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBomDiaAPartir5h() {
		String saudacao = SaudacaoUtil.saudar(5);
		
		assertEquals("Bom dia", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBoaTarde() {
		String saudacao = SaudacaoUtil.saudar(13);
		
		assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBoaNoite() {
		String saudacao = SaudacaoUtil.saudar(19);
		
		assertEquals("Boa noite", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBoaNoiteAs4h() {
		String saudacao = SaudacaoUtil.saudar(4);
		
		assertEquals("Boa noite", saudacao, "Saudação incorreta!");
	}
	
	
	@Test
	public void deveLancarUmaExcecao() {
		assertThrows(IllegalArgumentException.class, () -> saudar(-10));
	}
	
	@Test
	public void naoDeveLancarUmaExcecao() {
		assertDoesNotThrow(() -> saudar(0));
	}

}
