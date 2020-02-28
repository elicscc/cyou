//package com.ccstay.cyou.service;
//
//import com.ccstay.cyou.dao.ArticleDao;
//
//
//import com.ccstay.cyou.pojo.ArticleIK;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class ArticleIKService {
//   @Autowired
//    private ArticleDao articleDao;
////
////    //  @Autowired
////    // private IdWorker idWorker;
////    public void save(ArticleIK articleIK) {
////        //   article.setId(idWorker.nextId()+"");
////        articleDao.save(articleIK);
////
////    }
//
//    public Page<ArticleIK> findByKey(String key, int page, int size) {
//
//        Pageable pageable = PageRequest.of(page - 1, size);
//        return articleDao.findByTitleOrContentLike(key, key, pageable);
//    }
//}
