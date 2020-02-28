package com.ccstay.cyou.dao;


import com.ccstay.cyou.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface AdminRepository extends JpaRepository<Admin,String>, JpaSpecificationExecutor<Admin> {
	Admin findByLoginname(String ss);
}
