package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.repository.BlogAppPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogAppServiceImpl implements BlogAppPostService {
    @Autowired
    BlogAppPostRepository blogapprepository ;

    @Override
    public List<BlogAppPostModel> findAll() {
        return blogapprepository.findAll();
    }

    @Override
    public Optional<BlogAppPostModel> findById(UUID id) {
        return blogapprepository.findById(id);
    }

    @Override
    @Transactional
    public BlogAppPostModel save(BlogAppPostModel post) {
        return blogapprepository.save(post);
    }

    @Override
    @Transactional
    public void delete(BlogAppPostModel post) {
        blogapprepository.delete(post);
    }
}

