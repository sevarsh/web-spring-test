package com.example.demo.controllers;

import com.example.demo.models.Article;
import com.example.demo.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/blog/{id}")
    public String readArticle(@PathVariable(value = "id") long id, Model model) {
        if(!articleRepo.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Article> article = articleRepo.findById(id);
        ArrayList<Article> res = new ArrayList<>();
        article.ifPresent(res::add);
        model.addAttribute("article", res);
        model.addAttribute("title", res.get(0).getHeadline());
        return "read-article";
    }

    @GetMapping("/blog/{id}/edit")
    public String editArticle(@PathVariable(value = "id") long id, Model model) {
        if(!articleRepo.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Article> article = articleRepo.findById(id);
        ArrayList<Article> res = new ArrayList<>();
        article.ifPresent(res::add);
        model.addAttribute("article", res);
        return "edit-article";
    }

    @PostMapping("/blog/{id}/edit")
    public String updatePostArticle(@PathVariable(value = "id") long id, @RequestParam String headline,
                                    @RequestParam String annotation, @RequestParam String text, Model model) {
        Article article = articleRepo.findById(id).orElseThrow();
        article.setHeadline(headline);
        article.setAnnotation(annotation);
        article.setText(text);
        articleRepo.save(article);
        return "redirect:/blog/{id}";
    }
}
