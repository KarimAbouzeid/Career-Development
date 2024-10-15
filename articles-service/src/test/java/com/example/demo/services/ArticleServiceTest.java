package com.example.demo.services;

import com.example.demo.dtos.ArticleDTO;
import com.example.demo.entities.Article;
import com.example.demo.enums.ApprovalStatus;
import com.example.demo.mappers.ArticleMapper;
import com.example.demo.repositories.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    private ArticleRepository articleRepository;
    private ArticleMapper articleMapper;
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        articleRepository = mock(ArticleRepository.class);
        articleMapper = mock(ArticleMapper.class);
        articleService = new ArticleService(articleRepository, articleMapper);
    }

    @Test
    void getAllArticles_success_returnsAllArticles() {
        Article article1 = new Article();
        article1.setId(UUID.randomUUID());
        article1.setApprovalStatus(ApprovalStatus.Pending);
        Article article2 = new Article();
        article2.setId(UUID.randomUUID());
        article2.setApprovalStatus(ApprovalStatus.Approved);

        when(articleRepository.findAll()).thenReturn(Arrays.asList(article1, article2));
        when(articleMapper.toArticleDTO(any(Article.class))).thenReturn(new ArticleDTO());

        List<ArticleDTO> articles = articleService.getAllArticles();

        assertEquals(2, articles.size());
        verify(articleRepository, times(1)).findAll();
        verify(articleMapper, times(2)).toArticleDTO(any(Article.class));
    }

    @Test
    void testGetArticlesByUser() {
        UUID userId = UUID.randomUUID();
        Article article = new Article();
        article.setId(UUID.randomUUID());
        article.setApprovalStatus(ApprovalStatus.Pending);

        when(articleRepository.findByAuthor(userId)).thenReturn(Arrays.asList(article));
        when(articleMapper.toArticleDTO(any(Article.class))).thenReturn(new ArticleDTO());

        List<ArticleDTO> articles = articleService.getArticlesByUser(userId);

        assertEquals(1, articles.size());
        verify(articleRepository, times(1)).findByAuthor(userId);
        verify(articleMapper, times(1)).toArticleDTO(any(Article.class));
    }

    @Test
    void testUpdateApprovalStatus() {
        UUID articleId = UUID.randomUUID();
        Article article = new Article();
        article.setId(articleId);
        article.setApprovalStatus(ApprovalStatus.Pending);

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        articleService.updateApprovalStatus(articleId, "Approved");

        assertEquals(ApprovalStatus.Approved, article.getApprovalStatus());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void testUpdateApprovalStatusArticleNotFound() {
        UUID articleId = UUID.randomUUID();

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            articleService.updateApprovalStatus(articleId, "Approved");
        });

        assertEquals("Article not found with id " + articleId, exception.getMessage());
    }

    @Test
    void testUpdateComment() {
        UUID articleId = UUID.randomUUID();
        Article article = new Article();
        article.setId(articleId);
        article.setComment("Old Comment");

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        articleService.updateComment(articleId, "New Comment");

        assertEquals("New Comment", article.getComment());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void testUpdateComment_articleNotFound_throwsException() {
        UUID articleId = UUID.randomUUID();

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            articleService.updateComment(articleId, "New Comment");
        });

        assertEquals("Article not found with id " + articleId, exception.getMessage());
    }

    @Test
    void testSubmitArticle() {
        ArticleDTO articleDTO = new ArticleDTO();
        Article article = new Article();
        article.setId(UUID.randomUUID());
        article.setSubmissionDate(new Date());
        article.setApprovalStatus(ApprovalStatus.Pending);

        when(articleMapper.toArticle(articleDTO)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        when(articleMapper.toArticleDTO(article)).thenReturn(articleDTO);

        ArticleDTO submittedArticle = articleService.submitArticle(articleDTO);

        assertNotNull(submittedArticle);
        assertEquals(articleDTO, submittedArticle);
        verify(articleRepository, times(1)).save(any(Article.class));
    }
}
