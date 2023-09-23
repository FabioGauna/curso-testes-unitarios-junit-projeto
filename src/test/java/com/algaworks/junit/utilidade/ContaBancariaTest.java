package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ContaBancariaTest {
	
	//Criar conta bancária

	@Test
	void deveCriarContaBancariaComSaldoPositivo() {
		BigDecimal saldoInformado = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldoInformado);
		
		BigDecimal saldoDaContaCriada = contaBancaria.saldo();
		
		Assertions.assertEquals(saldoInformado, saldoDaContaCriada);
	}
	
	@Test
	void deveCriarContaBancariaComSaldoNegativo() {
		BigDecimal saldoNegativo = new BigDecimal(-10);
		ContaBancaria contaBancaria = new ContaBancaria(saldoNegativo);
		
		BigDecimal saldoDaContaCriada = contaBancaria.saldo();
		
		Assertions.assertEquals(saldoNegativo, saldoDaContaCriada);
	}
	
	@Test
	void deveLancarExcecaoAoCriarContaBancariaComSaldoNulo() {
		BigDecimal saldo = null;
		
		Executable execucaoDoConstrutor = () -> new ContaBancaria(saldo); 
		
		Assertions.assertThrows(IllegalArgumentException.class, execucaoDoConstrutor);
	}
	
	//Realizar saque
	
	@Test
	void deveLancarExcecaoAoRealizarSaqueComValorNulo() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		BigDecimal valorDeSaqueNulo = null;
		
		Executable execusaoDoSaque = () -> contaBancaria.saque(valorDeSaqueNulo);
		
		Assertions.assertThrows(IllegalArgumentException.class, execusaoDoSaque); 
	}
	
	@Test
	void deveLancarExcecaoAoRealizarSaqueComValorZero() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		BigDecimal valorDeSaqueZero = BigDecimal.ZERO;
		
		Executable execusaoDoSaque = () -> contaBancaria.saque(valorDeSaqueZero);
		
		Assertions.assertThrows(IllegalArgumentException.class, execusaoDoSaque); 
	}
	
	@Test
	void deveLancarExcecaoAoRealizarSaqueComValorNegativo() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		BigDecimal valorDeSaqueNegativo = new BigDecimal(-10);
		
		Executable execusaoDoSaque = () -> contaBancaria.saque(valorDeSaqueNegativo);
		
		Assertions.assertThrows(IllegalArgumentException.class, execusaoDoSaque); 
	}
	
	@Test
	void deveLancarExcecaoQuandoOSaldoForInsuficiente() {
		BigDecimal saldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(saldo);
		BigDecimal valorDeSaqueMaiorQueOSaldo = new BigDecimal(20);
		
		Executable execusaoDoSaque = () -> contaBancaria.saque(valorDeSaqueMaiorQueOSaldo);
		
		Assertions.assertThrows(RuntimeException.class, execusaoDoSaque); 
	}
	
	@Test
	void deveRealizarUmSaque() {
		BigDecimal novoSaldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(novoSaldo);
		BigDecimal valorDoSaque = new BigDecimal(5);
		BigDecimal saldoEsperado = new BigDecimal(5);
		
		contaBancaria.saque(valorDoSaque);
		BigDecimal saldoAtualizado = contaBancaria.saldo();
		
		Assertions.assertEquals(saldoEsperado, saldoAtualizado);
	}
	
	//Realizar depósito
	
	@Test
	void deveLancarExcecaoAoDepositarComValorNulo() {
		BigDecimal novoSaldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(novoSaldo);
		BigDecimal valorDeDepositoNulo = null;
		
		Executable execucaoDoDeposito = () -> contaBancaria.deposito(valorDeDepositoNulo);
		
		Assertions.assertThrows(IllegalArgumentException.class, execucaoDoDeposito); 
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
		BigDecimal valorDeDepositoNegativo = new BigDecimal(-10);
		
		Executable execucaoDoDeposito = () -> contaBancaria.deposito(valorDeDepositoNegativo);
		
		Assertions.assertThrows(IllegalArgumentException.class, execucaoDoDeposito); 
	}
	
	@Test
	void deveRealizarUmDeposito() {
		BigDecimal novoSaldo = new BigDecimal(10);
		ContaBancaria contaBancaria = new ContaBancaria(novoSaldo);
		BigDecimal valorDoDeposito = new BigDecimal(10);
		BigDecimal saldoEsperado = new BigDecimal(20);
		
		contaBancaria.deposito(valorDoDeposito);
		BigDecimal saldoAtualizado = contaBancaria.saldo();
		
		Assertions.assertEquals(saldoEsperado, saldoAtualizado); 
	}

}
