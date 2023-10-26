package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroEditorComMockTest {

	@Captor
	ArgumentCaptor<Mensagem> mensagemArgumentCaptor;
	
	@Mock
	ArmazenamentoEditor armazenamentoEditor;
	
	@Mock
	GerenciadorEnvioEmail gerenciadorEnvioEmail;
	
	@InjectMocks
	CadastroEditor cadastroEditor;
	
	@Nested
	class CadastroComEditorValido {

		@Spy
		Editor editor = EditorTestData.umEditorNovo().build();

		@BeforeEach
		void beforeEach() {

			when(armazenamentoEditor.salvar(any(Editor.class)))
					.thenAnswer(invocation -> {
						Editor editorPassado = invocation.getArgument(0, Editor.class);
						editorPassado.setId(1L);
						return editorPassado;
					});
		}

		@Test
		void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
			Editor editorSalvo = cadastroEditor.criar(editor);
			assertEquals(1L, editorSalvo.getId());
		}

		@Test
		void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {
			cadastroEditor.criar(editor);
			verify(armazenamentoEditor, times(1)).salvar(eq(editor));
		}

		@Test
		void Dado_um_editor_valido_Quando_criar_e_lancar_exception_Entao_nao_deve_enviar_email() {
			when(armazenamentoEditor.salvar(editor)).thenThrow(new RuntimeException());
			assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor));
			verify(gerenciadorEnvioEmail, never()).enviarEmail(any());
		}

		@Test
		void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor(){
			Editor editorSalvo = cadastroEditor.criar(editor);
			Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());
			Mensagem mensagem = mensagemArgumentCaptor.getValue();
			assertEquals(editorSalvo.getEmail(), editorSalvo.getEmail());
		}

		@Test
		void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email(){
			Editor editorSpy = Mockito.spy(editor);
			cadastroEditor.criar(editorSpy);
			Mockito.verify(editorSpy, Mockito.atLeast(1)).getEmail();
		}

		@Test
		void Dado_um_editor_com_email_existente_Quando_cadatrar_Entao_deve_lancar_exception(){
			Mockito.when(armazenamentoEditor.encontrarPorEmail("fabio_gauna@hotmail.com"))
					.thenReturn(Optional.empty())
					.thenReturn(Optional.of(editor));
			Editor editorComEmailExistente = EditorTestData.umEditorNovo().build();
			cadastroEditor.criar(editor);
			assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
		}

		@Test
		void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar(){
			cadastroEditor.criar(editor);
			InOrder inOrder = inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
			inOrder.verify(armazenamentoEditor, times(1)).salvar(editor);
			inOrder.verify(gerenciadorEnvioEmail, times(1)).enviarEmail(any(Mensagem.class));
		}
	}

	@Nested
	class CadastroComEditorNull{
		@Test
		void Dado_um_editor_null_Quando_cadastrar_Entao_deve_lancar_exception(){
			Assertions.assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
		}
	}

	@Nested
	class EdicaoComEditorValido{

		@Spy
		Editor editor = EditorTestData.umEditorEncontrado().build();

		@BeforeEach
		void beforeEach() {
			when(armazenamentoEditor.salvar(editor)).thenAnswer(invocation -> invocation.getArgument(0, Editor.class));
			when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));
		}

		@Test
		void Dado_um_editor_valido_Quando_editar_Entao_deve_alterar_um_editor_salvo(){
			Editor editorAtualizado = EditorTestData.umEditorEncontrado().build();
			cadastroEditor.editar(editorAtualizado);
			verify(editor, times(1)).atualizarComDados(editorAtualizado);
			InOrder inOrder = inOrder(editor, armazenamentoEditor);
			inOrder.verify(editor).atualizarComDados(editorAtualizado);
			inOrder.verify(armazenamentoEditor).salvar(editor);

		}
	}

	@Nested
	class EdicaoComEditorInexistente{
		@Test
		void Dado_um_editor_que_nao_exista_Quando_editar_Entao_deve_lancar_exception(){
			Editor editor = EditorTestData.umEditorComIdInexistente().build();
			when(armazenamentoEditor.encontrarPorId(99L)).thenReturn(Optional.empty());
			assertThrows(EditorNaoEncontradoException.class, () -> cadastroEditor.editar(editor));
			verify(armazenamentoEditor, never()).salvar(any(Editor.class));
		}
	}

	@Nested
	class EdicaoComEditorComEmailCadastradoParaOutroID{
		Editor editorEncontrado = EditorTestData.umEditorComIdInexistente().build();

		@BeforeEach
		void beforeEach() {
			when(armazenamentoEditor.encontrarPorEmailComIdDiferenteDe("fabio_gauna@hotmail.com",1L)).thenReturn(Optional.of(editorEncontrado));
		}

		@Test
		void Dado_um_editor_valido_com_email_cadastrado_em_outro_id_Quando_editar_Entao_deve_lancar_exception(){
			Editor editorAtualizado = EditorTestData.umEditorEncontrado().build();
			assertThrows(RegraNegocioException.class, () -> cadastroEditor.editar(editorAtualizado));
		}
	}

	@Nested
	class EdicaoComEditorNulo{

		@Test
		void Dado_um_editor_null_Quando_editar_Entao_deve_lancar_exception(){
			Assertions.assertThrows(NullPointerException.class, () -> cadastroEditor.editar(null));
		}

	}
}
