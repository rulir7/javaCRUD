package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.BlogAppPostModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogAppPostService {
    List<BlogAppPostModel> findAll(); //vai retornar uma lista de Posts
    Optional<BlogAppPostModel> findById(UUID id); //vai retornar um unico post passando o ID
    BlogAppPostModel save(BlogAppPostModel post); //salvar um post no banco
    void delete (BlogAppPostModel post); //para excluir um post
}
