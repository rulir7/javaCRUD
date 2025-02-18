package com.api.BlogAppApi.model;


import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="TB_POST")

public class BlogAppPostModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    //anotacao para geracao do id no banco de dados automaticamente
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 70)
    private String autor;
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false, length = 70)
    private String titulo;
    @Lob //para o banco aceitar texto grande e ter boa performace
    @Column(columnDefinition = "text")
    private String texto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
