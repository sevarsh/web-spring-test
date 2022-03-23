package com.example.demo.controllers;

import com.example.demo.models.Article;
import com.example.demo.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/blog/add")
    public String addArticle(Model model) {
        Iterable<Article> articles = articleRepo.findAll();
        model.addAttribute("articles", articles);
        return "add-article";
    }

    @PostMapping("/blog/add")
    public String addPostArticle(@RequestParam String headline, @RequestParam String annotation,
                                 @RequestParam String text, Model model) {
        Article article = new Article(headline, annotation, text);
        articleRepo.save(article);
        return "redirect:/blog";
    }
}
