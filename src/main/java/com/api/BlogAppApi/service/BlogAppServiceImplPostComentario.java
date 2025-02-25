package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.PostComentarioModel;
import com.api.BlogAppApi.repository.PostComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogAppServiceImplPostComentario implements BlogAppPostServiceComentario {
    @Autowired
    PostComentarioRepository postComentarioRepository;

    @Override
    @Transactional
    public PostComentarioModel saveComentario(PostComentarioModel postComentarioModel) {
        return postComentarioRepository.save(postComentarioModel);
    }
}
