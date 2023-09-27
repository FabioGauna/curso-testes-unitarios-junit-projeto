package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorTest {

	CadastroEditor cadastroEditor;
	Editor editor;
	ArmazenamentoEditorFixoEmMemoria armazenamentoEditorFixoEmMemoria;

	@BeforeAll
	static void beforeAll() {
		
	}
	
	@BeforeEach
	void beforeEach() {
		
		armazenamentoEditorFixoEmMemoria = new ArmazenamentoEditorFixoEmMemoria();
		
		cadastroEditor = new CadastroEditor(armazenamentoEditorFixoEmMemoria, new GerenciadorEnvioEmail() {
			@Override
			void enviarEmail(Mensagem mensagem) {
				System.out.println("Enviando mensagem para: " + mensagem.getDestinatario());
			}
		});
		
		editor = new Editor(null, "Fabio", "fabio_gauna@hotmail.com", BigDecimal.TEN, true);
	}
	
	@Test
	void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
		Editor editorSalvo = cadastroEditor.criar(editor);
		
		assertEquals(1L, editorSalvo.getId());
		assertTrue(armazenamentoEditorFixoEmMemoria.chamouSalvar);
	}

	@Test
	void Dado_um_editor_null_Entao_deve_lancar_uma_exception() {
		Executable executable = () -> cadastroEditor.criar(null);
		
		assertThrows(NullPointerException.class, executable);
		assertFalse(armazenamentoEditorFixoEmMemoria.chamouSalvar);
		
	}
	
	@Test
	void Dado_um_editor_com_email_existente_Quando_criar_Entao_deve_lancar_exception() {
		editor.setEmail("fabio_existe@hotmail.com");
		
		Executable executable = () -> cadastroEditor.criar(editor);
		
		assertThrows(RegraNegocioException.class, executable);
	}
	
	@Test
	void Dado_um_editor_com_email_existente_Quando_criar_Entao_nao_deve_salvar() {
		editor.setEmail("fabio_existe@hotmail.com");
		
		try {
			cadastroEditor.criar(editor);
		}catch (RegraNegocioException e) {}
		
		assertFalse(armazenamentoEditorFixoEmMemoria.chamouSalvar);
	}
}
