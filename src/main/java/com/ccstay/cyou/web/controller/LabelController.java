package com.ccstay.cyou.web.controller;

import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Channel;
import com.ccstay.cyou.pojo.Label;
import com.ccstay.cyou.service.ChannelService;
import com.ccstay.cyou.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequestMapping("/base/label")
@RestController
@CrossOrigin
public class LabelController {
    @Autowired
    private LabelService labelService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {


        return new Result(true, StatusCode.OK, "查询成功",labelService.findAll());
    }

    @RequestMapping(value = "/{labelld}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelld") String labelld) {
       // int i=1/0;
        return new Result(true, StatusCode.OK, "查询成功",labelService.findById(labelld));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{labelld}", method = RequestMethod.PUT)
    public Result update(@PathVariable("labelld") String labelld, @RequestBody Label label) {
        label.setId(labelld);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    @RequestMapping(value = "/{labelld}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("labelld") String labelld) {
        labelService.deleteById(labelld);
        return new Result(true, StatusCode.OK, "删除成功");
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label) {
      List<Label> list= labelService.findSearch(label);
        return new Result(true, StatusCode.OK, "查询成功",list);
    }
    /**
     * 组合条件分页查询列表
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result list(@RequestBody Label label, @PathVariable int page, @PathVariable int size){
        Page<Label> pageData = labelService.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }
}
