package com.algaworks.junit.utilidade;

import java.math.BigDecimal;
import java.util.Objects;

public class ContaBancaria {
	
	private BigDecimal saldo;

    public ContaBancaria(BigDecimal saldo) {
    	if(Objects.isNull(saldo)) {
    		throw new IllegalArgumentException("Saldo não pode ser nulo!");
    	}
    	
        this.saldo = saldo;
    }

    public void saque(BigDecimal valor) {
    	if(Objects.isNull(valor) || valor.signum() == -1 || valor.signum() == 0) {
    		throw new IllegalArgumentException("Valor inválido!");
    	}
    	
    	if(this.saldo.compareTo(valor) == -1) {
    		throw new RuntimeException("Saldo insuficiente!");
    	}
    	
    	this.saldo = this.saldo.subtract(valor);
    }

    public void deposito(BigDecimal valor) {
    	if(Objects.isNull(valor) || valor.signum() == -1 || valor.signum() == 0) {
    		throw new IllegalArgumentException("Valor inválido!");
    	}
    	
    	this.saldo = this.saldo.add(valor);
    }

    public BigDecimal saldo() {
        return this.saldo;
    }
}
