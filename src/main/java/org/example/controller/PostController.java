package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(Reader body, HttpServletResponse response) throws IOException {
        Post post;
        try {
            post = parsGson(body);
        } catch (NumberFormatException | JsonSyntaxException e) {
            response.getWriter().print("empty fields");
            return;
        }
        Gson gson = new Gson();
        System.out.println("update");
        try {
            Post post1 = service.getById(post);
            response.getWriter().print("update " + gson.toJson(post1));
            System.out.println("update " + post1);
        } catch (NotFoundException | NullPointerException e) {
            System.out.println("update catch " + post);
            response.getWriter().print("is empty " + gson.toJson(post));
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            Post post = parsGson(body);
            final Post data = service.save(post);
            response.getWriter().print(gson.toJson(data));
        } catch (NumberFormatException | NullPointerException | JsonSyntaxException e) {
            response.getWriter().print("empty fields");
        }
    }

    public void removeById(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            Post post = parsGson(body);
            if (service.removeById(post.getId())) {
                response.getWriter().print("remove " + gson.toJson(post));
                return;
            }
        } catch (NumberFormatException | NullPointerException | JsonSyntaxException e) {
            response.getWriter().print("enter id");
            return;
        }
        response.getWriter().print("id absent!");
    }

    private Post parsGson(Reader body) {
        Gson gson = new Gson();
        return gson.fromJson(body, Post.class);
    }
}
