package com.ccstay.cyou.dao;


import com.ccstay.cyou.pojo.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ColumnRepository extends JpaRepository<Column,String>, JpaSpecificationExecutor<Column> {
	
}
