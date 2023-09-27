package com.algaworks.junit.blog.negocio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;

public class ArmazenamentoEditorFixoEmMemoria implements ArmazenamentoEditor{

	public boolean chamouSalvar = false;
	
	@Override
	public Editor salvar(Editor editor) {
		chamouSalvar = true;
		if(editor.getId() == null) {
			editor.setId(1L);
		}
		return editor;
	}

	@Override
	public Optional<Editor> encontrarPorId(Long editor) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Editor> encontrarPorEmail(String email) {
		if(Objects.nonNull(email) && email.equals("fabio_existe@hotmail.com")) {
			return Optional.of(new Editor(2L, "Fabio", "fabio_existe@hotmail.com", BigDecimal.TEN, true));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Editor> encontrarPorEmailComIdDiferenteDe(String email, Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void remover(Long editorId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Editor> encontrarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
