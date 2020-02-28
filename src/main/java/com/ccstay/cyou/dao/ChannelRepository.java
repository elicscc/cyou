package com.ccstay.cyou.dao;


import com.ccstay.cyou.pojo.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ChannelRepository extends JpaRepository<Channel,String>, JpaSpecificationExecutor<Channel> {
	
}
