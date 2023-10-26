package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;

import java.math.BigDecimal;

public class PostTestData {

    private PostTestData(){

    }

    public static Post.Builder umNovoPost(){
        return Post.builder()
                .comTitulo("titulo")
                .comConteudo("conteudo")
                .comAutor(EditorTestData.umEditorEncontrado().build())
                .comPago(false)
                .comPublicado(false);
    }

    public static Post.Builder umPostExistente() {
        return umNovoPost()
                .comId(1L)
                .comSlug("ola-mundo-java")
                .comGanhos(GanhosTestData.umNovoGanho().build());
    }

}
