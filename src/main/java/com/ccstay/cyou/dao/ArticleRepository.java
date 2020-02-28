package com.ccstay.cyou.dao;


import com.ccstay.cyou.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleRepository extends JpaRepository<Article,String>, JpaSpecificationExecutor<Article> {
	//JPQL

    /**
     * 根据id修改状态
     * @param id
     */
    @Query(value = "UPDATE tb_article SET  state=1 WHERE id=?" ,nativeQuery = true)//默认是只读事务
    @Modifying
//增删改的时候加。SpringDataJPA不自动提供事务了-没事务了-手动事务
    void updateStateById(String id);

    /**
     * 文章点赞
     * @param id
     */
    @Query(value = "UPDATE tb_article SET  thumbup=thumbup+1 WHERE id=?" ,nativeQuery = true)//默认是只读事务
    @Modifying
//增删改的时候加。SpringDataJPA不自动提供事务了-没事务了-手动事务
    void addThumbup(String id);
}
