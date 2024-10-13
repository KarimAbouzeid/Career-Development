package com.example.demo.controllers;

import com.example.demo.dtos.ArticleDTO;
import com.example.demo.services.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // Get all articles
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Get articles by user
    @GetMapping("/submittedArticlesByUser/{userId}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByUser(@PathVariable UUID userId) {
        List<ArticleDTO> articles = articleService.getArticlesByUser(userId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Update approval status of an article
    @PutMapping("/updateApprovalStatus/{id}")
    public ResponseEntity<String> updateApprovalStatus(
            @PathVariable UUID id,
            @RequestParam String newStatus
    ) {
        articleService.updateApprovalStatus(id, newStatus);
        return new ResponseEntity<>("Approval status updated successfully", HttpStatus.OK);
    }

    // Update comment on an article
    @PutMapping("/updateComment/{id}")
    public ResponseEntity<String> updateComment(
            @PathVariable UUID id,
            @RequestParam String comment
    ) {
        articleService.updateComment(id, comment);
        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
    }

    @PostMapping("/submitArticle")
    public ResponseEntity<String> submitLearning(@RequestBody ArticleDTO articleDTO) {
        articleService.submitArticle(articleDTO);
        return new ResponseEntity<>("Article submitted successfully", HttpStatus.OK);
    }
}
