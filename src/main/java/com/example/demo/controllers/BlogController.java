package com.example.demo.controllers;

import com.example.demo.models.Article;
import com.example.demo.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @Autowired
    private ArticleRepository articleRepo;

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Article> articles = articleRepo.findAll();
        model.addAttribute("articles", articles);
        return "blog";
    }

}
