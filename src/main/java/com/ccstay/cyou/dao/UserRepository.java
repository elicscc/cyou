package com.ccstay.cyou.dao;

import com.ccstay.cyou.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
	User findByMobile(String mobile);
}
