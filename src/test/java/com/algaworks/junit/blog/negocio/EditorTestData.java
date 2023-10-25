package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    private EditorTestData(){

    }

    public static Editor umEditorNovo(){
        return new Editor(null, "Fabio", "fabio_gauna@hotmail.com", BigDecimal.TEN, true);
    }

    public static Editor umEditorEncontrado(){
        return new Editor(1L, "Fabio Araujo Gauna", "fabio_gauna@hotmail.com", BigDecimal.ZERO, false);
    }

    public static Editor umEditorComIdInexistente() {
        return new Editor(99L, "Fabio", "fabio_gauna@hotmail.com", BigDecimal.TEN, true);
    }
}
