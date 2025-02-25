package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.model.PostComentarioModel;
import com.api.BlogAppApi.service.BlogAppPostService;
import com.api.BlogAppApi.service.BlogAppPostServiceComentario;
import dtos.BlogAppRecordDto;
import dtos.BlogAppRecordPostComentarioDto;
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

    @Autowired
    BlogAppPostServiceComentario blogAppPostServiceComentario;


    @GetMapping(value = "/posts")
    public ResponseEntity<List<BlogAppPostModel>> getPosts() {
        List<BlogAppPostModel> posts = blogAppPostService.findAll();

        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(posts);
        }
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping(value = "posts/{id}")
    public ResponseEntity<Object> getPostDetails(@PathVariable("id") UUID id) {
        Optional<BlogAppPostModel> blogAppModelOptional = blogAppPostService.findById(id);


        if (blogAppModelOptional.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found");
        }
        BlogAppPostModel post = blogAppModelOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(post);

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
        if (!blogAppPostModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        blogAppPostService.delete(blogAppPostModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Blog deleted successfully.");
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable(value = "id") UUID id, @RequestBody @Valid BlogAppRecordDto blogAppRecordDto) {
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (!blogAppPostModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        var postModel = blogAppPostModelOptional.get();
        BeanUtils.copyProperties(blogAppRecordDto, postModel);
        return ResponseEntity.status(HttpStatus.OK).body(blogAppPostService.save(postModel));

    }


    @PostMapping(value = "/posts/{id}")
    public ResponseEntity<Object> saveComentarioPost(@PathVariable("id") UUID id, @RequestBody BlogAppRecordPostComentarioDto comentario) {
        var postComentario = new PostComentarioModel();
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (blogAppPostModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        BlogAppPostModel post = blogAppPostModelOptional.get();
        BeanUtils.copyProperties(comentario, postComentario);
        postComentario.setPostModel(post);
        postComentario.setData(LocalDate.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(blogAppPostServiceComentario.saveComentario(postComentario));
    }

    // listar os posts com seus comentarios
    @GetMapping(value = "/posts/{id}/comentarios")
    public ResponseEntity<Object> getComentarios(@PathVariable("id") UUID id) {
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (blogAppPostModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        BlogAppPostModel post = blogAppPostModelOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(post.getPostComentario());
    }

    //listar um comentario de um post
    @GetMapping(value = "/posts/{id}/comentarios/{idComentario}")
    public ResponseEntity<Object> getComentario(@PathVariable("id") UUID id, @PathVariable("idComentario") UUID idComentario) {
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (blogAppPostModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        BlogAppPostModel post = blogAppPostModelOptional.get();
        Optional<PostComentarioModel> postComentarioModelOptional = post.getPostComentario().stream().filter(comentario -> comentario.getId().equals(idComentario)).findFirst();
        if (postComentarioModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comentario not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(postComentarioModelOptional.get());
    }

    // deletar um comentario de um post
    @DeleteMapping(value = "/posts/{id}/comentarios/{idComentario}")
    public ResponseEntity<Object> deleteComentario(@PathVariable("id") UUID id, @PathVariable("idComentario") UUID idComentario) {
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (blogAppPostModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        BlogAppPostModel post = blogAppPostModelOptional.get();
        Optional<PostComentarioModel> postComentarioModelOptional = post.getPostComentario().stream().filter(comentario -> comentario.getId().equals(idComentario)).findFirst();
        if (postComentarioModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comentario not found.");
        }
        PostComentarioModel comentario = postComentarioModelOptional.get();
        post.getPostComentario().remove(comentario);
        blogAppPostService.save(post);
        return ResponseEntity.status(HttpStatus.OK).body("comentario deleted successfully.");
    }

    // atualizar um comentario de um post
    @PutMapping(value = "/posts/{id}/comentarios/{idComentario}")
    public ResponseEntity<Object> updateComentario(@PathVariable("id") UUID id, @PathVariable("idComentario") UUID idComentario, @RequestBody BlogAppRecordPostComentarioDto comentario) {
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (blogAppPostModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found.");
        }
        BlogAppPostModel post = blogAppPostModelOptional.get();
        Optional<PostComentarioModel> postComentarioModelOptional = post.getPostComentario().stream().filter(comentario1 -> comentario1.getId().equals(idComentario)).findFirst();
        if (postComentarioModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comentario not found.");
        }
        PostComentarioModel comentarioModel = postComentarioModelOptional.get();
        BeanUtils.copyProperties(comentario, comentarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(blogAppPostServiceComentario.saveComentario(comentarioModel));
    }




}




