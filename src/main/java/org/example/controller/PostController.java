package org.example.controller;

import com.google.gson.Gson;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public void getById(String body, HttpServletResponse response) throws IOException {
        Post post;
        try {
            post = parsBody(body);
        } catch (NumberFormatException e) {
            response.getWriter().print("empty fields");
            return;
        }
        Post post1;
        Gson gson = new Gson();
        System.out.println("update");
        try {
            post1 = service.getById(post);
            response.getWriter().print("update " + gson.toJson(post1));
            System.out.println("update " + post1);
        } catch (NotFoundException e) {
            System.out.println("update catch" + post);
            response.getWriter().print("is empty " + gson.toJson(post));
        }
    }

    public void save(String body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        Post post;
        try {
            post = parsBody(body);
        } catch (NumberFormatException e) {
            response.getWriter().print("empty fields");
            return;
        }
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(String body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        Post post;
        try {
            post = parsBody(body);
        } catch (NumberFormatException e) {
            response.getWriter().print("enter id");
            return;
        }

        if (service.removeById(post.getId())) {
            response.getWriter().print("remove " + gson.toJson(post));
            return;
        }
        response.getWriter().print("id absent!");
    }

    private Post parsBody(String body) {
        Map<String, String> map = new HashMap<>();
        String[] params = body.split("&");
        for (String param : params) {
            String[] paramPars = param.split("=");
            if (paramPars.length == 2) {
                map.put(paramPars[0], paramPars[1]);
            }
        }
        return new Post(Long.parseLong(map.get("id")), map.get("content"));
    }
}
