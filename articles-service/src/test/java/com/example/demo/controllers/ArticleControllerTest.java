package com.example.demo.controllers;

import com.example.demo.dtos.ArticleDTO;
import com.example.demo.services.ArticleService;
import exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@ContextConfiguration(classes = {ArticleController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
class ArticleControllerTest {

    @MockBean
    private ArticleService articleService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllArticles() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(UUID.randomUUID());
        List<ArticleDTO> articles = Arrays.asList(articleDTO);

        when(articleService.getAllArticles()).thenReturn(articles);

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());

        verify(articleService, times(1)).getAllArticles();
    }

    @Test
    void testGetArticlesByUser() throws Exception {
        UUID userId = UUID.randomUUID();
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(UUID.randomUUID());
        List<ArticleDTO> articles = Arrays.asList(articleDTO);

        when(articleService.getArticlesByUser(userId)).thenReturn(articles);

        mockMvc.perform(get("/api/articles/submittedArticlesByUser/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());

        verify(articleService, times(1)).getArticlesByUser(userId);
    }

    @Test
    void testUpdateApprovalStatus() throws Exception {
        UUID articleId = UUID.randomUUID();
        String newStatus = "Approved";

        mockMvc.perform(put("/api/articles/updateApprovalStatus/{id}", articleId)
                        .param("newStatus", newStatus))
                .andExpect(status().isOk())
                .andExpect(content().string("Approval status updated successfully"));

        verify(articleService, times(1)).updateApprovalStatus(articleId, newStatus);
    }

    @Test
    void testUpdateComment() throws Exception {
        UUID articleId = UUID.randomUUID();
        String comment = "This is a comment";

        mockMvc.perform(put("/api/articles/updateComment/{id}", articleId)
                        .param("comment", comment))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment updated successfully"));

        verify(articleService, times(1)).updateComment(articleId, comment);
    }

    @Test
    void testSubmitArticle() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(UUID.randomUUID());

        mockMvc.perform(post("/api/articles/submitArticle")
                        .contentType("application/json")
                        .content("{\"id\":\"" + articleDTO.getId() + "\"}")) // Adjust JSON based on your DTO structure
                .andExpect(status().isOk())
                .andExpect(content().string("Article submitted successfully"));

        verify(articleService, times(1)).submitArticle(any(ArticleDTO.class));
    }
}
