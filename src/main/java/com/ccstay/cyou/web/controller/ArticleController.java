package com.ccstay.cyou.web.controller;


import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Article;

import com.ccstay.cyou.service.ArticleService;
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

@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	
	/**
	 * 增加
	 * @param article
	 */
	@PostMapping
	public Result add(@RequestBody Article article  ){
		articleService.saveArticle(article);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.updateArticle(article);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		articleService.deleteArticleById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findArticleList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findArticleById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findArticleList(searchMap));
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
		Page<Article> pageResponse = articleService.findArticleListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Article>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}


	/**
	 *根据文章id审核文章
	 * @param id
	 * @return
	 */
	@PutMapping("/examine/{id}")
	public Result examine(@PathVariable String id){
		articleService.updateArticleStateToExamine(id);
		return new Result(true,StatusCode.OK,"审核成功");
	}

	/**
	 *文章点赞
	 * @param id
	 * @return
	 */
	@PutMapping("/thumbup/{id}")
	public Result thumbup(@PathVariable String id){
		articleService.addThumbup(id);
		return new Result(true,StatusCode.OK,"点赞成功");
	}

//	@Autowired
//	private ArticleIKService articleIKService;
//
//
//	@GetMapping(value = "/{key}/{page}/{size}")
//	public Result findByKey(@PathVariable String key, @PathVariable int page, @PathVariable int size){
//		Page<ArticleIK> pageData=   articleIKService.findByKey(key,page,size);
//		return new Result(true, StatusCode.OK,"查询成功",new PageResult<ArticleIK>(pageData.getTotalElements(),pageData.getContent()));
//
//	}
}
