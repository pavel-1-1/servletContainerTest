package org.example.servlet;

import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private String path;
    private String method;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            path = req.getRequestURI();
            method = req.getMethod();
            // get all
            System.out.println(method + " " + path);
            if (method.equals("GET") && path.equals("/read")) {
                System.out.println(method + " " + path);
                controller.all(resp);
                return;
            }
            // update
            if (method.equals("POST") && path.equals("/update")) {
                System.out.println("update" + path);
                controller.getById(req.getReader(), resp);
                return;
            }
            // create
            if (method.equals("POST") && path.equals("/create")) {
                System.out.println(method + " " + path);
                controller.save(req.getReader(), resp);
                return;
            }
            // delete
            if (method.equals("POST") && path.equals("/delete")) {
                System.out.println(method + " " + path);
                controller.removeById(req.getReader(), resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}