package com.ccstay.cyou.service;


import com.ccstay.cyou.dao.SpitDao;
import com.ccstay.cyou.pojo.Gathering;
import com.ccstay.cyou.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.ccstay.cyou.util.IdWorker;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit finById(String id) {
        return spitDao.findById(id).get();
    }



    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageable);
    }

    public void thumbup(String spitId) {
//        //效率低
//        Spit spit = spitDao.findById(spitId).get();
//        spit.setThumbup((spit.getThumbup()==null?0:spit.getThumbup())+1);
//        spitDao.save(spit);
        //使用原生mongo命令实现自增
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
        

    }

    /**
     * 发布吐槽（或吐槽评论）
     *
     * @param spit
     */
    public void save(Spit spit) {
        //主键
        spit.setId(idWorker.nextId() + "");
        //默认值
        spit.setPublishtime(new Date());//发布时间为当前时间
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //保存新的吐槽
        spitDao.save(spit);
        //判断是否存在上级id--处理对某个吐槽进行评论吐槽后，父吐槽的评论数+1
        if(!StringUtils.isEmpty(spit.getParentid())){
            //如果存在，则说明是在回复吐槽，则需要将被回复的吐槽的回复数+1
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("_id").is(spit.getParentid()))
                    ,new Update().inc("comment",1)
                    ,"spit");
        }
    }

    public Page<Spit> findGatheringListPage(Map searchMap, int page, int size) {
        //构建Spec查询条件
      //  Specification<Spit> specification = getGatheringSpecification(searchMap);
        //构建请求的分页对象
        PageRequest pageRequest =  PageRequest.of(page-1, size);
        return spitDao.findAll(pageRequest);
    }
    /**
     * 根据参数Map获取Spec条件对象
     * @param searchMap
     * @return
     */
    private Specification<Spit> getGatheringSpecification(Map searchMap) {

        return (Specification<Spit>) (root, query, cb) ->{
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // 编号
            if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
            }
            // 活动名称
            if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
            }
            // 大会简介
            if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
            }
            // 详细说明
            if (searchMap.get("detail")!=null && !"".equals(searchMap.get("detail"))) {
                predicateList.add(cb.like(root.get("detail").as(String.class), "%"+(String)searchMap.get("detail")+"%"));
            }
            // 主办方
            if (searchMap.get("sponsor")!=null && !"".equals(searchMap.get("sponsor"))) {
                predicateList.add(cb.like(root.get("sponsor").as(String.class), "%"+(String)searchMap.get("sponsor")+"%"));
            }
            // 活动图片
            if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
                predicateList.add(cb.like(root.get("image").as(String.class), "%"+(String)searchMap.get("image")+"%"));
            }
            // 举办地点
            if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
            }
            // 是否可见
            if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
            }
            // 城市
            if (searchMap.get("city")!=null && !"".equals(searchMap.get("city"))) {
                predicateList.add(cb.like(root.get("city").as(String.class), "%"+(String)searchMap.get("city")+"%"));
            }

            //最后组合为and关系并返回
            return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }
}
