package org.example.servlet;

import com.google.gson.Gson;
import org.example.controller.PostController;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static org.example.controller.PostController.APPLICATION_JSON;

public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        resp.setContentType("text/plain");
//        resp.getWriter().print("ok");
//        Post post = new Post(23, "pavel");
//        System.out.println(req.getRequestURI());
//        if (req.getMethod().equals("GET") && req.getRequestURI().equals("/")) {
//            resp.setContentType("text/html");
//            //resp.getWriter().print("ok");
//            req.getRequestDispatcher("index.html").forward(req, resp);
//        }
//        System.out.println(req.getRequestURI());
//        if (req.getMethod().equals("POST") && req.getRequestURI().equals("/create")) {
//            System.out.println("create" + req.getRequestURI());
//            req.getRequestDispatcher("form.jsp").forward(req, resp);
//        }
//
//        if (req.getMethod().equals("POST") && req.getRequestURI().equals("/create")) {
//            String name = req.getParameter("id");
//            String pass = req.getParameter("content");
//            //resp.getWriter().print("toGson");
//            req.getRequestDispatcher("form.jsp").forward(req, resp);
//            System.out.println(name + " " + pass);
//        } else if (req.getMethod().equals("GET") && req.getRequestURI().equals("/read")) {
//            Gson gson = new Gson();
//            resp.setContentType(APPLICATION_JSON);
//            resp.getWriter().print(gson.toJson(post));
//        }

        // если деплоились в root context, то достаточно этого


        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // get all
            System.out.println(method + " " + path);
            if (method.equals("GET") && path.equals("/")) {
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            if (method.equals("GET") && path.equals("/read")) {
                System.out.println(method + " " + path);
                controller.all(resp);
                return;
            }
            // update
            if (method.equals("POST") && path.equals("/update")) {
                System.out.println("update" + path);
                controller.getById(req.getReader().readLine(), resp);
                return;
            }
            // create
            if (method.equals("POST") && path.equals("/create")) {
                System.out.println(method + " " + path);
                controller.save(req.getReader().readLine(), resp);
                return;
            }
            // delete
            if (method.equals("POST") && path.equals("/delete")) {
                System.out.println(method + " " + path);
                controller.removeById(req.getReader().readLine(), resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}