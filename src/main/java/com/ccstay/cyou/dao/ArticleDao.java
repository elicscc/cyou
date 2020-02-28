package com.ccstay.cyou.dao;


import com.ccstay.cyou.pojo.ArticleIK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<ArticleIK,String> {

    Page<ArticleIK> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
