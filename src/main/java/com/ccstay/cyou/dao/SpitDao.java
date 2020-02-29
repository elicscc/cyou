package com.ccstay.cyou.dao;

import com.ccstay.cyou.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Map;

public interface SpitDao extends MongoRepository<Spit,String>{
    Page<Spit> findByParentid(String parentid, Pageable pageable);
    //Page<Spit> findBy(Map map, Pageable pageable);
}
