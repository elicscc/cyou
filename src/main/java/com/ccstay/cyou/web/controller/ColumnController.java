package com.ccstay.cyou.web.controller;

import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Channel;
import com.ccstay.cyou.pojo.Column;
import com.ccstay.cyou.service.ChannelService;
import com.ccstay.cyou.service.ColumnService;
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

@RequestMapping("/column")
public class ColumnController {

	@Autowired
	private ColumnService columnService;
	
	
	/**
	 * 增加
	 * @param column
	 */
	@PostMapping
	public Result add(@RequestBody Column column  ){
		columnService.saveColumn(column);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param column
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Column column, @PathVariable String id ){
		column.setId(id);
		columnService.updateColumn(column);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		columnService.deleteColumnById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",columnService.findColumnList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",columnService.findColumnById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",columnService.findColumnList(searchMap));
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
		Page<Column> pageResponse = columnService.findColumnListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Column>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	
}
