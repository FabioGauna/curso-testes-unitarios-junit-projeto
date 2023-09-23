package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PessoaTest {

	@Test
	void assercaoAgrupada() {
		Pessoa pessoa = new Pessoa("Fabio", "Gauna");
		
		assertAll("Asserções de Pessoas", 
				()-> assertEquals("Fabio", pessoa.getNome()),
				()-> assertEquals("Gauna", pessoa.getSobrenome()));
	}

}
