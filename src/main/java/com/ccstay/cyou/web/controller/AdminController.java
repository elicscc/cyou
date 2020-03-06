package com.ccstay.cyou.web.controller;



import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;
import com.ccstay.cyou.pojo.Admin;
import com.ccstay.cyou.service.AdminService;
import com.ccstay.cyou.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;


/**
 * 控制器层
 *
 * @author BoBoLaoShi
 */
@RestController

@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * admin登录
     *
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin admimLogin = adminService.login(admin);
        if (admimLogin == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }
        //JWT实现通话操作
        String token=jwtUtil.createJWT(admimLogin.getId(),admimLogin.getLoginname(),"admin");
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);

        map.put("role","admin");
        return new Result(true, StatusCode.OK, "登录成功",map);
    }

    /**
     * 增加
     *
     * @param admin
     */
    @PostMapping
    public Result add(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin
     */
    @PutMapping("/{id}")
    public Result edit(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.updateAdmin(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable String id) {
        adminService.deleteAdminById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result list() {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAdminList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result listById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAdminById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAdminList(searchMap));
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result listPage(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Admin> pageResponse = adminService.findAdminListPage(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Admin>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
