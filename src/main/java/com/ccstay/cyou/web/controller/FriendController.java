package com.ccstay.cyou.web.controller;

import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Channel;
import com.ccstay.cyou.service.ChannelService;
import com.ccstay.cyou.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @PostMapping("/like/{friendid}/{type}")
    public Result addfriend(@PathVariable String friendid, @PathVariable String type) {

        Claims claim = (Claims) request.getAttribute("user_claims");
        if (claim == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        String userid = claim.getId();
        if (type != null ) {

            if ("1".equals(type)) {
                int flag = friendService.addFriend(userid, friendid);
                if (flag <= 0) {
                    return new Result(false, StatusCode.ERROR, "对方已经是您的好友，请不要重复添加！");
                }
                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }

            } else if ("2".equals(type)) {

            } else {

                return new Result(false, StatusCode.ERROR, "参数异常");

            }
        }
        return null;


    }
}
