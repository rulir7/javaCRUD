package com.api.BlogAppApi.repository;

import com.api.BlogAppApi.model.BlogAppPostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogAppPostRepository extends JpaRepository<BlogAppPostModel, UUID> {
}

