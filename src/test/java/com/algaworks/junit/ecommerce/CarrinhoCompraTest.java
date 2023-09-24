package com.algaworks.junit.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Carrinho de compras")
class CarrinhoCompraTest {

    private CarrinhoCompra carrinhoCompra;
    private Cliente cliente;
    private List<ItemCarrinhoCompra> itens;
    private Produto videoGame;
    private Produto televisao;

    @Nested
    @DisplayName("Dado um carrinho com dois itens")
    class DadoUmCarrinhoComDoisItens {

        @BeforeEach
        public void beforeEach() {
            cliente = new Cliente(1L, "Fulano da Silva");

            videoGame = new Produto(1L, "Video Game", "Video Game", BigDecimal.valueOf(2000.00));
            televisao = new Produto(2L, "Televisão", "Televisão", BigDecimal.valueOf(2490.00));

            itens = new ArrayList<>();
            itens.add(new ItemCarrinhoCompra(videoGame, 2));
            itens.add(new ItemCarrinhoCompra(televisao, 1));

            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Nested
        @DisplayName("Quando retornar itens")
        class QuandoRetornarItens {

            @Test
            @DisplayName("Então deve retornar dois itens")
            void entaoDeveRetornarDoisItens() {
                assertEquals(2, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E deve retornar uma nova instância da lista de itens")
            void eDeveRetornarUmaNovaLista() {
                carrinhoCompra.getItens().clear(); //Get Itens, retorna uma nova lista
                assertEquals(2, carrinhoCompra.getItens().size()); //Lista permaneceu intacta
            }

        }

        @Nested
        @DisplayName("Quando remover um Video Game")
        class QuandoRemoverUmItem {

            @BeforeEach
            public void beforeEach() {
                carrinhoCompra.removerProduto(videoGame);
            }

            @Test
            @DisplayName("Então deve diminuir a quantidade total de itens")
            void entaoDeveDiminuirQuantidadeTotal() {
                assertEquals(1, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E não deve remover demais itens")
            void naoDeveRemoverDemaisItens() {
                assertEquals(televisao, carrinhoCompra.getItens().get(0).getProduto());
            }

        }

        @Nested
        @DisplayName("Quando aumentar quantidade de um Video Game")
        class QuandoAumentarQuantidade {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.aumentarQuantidadeProduto(videoGame);
            }

            @Test
            @DisplayName("Então deve somar na quantidade dos itens iguais")
            void deveSomarNaQuantidade() {
                assertEquals(3, carrinhoCompra.getItens().get(0).getQuantidade());
                assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
            }

            @Test
            @DisplayName("Então deve retornar quatro de quantidade total de itens")
            void deveRetornarQuantidadeTotalItens() {
                assertEquals(4, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então deve retornar valor total correto de itens")
            void deveRetornarValorTotalItens() {
                assertEquals(new BigDecimal("8490.0"), carrinhoCompra.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando diminuir quantidade de um Video Game")
        class QuandoDiminuirQuantidade {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.diminuirQuantidadeProduto(videoGame);
            }

            @Test
            @DisplayName("Então deve somar na quantidade dos itens iguais")
            void deveSomarNaQuantidade() {
                assertEquals(1, carrinhoCompra.getItens().get(0).getQuantidade());
                assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
            }

            @Test
            @DisplayName("Então deve retornar três de quantidade total de itens")
            void deveRetornarQuantidadeTotalItens() {
                assertEquals(2, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então deve retornar valor total correto de itens")
            void deveRetornarValorTotalItens() {
                assertEquals(new BigDecimal("4490.0"), carrinhoCompra.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando diminuir quantidade de um item com apenas um de quantidade")
        class QuandoDiminuirQuantidadeDeItemUnico {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.diminuirQuantidadeProduto(televisao);
            }

            @Test
            @DisplayName("Então deve remover item")
            void entaoDeveRemoverItem() {
                assertNotEquals(carrinhoCompra.getItens().get(0).getProduto(), televisao);
            }
        }

        @Nested
        @DisplayName("Quando adicionar item com quantidade inválida")
        class QuandoAdicionarItemComQuantidadeInvalida {

            @Test
            @DisplayName("Então deve lançar exception")
            void entaoDeveFalhar() {
                assertThrows(RuntimeException.class, ()-> carrinhoCompra.adicionarProduto(televisao, -1));
            }
        }

        @Nested
        @DisplayName("Quando esvaziar carrinho")
        class QuandoEsvaziarCarrinho {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.esvaziar();
            }

            @Test
            @DisplayName("Então deve somar na quantidade dos itens iguais")
            void deveSomarNaQuantidade() {
                assertEquals(0, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("Então deve retornar zero de quantidade total de itens")
            void deveRetornarQuantidadeTotalItens() {
                assertEquals(0, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então deve retornar zero de valor total de itens")
            void deveRetornarValorTotalItens() {
                assertEquals(BigDecimal.ZERO, carrinhoCompra.getValorTotal());
            }
        }

    }

    @Nested
    @DisplayName("Dado um carrinho vazio")
    class DadoUmCarinhoVazio {
    }
}