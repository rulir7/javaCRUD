package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.service.BlogAppPostService;
import dtos.BlogAppRecordDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class BlogAppController {

    @Autowired
    BlogAppPostService blogAppPostService;

    @GetMapping (value = "/posts")
    public ResponseEntity<List<BlogAppPostModel>> getPosts(){
        List<BlogAppPostModel> posts = blogAppPostService.findAll();

        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(posts);
        }
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping(value = "posts/{id}")
    public ResponseEntity<Object> getPostDetails(@PathVariable("id")UUID id) {
        Optional<BlogAppPostModel> blogAppModelOptional = blogAppPostService.findById(id);
        BlogAppPostModel post = blogAppModelOptional.get();

        if (!blogAppModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found");
        }
        return  ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PostMapping(value = "/newpost") //retorna 201 ao criar
    public ResponseEntity<Object> savePost(@RequestBody @Valid BlogAppRecordDto blogAppRecordDto) {
        var postModel = new BlogAppPostModel();
        BeanUtils.copyProperties(blogAppRecordDto, postModel);
        postModel.setData(LocalDate.now(ZoneId.of(("UTC"))));
        return ResponseEntity.status(HttpStatus.CREATED).body(blogAppPostService.save(postModel));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "id") UUID id) {
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (!blogAppPostModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        blogAppPostService.delete(blogAppPostModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Blog deleted successfully.");
    }



}
