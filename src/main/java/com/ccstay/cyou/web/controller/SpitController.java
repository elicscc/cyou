package com.ccstay.cyou.web.controller;
import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Channel;
import com.ccstay.cyou.pojo.Gathering;
import com.ccstay.cyou.pojo.Spit;
import com.ccstay.cyou.service.ChannelService;
import com.ccstay.cyou.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    @GetMapping(value = "/{spitId}")
    public Result finById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.finById(spitId));
    }


    @PutMapping(value = "/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.setId(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @PostMapping()
    public Result save( @RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "创建成功");
    }

    @DeleteMapping(value = "/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }
    @GetMapping(value = "/comment/{parentid}/{page}/{size}")
    public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData=spitService.findByParentid(parentid,page,size);
        return new Result(true, StatusCode.OK, "查询成功",new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }

    @PutMapping(value = "/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId) {
        String userid="111";
        if(null!=redisTemplate.opsForValue().get("thumbup_"+userid)){
    return new Result(false,StatusCode.ERROR,"不能重复点赞");
        }

        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid,1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }
    /**
     * 分页+多条件查询
     * @param searchMap 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result listPage(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
        Page<Spit> pageResponse = spitService.findGatheringListPage(searchMap, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Spit>(pageResponse.getTotalElements(), pageResponse.getContent()) );
    }

}
