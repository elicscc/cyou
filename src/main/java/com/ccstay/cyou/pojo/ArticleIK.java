package com.ccstay.cyou.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Document(indexName = "tensquare_article", type = "article")
public class ArticleIK implements Serializable {
    @Id
    private String id;
    @Field(//是否生成索引,默认是true，如果是false，则保存的时候，不生成索引词条
            index = true,
            //存储文档的时候，生成索引词条所使用的分词器
            analyzer = "ik_max_word",
            //进行字段分词搜索的时候，使用的分词器
            searchAnalyzer = "ik_max_word")
    private String title;
    @Field(//是否生成索引,默认是true，如果是false，则保存的时候，不生成索引词条
            index = true,
            //存储文档的时候，生成索引词条所使用的分词器
            analyzer = "ik_max_word",
            //进行字段分词搜索的时候，使用的分词器
            searchAnalyzer = "ik_max_word")
    private String content;

    private String state;//审核状态
}
