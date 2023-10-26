package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Ganhos;

import java.math.BigDecimal;

public class GanhosTestData {

    private GanhosTestData() {
    }

    public static Ganhos.Builder umNovoGanho() {
        return Ganhos.builder()
                .comValorPagoPorPalavra(new BigDecimal("1"))
                .comQuantidadePalavras(1)
                .comTotalGanho(new BigDecimal("10"));
    }
}
