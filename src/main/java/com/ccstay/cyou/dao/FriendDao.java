package com.ccstay.cyou.dao;

import com.ccstay.cyou.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    Friend findByUseridAndFriendid(String userid, String friendid);

@Query(value = "UPDATE  tb_friend SET islike=? WHERE userid=? AND friendid=?",nativeQuery = true)
    void updateIsLike(String islike, String userid, String friendid);
}
