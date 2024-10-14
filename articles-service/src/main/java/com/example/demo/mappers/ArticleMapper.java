package com.example.demo.mappers;

import com.example.demo.dtos.ArticleDTO;
import com.example.demo.entities.Article;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleDTO toArticleDTO(Article article);

    Article toArticle(ArticleDTO articleDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArticleFromDTO(ArticleDTO articleDTO, @MappingTarget Article article);
}
