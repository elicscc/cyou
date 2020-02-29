package com.ccstay.cyou.web.controller;
import com.ccstay.cyou.entity.PageResult;
import com.ccstay.cyou.entity.Result;
import com.ccstay.cyou.entity.StatusCode;

import com.ccstay.cyou.pojo.User;

import com.ccstay.cyou.service.UserService;
import com.ccstay.cyou.util.BCryptPasswordEncoder;
import com.ccstay.cyou.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;
	
	
	/**
	 * 增加
	 * @param user
	 */
	@PostMapping
	public Result add(@RequestBody User user  ){
		userService.saveUser(user);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.updateUser(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		userService.deleteUserById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findUserList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findUserById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findUserList(searchMap));
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
		Page<User> pageResponse = userService.findUserListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}

	/**
	 * 发送短信验证码
	 * @param mobile
	 * @return
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendSmsCheckcode(@PathVariable String mobile){
		if (!userService.checkRegMb(mobile)){
			return new Result(false,StatusCode.ERROR,"该手机已经注册请登录！");
		}
		if (StringUtils.isEmpty(mobile)|| !userService.IsMobilePhone(mobile)){
			return new Result(false,StatusCode.ERROR,"手机号不正确");
		}
		userService.saveSmsCheckcode(mobile);
		return  new Result(true,StatusCode.OK,"发送成功");
	}
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	/**
	 * 用户注册
	 * @param user
	 */

	@PostMapping("/register/{checkcode}")
	public Result register(@RequestBody User user , @PathVariable String checkcode ){

		if (!userService.checkRegMb(user.getMobile())){
			return new Result(false,StatusCode.ERROR,"该手机已经注册请登录！");
		}
		if (StringUtils.isEmpty(user.getMobile())|| !userService.IsMobilePhone(user.getMobile())){
			return new Result(false,StatusCode.ERROR,"该手机号不正确");
		}

		String checkcodeRedis = redisTemplate.opsForValue().get("sms.checkcode" + user.getMobile());
		//判断
		//判断验证码是否为空
		if (checkcodeRedis==null||checkcodeRedis.isEmpty()) {
			return new Result(false,StatusCode.ERROR,"请先获取手机验证码");
		}
		if(!checkcodeRedis.equals((checkcode))){
			return new Result(false,StatusCode.ERROR,"验证码不正确");
		}
		if(user.getNickname()==null){
			return new Result(false,StatusCode.ERROR,"昵称不能为空");
		}
		if(user.getPassword()==null){
			return new Result(false,StatusCode.ERROR,"密码不能为空");
		}

		userService.addUser(user);
		return new Result(true,StatusCode.OK,"注册成功快去登录吧");
	}

	@PostMapping("/login")
	public Result login(@RequestBody User user){
		if(StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getPassword())){
			return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
		}
		user=userService.login(user.getMobile(),user.getPassword());
		if(null!=user){
			//登录成功
			//签发token
			String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
			//构建Map，用来封装token（最终转json）
			Map map=new HashMap();
			map.put("token",token);
			//用户昵称
			map.put("name",user.getNickname());
			//用户头像
			map.put("avatar",user.getAvatar());
			return new Result(true,StatusCode.OK,"登录成功",map);
		}else{
			//登录失败
			return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
		}
	}

}
