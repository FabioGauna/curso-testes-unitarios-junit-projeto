package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ContaBancariaTest {
	
	//Criar conta bancária

	@Test
	void deveCriarContaBancariaComSaldoPositivo() {
		
		BigDecimal saldoPositivo = new BigDecimal(10);
		
		ContaBancaria contaBancaria = new ContaBancaria(saldoPositivo);
		
		Assertions.assertEquals(new BigDecimal(10), contaBancaria.saldo());
	}
	
	@Test
	void deveCriarContaBancariaComSaldoNegativo() {
		
		BigDecimal saldoNegativo = new BigDecimal(-10);
		
		ContaBancaria contaBancaria = new ContaBancaria(saldoNegativo);
		
		Assertions.assertEquals(new BigDecimal(-10), contaBancaria.saldo());
	}
	
	@Test
	void deveLancarExcecaoAoCriarContaBancariaComSaldoNulo() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> new ContaBancaria(null));
	}
	
	//Realizar saque
	
	@Test
	void deveLancarExcecaoAoRealizarSaqueComValorNulo() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(null)); 
	}
	
	@Test
	void deveLancarExcecaoAoRealizarSaqueComValorZero() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.ZERO)); 
	}
	
	@Test
	void deveLancarExcecaoAoRealizarSaqueComValorNegativo() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		BigDecimal valor = new BigDecimal(-10);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(valor)); 
	}
	
	@Test
	void deveLancarExcecaoQuandoOSaldoForInsuficiente() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		BigDecimal valor = new BigDecimal(20);
		
		Assertions.assertThrows(RuntimeException.class, () -> contaBancaria.saque(valor)); 
	}
	
	@Test
	void deveRealizarUmSaque() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		BigDecimal valor = new BigDecimal(5);
		BigDecimal novoSaldo = new BigDecimal(5);
		
		contaBancaria.saque(valor);
		
		Assertions.assertEquals(novoSaldo, contaBancaria.saldo());
	}
	
	//Realizar depósito
	
	@Test
	void deveLancarExcecaoAoDepositarComValorNulo() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(null)); 
	}
	
	@Test
	void deveLancarExcecaoAoDepositarComValorZero() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(BigDecimal.ZERO)); 
	}
	
	@Test
	void deveLancarExcecaoAoDepositarComValorNegativo() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		BigDecimal valor = new BigDecimal(-10);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(valor)); 
	}
	
	@Test
	void deveRealizarUmDeposito() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		
		BigDecimal valor = new BigDecimal(10);
		BigDecimal novoSaldo = new BigDecimal(20);
		
		contaBancaria.deposito(valor);
		
		Assertions.assertEquals(novoSaldo, contaBancaria.saldo()); 
	}

}
