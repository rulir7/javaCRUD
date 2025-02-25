package com.api.BlogAppApi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="TB_POSTCOMENTARIO")
public class PostComentarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate data;

    //@NotBlank
    @Lob
    @Column(columnDefinition = "text")
    //@Column(name = "texto", columnDefinition = "TEXT")
    private String comentario;

    @ManyToOne
    private BlogAppPostModel postModel;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BlogAppPostModel getPostModel() {
        return postModel;
    }

    public void setPostModel(BlogAppPostModel postModel) {
        this.postModel = postModel;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }


}
