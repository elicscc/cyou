package com.ccstay.cyou.web.controller;

import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Channel;
import com.ccstay.cyou.pojo.Gathering;
import com.ccstay.cyou.service.ChannelService;
import com.ccstay.cyou.service.GatheringService;
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

@RequestMapping("/gathering")
public class GatheringController {

	@Autowired
	private GatheringService gatheringService;
	
	
	/**
	 * 增加
	 * @param gathering
	 */
	@PostMapping
	public Result add(@RequestBody Gathering gathering  ){
		gatheringService.saveGathering(gathering);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param gathering
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Gathering gathering, @PathVariable String id ){
		gathering.setId(id);
		gatheringService.updateGathering(gathering);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		gatheringService.deleteGatheringById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping(("/{page}/{size}"))
	public Result list(@PathVariable int page, @PathVariable int size){

		Page<Gathering> pageResponse=gatheringService.findGatheringList(page,size);
		return new Result(true,StatusCode.OK,"查询成功",  new PageResult<Gathering>(pageResponse.getTotalElements(), pageResponse.getContent()));
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",gatheringService.findGatheringById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",gatheringService.findGatheringList(searchMap));
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
		Page<Gathering> pageResponse = gatheringService.findGatheringListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Gathering>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	
}
