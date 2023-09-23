package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PessoaTest {

	@Test
	void assercaoAgrupada() {
		String nome = "Fabio";
		String sobrenome = "Gauna";
		Pessoa pessoa = new Pessoa(nome, sobrenome);
		
		String nomeRetornado = pessoa.getNome();
		String sobrenomeRetornado = pessoa.getSobrenome();
		
		assertAll("Asserções de Pessoas", 
				()-> assertEquals(nome, nomeRetornado),
				()-> assertEquals(sobrenome, sobrenomeRetornado));
	}

}
