package com.algaworks.junit.utilidade;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SaudacaoUtilTest {

	@Test
	public void deveSaudarBomDia() {
		//Arrange
		int horaValida = 9;
		
		//Action
		String saudacao = SaudacaoUtil.saudar(horaValida);
		
		//Assertions
		assertEquals("Bom dia", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBomDiaAPartir5h() {
		int horaValida = 5;
		
		String saudacao = SaudacaoUtil.saudar(horaValida);
		
		assertEquals("Bom dia", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBoaTarde() {
		int horaValida = 13;
		
		String saudacao = SaudacaoUtil.saudar(horaValida);
		
		assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBoaNoite() {
		int horaValida = 19;
		
		String saudacao = SaudacaoUtil.saudar(horaValida);
		
		assertEquals("Boa noite", saudacao, "Saudação incorreta!");
	}
	
	@Test
	public void deveSaudarBoaNoiteAs4h() {
		int horaValida = 4;
		
		String saudacao = SaudacaoUtil.saudar(horaValida);
		
		assertEquals("Boa noite", saudacao, "Saudação incorreta!");
	}
	
	
	@Test
	public void deveLancarUmaExcecao() {
		int horaInvalida = -10;
		
		Executable execucaoInvalida = () -> saudar(horaInvalida);
		
		assertThrows(IllegalArgumentException.class, execucaoInvalida);
	}
	
	@Test
	public void naoDeveLancarUmaExcecao() {
		int horaInvalida = 0;
		
		Executable execucaoValida = () -> saudar(horaInvalida);
		
		
		assertDoesNotThrow(execucaoValida);
	}

	@ParameterizedTest
	@ValueSource(ints = {5,6,7,8,9,10,11})
	public void Dado_um_horario_matinal__Quando_saudar__Entao_deve_retorar_bom_dia(int hora){
		String saudacao = SaudacaoUtil.saudar(hora);
		assertEquals("Bom dia", saudacao, "Saudação incorreta!");
	}

}
