package com.ccstay.cyou.service;


import com.ccstay.cyou.dao.LabelDao;
import com.ccstay.cyou.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ccstay.cyou.util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public void save(Label label) {
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label) {

        labelDao.save(label);
    }
    public void deleteById(String id) {

        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
       return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
              List<Predicate> list=new ArrayList<>();
               if (label.getLabelname()!=null&&!"".equals(label.getLabelname())) {
                   Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
               list.add(predicate);
               }
                if (label.getState()!=null&&!"".equals(label.getState())) {
                    Predicate predicate = criteriaBuilder.like(root.get("state").as(String.class), "%" + label.getState() + "%");
                    list.add(predicate);
                }
                Predicate[] pa=new Predicate[list.size()];
                pa=list.toArray(pa);
                return criteriaBuilder.and(pa);

            }
        });

    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                if (label.getLabelname()!=null&&!"".equals(label.getLabelname())) {
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState()!=null&&!"".equals(label.getState())) {
                    Predicate predicate = criteriaBuilder.like(root.get("state").as(String.class), "%" + label.getState() + "%");
                    list.add(predicate);
                }
                Predicate[] pa=new Predicate[list.size()];
                pa=list.toArray(pa);
                return criteriaBuilder.and(pa);

            }
        }, pageable);
    }
}
