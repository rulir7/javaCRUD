package com.api.BlogAppApi.repository;

import com.api.BlogAppApi.model.PostComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostComentarioRepository extends JpaRepository<PostComentarioModel, UUID> {
}
