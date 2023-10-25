package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.exception.PostNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroPostTest {

    @Mock
    ArmazenamentoPost armazenamentoPost;

    @Mock
    CalculadoraGanhos calculadoraGanhos;

    @Mock
    GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    CadastroPost cadastroPost;

    @Captor
    ArgumentCaptor<Notificacao> notificacaoArgumentCaptor;

    @Spy
    Editor editor = new Editor(1L, "Fabio", "fabio_gauna@hotmail.com", BigDecimal.TEN, true);

    @Nested
    @DisplayName("Cadastro de Post válido")
    class CadastroDePostValido {

        @Spy
        Post post = new Post("titulo", "conteudo", editor, true, true);

        @BeforeEach
        void init() {
            when(armazenamentoPost.salvar(any(Post.class))).then(invocationOnMock -> {
                Post postCriado = invocationOnMock.getArgument(0, Post.class);
                postCriado.setId(1L);
                return postCriado;
            });
        }

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_salvar() {
            cadastroPost.criar(post);
            verify(armazenamentoPost, times(1)).salvar(any(Post.class));
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_id_valido() {
            Post postCriado = cadastroPost.criar(post);
            assertEquals(1L, postCriado.getId());
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_post_com_slug() {
            Post postCriado = cadastroPost.criar(post);
            verify(post, times(1)).setSlug(anyString());
            assertNotNull(postCriado.getSlug());
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_post_com_ganhos() {
            Ganhos ganhos = new Ganhos(new BigDecimal("1"), 1, new BigDecimal("10"));
            when(calculadoraGanhos.calcular(post)).thenReturn(ganhos);
            Post postCriado = cadastroPost.criar(post);
            verify(post, times(1)).setGanhos(any(Ganhos.class));
            assertNotNull(postCriado.getGanhos());
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_calcular_ganhos_antes_de_salvar() {
            cadastroPost.criar(post);
            InOrder inOrder = inOrder(calculadoraGanhos, armazenamentoPost);
            inOrder.verify(calculadoraGanhos, times(1)).calcular(post);
            inOrder.verify(armazenamentoPost, times(1)).salvar(post);
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_gerar_slug_antes_de_salvar() {
            cadastroPost.criar(post);
            InOrder inOrder = inOrder(post, armazenamentoPost);
            inOrder.verify(post, times(1)).setSlug(any());
            inOrder.verify(armazenamentoPost, times(1)).salvar(post);
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_enviar_notificacao_apos_salvar() {
            cadastroPost.criar(post);
            InOrder inOrder = inOrder(armazenamentoPost, gerenciadorNotificacao);
            inOrder.verify(armazenamentoPost, times(1)).salvar(post);
            inOrder.verify(gerenciadorNotificacao, times(1)).enviar(any(Notificacao.class));
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_gerar_notificacao_com_titulo_do_post() {
            cadastroPost.criar(post);
            verify(gerenciadorNotificacao).enviar(notificacaoArgumentCaptor.capture());
            Notificacao notificacao = notificacaoArgumentCaptor.getValue();
            assertEquals("Novo post criado -> titulo", notificacao.getConteudo());
        }
    }

    @Nested
    @DisplayName("Cadastro de Post nulo")
    class CadastroDePostNulo {
        @Test
        public void Dado_um_post_null__Quanto_cadastrar__Entao_deve_lancar_exception_e_nao_deve_salvar() {
            assertThrows(NullPointerException.class, () -> cadastroPost.criar(null));
            verify(armazenamentoPost, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("Edição de Post nulo")
    class EdicaoDePostNulo {
        @Test
        void Dado_um_post_null__Quando_editar__Entao_deve_lancar_exception_e_nao_deve_salvar(){
            assertThrows(NullPointerException.class, () -> cadastroPost.editar(null));
            verify(armazenamentoPost, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("Edição de Post válido e pago")
    class EdicaoDePostValidoEPago {

        Ganhos ganhos = new Ganhos(new BigDecimal("1"), 1, new BigDecimal("10"));

        @Spy
        Post post = new Post(1L, "titulo", "conteudo", editor, "ola-mundo-java",
            ganhos, true, true);

        @BeforeEach
        void init() {
            when(armazenamentoPost.salvar(post)).then(invocationOnMock ->
                invocationOnMock.getArgument(0, Post.class));
        }

        @Test
        void Dado_um_post_valido__Quando_editar__Entao_deve_salvar() {
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            cadastroPost.editar(post);
            verify(armazenamentoPost, times(1)).salvar(post);
        }

        @Test
        void Dado_um_post_valido__Quando_editar__Entao_deve_retornar_mesmo_id() {
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            Post postEditado = cadastroPost.editar(post);
            assertEquals(1L, postEditado.getId());
        }

        @Test
        void Dado_um_post_pago__Quando_editar__Entao_deve_retornar_post_com_os_mesmos_ganhos_sem_recalcular() {
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            Post postEditado = cadastroPost.editar(post);
            verify(postEditado, times(1)).isPago();
            verify(calculadoraGanhos, never()).calcular(post);
            assertNotNull(postEditado.getGanhos());
        }

    }

    @Nested
    @DisplayName("Edição de Post válido e não pago")
    class EdicaoDePostValidoENaoPago {

        Ganhos ganhos = new Ganhos(new BigDecimal("1"), 1, new BigDecimal("10"));

        @Spy
        Post post = new Post(1L, "titulo", "conteudo", editor, "ola-mundo-java",
                ganhos, false, false);

        @BeforeEach
        void init() {
            when(armazenamentoPost.salvar(post)).then(invocationOnMock ->
                    invocationOnMock.getArgument(0, Post.class));
        }

        @Test
        void Dado_um_post_nao_pago__Quando_editar__Entao_deve_retornar_post_com_os_ganhos_recalculados() {
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            Ganhos ganhosNovos = new Ganhos(new BigDecimal("2"), 1, new BigDecimal("20"));
            when(calculadoraGanhos.calcular(post)).thenReturn(ganhosNovos);
            Post postEditado = cadastroPost.editar(post);
            verify(postEditado, times(1)).isPago();
            verify(postEditado, times(1)).setGanhos(ganhosNovos);
            verify(calculadoraGanhos, times(1)).calcular(post);
            assertNotNull(postEditado.getGanhos());
        }
    }

    @Nested
    @DisplayName("Remoção de Post com id nulo")
    class RemocaoDePostNulo {
        @Test
        void Dado_um_post_nulo__Quando_remover__Entao_deve_retornar_exception_e_nao_remover() {
            assertThrows(NullPointerException.class, () -> cadastroPost.remover(null));
            verify(armazenamentoPost, never()).remover(anyLong());
        }
    }

    @Nested
    @DisplayName("Remoção de Post com id válido")
    class RemocaoDePostComIdValido {

        Ganhos ganhos = new Ganhos(new BigDecimal("1"), 1, new BigDecimal("10"));

        @Spy
        Post post = new Post(1L, "titulo", "conteudo", editor, "ola-mundo-java",
                ganhos, false, false);

        @Test
        void Dado_um_post_valido__Quando_remover_Entao_deve_excluir() {
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            cadastroPost.remover(1L);
            verify(armazenamentoPost, times(1)).remover(1L);
        }

        @Test
        void Dado_um_post_nao_encontrado__Quando_remover__Entao_deve_lancar_exception() {
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.empty());
            assertThrows(PostNaoEncontradoException.class, () -> cadastroPost.remover(1L));
        }

        @Test
        void Dado_um_post_publicado__Quando_remover_Entao_deve_lancar_exception_e_nao_remover() {
            post.setPublicado(true);
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            assertThrows(RegraNegocioException.class, () -> cadastroPost.remover(1L));
            verify(armazenamentoPost, never()).remover(1L);
        }

        @Test
        void Dado_um_post_pago__Quando_remover_Entao_deve_lancar_exception_e_nao_remover() {
            post.setPago(true);
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            assertThrows(RegraNegocioException.class, () -> cadastroPost.remover(1L));
            verify(armazenamentoPost, never()).remover(1L);
        }
    }
}
