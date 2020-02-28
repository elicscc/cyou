package com.ccstay.cyou.dao;

import com.ccstay.cyou.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitRepository extends JpaRepository<Recruit,String>, JpaSpecificationExecutor<Recruit> {
	//属性表达式

    /**
     * 根据状态，查询最新职位列表,按创建日期降序排序，并只查询前4条
     * @param state
     * @return
     */
    List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    /**
     * 根据状态，查询推荐职位列表,按创建日期降序排序，并只查询前4条
     * @param state
     * @return
     */
    List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
