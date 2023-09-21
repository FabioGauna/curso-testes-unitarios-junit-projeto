package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SaudacaoUtilTest {

	@Test
	public void saudar() {
		String saudacao = SaudacaoUtil.saudar(9);
		
		assertEquals("Bom dia", saudacao, "Saudação incorreta!");
	}

}
