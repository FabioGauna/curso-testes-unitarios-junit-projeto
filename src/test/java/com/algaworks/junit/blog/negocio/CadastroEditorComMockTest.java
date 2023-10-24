package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroEditorComMockTest {

	Editor editor;

	@Captor
	ArgumentCaptor<Mensagem> mensagemArgumentCaptor;
	
	@Mock
	ArmazenamentoEditor armazenamentoEditor;
	
	@Mock
	GerenciadorEnvioEmail gerenciadorEnvioEmail;
	
	@InjectMocks
	CadastroEditor cadastroEditor;
	
	@BeforeEach
	void beforeEach() {
		editor = new Editor(null, "Fabio", "fabio_gauna@hotmail.com", BigDecimal.TEN, true);
		
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
	
}
