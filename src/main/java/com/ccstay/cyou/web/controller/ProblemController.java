package com.ccstay.cyou.web.controller;

import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;

import com.ccstay.cyou.pojo.Problem;

import com.ccstay.cyou.service.ProblemService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController

@RequestMapping("/problem")
public class ProblemController {
	//注入HttpServletRequest
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ProblemService problemService;


//	@RequestMapping(value = "/label/{labelld}", method = RequestMethod.GET)
//	public Result findByLabelid(@PathVariable String labelld){
//		return  baseClient.findById(labelld);
//	}
	
	/**
	 * 增加
	 * @param problem
	 */
	@PostMapping
	public Result add(@RequestBody Problem problem  ){
		//1.先鉴权，必须是普通用户角色才能发布问题
		Claims claims= (Claims) request.getAttribute("user_claims");
		if(null==claims){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
		}

		//补全发布问题的用户id
		problem.setUserid(claims.getId());
		problem.setNickname(claims.getSubject());
		problemService.saveProblem(problem);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.updateProblem(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		problemService.deleteProblemById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemList(searchMap));
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
		Page<Problem> pageResponse = problemService.findProblemListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}

	/**
	 * 根据标签ID和分页条件查询最新问题列表
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/newlist/{labelid}/{page}/{size}")
	public Result listPageNewReplyByLabelid(@PathVariable String labelid , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageDate = problemService.newlist(labelid,page,size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageDate.getTotalElements(), pageDate.getContent()) );
	}

	/**
	 * 根据标签ID和分页条件查询最热问题列表
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/hotlist/{labelid}/{page}/{size}")
	public Result listPageHotReplyByLabelid(@PathVariable String labelid , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageDate = problemService.hotlist(labelid,page,size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageDate.getTotalElements(), pageDate.getContent()) );
	}

	/**
	 * 根据标签ID和分页条件查询等待问题列表
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/waitlist/{labelid}/{page}/{size}")
	public Result listPageWaitReplyByLabelid(@PathVariable String labelid , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageDate = problemService.waitlist(labelid,page,size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageDate.getTotalElements(), pageDate.getContent()) );
	}

}
