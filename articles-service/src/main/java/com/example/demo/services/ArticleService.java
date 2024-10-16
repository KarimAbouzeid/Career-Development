package com.example.demo.services;

import com.example.demo.dtos.ArticleDTO;
import com.example.demo.dtos.ReceivedNotificationDTO;
import com.example.demo.entities.Article;
import com.example.demo.enums.ApprovalStatus;
import com.example.demo.enums.EntityType;
import com.example.demo.kafka.KafkaProducerService;
import com.example.demo.mappers.ArticleMapper;
import com.example.demo.repositories.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    private final UserService userService;

    private final KafkaProducerService kafkaProducerService;

    @Value("notification")
    private String approvalNotificationTopic;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper, UserService userService, KafkaProducerService kafkaProducerService) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.userService = userService;
        this.kafkaProducerService = kafkaProducerService;
    }

    public List<ArticleDTO> getAllArticles() {
        List<Article> submittedLearnings = articleRepository.findAll();
        return submittedLearnings.stream()
                .map(articleMapper::toArticleDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getArticlesByUser(UUID userId) {
        List<Article> submittedLearnings = articleRepository.findByAuthor(userId);
        return submittedLearnings.stream()
                .map(articleMapper::toArticleDTO)
                .collect(Collectors.toList());
    }

    public void updateApprovalStatus(UUID id, String newStatus) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + id));

        article.setApprovalStatus(ApprovalStatus.valueOf(newStatus));
        articleRepository.save(article);

        articleMapper.toArticleDTO(article);

        ReceivedNotificationDTO notification = new ReceivedNotificationDTO("Article status updated by manager.", new Date(), EntityType.Manager,article.getAuthor(),false);

        kafkaProducerService.sendNotification(approvalNotificationTopic,notification);

    }

    public void updateComment(UUID id, String comment) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + id));

        article.setComment(comment);
        articleRepository.save(article);

        articleMapper.toArticleDTO(article);

        ReceivedNotificationDTO notification = new ReceivedNotificationDTO("Article comment updated by manager.", new Date(), EntityType.Manager,article.getAuthor(),false);

        kafkaProducerService.sendNotification(approvalNotificationTopic,notification);

    }

    public ArticleDTO submitArticle(ArticleDTO articleDTO) {
        Article article = articleMapper.toArticle(articleDTO);
        article.setId(UUID.randomUUID());
        article.setSubmissionDate(new Date());
        article.setApprovalStatus(ApprovalStatus.Pending);

        Article savedArticle = articleRepository.save(article);

        UUID managerId = userService.getManager(article.getAuthor());
        ReceivedNotificationDTO notification = new ReceivedNotificationDTO("New article submitted by employee.", new Date(), EntityType.User,managerId,false);

        kafkaProducerService.sendNotification(approvalNotificationTopic,notification);

        return articleMapper.toArticleDTO(savedArticle);


    }

}
