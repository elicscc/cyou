package com.ccstay.cyou.web.controller;

import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Channel;
import com.ccstay.cyou.service.ChannelService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController

@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	
	/**
	 * 增加
	 * @param channel
	 */
	@PostMapping
	public Result add(@RequestBody Channel channel  ){
		channelService.saveChannel(channel);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param channel
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Channel channel, @PathVariable String id ){
		channel.setId(id);
		channelService.updateChannel(channel);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		channelService.deleteChannelById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",channelService.findChannelList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",channelService.findChannelById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",channelService.findChannelList(searchMap));
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
		Page<Channel> pageResponse = channelService.findChannelListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Channel>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	
}
